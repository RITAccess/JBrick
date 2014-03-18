package jbrick.library;

import java.awt.Component;

import jbrick.library.*;

import javax.swing.AbstractButton;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import com.jbricx.swing.ui.MainWindow;
import com.jbricx.swing.ui.tabs.JBricxEditorTabFolder;

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
}
