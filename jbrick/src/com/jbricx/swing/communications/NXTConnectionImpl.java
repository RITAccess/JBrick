package com.jbricx.swing.communications;

import java.nio.ByteBuffer;

import com.jbricx.swing.communications.enums.ConnectionType;
import com.jbricx.swing.communications.enums.Sensor;
import com.jbricx.swing.communications.enums.SensorMode;
import com.jbricx.swing.communications.enums.SensorType;
import com.jbricx.swing.communications.exceptions.NXTNotFoundException;
import com.jbricx.swing.communications.exceptions.UnableToCreateNXTException;
import com.sun.jna.Native;
import com.sun.jna.Pointer;

/**
 * Implements the low-level methods for connecting to the Lego Brick.
 * 
 * I warn you that we have no idea how this stuff work. Somebody copied it here,
 * and wherever it came from, it sure lacks documentation. So far, I've seen
 * this hex values but I haven't figured out what they mean. It looks like for
 * every command you send to the brick, you must set a hex value so the brick
 * would know what to do.
 * 
 * 0x03 0x04 0x05 0x07 0x0A 0x0B Battery, apparently.
 * 
 * @author Spencer Herzberg
 * @author byktol
 */
public class NXTConnectionImpl implements NXTConnection {

	/**
	 * The Lego Fantom driver interface.
	 */
	private static Fantom fantom;
	/**
	 * The pointer to the active connection. If this pointer is being used, the
	 * only interaction allowed with the brick are those associated with the
	 * Fantom driver.
	 */
	private Pointer nxtPointer;
	private ConnectionType connectionType;
	private static boolean isDriverLoaded;

	static {
		try {
			fantom = (Fantom) Native.loadLibrary("fantom", Fantom.class);
			isDriverLoaded = true;
		} catch (final UnsatisfiedLinkError e) {
			// TODO: notify the user that the fantom driver is missing or
			// something
			isDriverLoaded = false;
		}
	}

	public NXTConnectionImpl(String name) throws NXTNotFoundException,
			UnableToCreateNXTException {
		if (isFantomDriverLoaded()) {
			this.nxtPointer = connect(name);
		}
	}

	protected static boolean isFantomDriverLoaded() {
		return isDriverLoaded;
	}

	public ConnectionType getConnectionType() {
		return connectionType;
	}

	public void startProgram(String filename) {
		Status status = new Status();
		byte[] filenameBytes = filename.getBytes();
		ByteBuffer command = ByteBuffer.allocate(filenameBytes.length + 1 + 1);
		command.put((byte) 0x00);
		command.put(filenameBytes);
		command.put((byte) 0x00);
		fantom.nFANTOM100_iNXT_sendDirectCommand(nxtPointer, true, command,
				command.capacity(), null, 0, status);
		System.out.println(status.getStatus().toString());
	}

	public void stopProgram() {
		ByteBuffer command = ByteBuffer.allocate(1);
		command.put((byte) 0x01);
		fantom.nFANTOM100_iNXT_sendDirectCommand(nxtPointer, true, command, 1,
				null, 0, new Status());
	}

	private Pointer connect(String name) throws NXTNotFoundException,
			UnableToCreateNXTException {
		connectionType = ConnectionType.valueOf(name);
		Status status = new Status();
		Pointer iNXTIterator = fantom.nFANTOM100_createNXTIterator(true, 5,
				status);
		try {
			while (!Status.Statuses.NO_MORE_ITEMS_FOUND.equals(status
					.getStatus())) {
				byte[] resourceName = FantomUtils.newResourceName();
				fantom.nFANTOM100_iNXTIterator_getName(iNXTIterator,
						resourceName, status);

				if (FantomUtils.asString(resourceName).contains(name)) {
					Pointer iNXT = fantom.nFANTOM100_iNXTIterator_getNXT(
							iNXTIterator, status);

					if (Status.Statuses.SUCCESS.equals(status.getStatus())) {
						return iNXT;
					} else {
						throw new UnableToCreateNXTException(
								" not able to create connection");
					}
				}
				fantom.nFANTOM100_iNXTIterator_advance(iNXTIterator, status);
			}
		} finally {
			if (iNXTIterator != null) {
				fantom.nFANTOM100_destroyNXTIterator(iNXTIterator, status);
			}
		}
		throw new NXTNotFoundException();
	}

