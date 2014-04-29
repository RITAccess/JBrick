package jbrick.tests;

import jbrick.library.FileFunctions;
import jbrick.library.StartupFunctions;

import org.junit.After;
import org.junit.Before;

public class NXTFS {

	@Before
	public void cleanStart(){
		// PRECONDITIONS - no files are in the previous load of JBricks
		StartupFunctions.clearTabs();
	}
	
	@After
	public void cleanEnd(){
		while (FileFunctions.files.size() > 0){
			FileFunctions.deleteFile(FileFunctions.files.get(0));
		}
	}
	
	/**
	 * TC601 - Compile
	 */
	public void TC601() {
		
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
	public void TC602() {
		
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
		
	}
}
