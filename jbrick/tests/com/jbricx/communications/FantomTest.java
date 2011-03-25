package com.jbricx.communications;

import com.jbricx.communications.enums.Sensor;
import com.jbricx.communications.enums.SensorMode;
import com.jbricx.communications.enums.SensorType;

public class FantomTest {

	/**
	 * @param args
	 */
	
	static NXTConnection nxt;
	
	
	public static void main(String[] args){
		try{
			System.out.println("Starting");
			NXTConnection nxt = new NXTConnectionImpl("USB");
//			nxt.resetMotor(Motor.MOTOR_A.getPort());
//			nxt.runMotor(Motor.MOTOR_A.getPort(),100);
//			System.out.println("Connected");
//			Thread.sleep(1000);
//			
//			nxt.stopMotor(Motor.MOTOR_A.getPort());
//			
//			System.out.println("DONE");
			
			String sensor = Sensor.SENSOR_1.getName();
			nxt.setSensorType(sensor, SensorType.SWITCH);
			nxt.setSensorMode(sensor, SensorMode.BOOLEAN);
			
			for (int i=0; i<10; i++){
				System.out.println(nxt.getRawSensorValue(Sensor.SENSOR_1.getName()));
				System.out.println("Sensor Mode: "+Sensor.SENSOR_1.getMode());
				System.out.println("Sensor Type: "+Sensor.SENSOR_1.getType());
				Thread.sleep(2000);
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	
	
}
