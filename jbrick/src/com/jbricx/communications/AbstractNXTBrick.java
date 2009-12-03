package com.jbricx.communications;

import java.util.Enumeration;

abstract class AbstractNXTBrick {

	private String comm;
	
	public void setComm(String com){
		this.comm=com;
	}
	
	abstract ExitStatus getBatteryLevel();
	
	abstract ExitStatus playTone(int frequency, int duration);
	
	abstract ExitStatus playTone(int frequency, int duration, boolean loop);
	
	abstract ExitStatus run(String filename);
	
	abstract ExitStatus getRunningProgram();
	
	abstract ExitStatus downloadFile(String filename);
	
	abstract ExitStatus deleteFile(String filename);
	
}