	/**
	 * Disconnects the links to the connected brick.
	 */
	public void disconnect() {
		Status status = new Status();
		fantom.nFANTOM100_destroyNXT(nxtPointer, status);
	}

	// private Pointer directConnect()throws UnableToCreateNXTException
	// {
	// Status status= new Status();
	// Pointer iNXT =
	// fantom.nFANTOM100_createNXT("BTH::NXT::00:16:53:09:96:B4::3", status,
	// false);
	//
	// if (Status.Statuses.SUCCESS.equals(status.getStatus())) {
	// return iNXT;
	// } else {
	// throw new UnableToCreateNXTException( " not able to create connection" );
	// }
	// }
	public int getBattery() {
		Status status = new Status();
		ByteBuffer command = ByteBuffer.allocate(1);

		command.put((byte) 0x0B);// direct command get battery

		byte[] response = new byte[4]; // this 4 bytes are vital to get the
										// following readings right

		fantom.nFANTOM100_iNXT_sendDirectCommand(nxtPointer, true, command,
				command.capacity(), response, response.length, status);
		return response[0];
	}

	public boolean isConnected() {
		Status status = new Status();
		ByteBuffer command = ByteBuffer.allocate(1);

		command.put((byte) 0x0B);// direct command get battery

		byte[] response = new byte[4]; // this 4 bytes are vital to get the
										// following readings right

		fantom.nFANTOM100_iNXT_sendDirectCommand(nxtPointer, true, command,
				command.capacity(), response, response.length, status);

		boolean result;
		if (response[0] == 0) {
			result = false;
		} else {
			result = true;
		}
		return result;
	}

	public void playSound(int freq, int duration) {
		Status status = new Status();
		ByteBuffer command = ByteBuffer.allocate(6);

		command.put((byte) 0x03);// direct command play tone
		command.put((byte) freq);
		command.put((byte) (freq >> 8));
		command.put((byte) duration);
		command.put((byte) (duration >> 8));
		command.put((byte) 0x00);

		fantom.nFANTOM100_iNXT_sendDirectCommand(nxtPointer, false, command,
				command.capacity(), null, 0, status);
	}

	public void runMotor(byte motorName, int speed) {
		// Restrict Speed to 100
		if (speed > 100) {
			speed = 100;
		} else if (speed < -100) {
			speed = -100;
		}

		Status status = new Status();
		ByteBuffer command = ByteBuffer.allocate(12);

		command.put((byte) 0x04);// Motor Control, 4
		command.put(motorName);// Motor Number 0,1,2, 2
		command.put((byte) speed);// Speed, 75
		command.put((byte) 0x01);// Mode, 5 - Changed from 5 to 1 to de-reg
		command.put((byte) 0x00);// Reg mode, 1 - Changed from 1 to 0
		command.put((byte) 0x00);// Turn Ratio, 0
		command.put((byte) 0x20);// Run State, 32 - Changed from duration to be
									// 0x20
									// = running
		command.put((byte) 0x00);// Tacholimit = 0>> 8, 1
		command.put((byte) 0x00);// Tacholimit = 0>> 16, 1
		command.put((byte) 0x00);// Tacholimit = 0>> 24, 0
		command.put((byte) 0x00);// Tacholimit = 0>> 32, 0
		command.put((byte) 0x00);// Tacholimit = 0>> 40, 0

		try {
			fantom.nFANTOM100_iNXT_sendDirectCommand(nxtPointer, false,
					command, command.capacity(), null, 0, status);
		} catch (NullPointerException e) {
		}

	}

	public void stopMotor(byte motorName) {
		Status status = new Status();
		ByteBuffer command = ByteBuffer.allocate(12);

		command.put((byte) 0x04);// LSREAD
		command.put(motorName);// port
		command.put((byte) 0x00);// power -100 - 100
		command.put((byte) 0x00);// mode
		command.put((byte) 0x01);// regulation
		command.put((byte) 0x00);// turnratio
		command.put((byte) 0x00);// runstate
		command.put((byte) 0x00);// tacholimit = 0
		command.put((byte) 0x00);// tacholimit = 0
		command.put((byte) 0x00);// tacholimit = 0
		command.put((byte) 0x00);// tacholimit = 0
		command.put((byte) 0x00);// tacholimit = 0

		try {
			fantom.nFANTOM100_iNXT_sendDirectCommand(nxtPointer, false,
					command, command.capacity(), null, 0, status);
		} catch (NullPointerException e) {
		}
	}

