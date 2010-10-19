package com.jbricx.pjo;


import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;

import com.jbricx.ui.JBrickTabItem;
import com.jbricx.ui.SafeSaveDialog;
import java.io.File;

public class ActionControlClass {

    public static void saveFile(JBrickTabItem tabItem) {
        SafeSaveDialog dlg = new SafeSaveDialog(tabItem.getParent().getShell());
        dlg.setFilterNames(FileExtensionConstants.FILTER_NAMES);
        dlg.setFilterExtensions(FileExtensionConstants.FILTER_EXTENSIONS);

        String fileName = new File(dlg.open()).getName();
        tabItem.setText(fileName);

        /* OLD implementation (really not sure about the logic of gettin the
        filename when it wouldn't even have been set!)
        
        tabItem.setText(fileName.);
        String fileName = tabItem.getDocument().getFileName();
        if (fileName == null) {
        SafeSaveDialog dlg = new SafeSaveDialog(tabItem.getParent().getShell());
        dlg.setFilterNames(FileExtensionConstants.FILTER_NAMES);
        dlg.setFilterExtensions(FileExtensionConstants.FILTER_EXTENSIONS);
        fileName = dlg.open();
        }
        if (fileName != null) {
        try {
        tabItem.getDocument().setFileName(fileName);
        tabItem.getDocument().save();

        } catch (IOException e) {
        showError(tabItem.getParent().getShell(), "Can't save file " + fileName + "; " + e.getMessage());
        }
        } */
    }

    public static void showError(Shell shell, String msg) {
        MessageDialog.openError(shell, "Error", msg);
    }
}
