package com.jbricx.swing.actions;

import com.jbricx.ui.JBrickManager;

/**
 * This action class responds to requests for a new file
 */
public class NewAction extends AbstractAction {

  /**
   * NewAction constructor
   */
  public NewAction(final JBrickManager manager) {
    super("&New@Ctrl+N", ImageDescriptor.createFromFile(NewAction.class, "/images/document-new.png"), manager);
    setToolTipText("New");
  }

  /**
   * Creates a new file
   */
  public void run() {
    getManager().getTabFolder().openNewFile();
  }
}
