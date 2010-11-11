package com.jbricx.pjo;

import java.io.IOException;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;

import com.jbricx.ui.JBrickTabItem;
import com.jbricx.ui.MainWindow;
import com.jbricx.ui.SafeSaveDialog;
import java.io.File;

public class ActionControlClass {

    public static void saveFile(JBrickTabItem tabItem, boolean isSaveAs) {
        String fileLocation = tabItem.getDocument().getFileName();
        MainWindow mainWindow = JBrickEditor.getInstance().getMainWindow();
        Shell mainShell = tabItem.getParent().getShell();

        boolean isNewFile = false;
        String filename = null;

        if (isSaveAs || fileLocation == null) {
            SafeSaveDialog dlg = new SafeSaveDialog(mainShell);
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
                tabItem.getDocument().setFileName(fileLocation);
                tabItem.getDocument().save();
                tabItem.setText(filename);// to show the filename in the tab
                tabItem.setFile(file);

                mainWindow.setStatus(filename + " save complete.");
                //JBrickEditor.getInstance().getMainWindow().refresh_ 2();
            } else {
                mainWindow.setStatus("No changes have been made to the file.");
            }
        } catch (IOException e) {
            mainWindow.setStatus(filename + " could not be saved!");
            showError(mainShell, "Can't save file " + fileLocation + "; " + e.getMessage());
        } catch (NullPointerException ne) {
            // user has opted to cancel the save dialog
        }
    }

    public static void showError(Shell shell, String msg) {
        MessageDialog.openError(shell, "Error", msg);
    }
}
