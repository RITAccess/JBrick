package com.jbricx.communications;

import java.util.List;

public abstract class AbstractNXTBrick {

	
	private String joinToString(List<String> lst){
		String str = "";
		for (String s: lst){
			if (s.split(" ").length > 1 ){
				str+="\""+s+"\"";
			}
			else{
				str+=s;
			}
			str+=" ";
		}
		
		return str;
	}
	public ExitStatus run(List<String> command){
		System.out.println(joinToString(command));
		return (new ProcessRunner()).run(command);
	}
	
	
	
	public abstract ExitStatus compile(String filename);
	
	public abstract ExitStatus getBatteryLevel();
	
	public abstract ExitStatus playTone(int frequency, int duration);
	
	public abstract ExitStatus playTone(int frequency, int duration, boolean loop);
	
	public abstract ExitStatus run(String filename);
	
	public abstract ExitStatus getRunningProgram();
	
	public abstract ExitStatus downloadFile(String filename);
	
	public abstract ExitStatus deleteFile(String filename);
	
}
