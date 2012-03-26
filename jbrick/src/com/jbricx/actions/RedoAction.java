package com.jbricx.actions;

import com.jbricx.ui.JBrickManager;

/**
 * This action redoes the last action
 */
public class RedoAction extends AbstractAction {

  /**
   * RedoAction constructor
   */
  public RedoAction(final JBrickManager manager) {
    super("&Redo@Ctrl+Y", ImageDescriptor.createFromFile(RedoAction.class, "/images/edit-redo.png"), manager);
    setToolTipText("Redo");
  }

  /**
   * Runs the action
   */
  public void run() {
    getManager().getTabFolder().redo();
  }

  /*public void enableRedo() {
    setEnabled(true);
  }

  public void disableRedo() {
    setEnabled(false);
  }*/
}
