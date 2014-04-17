package jbrick.tests;

import java.awt.Component;

import com.jbricx.swing.ui.MainWindow;

import jbrick.library.*;
import static org.junit.Assert.*;

import org.junit.*;

public class ContainerFS {

	/**
	 * TC101 - Default Panes on Startup
	 */
	@Test
	public void TC101() {
		
		// 1. User opens the JBrick application
		// => The Code Frame has one file open in the code frame (center) and a status pane (bottom)
		// (No file has been opened before hand, this is the equivalent to opening the application for the first time.)
		
		MainWindow jbricks = StartupFunctions.newJBricksInstance("JBricks - TC101");
		Component editorPane = ContainerFunctions.getEditorPane(jbricks);
		Component statusPane = ContainerFunctions.getStatusPane(jbricks);
		
		assertTrue(editorPane.getLocationOnScreen().getY() < statusPane.getLocationOnScreen().getY());
		
	}
	
	/**
	 * TC102 - Reset Main Window View
	 */
	public void TC102() {
		
		// 1. User opens the JBrick application 
		// => The Code Frame has one file open in the code frame (center) and a status pane (bottom)
		// (No file has been opened before hand, this is the equivalent to opening the application for the first time.)
		
		MainWindow jbricks = StartupFunctions.newJBricksInstance("JBricks - TC102");
		Component editorPane = ContainerFunctions.getEditorPane(jbricks);
		Component statusPane = ContainerFunctions.getStatusPane(jbricks);
		
		assertTrue(editorPane.getLocationOnScreen().getY() < statusPane.getLocationOnScreen().getY()); 
		
		// 2. User rearranges frame sizes
		// => The size of the code frame and status frame are different than their defaults
		// (These may be changed with the 'maximize *' options in the view menu)
		
		int defaultHeight = editorPane.getHeight();
		
		MenuFunctions.maximizeEditorPane(jbricks);
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		editorPane = ContainerFunctions.getEditorPane(jbricks);
		
		int newHeight = editorPane.getHeight();
		
		System.out.println(defaultHeight);
		System.out.println(newHeight);
		assertTrue(defaultHeight < newHeight);
		
		// 3. User selects "Reset View" in view menu
		// => The size of the frames return to their default size.
		
		MenuFunctions.resetView(jbricks);
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		editorPane = ContainerFunctions.getEditorPane(jbricks);
		
		newHeight = editorPane.getHeight();
		
		System.out.println(defaultHeight);
		System.out.println(newHeight);
		assertTrue(defaultHeight == newHeight);
		
	}
	
	/**
	 * TC103 - Open the Expander View
	 */
	public void TC103() {
		
		// 1. User opens the JBrick application 
		// => The Code Frame has one file open in the code frame (center) and a status pane (bottom)
		// (No file has been opened before hand, this is the equivalent to opening the application for the first time.)
		
		//TODO open the application
		//TODO check the frame positions
		
		// 2. User selects the "Show/Hide File Viewer"
		// => The file viewer is opened on the left of the code and status frame. The viewer shows the files and folders in the workspace directory set in the preferences.
		
		//TODO select the file viewer menu option
		//TODO check that the frame is opened and has a width value
		
	}
	
}
