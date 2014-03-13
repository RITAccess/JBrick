package jbrick.tests;

public class NXTFS {

	/**
	 * TC601 - Compile
	 */
	public static boolean TC601() {
		
		// 1. User starts up JBrick
		// => The Code Frame has only one tab opened, "New File 1"
		// (No file has been opened before hand, this is the equivalent to opening the application for the first time.)
		
		//TODO open the application
		
		// 2. User writes and compiles code that can compile
		// => Jbrick compiles the code and the Status Frame shows no errors
		
		//TODO input valid code into the code frame
		//TODO click the compile icon
		//TODO check that the code compiled and the status frame is error free
		
	}
	
	/**
	 * TC602 - Download
	 */
	public static boolean TC602() {
		
		// 1. User starts up JBrick
		// => The Code Frame has only one tab opened, "New File 1"
		// (No file has been opened before hand, this is the equivalent to opening the application for the first time.)
		
		//TODO open the application
		
		// 2. User writes and compiles code that can compile
		// => Jbrick compiles the code and the Status Frame shows no errors
		
		//TODO input valid code into the code frame
		//TODO click the compile icon
		//TODO check that the code compiled and the status frame is error free
		
		// 3. User downloads code onto robot
		// => No error occurs
		
		//TODO click the download icon
		//TODO check that no error message appears
		
		return false;
	}
}
