package com.jbricx.actions;

import org.eclipse.jface.resource.ImageDescriptor;

import com.jbricx.ui.FindReplaceDialog;
import com.jbricx.ui.JBrickManager;

/**
 * This action searches text
 */
public class FindAction extends AbstractAction {
  /**
   * FindAction constructor
   */
  public FindAction(final JBrickManager manager) {
    super("&Find@Ctrl+F", ImageDescriptor.createFromFile(FindAction.class,"/images/edit-find.png"), manager);
    setToolTipText("Find");
    System.out.print("FindAction:Function()");
  }

  /**
   * Runs the action
   */
  public void run() {
	  	System.out.print("FindAction:run()");
	  	FindReplaceDialog dlg = new FindReplaceDialog(getManager().getShell(), getManager().getCurrentTabItem().getDocument(),
        getManager().getTabFolder().getSourceViewer());
	  	dlg.open();
  }
}
