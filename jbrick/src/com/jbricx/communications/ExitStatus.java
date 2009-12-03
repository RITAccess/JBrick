package com.jbricx.communications;

public class ExitStatus {

	public static int OK = 0;
	public static int ERROR = 1;
	
	private String _message;
	private int _status;
	
	public ExitStatus(int status, String msg){
		this._status = status;
		this._message = msg;
	}
	
	public boolean isOk(){
		return this._status == OK;
	}
	
	public String getMesage(){
		return this._message;
	}
	
}
