package com.jbricx.ui.joystick.wii;

import java.io.IOException;

import com.jbricx.communications.AbstractNXTBrick;
import com.jbricx.communications.NXT;
import com.jbricx.communications.NXTManager;
import com.jbricx.communications.NXT.ConnectionType;
import com.jbricx.communications.NXT.Motor;
import com.jbricx.communications.exceptions.AlreadyConnectedException;
import com.jbricx.communications.exceptions.NXTNotFoundException;
import com.jbricx.communications.exceptions.NotConnectedException;
import com.jbricx.communications.exceptions.UnableToCreateNXTException;
import com.jbricx.ui.findbrick.FindBrickFileIO;
/**
 * @author Mike Goldstein
 */
public class SerialTest implements WiiPacketEvent {

	/**
	 * @param args
	 */
	// static SerialDriver s;
	NunchuckConnection NCC;
	static AbstractNXTBrick nxt;
	private static final boolean debug=true;
private static void connectNXT(){
		
		String brickname = "brick2";
		try {
			nxt = NXTManager.connect(brickname, ConnectionType.BLUETOOTH);
			nxt.playTone(2000, 300);
			System.out.println("Joystick: Brick Connected!");
			nxt.playTone(3000, 300);
		} catch (AlreadyConnectedException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println("Joystick already Connected");
			try {
				nxt = NXTManager.getBrick(brickname);
				nxt.playTone(2000, 300);
				System.out.println("Joystick: Brick Connected!");
			} catch (NXTNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				System.out.println("Could not find brick");
			}
		} catch (NXTNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnableToCreateNXTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
static Motor Motor_1_ID = Motor.MOTOR_A;
static Motor Motor_2_ID = Motor.MOTOR_C;
static int Motor_1_DIR = 1;
static int Motor_2_DIR = 1;



	static short getUnsignedByte(byte b) {
		return (short) ((short) (b) & 0x00FF);

	}
	public static void main(String[] args) {
		/* test for getUnsignedByte();
		byte b = (byte) 0xFF;
		System.out.println("b as a byte: " + b + "; b as a uByte: "+ getUnsignedByte(b));
		*/
		// TODO Auto-generated method stub
		//new Thread(sendToLego).start();
		connectNXT();
		new SerialTest().run2();
		

		//while (true) {
			// System.out.println(s.readint());
			//try {
		//		Thread.sleep(5000);
		//	} catch (InterruptedException e) {
				// TODO Auto-generated catch block
		//		e.printStackTrace();
		//	}
	//		try {
				//request data from nunchuck
	//			//System.out.println("Sending ac");
	//			s.write((byte) 0xAC);
				
				//wait until data gets here
				//while(!s.availableBool()){
					//System.out.println(s.available()+ "; ");
					//try {
					//	Thread.sleep(100);
					//} catch (InterruptedException e) {
						// TODO Auto-generated catch block
					//	e.printStackTrace();
					//}
			//	}
			//	byte[] bb = s.getCurrent();
				//System.out.println("First Byte: " + getUnsignedByte(bb[0]) + 
							//	   "Second Byte: " + getUnsignedByte(bb[1]) +
							//	   "Third Byte: " + getUnsignedByte(bb[2]));
	//		} catch (IOException e1) {
				// TODO Auto-generated catch block
	//			e1.printStackTrace();
	//		} catch (NotConnectedException e1) {
				// TODO Auto-generated catch block
	//			e1.printStackTrace();
	//		}
	//		try {
	//			Thread.sleep(50);
	//		} catch (InterruptedException e) {
				// TODO Auto-generated catch block
	//			e.printStackTrace();
	//		}
	//	}
	}
	
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//test to get serial data from the driver
	public void run2() {
		NCC = new NunchuckConnection(this);

		while (true) {
			NCC.sendByte((byte) 0xAC);
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	// this method should be called when we get a new packet
	@Override
	public void onNewData(WiiPacket packet) {
		// TODO Auto-generated method stub
//		int speed=0;
//		if(packet.cState){
//			speed+=100;
//		}else if(packet.zState){
//			speed -=100;
//			
//		}
//		nxt.motorOn(Motor.MOTOR_A, speed);
//		nxt.motorOn(Motor.MOTOR_C, speed);
		
		int aSpeed=0, cSpeed =0;
		if(packet.yPos>=10){
			aSpeed=(int)( 50.0 + (double)(packet.yPos) *0.5);
			cSpeed=(int)( 50.0 + (double)(packet.yPos) *0.5);
		}
		else if (packet.yPos <= -10){
			aSpeed=((int)( - 50.0 + (double)(packet.yPos) * 0.5));
			cSpeed=((int)( - 50.0 + (double)(packet.yPos) * 0.5));
		}
		int aSpeedDiff;
		
		int cSpeedDiff;
		
		if(packet.xPos > 10){
			if(packet.yPos >= 10)
				cSpeed += (int)( (double)(packet.xPos) * 0.5);
			else if (packet.yPos <= -10)
				cSpeed -= (int)( (double)(packet.xPos) * 0.5);
			else
				cSpeed=packet.xPos;
		}else if (packet.xPos <= -10){
			if (packet.yPos <= -10)
				aSpeed += (int)( (double)(packet.xPos) * 0.5);
			else if (packet.yPos >= 10)
				aSpeed -= (int)( (double)(packet.xPos) * 0.5);
			else
				aSpeed=-packet.xPos;
		}
		
		
		nxt.motorOn(Motor.MOTOR_A, aSpeed);
		nxt.motorOn(Motor.MOTOR_C, cSpeed);
		if(debug)
			System.out.println("xPos: " + packet.xPos + "; \t yPos: " + packet.yPos
				+ "; \tcState: " + packet.cState + ";\t zState: " + packet.zState + ";\t MaSpeed: "+aSpeed+";\t McSpeed: " + cSpeed+";");
		
		if(packet.cState==true){
			nxt.playTone(1500, 500);
		}
		else if (packet.zState==true){
			nxt.playTone(1200, 500);
		}
		else{
			nxt.playTone(0, 0);
		}
		
	}
	
	
	
}
