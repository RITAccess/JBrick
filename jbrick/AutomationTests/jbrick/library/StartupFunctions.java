package jbrick.library;

import com.jbricx.swing.ui.MainWindow;

/**
 * Functions for starting up the JBricks Application
 */

public class StartupFunctions {
	
	public static MainWindow newJBricksInstance(String title) {
		
		MainWindow mainWindow = new MainWindow();
		mainWindow.setTitle(title);
		mainWindow.run();
		
		return mainWindow;
		
	}
	
	public static MainWindow newJBricksInstance() {
		return newJBricksInstance("Automated Bricks Test");
	}
}
