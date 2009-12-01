package com.jbricx.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;

import com.jbricx.communications.ExitStatus;
import com.jbricx.communications.JCompiler;
import com.jbricx.pjo.JBrickEditor;

/**
 * This class shows an About box
 */
public class CompileAction extends Action {
  /**
   * AboutAction constructor
   */
  public CompileAction() {
    super("&Compile@Ctrl+Shift-b", ImageDescriptor.createFromFile(CompileAction.class, 
    		"/images/compile.png"));
    setToolTipText("Compile");
  }

  /**
   * Shows an about box
   */
  public void run() {
	  
    JCompiler c = new JCompiler();
    System.out.println(JBrickEditor.getMainWindow().getCurrentTabItem().getDocument().getFileName());
    ExitStatus exitstatus = c.compile(JBrickEditor.getMainWindow().getCurrentTabItem().getDocument().getFileName());
    
    if (exitstatus == ExitStatus.Ok){
    	MessageDialog.openInformation(JBrickEditor.getApp().getMainWindow().getShell(),
    	        "Compile", c.getMessage());
    }
    else if (exitstatus==ExitStatus.Error){
    	MessageDialog.openInformation(JBrickEditor.getApp().getMainWindow().getShell(),
    	        "Compile", c.getErrorMessage());
    }
  }
}
