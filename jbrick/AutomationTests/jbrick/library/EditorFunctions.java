package jbrick.library;

import java.awt.Component;
import com.jbricx.swing.ui.MainWindow;
import com.jbricx.swing.ui.tabs.JBricxTabItem;

/**
 * Functions for editing and working with the Edit Pane
 */
public class EditorFunctions {

	public static void insertText(MainWindow mainWindow, String insertText, int position) {
		Component comp = TestUtils.getComponent(mainWindow, JBricxTabItem.class);
		if (comp instanceof JBricxTabItem){
			((JBricxTabItem) comp).insert(insertText, position);
		}
	}
	
	public static void writeText(MainWindow mainWindow, String insertText) {
		Component comp = TestUtils.getComponent(mainWindow, JBricxTabItem.class);
		
		if (comp instanceof JBricxTabItem){
			int cpos = ((JBricxTabItem) comp).getCaretPosition();
			insertText(mainWindow, insertText, cpos);
		}
	}
	
	public static boolean checkText(MainWindow mainWindow, String testString){
		Component comp = TestUtils.getComponent(mainWindow, JBricxTabItem.class);
		if (comp instanceof JBricxTabItem){
			return testString.equals(((JBricxTabItem) comp).getText());
		}
		return false;
	}
}
