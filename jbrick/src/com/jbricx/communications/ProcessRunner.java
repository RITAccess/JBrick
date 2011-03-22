package com.jbricx.communications;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ProcessRunner {

	// public ExitStatus run(List<String> cmd) {
	public ExitStatus run(List<String> cmd) {
		download();
		return new ExitStatus(
				ExitStatus.OK,
				"If you got this error something terrible happened. Good luck finding out what happend. Let the force be with you.");
	}
	
	public static void download(){
		List<String> command = new ArrayList<String>();
//		command.add("D:\\workspace\\jbrick\\lib\\nbc.exe");
//		command.add("-S=usb");
//		command.add("-d");
//		command.add("D:/Desktop/nxc/Untitled1.nxc");

		Process p;
		ProcessBuilder pb = new ProcessBuilder(
		        "D:\\workspace\\jbrick\\lib\\nbc.exe"
            ,"-S=usb"
            ,"-d"
            ,"D:\\Desktop\\nxc\\hola.nxc");

		try {
			p = pb.start();

			InputStream is = p.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);

			InputStream eis = p.getErrorStream();
			InputStreamReader eisr = new InputStreamReader(eis);
			BufferedReader ebr = new BufferedReader(eisr);

			String line = "";

			while ((line = br.readLine()) != null) {
				System.out.println(line);
			}

			while ((line = ebr.readLine()) != null) {
				System.out.println(line);
			}
		} catch (Exception e) {
			System.out.println("ProcessRunner.main()" + e.getMessage());
		}
	}

	public static void main(String[] args) {
		download();
	}
}
