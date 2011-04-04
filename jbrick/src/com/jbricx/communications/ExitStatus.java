package com.jbricx.communications;

import java.util.List;

public class ExitStatus {

	public static int OK = 0;
	public static int ERROR = 1;
	
	private int status;
	private List<CompilerError> compilerErrors;
	
	public ExitStatus(int status, final List<CompilerError> list) {
	  this.status = status;
	  this.compilerErrors = list;
  }

  public boolean isOk(){
		return this.status == OK;
	}
	
	public List<CompilerError> getCompilerErrors() {
    return compilerErrors;
  }
}
