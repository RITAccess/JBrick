package jbrick.tests;

import jbrick.library.FileFunctions;
import jbrick.library.StartupFunctions;

import org.junit.After;
import org.junit.Before;

public class SettingsFS {

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
	 * TC201 - Reset Default Font
	 */
	public void TC201() {
		
		// 1. User opens the JBrick application 
		// => The Code Frame has one file open in the code frame (center), a status pane (bottom), and a picture menu frame (top)
		// (No file has been opened before hand, this is the equivalent to opening the application for the first time.)
		
		//TODO open the application
		
		// 2. User opens preferences and changes the font options
		// => The Code Frame has a font that differs from the default font (Courier-plain-12)
		
		//TODO open the preferences window
		//TODO change the font
		//TODO select "apply" button and then "ok" button
		//TODO check that the font changed
		
		// 3. User opens preferences and sets font to default settings
		// => The Code Frame has a font that is the same as the default font 
		
		//TODO open the preferences window
		//TODO select the "reset to default" button
		//TODO select "apply" button and then "ok" button
		//TODO check that the font is the default font
		
	}
}
