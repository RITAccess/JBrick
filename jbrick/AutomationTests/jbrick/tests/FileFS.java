package jbrick.tests;

import com.jbricx.swing.ui.MainWindow;

import jbrick.library.*;
import static org.junit.Assert.*;
import org.junit.*; 

public class FileFS {
	
	/**
	 * TC001 - Default File on Startup
	 */
	@Test
	public void TC001() {
		
		// 1. User opens the JBrick application
		// => The Code Frame has only one tab opened, "New File 1"
		// (No file has been opened before hand, this is the equivalent to opening the application for the first time.)
		
		MainWindow jbricks = StartupFunctions.newJBricksInstance("JBricks - TC001");
		String fileName = FileFunctions.getFileName(jbricks);
		assertTrue(fileName.equals("New File 1"));
	}
	
	/**
	 * TC002 - Open a File
	 */
	public void TC002() {
		
		// 1. User opens the JBrick application
		// => The Code Frame has only one tab opened, "New File 1"
		// (No file has been opened before hand, this is the equivalent to opening the application for the first time.)
		
		StartupFunctions.newJBricksInstance("JBricks - TC001");
		//TODO check the file name
		
		// 2. User opens a file using the File>Open menu
		// => Open dialog appears
		
		//TODO select the open option in the file menu
		//TODO check that the open dialog appears
		
		// 3. User selects an NXC file
		// => "New File 1" is removed, the opened file is the only tab opened.
		
		//TODO select the NXC file
		//TODO read the file names, check that there is one opened, and it is the file name we chose
		assertTrue(false);
	}
	
	/**
	 * TC003 - Reopen File on Startup
	 */
	public void TC003() {
		
		// 1. User opens the JBrick application
		// => The Code Frame has only one tab opened, "New File 1"
		// (No file has been opened before hand, this is the equivalent to opening the application for the first time.)
				
		StartupFunctions.newJBricksInstance("JBricks - TC001");
		//TODO check the file name
		
		// 2. User opens a file using the File>Open menu
		// => Open dialog appears
				
		//TODO select the open option in the file menu
		//TODO select a file from the opened prompt
		
		// 3. User quits the JBrick application
		// => The application is closed with no errors
		// (Use the Quit menu option under file. NOT Close)
		
		//TODO select the file menu quit option
		
		// 4. User re-opens the JBrick application
		// => The file that was loaded in step 2 is the focused file, "New File 1" is not in the list of opened files
		
		StartupFunctions.newJBricksInstance("JBricks - TC001");
		//TODO check the file name
		
		assertTrue(false);
	}
	
	/**
	 * TC004 - Generate a New File
	 */
	public void TC004() {
		
		// 1. User Opens the JBrick application
		// => The Code Frame has only one tab opened, "New File 1"
		
		StartupFunctions.newJBricksInstance("JBricks - TC001");
		//TODO check the file name
		
		// 2. User creates a new file using File>New menu option
		// => A new tab is generated in the code frame, labeled "New File 2"
		
		//TODO select the new menu option button
		//TODO check the file names
		
		assertTrue(false);
	}
	
	/**
	 * TC005 - Save a file to computer
	 */
	public void TC005() {
		
		// 1. User Opens the JBrick application
		// => The Code Frame has only one tab opened, "New File 1"	
		
		StartupFunctions.newJBricksInstance("JBricks - TC001");
		//TODO check the file name
		
		// 2. User saves the file to the computer using the File>Save menu option
		// => The file is saved and visible on the computer hard-drive
		// (For this test, it is not important where the file is saved, or what it is saved as.)
		
		//TODO select the save option from the file menu
		//TODO check that the file is created (File.exists?)
		
		assertTrue(false);
	}
	
	/**
	 * TC006 - Save a file to computer
	 */
	public void TC006() {
		
		// 1. User Opens the JBrick application
		// => The Code Frame has only one tab opened, "New File 1"	
		
		StartupFunctions.newJBricksInstance("JBricks - TC001");
		//TODO check the file name
		
		// 2. User saves the file to the computer using the File>Save menu option
		// => A prompt appears on the screen, the file is saved and visible on the computer hard-drive
		// (For this test, it is not important where the file is saved, or what it is saved as.)
		
		//TODO select the save option from the file menu
		//TODO check that the file is created
		
		// 3. User writes text in the code pane and saves
		// => The files is updated on the computer with the new text
		// (A prompt should NOT reappear for this save)
		
		//TODO write in the code frame
		//TODO save the file again
		//TODO check that the file is updated on the computer
		
		assertTrue(false);
	}
	
	/**
	 * TC007 - Save as a different file to computer
	 */
	public void TC007() {
		
		// 1. User Opens the JBrick application
		// => The Code Frame has only one tab opened, "New File 1"
		
		StartupFunctions.newJBricksInstance("JBricks - TC001");
		//TODO check the file name
		
		// 2. User saves the file to the computer using the File>Save menu option
		// => A prompt appears on the screen, the file is saved and visible on the computer hard-drive
		// (For this test, it is not important where the file is saved, or what it is saved as.)
		
		//TODO select the save option from the file menu
		//TODO check that the file is created
		
		// 3. User writes text in the code pane and selects 'Save As' from the File menu
		// => A prompt appears on the screen, the file is saved in the new location.
		// (The new text is found ONLY in the second file.)
		
		//TODO write in the code frame
		//TODO select the save-as prompt in the file menu
		//TODO save to a new location
		//TODO check that the text is different from the first file and the new file
		
		assertTrue(false);
	}
	
	/**
	 * TC008 - Open a Deleted File
	 */
	public void TC008() {
		// 1. User opens the JBrick application
		// => The Code Frame has only one tab opened, "New File 1"	
		
		StartupFunctions.newJBricksInstance("JBricks - TC001");
		//TODO check the file name
		
		// 2. User saves the file to the computer using the File>Save menu option
		// => A prompt appears on the screen, the file is saved and visible on the computer hard-drive
		// (For this test, it is not important where the file is saved, or what it is saved as.)
		
		//TODO select the save option from the file menu
		//TODO check that the file is created
		
		// 3. User closes the JBrick application
		// => The application closes
		
		//TODO quit the JBrick application
		
		// 4. User Deletes/Renames/Moves the saved file
		// => The file is removed from the computer system
		
		//TODO delete the file on the computer
		//TODO check that the file is deleted
		
		// 5. User Reopens JBrick
		// => The default file, "New File 1" is opened
		
		StartupFunctions.newJBricksInstance("JBricks - TC001");
		//TODO check the file name
		
		assertTrue(false);
	}
}
