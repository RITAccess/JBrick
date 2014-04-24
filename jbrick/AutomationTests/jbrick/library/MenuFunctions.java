package jbrick.library;

import com.jbricx.swing.ui.MainWindow;

/**
 * Framework for interacting with the top level menu
 */


public class MenuFunctions {
	
	public static void openFile(MainWindow mainWindow, String fileName) {
		TestUtils.getButton(mainWindow, "Open").doClick();
	}
	
	public static void newFile(MainWindow mainWindow) {
		TestUtils.getButton(mainWindow, "New").doClick();
	}
	
	public static void saveFile(MainWindow mainWindow) {
		TestUtils.getButton(mainWindow, "Save").doClick();
	}
	
	public static void showFilePane(MainWindow mainWindow) {
		TestUtils.getButton(mainWindow, "Show File Viewer").doClick();
	}
	
	public static void hideFilePane(MainWindow mainWindow) {
		TestUtils.getButton(mainWindow, "Hide File Viewer").doClick();
	}
	
	public static void maximizeEditorPane(MainWindow mainWindow) {
		TestUtils.getButton(mainWindow, "Maximize File Editor").doClick();
	}
	
	public static void maximizeStatusPane(MainWindow mainWindow) {
		TestUtils.getButton(mainWindow, "Maximize File Status").doClick();
	}
	
	public static void maximizeFilePane(MainWindow mainWindow) {
		TestUtils.getButton(mainWindow, "Maximize File Viewer").doClick();
	}
	
	public static void resetView(MainWindow mainWindow) {
		TestUtils.getButton(mainWindow, "Reset View").doClick();
	}
}
