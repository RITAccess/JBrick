package com.jbricx.actions;


import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;

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
    
    
    
    System.out.println("accelerator is: "+ convertAccelerator(getAccelerator())  );
  }

  /**
   * Saves the file
   */
  public void run() {
	  JBrickEditor.getMainWindow().setStatus("Saving File . . .");
	  ActionControlClass.saveFile(JBrickEditor.getMainWindow().getCurrentTabItem()) ;
	  JBrickEditor.getMainWindow().setStatus("Saving File . . .");
	  
  }
}
