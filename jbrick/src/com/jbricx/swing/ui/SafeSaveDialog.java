package com.jbricx.swing.ui;

import java.awt.Component;
import java.awt.FileDialog;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

import com.jbricx.swing.ui.JBricxManager;
import com.jbricx.swing.ui.preferences.PreferenceStore;
import com.jbricx.swing.ui.tabs.JBricxEditorTabFolder;

/**
 * This class provides a facade for the "save" FileDialog class. If the selected
 * file already exists, the user is asked to confirm before overwriting.
 */
public class SafeSaveDialog {
    // The wrapped FileDialog

    private Component mainShell;
    private JBricxManager manager;
    private MyCustomFilter filter;

	/**
	 * SafeSaveDialog constructor
	 * 
	 * @param shell
	 *            the parent shell
	 */
	public SafeSaveDialog(final JBricxManager manager) {
		this.manager = manager;
		mainShell = manager.getShell();
		filter = new MyCustomFilter();

	}

	class MyCustomFilter extends FileFilter {
		@Override
		public boolean accept(File file) {
            // Allow only directories, or files with ".txt" extension
        	return file.isDirectory() || file.getAbsolutePath().endsWith(PreferenceStore.FILTER_EXTENSION);
        }
        @Override
        public String getDescription() {
            return PreferenceStore.FILTER_NAME;
        }
    }
	
	public String open() {
        // We store the selected file name in fileName
        String fileName = null;
        final JFileChooser fileChooser = new JFileChooser(PreferenceStore.getPrefs().get(PreferenceStore.WRKSPC,PreferenceStore.WRKSPC_DEFAULT));
        fileChooser.setFileFilter(filter);
        
        /* The user has finished when one of the following happens:
         * The user may provide a new file name or an existing filename
         * In case user selects an existing file:
         * case 1: the selected is also open in editor
         * consequence: do not allow to overwrite such file (else we will end
         *              up with two files with same file path in the editor
         * case 2: the selected file is not open in editor
         * consequence: ask the user for confirmation on over writing
         *
         */
        boolean done = false;

        while (!done) {
            // Open the File Dialog
        	int returnVal = fileChooser.showSaveDialog(mainShell);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
            	// User has selected a file; see if it already exists
                File file = fileChooser.getSelectedFile();
                fileName = file.getAbsolutePath();
                
                if (fileName.indexOf('.')==-1) {
                	fileName += ".nxc";
                	file = new File(file.getParentFile(), fileName);
                	}
                JBricxEditorTabFolder tabfolder = manager.getTabFolder();

                if (file.exists()) {
               
                    // do not allow the user to specify the an existing file if already open in editor

                    int tabIndex = tabfolder.getTabIndexByFilepath(fileName);
                    if (tabIndex != -1) { // check if the file is already open
                    	JOptionPane.showMessageDialog(mainShell,
                    			"The file you have selected is already in the editor. Please specify a different name!",
                    		    fileName + " is already in editor!",
                    		    JOptionPane.WARNING_MESSAGE);
                        fileName = null;
                    } else {
                    	
                    	Object[] options = {"Yes", "No"};
                    	int overwrite = JOptionPane.showOptionDialog(mainShell,
                    			fileName + " already exists. \n"
                    			+ "Do you want to replace it?","Confirm over write",
                    		    JOptionPane.YES_NO_OPTION,
                    		    JOptionPane.QUESTION_MESSAGE,
                    		    null,
                    		    options,
                    		    options[1]);

                        if (overwrite==JOptionPane.NO_OPTION) {
                            fileName = null;
                        }
                        done = true;
                    }
                } else {
                    done = true;
                }
                
                //tabfolder.saveFile(fileName);
            } else {
            	// User has cancelled, so quit and return
            	done = true;
                
            }
        }
        return fileName;
    }

}
