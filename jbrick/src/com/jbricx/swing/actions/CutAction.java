package com.jbricx.swing.actions;

import org.eclipse.jface.resource.ImageDescriptor;

import com.jbricx.ui.JBrickManager;

/**
 * This action cuts the current selection to the clipboard
 */
public class CutAction extends AbstractAction {

  /**
   * CutAction constructor
   */
  public CutAction(final JBrickManager manager) {
    super("Cu&t@Ctrl+X", ImageDescriptor.createFromFile(CutAction.class, "/images/edit-cut.png"), manager);
    setToolTipText("Cut");
  }

  /**
   * Runs the action
   */
  public void run() {
    getManager().getTabFolder().cut();
  }
}
