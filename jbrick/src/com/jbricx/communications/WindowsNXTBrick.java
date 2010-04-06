package com.jbricx.communications;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;


import com.jbricx.communications.NXT.ConnectionType;
import com.jbricx.communications.NXT.Motor;
import com.jbricx.communications.NXT.Sensor;
import com.jbricx.communications.NXT.SensorMode;
import com.jbricx.communications.NXT.SensorType;
import com.jbricx.communications.exceptions.NXTNotFoundException;
import com.jbricx.communications.exceptions.UnableToCreateNXTException;

public class WindowsNXTBrick extends AbstractNXTBrick{

	
	private static final String COM = "/COM=usb";//USB0::0X0694::0X0002::0016530996B4::RAW";
	
	//these must be on the build path. we will want to have 
	// this in preferences eventually.
	private static String NBC="lib/nbc.exe";
	private static String NEXTTOOL="lib/NeXTTool.exe";
	private static String BRICKTOOL = "BrickTool.exe";
	
	private static NXT nxt;
	
	
	@Override
	public ExitStatus compile(String filename) {
		System.out.println("Trying to compile: "+filename);
		List<String> command = new ArrayList<String>();
		command.add(NBC);
		//command.add("-help");
		command.add("-S");//+where);
		command.add("usb");
		command.add("-d");
		command.add(filename);
		
		return run(command);
	}
	
	@Override
	public ExitStatus deleteFile(String filename) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ExitStatus downloadFile(String filename) {
//		List<String> command = new ArrayList<String>();
//		command.add(NBC);
//		//command.add("-help");
////		command.add("-S");//+where);
////			command.add("usb");
//			command.add("-d");
////			command.add("C:\\Users\\spencer\\sample.nxc");
//		command.add(filename);
//		System.out.println("Command:"+command.toString());
//		return run(command);
		System.out.println("Downloading...");
		nxt.download(filename);
		return new ExitStatus(ExitStatus.ERROR,"Download Failed");
	}

	

	@Override
	public ExitStatus getRunningProgram() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ExitStatus playTone(int frequency, int duration) {
		System.out.println("play tone");
		nxt.playSound(frequency, duration);
		return null;
	}

	@Override
	public ExitStatus playTone(int frequency, int duration, boolean loop) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ExitStatus run(String filename) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void NXTConnect(ConnectionType type) throws NXTNotFoundException, UnableToCreateNXTException {
		nxt = new NXT(type.getName());
	}
	
	@Override
	public int getBatteryLevel() {
		// TODO Auto-generated method stub
//		List<String> command = new ArrayList<String>();
//		command.add(NEXTTOOL);
//		command.add("/COM=usb");
//		command.add("-listbricks");
//		return run(command);
		return nxt.getBattery();
	}
	
	@Override
	public boolean isConnected(){
		System.out.println(nxt.isConnected());
		return nxt.isConnected();
	}

	@Override
	public byte getRawSensorValue(String name) {
		return nxt.getRawSensorValue(name);
	}

	@Override
	public byte getRawSensorValue(Sensor sensor) {
		return nxt.getRawSensorValue(sensor.getName());
	}


	
	@Override
	public byte[] getSensorValues(String name) {
		return nxt.getSensorValues(name);
	}

	@Override
	public byte[] getSensorValues(Sensor sensor) {
		return nxt.getSensorValues(sensor.getName());
	}

	@Override
	public SensorMode[] listSensorModes() {
		return SensorMode.values();
	}

	@Override
	public SensorType[] listSensorTypes() {
		return SensorType.values();
	}

	@Override
	public Sensor[] listSensors() {
		return Sensor.values();
	}

	@Override
	public void motorOff(String motorName) {
		nxt.stopMotor(Motor.valueOf(motorName).getPort());
		
	}

	@Override
	public void motorOff(Motor motor) {
		nxt.stopMotor(motor.getPort());
		
	}

	@Override
	public void motorOn(String motorName, int speed) {
		nxt.runMotor(Motor.valueOf(motorName).getPort(), speed);
		
	}

	@Override
	public void motorOn(Motor motor, int speed) {
		nxt.runMotor(motor.getPort(), speed);
		
	}

	@Override
	public void motorReset(String motorName) {
		nxt.resetMotor(Motor.valueOf(motorName).getPort());
		
	}

	@Override
	public void motorReset(Motor motor) {
		nxt.resetMotor(motor.getPort());
		
	}

	@Override
	public void setSensorMode(String name, SensorMode mode) {
		nxt.setSensorMode(name, mode);
		
	}

	@Override
	public void setSensorMode(Sensor sensor, SensorMode mode) {
		nxt.setSensorMode(sensor.getName(), mode);
		
	}

	@Override
	public void setSensorType(String name, SensorType type) {
		nxt.setSensorType(name, type);
		
	}

	@Override
	public void setSensorType(Sensor sensor, SensorType type) {
		nxt.setSensorType(sensor.getName(), type);
		
	}

	
//	private int getConvertedSensorData(String name, byte mode) {
//		return nxt.getConvertedSensorData(name);
//	}

	@Override
	public int getConvertedSensorData(Sensor name) {
		return nxt.getConvertedSensorData(name.getName(), name.getMode());
	}

	

}
