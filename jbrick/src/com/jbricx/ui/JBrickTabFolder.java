/**
 * 
 */
package com.jbricx.ui;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabFolder2Adapter;
import org.eclipse.swt.custom.CTabFolderEvent;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;

import com.jbricx.pjo.JBrickEditor;

/**
 * @author byktol
 */
public class JBrickTabFolder extends CTabFolder implements TabFolder {

    private final List<String> filenamesList;

    public JBrickTabFolder(final Composite parent, final int style) {
        super(parent, style);
        filenamesList = new ArrayList<String>();

        setMinimizeVisible(true);
        setMaximizeVisible(true);
        addCTabFolder2Listener(new CTabFolder2Adapter() {

            @Override
            public void close(CTabFolderEvent event) {
                JBrickTabItem tabItem = (JBrickTabItem) event.item;
                if (askCloseWithoutSaving(tabItem)) {
                    JBrickEditor.getInstance().getMainWindow().setStatus("Closed");
                    filenamesList.remove(tabItem.getFilename());
                } else {
                    event.doit = false;
                }
            }
        });
    }

    public boolean contains(final String filename) {
        return filenamesList.contains(filename);
    }

    @Override
    public boolean open(String filename) {
        if (filenamesList.contains(filename)) {
            return false;
        } else {
            filenamesList.add(filename);
            JBrickTabItem newTabItem = new JBrickTabItem(this, SWT.CLOSE,
                    new File(filename));
            this.setSelection(newTabItem);
        }
        return true;
    }

    @Override
    public boolean openNewFile() {
        System.out.println("opening new file");
        JBrickTabItem newTabItem = new JBrickTabItem(this, SWT.CLOSE, null);
        this.setSelection(newTabItem);

        return true;
    }

    @Override
    public JBrickTabItem getSelection() {
        return (JBrickTabItem) super.getSelection();
    }

    @Override
    public JBrickTabItem getItem(int index) {
        return (JBrickTabItem) super.getItem(index);
    }

    /**
     * Checks the current file for unsaved changes. If it has unsaved changes,
     * confirms that user wants to overwrite
     *
     * @return boolean
     */
    public boolean checkOverwrite() {
        boolean proceed = true;

        for (CTabItem tab : getItems()) {
            JBrickTabItem tabItem = (JBrickTabItem) tab;
            proceed = askCloseWithoutSaving(tabItem);
        }
        return proceed;
    }

    private boolean askCloseWithoutSaving(JBrickTabItem tabItem) {
        boolean proceed = true;
        if (tabItem.getDocument().isDirty()) {
            proceed = MessageDialog.openConfirm(null, "Close without saving!",
                    "You have unsaved file(s) in the document. Are you sure you want to proceed without saving them?");
        }
        return proceed;
    }
}