	public void resetMotor(byte motorName) {
		Status status = new Status();
		ByteBuffer command = ByteBuffer.allocate(2);

		command.put((byte) 0x0A);// reset
		command.put(motorName);// port

		try {
			fantom.nFANTOM100_iNXT_sendDirectCommand(nxtPointer, false,
					command, command.capacity(), null, 0, status);
		} catch (NullPointerException e) {
		}
	}

	public void setSensorType(String name, SensorType type) {
		Sensor s = Sensor.valueOf(name);
		s.setType(type);

		resetSensor(s);
	}

	public void setSensorMode(String name, SensorMode mode) {
		Sensor s = Sensor.valueOf(name);
		s.setMode(mode);

		resetSensor(s);
	}

	public void resetSensor(Sensor s) {
		Status status = new Status();
		ByteBuffer command = ByteBuffer.allocate(4);

		command.put((byte) 0x05);// setinputmode //TODO what is this?
		command.put(s.getPort());// port
		command.put(s.getType());// sensortype LOWSPEED_9V - Changed to
										// switch
		command.put(s.getMode());// sensormode - Changed

		byte[] response = new byte[2];

		fantom.nFANTOM100_iNXT_sendDirectCommand(nxtPointer, true, command,
				command.capacity(), response, response.length, status);

		// TODO FIX ME
		// return response[0]; //status
	}

	public byte getRawSensorValue(String sensorName) {
		return getSensorValues(sensorName)[11];
	}

	public int getConvertedSensorData(String sensorName, byte sensorMode) {
		int finalVal;
		byte response[] = getSensorValues(sensorName);

		if (sensorMode != (byte) 0x20) {
			int buf[] = new int[2];
			buf[0] = unsignedByteToInt(response[9]);
			buf[1] = unsignedByteToInt(response[10]);
			finalVal = buf[0] + (buf[1] << 8);
		} else {
			finalVal = getBoolVal(sensorName);
		}
		return finalVal;
	}

	public int getBoolVal(String sensorName) {
		byte response[] = getSensorValues(sensorName);
		int returnVal = 0;
		if (response[11] == 1) {
			returnVal = 1;
		}
		return returnVal;

	}

	public byte[] getSensorValues(String sensorName) {
		Sensor s = Sensor.valueOf(sensorName);
		// used for the distance
		Status status = new Status();
		ByteBuffer command = ByteBuffer.allocate(2);

		// command.put((byte)0x00);//setinputmode// TODO not needed??
		command.put((byte) 0x07);// setinputmode// TODO Why 7?
		command.put(s.getPort());// port

		byte[] response = new byte[15];

		fantom.nFANTOM100_iNXT_sendDirectCommand(nxtPointer, true, command,
				command.capacity(), response, response.length, status);

		for (int i = 0; i < 15; i++) {
			System.out.print(response[i]);
			System.out.print("\t");
		}
		int buf[] = new int[2];
		int finalVal;
		buf[0] = unsignedByteToInt(response[9]);
		buf[1] = unsignedByteToInt(response[10]);
		finalVal = buf[0] + (buf[1] << 8);
		System.out.println();
		System.out.println(" buf0: " + buf[0] + "; buf1: " + buf[1]
				+ "; Final Value: " + finalVal);

		System.out.println();
		System.out.println(response[2] + " " + response[1]);
		return response; // number of bytes ready to read
	}

	public int unsignedByteToInt(byte b) {
		return b & 0xFF;
	}

	/*
	 * private Pointer directConnect()throws UnableToCreateNXTException { Status
	 * status= new Status(); Pointer iNXT =
	 * fantom.nFANTOM100_createNXT("BTH::5brickXT::00:16:53:01:BA:74::8",
	 * status, false );
	 * 
	 * if (Status.Statuses.SUCCESS.equals(status.getStatus())) { return iNXT; }
	 * else { throw new UnableToCreateNXTException(
	 * " not able to create connection" ); } }
	 */
	public static void main(String args[]) {

		System.out.println("----");
		try {
			NXTConnectionImpl nxt = new NXTConnectionImpl("USB");
			System.out.println("----");
			for (int i = 0; i < 10; i++) {
				System.out.println("i " + i);
				System.out.println(nxt.isConnected());

			}
			System.out.println("main done");
		} catch (NXTNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnableToCreateNXTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("Totally Done");
	}
}
