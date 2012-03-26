package com.jbricx.actions;

import com.jbricx.ui.JBrickManager;

/**
 * This action copies the current selection to the clipboard
 */
public class SelectAllAction extends AbstractAction {
  /**
   * CopyAction constructor
   */
  public SelectAllAction(final JBrickManager manager) {
    super("&Select All@Ctrl+A", ImageDescriptor.createFromFile(SelectAllAction.class, "/images/edit-selectall.png"),
        manager);
    setToolTipText("Select All");
  }

  /**
   * Runs the action
   */
  public void run() {
    getManager().getTabFolder().selectAll();
  }
}
