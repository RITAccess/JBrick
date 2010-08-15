package com.jbricx.ui.joystick.wii;

/**
 * Data class...
 * 
 * @author Mike Goldstein
 * 
 */
public class WiiPacket {
	public int xPos;
	public int yPos;
	public boolean cState;
	public boolean zState;
	// future work - make these constants customizable - aka calibrate button on
	// gui to set them
	//private final static int idlePos = 130;
	//private final static int maxPos = 221;
	//private final static int minPos = 40;
	
	//CHANGE THESE VALUES IF YOU USE A DIFFERENT WII NUNCHUK!
	private static final byte offsetY=7;
	private static final byte offsetX=3;
	
public WiiPacket(int x, int y, byte buttons){
	this.xPos=translateJoy(x,offsetX);
	this.yPos=translateJoy(y, offsetY);
	if((buttons & 0x01) == 0){
		this.zState=true;
	}
	//and out all bytes but the second to last and shift over to the right by 1
	if(((buttons & 0x02) >>> 1) == 0){  
		this.cState=true;
	}
}

	public static int translateJoy(int wiiVal, byte offset){
		
		double value = wiiVal-30; //value should now be between 0-190
		
		value = Math.ceil(value*1.05)-100-offset; //now roughly between 0-200
		
		if (value < -100)
			value=-100;
		if (value >100)
			value = 100;
		//if (value <=4 && value >=-4)
		//	value = 0;
		return (int)(value);  //now between -100 to 100
		
		
		
	}
}
