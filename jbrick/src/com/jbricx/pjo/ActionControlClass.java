package com.jbricx.pjo;

import java.awt.FileDialog;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

import com.jbricx.swing.ui.JBricxManager;
import com.jbricx.swing.ui.MainWindow;
import com.jbricx.swing.ui.tabs.JBricxTabItem;


/**
 * 
 * @author Daniel Larsen 
 * @author Ethan Jurman
 * 
 * Deals with errors from file saving and presenting them to the user.
 * Depending on whether save or save as will prompt for a file dialog box
 */
public class ActionControlClass {

	/**
	 * saveFile - Saves a tab to a file. Opens a FileDialog if the file has no file association or 
	 * the files location is no longer valid.
	 * @param tabItem Tab Item to save
	 * @param isSaveAs Whether the user requested Save(false) or Save As(true)
	 * @param manager Main Manager for reference.
	 * @return returns if the tabItem was saved to a file
	 */
    public static boolean saveFile(JBricxTabItem tabItem, boolean isSaveAs, final JBricxManager manager) {
    	String filepath = null;
        /*
         * if the choosen action is to Save As,
         * if the tabItem is a new file,
         * or if the filepath no longer exists:
         * save the file with the file dialog
         * else :
         * save the file to it's current filepath 
         */
        if (isSaveAs || tabItem.isNewFile() || !(new File(tabItem.getFileAbsolutePath()).exists())) {
        	FileDialog fDialog = new FileDialog(manager.getShell(), "Save As", FileDialog.SAVE);
        	fDialog.setFilenameFilter(new FilenameFilter(){
				@Override
				public boolean accept(File dir, String name) {
					return name.toLowerCase().endsWith(".nxc");
				}
        	});
    		fDialog.setFile(tabItem.getFileName());
    		fDialog.setVisible(true);
    		filepath = fDialog.getFile();
    		if (filepath != null) {
    			filepath = fDialog.getDirectory() + filepath;
				if (!filepath.toLowerCase().endsWith(".nxc")) {
    				    filepath = filepath + ".nxc";
    			}
				try {
					tabItem.saveAs(filepath); // handles save logic
				} catch (IOException e) {
					e.printStackTrace();
				}
    		}
            manager.refreshExplorerContent();
        } else {
        	try {
				tabItem.save(); // handles save logic
			} catch (IOException e) {
				e.printStackTrace();
			}
    	}
		filepath = tabItem.getFileAbsolutePath();
		MainWindow.lostFocusTime = System.currentTimeMillis(); // file is saved externally
		return tabItem.isDirty();
    }
}
