package jbrick.library;

import java.awt.Component;

import com.jbricx.swing.ui.MainWindow;
import com.jbricx.swing.ui.tabs.JBricxFilePane;
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
}
