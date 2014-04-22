package jbrick.tests;

import jbrick.library.*;
import static org.junit.Assert.*;

import org.junit.*; 

import com.jbricx.swing.ui.MainWindow;

public class MenuFS {

	/**
	 * TC401 - New File Toolbar Icon
	 */
	@Test
	public void TC401() {
		// PRECONDITIONS - no files are in the previous load of JBricks
		StartupFunctions.clearTabs();
		
		// 1. User starts up JBrick
		// => JBrick opens with default configuration, there is one file opened "New File 1"
		// (No file has been opened before hand, this is the equivalent to opening the application for the first time.)
		
		MainWindow jbricks = StartupFunctions.newJBricksInstance("JBricks - TC401");
		assertTrue(FileFunctions.getFileName(jbricks).equals("New File 1"));
		
		// 2. User clicks on the "New File" toolbar icon
		// => A new tab in the code frame is generated, labeled "New File 2"
		TestUtils.getButton(jbricks, "New").doClick();
		assertTrue(FileFunctions.getCurrentFile(jbricks).equals("New File 2"));
	}
	
	/**
	 * TC402 - Open File Toolbar Icon
	 */
	public void TC402() {
		// PRECONDITIONS - no files are in the previous load of JBricks
		StartupFunctions.clearTabs();
		
		// 1. User starts up JBrick
		// => JBrick opens with default configuration, there is one file opened "New File 1"
		// (No file has been opened before hand, this is the equivalent to opening the application for the first time.)
		

		MainWindow jbricks = StartupFunctions.newJBricksInstance("JBricks - TC402");
		assertTrue(FileFunctions.getFileName(jbricks).equals("New File 1"));
		
		// 2. User clicks on the "Open File" toolbar icon
		// => A file explorer show available options for the new tab
		
		//TODO click the "open file" icon
		//TODO check that the open file explorer opens
		
		// 3. User navigates to desired file and opens it
		// => A new tab in the code frame is generated, with content from desired file
		
		//TODO click on one of the files in the explorer and select "open"
		//TODO check that the file was opened in the code frame

		assertTrue(false);
	}
	
	/**
	 * TC403 - Save File Toolbar Icon
	 */
	public void TC403() {
		
		// 1. User starts up JBrick
		// => JBrick opens with default configuration, there is one file opened "New File 1"
		// (No file has been opened before hand, this is the equivalent to opening the application for the first time.)
		
		//TODO open the application
		
		// 2. User clicks on the "Save" toolbar icon
		// => The file is saved and visible on the computer hard-drive
		// (For this test, it is not important where the file is saved, or what it is saved as.)
		
		//TODO click on the save icon
		//TODO check that the file was saved

		assertTrue(false);
	}
	
	/**
	 * TC404 - Save As File Toolbar Icon
	 */
	public void TC404() {
		
		// 1. User starts up JBrick
		// => JBrick opens with default configuration, there is one file opened "New File 1"
		// (No file has been opened before hand, this is the equivalent to opening the application for the first time.)
		
		//TODO open the application
		
		// 2. User clicks on the "Save As" toolbar icon
		// => A prompt appears on the screen, the file is saved and visible on the computer hard-drive
		// (For this test, it is not important where the file is saved, or what it is saved as.)
		
		//TODO click on the save as icon
		//TODO save the file with the default name / location
		//TODO check that the file was saved
		
		// 3. User writes text in the code pane and clicks 'Save As' from the Toolbar
		// => A prompt appears on the screen, the file is saved in the new location. The new text is found ONLY in the second file.
		
		//TODO click on the save as icon
		//TODO save the file in a different location
		//TODO check that the new file was saved
		//TODO check that the original file was not altered

		assertTrue(false);
	}
}
