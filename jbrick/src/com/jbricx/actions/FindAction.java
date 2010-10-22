package com.jbricx.actions;


import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;

import com.jbricx.pjo.JBrickEditor;
import com.jbricx.ui.FindReplaceDialog;


/**
 * This action searches text
 */
public class FindAction extends Action {
  /**
   * FindAction constructor
   */
  public FindAction() {
    super("&Find@Ctrl+F", ImageDescriptor.createFromFile(FindAction.class,"/images/edit-find.png"));
    setToolTipText("Find");
    System.out.print("FindAction:Function()");
  }

  /**
   * Runs the action
   */
  public void run() {
	  	System.out.print("FindAction:run()");
	  	FindReplaceDialog dlg = new FindReplaceDialog(JBrickEditor.getInstance()
        .getMainWindow().getShell(), JBrickEditor.getInstance().getMainWindow().getCurrentTabItem().getDocument(),
        JBrickEditor.getInstance().getMainWindow().getCurrentTabItem().getViewer());
	  	dlg.open();
  }
}
