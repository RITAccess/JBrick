package com.jbricx.communications;

public class ExitStatus {

	public static int OK = 0;
	public static int ERROR = 1;
	
	private String message;
	private int status;
	
	public ExitStatus(int status, String msg){
		this.status = status;
		this.message = msg;
	}
	
	public boolean isOk(){
		return this.status == OK;
	}
	
	public String getMesage(){
		return this.message;
	}
	
	public String toString(){
		String str = "";
		str+="[Exit Status]\n";
		if (this.status==OK){
			str+="Status: OK\n";
		}
		else{
			str+="Status: ERROR\n";
		}
		
		str+="Message: "+this.message;
		
		return str;
	}
}
