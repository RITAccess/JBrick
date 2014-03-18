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
			mainWindow.runNoFantom();
		
		return mainWindow;	
	}
	
	public static MainWindow newJBricksInstance(String title) {
		return newJBricksInstance(title, true);
	}
	
	public static MainWindow newJBricksInstanceNoFantom(String title){
		return newJBricksInstance(title, false);
	}
	
	public static MainWindow newJBricksInstance() {
		return newJBricksInstance("Automated Bricks Test");
	}
	
	public static MainWindow newJBricksInstanceNoFantom() {
		return newJBricksInstanceNoFantom("Automated Bricks Test (No Fantom)");
	}
	
	public static void clearTabs(){
		MainWindow mainWindow = new MainWindow();
		mainWindow.resetTabPreferences();
		mainWindow.dispose();
	}
}
