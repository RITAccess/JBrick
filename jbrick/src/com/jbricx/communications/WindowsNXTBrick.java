package com.jbricx.communications;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.views.AbstractView;

public class WindowsNXTBrick extends AbstractNXTBrick{

	
	private static final String COM = "/COM=usb";//USB0::0X0694::0X0002::0016530996B4::RAW";
	
	//these must be on the build path. we will want to have 
	// this in preferences eventually.
	private static String NBC="nbc.exe";
	private static String NEXTTOOL="nexttool.exe"; 
	
	
	@Override
	public ExitStatus compile(String filename) {
		System.out.println("Trying to compile: "+filename);
		List<String> command = new ArrayList<String>();
		command.add(NBC);
		//command.add("-help");
		command.add("-S");//+where);
		command.add("usb");
		command.add("-d");
//			command.add("C:\\Users\\spencer\\sample.nxc");
		command.add(filename);
		
//			command.add("-O");
//			command.add("c:\\Users\\Spencer\\out");
		return run(command);
	}
	
	@Override
	public ExitStatus deleteFile(String filename) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ExitStatus downloadFile(String filename) {
		List<String> command = new ArrayList<String>();
		command.add(NBC);
		//command.add("-help");
//		command.add("-S");//+where);
//			command.add("usb");
			command.add("-d");
//			command.add("C:\\Users\\spencer\\sample.nxc");
		command.add(filename);
		
		return run(command);
	}

	@Override
	public ExitStatus getBatteryLevel() {
		// TODO Auto-generated method stub
		List<String> command = new ArrayList<String>();
		command.add(NEXTTOOL);
		command.add(COM);
		command.add("-listfiles");
//		command.add(COM);
		return run(command);
	}

	@Override
	public ExitStatus getRunningProgram() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ExitStatus playTone(int frequency, int duration) {
		// TODO Auto-generated method stub
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

	

}
