package jbrick.library;

import java.awt.Component;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.jbricx.swing.actions.OpenAction;
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
	
	public static String getCurrentFile(MainWindow mainWindow) {
		String name = "";
		Component comp = TestUtils.getComponent(mainWindow, JBricxEditorTabFolder.class);
		if (comp instanceof JBricxEditorTabFolder){
			name = ((JBricxEditorTabFolder) comp).getSelection().getFileName();
		}
		return name;
	}
	
	/**
	 * Opens file, without the open dialog (this can be for the most part 
	 * 		assumed to be working, as this is built into java.swing)
	 * @param mainWindow
	 */
	public static void openFile(MainWindow mainWindow, String filePath) {
		File selectedFile = new File(filePath);
		if(selectedFile.exists()){
			mainWindow.getTabFolder().open(selectedFile.getAbsolutePath());
		}
	}
	
	/**
	 * creates a file (if there is none) at a given filePath
	 * @param filePath
	 * @return if the filePath already exists return false, else return true
	 */
	public static boolean createFile(String filePath){
		File newFile = new File(filePath);
		try {
			return newFile.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * deletes a file (if there is one) at a given filePath
	 * @param filePath
	 * @return if the file was deleted => true, else => false
	 */
	public static boolean deleteFile(String filePath){
		File delFile = new File(filePath);
		return delFile.delete();
	}
	
	/**
	 * saves file at filePath, with content in current tab
	 * @param mainWindow
	 * @param filePath
	 * @return false is something went wrong, true if everything was successful
	 */
	public static boolean saveFile(MainWindow mainWindow, String filePath){
		try {
			String content;
			Component comp = TestUtils.getComponent(mainWindow, JBricxEditorTabFolder.class);
			if (comp instanceof JBricxEditorTabFolder){
				content = ((JBricxEditorTabFolder) comp).getSelection().getText();
			} else {
				return false;
			}
			File file = new File(filePath);
			if (!file.exists()) {
				file.createNewFile();
			}
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(content);
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	/**
	 * checks if file exists
	 * @param filePath
	 * @return false if file does not exist, true if filePath leads to an existing file
	 */
	public static boolean fileExists(String filePath){
		return new File(filePath).exists();
	}
}
