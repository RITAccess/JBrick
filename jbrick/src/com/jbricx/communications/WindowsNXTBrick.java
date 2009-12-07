package com.jbricx.communications;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.views.AbstractView;

public class WindowsNXTBrick extends AbstractNXTBrick{

	@Override
	public ExitStatus compile(String filename) {
		System.out.println("Trying to compile: "+filename);
		List<String> command = new ArrayList<String>();
		command.add("C:\\Program Files\\BricxCC\\nbc.exe");
		//command.add("-help");
		command.add("-S");//+where);
		command.add("usb");
		command.add("-d");
//			command.add("C:\\Users\\spencer\\sample.nxc");
		command.add(filename);
		
//			command.add("-O");
//			command.add("c:\\Users\\Spencer\\out");
		System.out.println(command);
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
		command.add("C:\\Program Files\\BricxCC\\nbc.exe");
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
		return null;
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
