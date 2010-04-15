package com.jbricx.actions;


import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;

import com.jbricx.pjo.ActionControlClass;
import com.jbricx.pjo.FileExtensionConstants;
import com.jbricx.pjo.JBrickEditor;
import com.jbricx.ui.SafeSaveDialog;


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
    SafeSaveDialog dlg = new SafeSaveDialog(JBrickEditor.getApp().getMainWindow()
        .getShell());
    dlg.setFilterNames(FileExtensionConstants.FILTER_NAMES);
    dlg.setFilterExtensions(FileExtensionConstants.FILTER_EXTENSIONS);
    String fileName = dlg.open();
    if (fileName != null) {
	  ActionControlClass.saveFile(JBrickEditor.getMainWindow().getCurrentTabItem()) ;
	  if (JBrickEditor.getMainWindow().isAutoCompile() == true){
		  CompileAction compileAction = new CompileAction(); 
		  compileAction.run() ;
	  }
    }
  }
}
