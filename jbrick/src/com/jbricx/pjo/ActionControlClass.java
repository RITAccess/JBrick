package com.jbricx.pjo;

import java.io.IOException;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;

import com.jbricx.ui.JBrickTabItem;
import com.jbricx.ui.SafeSaveDialog;
import java.io.File;

public class ActionControlClass {

    public static void saveFile(JBrickTabItem tabItem) {
        String fileLocation = tabItem.getDocument().getFileName();

        if (fileLocation == null) { // new file has been opened
            SafeSaveDialog dlg = new SafeSaveDialog(tabItem.getParent().getShell());
            dlg.setFilterNames(FileExtensionConstants.FILTER_NAMES);
            dlg.setFilterExtensions(FileExtensionConstants.FILTER_EXTENSIONS);

            fileLocation = dlg.open();
        }
        try {
            String filename = new File(fileLocation).getName(); // just the name of the file

            tabItem.getDocument().setFileName(fileLocation);
            tabItem.getDocument().save();
            tabItem.setText(filename);// to show the filename in the tab
        } catch (IOException e) {
            showError(tabItem.getParent().getShell(), "Can't save file " + fileLocation + "; " + e.getMessage());
        }
        //}
    }

    public static void showError(Shell shell, String msg) {
        MessageDialog.openError(shell, "Error", msg);
    }
}
