package com.jbricx.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;

import com.jbricx.pjo.ActionControlClass;
import com.jbricx.pjo.JBrickEditor;
import com.jbricx.ui.tabs.JBrickTabItem;

/**
 * This action class responds to requests to save a file as . . .
 */
public class SaveAsAction extends Action {

    /**
     * SaveAsAction constructor
     */
    public SaveAsAction() {
        super("Save As...", ImageDescriptor.createFromFile(SaveAsAction.class,
                "/images/document-save-as.png"));
        setToolTipText("Save As");
    }

    /**
     * Saves the file
     */
    public void run() {
        JBrickTabItem tabItem = JBrickEditor.getInstance().getMainWindow().getCurrentTabItem();
        ActionControlClass.saveFile(tabItem, true);

        if (JBrickEditor.getInstance().getMainWindow().isAutoCompile() == true) {
            CompileAction compileAction = new CompileAction();
            compileAction.run();
        }
    }
}
