package com.jbricx.communications;

public class BrickCreator {

	
	public static NXTConnectionManager createBrick(){
	  NXTConnectionManager brick=null;
		
		if(  System.getProperty("os.name").contains("indow") ){
			//windows
			//brick = new WindowsNXTBrick();
		}
		else if(  System.getProperty("os.name").contains("inux") ){
			//linux
			
		}
		else{
			//mac?
		}
		
		return brick;
	}
	
}
