package com.jbricx.pjo;

import java.awt.FileDialog;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

import javafx.application.Platform;
import javafx.stage.FileChooser;

import com.jbricx.swing.ui.JBricxManager;
import com.jbricx.swing.ui.MainWindow;
import com.jbricx.swing.ui.preferences.PreferenceStore;
import com.jbricx.swing.ui.preferences.PreferenceStore.Preference;
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
    public static boolean saveFile(final JBricxTabItem tabItem, final boolean isSaveAs, final JBricxManager manager) {
        /*
         * if the choosen action is to Save As,
         * if the tabItem is a new file,
         * or if the filepath no longer exists:
         * save the file with the file dialog
         * else :
         * save the file to it's current filepath 
         */
    	
        Platform.runLater(new Runnable(){

			@Override
			public void run() {
				String filepath = null;
		        if (isSaveAs || tabItem.isNewFile() || !(new File(tabItem.getFileAbsolutePath()).exists())) {
					FileChooser fChooser = new FileChooser();
					fChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("NXC files (*.nxc)", "*.nxc", "*.NXC"));
		        	fChooser.setTitle("Save As");
		        	if (fChooser.getInitialDirectory() == null) {
		        		fChooser.setInitialDirectory(new File(PreferenceStore.getString(Preference.WORKSPACE)));
		        	}
		        	File file = fChooser.showSaveDialog(null);
		        	if (file != null) {
		        		filepath = file.getAbsolutePath();
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
			}
        		
    	});
		return tabItem.isDirty();
        
    }
}
