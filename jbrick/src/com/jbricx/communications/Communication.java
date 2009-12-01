package com.jbricx.communications;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;



public class Communication extends ProcessRunner {
//	public static final int EXITSTATUS_OK = 0;
//	public static final int EXITSTATUS_ERROR = 1;
	
	private String errMsg;
	
	public ExitStatus download(String filename) {
		//nexttool /COM=USB0::0X0694::0X0002::0016530996B4::RAW -run=sample.rxe

		
		
		if(! new File(filename).exists() ){
			errMsg = "File does not exist.";
			return ExitStatus.Error;
		}
		
		
		if(  System.getProperty("os.name").contains("indow") ){
			String where = "usb";
			
			List<String> command = new ArrayList<String>();
			command.add("C:\\Program Files\\BricxCC\\nbc.exe");
			//command.add("-help");
//			command.add("-S");//+where);
//				command.add("usb");
				command.add("-d");
//				command.add("C:\\Users\\spencer\\sample.nxc");
			command.add(filename);
			
//				command.add("-O");
//				command.add("c:\\Users\\Spencer\\out");

			System.out.println(command.toString());

//				ProcessBuilder pb = new ProcessBuilder("\"C:/Program Files/BricxCC/nbc.exe\"", "-S", "usb", "-d", "\"C:/Users/spencer/sample.nxc\"");
			
//				String sample = "\"C:/Program Files/BricxCC/nbc.exe\" -S usb -d c:/Users/Spencer/sample.nxc";
//				System.out.println(sample);
//				pb = new ProcessBuilder("\"C:/Program Files/BricxCC/nbc.exe\" -S usb -d c:/Users/Spencer/sample.nxc");
			
			return run(command,"Downloading was a success!","Communication Error");
			
			
		}
	
		errMsg="You are not in Windows!";
		return ExitStatus.Error;
		
	}
	

	

	public void brickControl() {
	
	}
	
	public static void main(String args[]){

	}

}
