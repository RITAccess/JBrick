package jbrick.library;


import javax.swing.AbstractButton;
import com.jbricx.swing.ui.MainWindow;

/**
 * Framework for interacting with the top level menu
 */


public class MenuFunctions {
	
	
	public static void openFile(MainWindow mainWindow, String fileName) {
		AbstractButton openButton = TestUtils.getButton(mainWindow, "Open");
		openButton.doClick();
	}
	
	public static void newFile(MainWindow mainWindow) {
		AbstractButton newButton = TestUtils.getButton(mainWindow, "New");
		newButton.doClick();
	}
	
	public static void saveFile(MainWindow mainWindow) {
		AbstractButton saveButton = TestUtils.getButton(mainWindow, "Save");
		saveButton.doClick();
	}
	
	public static void toggleFilePane(MainWindow mainWindow) {
		AbstractButton togButton = TestUtils.getButton(mainWindow, "Show/Hide File Viewer");
		togButton.doClick();
	}
	
	public static void maximizeEditorPane(MainWindow mainWindow) {
		AbstractButton maxButton = TestUtils.getButton(mainWindow, "Maximize File Editor");
		maxButton.doClick();
	}
	
	public static void maximizeStatusPane(MainWindow mainWindow) {
		AbstractButton maxButton = TestUtils.getButton(mainWindow, "Maximize File Status");
		maxButton.doClick();
	}
	
	public static void maximizeFilePane(MainWindow mainWindow) {
		AbstractButton maxButton = TestUtils.getButton(mainWindow, "Maximize File Viewer");
		maxButton.doClick();
	}
	
	public static void resetView(MainWindow mainWindow) {
		AbstractButton resetButton = TestUtils.getButton(mainWindow, "Reset View");
		resetButton.doClick();
	}
}
