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
		
		// PRECONDITIONS - no files are in the previous load of JBricks
		StartupFunctions.clearTabs();
		
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
	@Test
	public void TC002() {
		
		// PRECONDITIONS - no files are in the previous load of JBricks
		StartupFunctions.clearTabs();
		
		// 1. User opens the JBrick application
		// => The Code Frame has only one tab opened, "New File 1"
		// (No file has been opened before hand, this is the equivalent to opening the application for the first time.)
		
		MainWindow jbricks = StartupFunctions.newJBricksInstance("JBricks - TC002");
		String fileName = FileFunctions.getFileName(jbricks);
		assertTrue(fileName.equals("New File 1"));
		
		// 2. User opens a file using the File>Open menu
		// => Open dialog appears
		
		// NB: We don't test the open dialog directly, only the action to open a file
		
		String filePath = "MyFirstNxcProgram.nxc";
		FileFunctions.createFile(filePath);
		FileFunctions.openFile(jbricks, filePath);
		
		// 3. User selects an NXC file
		// => the selected file opens in a new tab (it is also the current tab)
		
		// NB: delete file
		
		FileFunctions.deleteFile(filePath);
		assertTrue(FileFunctions.getCurrentFile(jbricks).equals(filePath));
	}
	
	/**
	 * TC003 - Reopen File on Startup
	 */
	@Test
	public void TC003() {
		
		// PRECONDITIONS - no files are in the previous load of JBricks
		StartupFunctions.clearTabs();
		
		// 1. User opens the JBrick application
		// => The Code Frame has only one tab opened, "New File 1"
		
		MainWindow jbricks = StartupFunctions.newJBricksInstance("JBricks - TC003 a");
		String fileName = FileFunctions.getFileName(jbricks);
		assertTrue(fileName.equals("New File 1"));
		
		// 2. User opens a file using the File>Open menu
		// => Open dialog appears
				
		String filePath = "MyFirstNxcProgram.nxc";
		FileFunctions.createFile(filePath);
		FileFunctions.openFile(jbricks, filePath);
		
		// 3. User quits the JBrick application
		// => The application is closed with no errors
		// (Use the Quit menu option under file. NOT Close)
		
		TestUtils.getButton(jbricks, "Quit").doClick();
		
		// 4. User re-opens the JBrick application
		// => The file that was loaded in step 2 is the focused file, "New File 1" is not in the list of opened files
		
		StartupFunctions.newJBricksInstance("JBricks - TC003 b");
		FileFunctions.deleteFile(filePath);
		assertTrue(FileFunctions.getCurrentFile(jbricks).equals(filePath));
	}
	
	/**
	 * TC004 - Generate a New File
	 */
	@Test
	public void TC004() {
		
		// PRECONDITIONS - no files are in the previous load of JBricks
		StartupFunctions.clearTabs();
		
		// 1. User Opens the JBrick application
		// => The Code Frame has only one tab opened, "New File 1"
		
		MainWindow jbricks = StartupFunctions.newJBricksInstance("JBricks - TC004");
		String fileName = FileFunctions.getFileName(jbricks);
		assertTrue(fileName.equals("New File 1"));
		
		// 2. User creates a new file using File>New menu option
		// => A new tab is generated in the code frame, labeled "New File 2"
		
		MenuFunctions.newFile(jbricks);
		int tabCount = FileFunctions.getTabCount(jbricks);
		assertTrue(tabCount == 2);
		fileName = FileFunctions.getCurrentFile(jbricks);
		assertTrue(fileName.equals("New File 2"));
	}
	
	/**
	 * TC005 - Save a file to computer
	 */
	public void TC005() {
		
		// 1. User Opens the JBrick application
		// => The Code Frame has only one tab opened, "New File 1"	
		
		StartupFunctions.newJBricksInstance("JBricks - TC005");
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
		
		StartupFunctions.newJBricksInstance("JBricks - TC006");
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
		
		StartupFunctions.newJBricksInstance("JBricks - TC007");
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
	@Test
	public void TC008() {
		
		// PRECONDITIONS - no files are in the previous load of JBricks
		StartupFunctions.clearTabs();
		
		// 1. User opens the JBrick application
		// => The Code Frame has only one tab opened, "New File 1"	
		
		MainWindow jbricks = StartupFunctions.newJBricksInstance("JBricks - TC008");
		String fileName = FileFunctions.getFileName(jbricks);
		assertTrue(fileName.equals("New File 1"));
		
		// 2. User saves the file to the computer using the File>Save menu option
		// => A prompt appears on the screen, the file is saved and visible on the computer hard-drive
		// (For this test, it is not important where the file is saved, or what it is saved as.)
		
		String newFileName = "Saved_File.nxc";
		
		FileFunctions.saveFile(jbricks, newFileName);
		assertTrue(FileFunctions.fileExists(newFileName));
		
		// 3. User closes the JBrick application
		// => The application closes
		
		TestUtils.getButton(jbricks, "Quit").doClick();
		
		// 4. User Deletes/Renames/Moves the saved file
		// => The file is removed from the computer system
		
		FileFunctions.deleteFile(newFileName);
		assertTrue(!FileFunctions.fileExists(newFileName));
		
		// 5. User Reopens JBrick
		// => The default file, "New File 1" is opened
		
		StartupFunctions.newJBricksInstance("JBricks - TC001");
		fileName = FileFunctions.getFileName(jbricks);
		assertTrue(fileName.equals("New File 1"));
	}
}
