package com.jbricx.ui;

import com.jbricx.pjo.FileExtensionConstants;
import com.jbricx.pjo.JBrickEditor;
import java.io.File;
import org.eclipse.core.internal.resources.Folder;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.PreferenceStore;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;

/**
 * This class provides a facade for the "save" FileDialog class. If the selected
 * file already exists, the user is asked to confirm before overwriting.
 */
public class SafeSaveDialog {
    // The wrapped FileDialog

    private FileDialog dlg;
    private Shell mainShell;

    /**
     * SafeSaveDialog constructor
     *
     * @param shell the parent shell
     */
    public SafeSaveDialog(Shell shell) {
        PreferenceStore store = JBrickEditor.getInstance().getPreferences();
        String workspacePath = store.getString(FileExtensionConstants.WRKSPC);

        mainShell = shell;

        dlg = new FileDialog(shell, SWT.SAVE);
        dlg.setFilterNames(FileExtensionConstants.FILTER_NAMES);
        dlg.setFilterExtensions(FileExtensionConstants.FILTER_EXTENSIONS);
        dlg.setFilterPath(workspacePath);
    }

    public String open() {
        // We store the selected file name in fileName
        String fileName = null;

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
            fileName = dlg.open();
            if (fileName == null) {
                // User has cancelled, so quit and return
                done = true;
            } else {
                // User has selected a file; see if it already exists
                File file = new File(fileName);

                JBrickEditorTabFolder tabfolder = JBrickEditor.getInstance().getMainWindow().getTabFolder();
                
                if (file.exists()) {
                    // do not allow the user to specify the an existing file if already open in editor

                    int tabIndex = tabfolder.contains(fileName);
                    if (tabIndex != -1) { // check if the file is already open
                        //tabfolder.setSelection(tabItem);
                        MessageDialog.openWarning(mainShell, fileName + " is already in editor!",
                                "The file you have selected is already in the editor. Please specify a different name!");
                        fileName = null;
                    } else {
                        boolean overwrite = MessageDialog.openQuestion(mainShell, "Confirm over write", fileName + " already exists. Do you want to replace it?");

                        if (!overwrite) {
                            fileName = null;
                        }
                        done = true;
                    }
                } else {
                    done = true;
                }
                
                tabfolder.saveFile(fileName);
                //tabfolder.
            }
        }
        return fileName;
    }

    public String getFileName() {
        return dlg.getFileName();
    }

    public String[] getFileNames() {
        return dlg.getFileNames();
    }

    public String[] getFilterExtensions() {
        return dlg.getFilterExtensions();
    }

    public String[] getFilterNames() {
        return dlg.getFilterNames();
    }

    public String getFilterPath() {
        return dlg.getFilterPath();
    }

    public void setFileName(String string) {
        dlg.setFileName(string);
    }

    public void setFilterExtensions(String[] extensions) {
        dlg.setFilterExtensions(extensions);
    }

    public void setFilterNames(String[] names) {
        dlg.setFilterNames(names);
    }

    public void setFilterPath(String string) {
        dlg.setFilterPath(string);
    }

    public Shell getParent() {
        return dlg.getParent();
    }

    public int getStyle() {
        return dlg.getStyle();
    }

    public String getText() {
        return dlg.getText();
    }

    public void setText(String string) {
        dlg.setText(string);
    }
}
