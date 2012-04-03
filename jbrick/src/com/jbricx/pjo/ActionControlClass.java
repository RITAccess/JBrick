package com.jbricx.pjo;

import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.jbricx.swing.ui.JBricxManager;
import com.jbricx.swing.ui.SafeSaveDialog;
import com.jbricx.swing.ui.tabs.JBricxTabItem;


public class ActionControlClass {

    public static void saveFile(JBricxTabItem tabItem, boolean isSaveAs, final JBricxManager manager) {
        String fileLocation = tabItem.getFilename();
        

        boolean isNewFile = false;
        String filename = null;

        if (isSaveAs || fileLocation == null) {
        	System.out.println("Is a new file!");
            SafeSaveDialog dlg = new SafeSaveDialog(manager);
            fileLocation = dlg.open();
            System.out.println(fileLocation);
            isNewFile = true;
        }
        try {
        	System.out.println("Continuing on!");
            File file = new File(fileLocation);
            filename = file.getName(); // get just name of the file
            /*
             * trigger save only if changes have been made to editor or SaveAs
             * is perfomed or is a newly opened file
             */
            if (isSaveAs || tabItem.getPersistantDocument().isDirty() || isNewFile) {
                if (isSaveAs) { // remove the currently selected file from the list
                    try {
                        int currFileIndex = manager.getTabFolder().getSelectedIndex();
                        manager.getTabFolder().closeFile(currFileIndex);
                    } catch (NullPointerException ne) {
                    }
                }
                tabItem.getPersistantDocument().setFileName(fileLocation);
                tabItem.getPersistantDocument().save();
               //TODO tabItem.setName(filename);// to show the filename in the tab

                tabItem.setFile(file);

                manager.refreshExplorerContent();
            } 
        } catch (IOException e) {
        	JOptionPane.showMessageDialog(manager.getShell(),
        			"Can't save file" + fileLocation +"; " ,
        			e.getMessage(),
        		    JOptionPane.WARNING_MESSAGE);

        } catch (NullPointerException ne) {
            // user has opted to cancel the save dialog
        }
    }

    public static void showError(JFrame shell, String msg) {
    	JOptionPane.showMessageDialog(shell,
    			"Error" ,
    			msg,
    		    JOptionPane.WARNING_MESSAGE);
    }
}
