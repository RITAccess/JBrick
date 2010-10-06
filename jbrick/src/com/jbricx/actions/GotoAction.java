package com.jbricx.actions;


import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;

import com.jbricx.pjo.JBrickEditor;
import com.jbricx.ui.FindReplaceDialog;
import com.jbricx.ui.GotoDialog;


/**
 * This action searches text
 */
public class GotoAction extends Action {
  /**
   * FindAction constructor
   */
  public GotoAction() {
    super("&Goto@Ctrl+G", ImageDescriptor.createFromFile(GotoAction.class,
        "/images/edit-find.png"));
    setToolTipText("Goto");
  }

  /**
   * Runs the action
   */
  public void run() {
    GotoDialog dlg = new GotoDialog(JBrickEditor.getInstance()
        .getMainWindow().getShell(), JBrickEditor.getInstance().getMainWindow().getCurrentTabItem().getDocument(),
        JBrickEditor.getInstance().getMainWindow().getCurrentTabItem().getViewer());
    dlg.open();
  }
}
