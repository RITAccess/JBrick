package com.jbricx.communications;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class JCompiler extends ProcessRunner{
	public static final int EXITSTATUS_OK = 0;
	public static final int EXITSTATUS_ERROR = 1;
	
	public String errMsg;
	
	public ExitStatus compile(String filename) {
		errMsg="";
		Process p = null;
		
		
		if(! new File(filename).exists() ){
			errMsg = "File does not exist.";
			return ExitStatus.Error;
		}
		

		if(  System.getProperty("os.name").contains("indow") ){
			String where = "usb";
			
			List<String> command = new ArrayList<String>();
			command.add("C:\\Program Files\\BricxCC\\nbc.exe");
			//command.add("-help");
			command.add("-S");//+where);
			command.add("usb");
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
			
			return run(command,"Downloading was a success!","Download Error!");
		}
		
		errMsg="Your not in Windows!";
		return ExitStatus.Error;
				
				
	
	}

	
	
	public void debug() {
	
	}
	
	public static void main(String args[]){
		JCompiler c = new JCompiler();
		ExitStatus check = c.compile("C:\\Users\\spencer\\sample2.nxc");
		if (check==ExitStatus.Error){
			System.out.println("ERROR\n");
			System.out.println(c.getErrorMessage());
		}
		else{
			System.out.println("NO ERRORS");
		}
	}

}
