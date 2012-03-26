package com.jbricx.swing.actions;

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
  }

  /**
   * Runs the action
   */
  public void run() {
	  	FindReplaceDialog dlg = new FindReplaceDialog(getManager().getShell(), getManager().getTabFolder().getSelection().getDocument(),
        getManager().getTabFolder().getSourceViewer());
	  	dlg.open();
  }
}
