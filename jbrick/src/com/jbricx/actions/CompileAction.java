package com.jbricx.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;

import com.jbricx.communications.BrickCreator;
import com.jbricx.communications.ExitStatus;
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
  public void run(){
	  
    ExitStatus e = BrickCreator.createBrick().compile(JBrickEditor.getMainWindow().getCurrentTabItem().getDocument().getFileName());
    
    if (e.isOk()){
    	MessageDialog.openInformation(JBrickEditor.getApp().getMainWindow().getShell(),"Compile", "Compile was a success!");
    }
    else{
    	MessageDialog.openInformation(JBrickEditor.getApp().getMainWindow().getShell(),"Compile", e.getMesage());
    }
    	        
  }
}
