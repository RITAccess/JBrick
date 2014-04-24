package jbrick.tests;

import java.awt.Component;
import java.util.concurrent.Callable;

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
	@Test
	public void TC102() {
		
		// 1. User opens the JBrick application 
		// => The Code Frame has one file open in the code frame (center) and a status pane (bottom)
		// (No file has been opened before hand, this is the equivalent to opening the application for the first time.)
		
		final MainWindow jbricks = StartupFunctions.newJBricksInstance("JBricks - TC102");
		final Component editorPane = ContainerFunctions.getEditorPane(jbricks);
		final Component statusPane = ContainerFunctions.getStatusPane(jbricks);
		
		assertTrue(editorPane.getLocationOnScreen().getY() < statusPane.getLocationOnScreen().getY()); 
		
		// 2. User rearranges frame sizes
		// => The size of the code frame and status frame are different than their defaults
		// (These may be changed with the 'maximize *' options in the view menu)
		
		final int defaultHeight = editorPane.getHeight();
		
		MenuFunctions.maximizeEditorPane(jbricks);
		TestUtils.waitUntil(5000, new Callable<Object>() {
			@Override
			public Object call() throws Exception {
				return (editorPane.getHeight() != defaultHeight);
			}
		});
		final int newHeight = editorPane.getHeight();
		
		assertTrue(defaultHeight < newHeight);
		
		// 3. User selects "Reset View" in view menu
		// => The size of the frames return to their default size.
		
		MenuFunctions.resetView(jbricks);
		TestUtils.waitUntil(5000, new Callable<Object>() {
			@Override
			public Object call() throws Exception {
				return (editorPane.getHeight() != newHeight);
			}
		});
		int resetHeight = editorPane.getHeight();
		
		
		// The default Height may differ slightly from the resetHeight
		assertTrue(Math.abs(defaultHeight - resetHeight) < 10);
		
	}
	
	/**
	 * TC103 - Open the Expander View
	 */
	@Test
	public void TC103() {
		
		// 1. User opens the JBrick application 
		// => The Code Frame has one file open in the code frame (center) and a status pane (bottom)
		// (No file has been opened before hand, this is the equivalent to opening the application for the first time.)
		
		final MainWindow jbricks = StartupFunctions.newJBricksInstance("JBricks - TC102");
		final Component editorPane = ContainerFunctions.getEditorPane(jbricks);
		final Component statusPane = ContainerFunctions.getStatusPane(jbricks);
		
		assertTrue(editorPane.getLocationOnScreen().getY() < statusPane.getLocationOnScreen().getY()); 
		
		// 2. User selects the "Show File Viewer"
		// => The file viewer is opened on the left of the code and status frame. The viewer shows the files and folders in the workspace directory set in the preferences.
		
		MenuFunctions.showFilePane(jbricks);
		TestUtils.waitUntil(5000, new Callable<Object>() {
			@Override
			public Object call() throws Exception {
				return (ContainerFunctions.getFilePane(jbricks) != null);
			}
		});
		final Component filePane = ContainerFunctions.getFilePane(jbricks);

		double filePaneX = filePane.getLocationOnScreen().getX();
		double editorPaneX = editorPane.getLocationOnScreen().getX();
		assertTrue(filePaneX < editorPaneX);
		
		// Any further testing, including what documents are listed in the file Pane, requires more decoupling
				
		
	}
	
}
