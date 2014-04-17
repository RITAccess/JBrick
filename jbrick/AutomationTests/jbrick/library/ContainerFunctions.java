package jbrick.library;

import java.awt.Component;

import com.jbricx.swing.ui.*;
import com.jbricx.swing.ui.tabs.JBricxEditorTabFolder;
import com.jbricx.swing.ui.tabs.JBricxFilePane;
import com.jbricx.swing.ui.tabs.JBricxStatusPane;

/**
 * Functions for grabbing and editing the the size of the containers:
 * 	editorPane = new JBricxEditorTabFolder();
 * 	statusPane = new JBricxStatusPane();
 * 	filePane = new JBricxFilePane();
 */
public class ContainerFunctions {

	public static JBricxEditorTabFolder getEditorPane(MainWindow mainWindow) {
		Component comp = TestUtils.getComponent(mainWindow, JBricxEditorTabFolder.class);
		if (comp instanceof JBricxEditorTabFolder){
			return ((JBricxEditorTabFolder) comp);
		}
		return null;
	}
	
	public static JBricxStatusPane getStatusPane(MainWindow mainWindow) {
		Component comp = TestUtils.getComponent(mainWindow, JBricxStatusPane.class);
		if (comp instanceof JBricxStatusPane){
			return ((JBricxStatusPane) comp);
		}
		return null;
	}
	
	public static JBricxFilePane getFilePane(MainWindow mainWindow) {
		Component comp = TestUtils.getComponent(mainWindow, JBricxFilePane.class);
		if (comp instanceof JBricxFilePane){
			return ((JBricxFilePane) comp);
		}
		return null;
	}
}
