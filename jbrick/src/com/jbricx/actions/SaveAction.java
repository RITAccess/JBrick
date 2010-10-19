package com.jbricx.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;

import com.jbricx.pjo.ActionControlClass;
import com.jbricx.pjo.JBrickEditor;

/**
 * This action class responds to requests to save a file
 */
public class SaveAction extends Action {

    /**
     * SaveAction constructor
     */
    public SaveAction() {
        super("&Save@Ctrl+s", ImageDescriptor.createFromFile(SaveAction.class,
                "/images/document-save.png"));

        setToolTipText("Save");
        //setAccelerator(SWT.CTRL + 's');
    }

    /**
     * Saves the file
     */
    public void run() {
        JBrickEditor.getInstance().getMainWindow().setStatus("Saving File . . .");
        ActionControlClass.saveFile(JBrickEditor.getInstance().getMainWindow().getCurrentTabItem());

        if (JBrickEditor.getInstance().getMainWindow().isAutoCompile() == true) {
            CompileAction compileAction = new CompileAction();
            compileAction.run();
        }
    }
}
