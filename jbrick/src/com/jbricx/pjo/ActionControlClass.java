package com.jbricx.pjo;

import java.io.File;
import java.io.IOException;

import com.jbricx.swing.ui.JBricxManager;
import com.jbricx.swing.ui.tabs.JBricxTabItem;


public class ActionControlClass {

    public static void saveFile(JBricxTabItem tabItem, boolean isSaveAs, final JBricxManager manager, final String workpath) {
        String fileLocation = tabItem.getDocumentName();
        

        boolean isNewFile = false;
        String filename = null;

        if (isSaveAs || fileLocation == null) {
            SafeSaveDialog dlg = new SafeSaveDialog(mainShell, manager, workpath);
            fileLocation = dlg.open();
            isNewFile = true;
        }
        try {
            File file = new File(fileLocation);
            filename = file.getName(); // get just name of the file
            /*
             * trigger save only if changes have been made to editor or SaveAs
             * is perfomed or is a newly opened file
             */
            if (isSaveAs || tabItem.getDocument().isDirty() || isNewFile) {
                if (isSaveAs) { // remove the currently selected file from the list
                    try {
                        String currFilePath = manager.getTabFolder().getCurrentFilename();
                        manager.getTabFolder().closeFile(currFilePath);
                    } catch (NullPointerException ne) {
                    }
                }
                tabItem.getDocument().setFileName(fileLocation);
                tabItem.getDocument().save();
                tabItem.setText(filename);// to show the filename in the tab

                tabItem.setFile(file);

                manager.setStatus(filename + " save complete.");
                manager.refreshExplorerContent();
            } else {
                manager.setStatus("No changes have been made to the file.");
            }
        } catch (IOException e) {
            manager.setStatus(filename + " could not be saved!");
            showError(mainShell, "Can't save file " + fileLocation + "; " + e.getMessage());
        } catch (NullPointerException ne) {
            // user has opted to cancel the save dialog
        }
    }

    public static void showError(Shell shell, String msg) {
        MessageDialog.openError(shell, "Error", msg);
    }
}
