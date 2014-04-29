package jbrick.tests;

import jbrick.library.FileFunctions;
import jbrick.library.StartupFunctions;

import org.junit.After;
import org.junit.Before;

public class NotificationFS {

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
	 * TC501 - Code Error Notification
	 */
	public void TC501() {
		
		// 1. User starts up JBrick
		// => The Code Frame has only one tab opened, "New File 1"
		// (No file has been opened before hand, this is the equivalent to opening the application for the first time.)
		
		//TODO open the application
		
		// 2. User writes and compiles code that cannot compile
		// => The Status Frame show the line of code with the error and a hyperlink to line the error is contained
		
		// TODO Text is written to the code frame that has errors
		// TODO The compile icon is clicked
		// TODO Check that the status frame has the line number of the error 
		// TODO Check that the status frame has a hyperlink to the error line
		
		// 3. User hovers over the hyperlink
		// => The Code Frame jumps to the line number specified by hyperlink in the Status Frame
		
		// TODO hover over the hyperlink
		// TODO verify that the code frame jumps to the line number
		
	}
	
	/**
	 * TC502 - Audible Compile Notification
	 * Note, this test should not work yet since this feature is incomplete.
	 */
	public void TC502() {
		
		// 1. User starts up JBrick
		// => The Code Frame has only one tab opened, "New File 1"
		// (No file has been opened before hand, this is the equivalent to opening the application for the first time.)
		
		//TODO open the application
		
		// 2. User writes code that can be compiled and selects the compile button
		// => An audio notification is heard indicating that the file compiled correctly
		
		//TODO input correct code into the code frame
		//TODO select the compile icon
		//TODO check that the compile success sound plays
		
		// 3. User writes code that cannot be compiled and selects the compile button
		// => An audio notification is heard indicating that the file could not compile
		
		//TODO input incorrect code into the code frame
		//TODO select the compile icon
		//TODO check that the compile failure sound plays
		
	}
	
}
