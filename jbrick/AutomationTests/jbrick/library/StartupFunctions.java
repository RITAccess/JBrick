package jbrick.library;

import com.jbricx.swing.ui.MainWindow;

/**
 * Functions for starting up the JBricks Application
 */

public class StartupFunctions {
	
	private static MainWindow newJBricksInstance(String title, boolean fantomCheck) {
		MainWindow mainWindow = new MainWindow();
		mainWindow.setTitle(title);
		
		if (fantomCheck)
			mainWindow.run();
		else
			mainWindow.run();
		
		return mainWindow;	
	}
	
	public static MainWindow newJBricksInstance(String title) {
		return newJBricksInstance(title, false);
	}
	
	public static MainWindow newJBricksInstanceWithFantom(String title){
		return newJBricksInstance(title, true);
	}
	
	public static MainWindow newJBricksInstance() {
		return newJBricksInstance("Automated Bricks Test");
	}
	
	public static MainWindow newJBricksInstanceWithFantom() {
		return newJBricksInstanceWithFantom("Automated Bricks Test (With Fantom)");
	}
	
	public static void clearTabs(){
		MainWindow mainWindow = new MainWindow();
		mainWindow.resetTabPreferences();
		mainWindow.dispose();
	}
}
