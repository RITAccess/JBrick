package com.jbricx.swing.actions;

import com.jbricx.ui.JBrickManager;

/**
 * This action paste the contents of the clipboard into the document
 */
public class PasteAction extends AbstractAction {
  /**
   * PasteAction constructor
   */
  public PasteAction(final JBrickManager manager) {
    super("&Paste@Ctrl+V", ImageDescriptor.createFromFile(PasteAction.class, "/images/edit-paste.png"), manager);
    setToolTipText("Paste");
  }

  /**
   * Runs the action
   */
  public void run() {
    getManager().getTabFolder().paste();
  }
}
