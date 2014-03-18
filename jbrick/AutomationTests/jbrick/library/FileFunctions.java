package jbrick.library;

import java.awt.Component;

import com.jbricx.swing.ui.MainWindow;
import com.jbricx.swing.ui.tabs.JBricxEditorTabFolder;
import com.jbricx.swing.ui.tabs.JBricxTabItem;

/**
 * Functions for handling files
 */

public class FileFunctions {

	public static String getFileName(MainWindow mainWindow) {
		
		Component comp = TestUtils.getComponent(mainWindow, JBricxTabItem.class);
		String name = "";
		if (comp instanceof JBricxTabItem){
			name = ((JBricxTabItem) comp).getFileName();
		}
		
		return name;
	}
	
	public static int getTabCount(MainWindow mainWindow) {
		int count = 0;
		Component comp = TestUtils.getComponent(mainWindow, JBricxEditorTabFolder.class);
		if (comp instanceof JBricxEditorTabFolder){
			count = ((JBricxEditorTabFolder) comp).getTabCount();
		}
		return count;
	}
	
	public static void closeTab(MainWindow mainWindow, int tabIndex){
		Component comp = TestUtils.getComponent(mainWindow, JBricxEditorTabFolder.class);
		if (comp instanceof JBricxEditorTabFolder){
			((JBricxEditorTabFolder) comp).closeFile(tabIndex);
		}
	}
}
