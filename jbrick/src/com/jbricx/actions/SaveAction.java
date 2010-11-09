package com.jbricx.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;

import com.jbricx.pjo.ActionControlClass;
import com.jbricx.pjo.JBrickEditor;
import com.jbricx.ui.MainWindow;

/**
 * This action class responds to requests to save a file
 */
public class SaveAction extends Action {

    /**
     * SaveAction constructor
     */
    public SaveAction() {
        super("&Save@Ctrl+S", ImageDescriptor.createFromFile(SaveAction.class,
                "/images/document-save.png"));

        setToolTipText("Save");
        //setAccelerator(SWT.CTRL + 's');
    }

    /**
     * Saves the file
     */
    @Override
    public void run() {
        MainWindow mainWindow = JBrickEditor.getInstance().getMainWindow();
        mainWindow.setStatus("Saving File . . .");
        ActionControlClass.saveFile(JBrickEditor.getInstance().getMainWindow().getCurrentTabItem(), false);

        if (mainWindow.isAutoCompile() == true) {
            CompileAction compileAction = new CompileAction();
            compileAction.run();
        }
    }
}
