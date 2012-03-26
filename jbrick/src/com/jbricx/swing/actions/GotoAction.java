package com.jbricx.swing.actions;

import com.jbricx.ui.GotoDialog;
import com.jbricx.ui.JBrickManager;

/**
 * This action searches text
 */
public class GotoAction extends AbstractAction {
  /**
   * FindAction constructor
   */
  public GotoAction(final JBrickManager manager) {
    super("&Goto@Ctrl+G", ImageDescriptor.createFromFile(GotoAction.class,
        "/images/edit-find.png"), manager);
    setToolTipText("Goto");
  }

  /**
   * Runs the action
   */
  public void run() {
    GotoDialog dlg = new GotoDialog(getManager().getShell(), getManager()
        .getTabFolder().getSelection().getDocument(), getManager()
        .getTabFolder().getSourceViewer());
    dlg.openUp();
  }
}
