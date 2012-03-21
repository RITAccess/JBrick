package com.jbricx.swing.actions;

import org.eclipse.jface.resource.ImageDescriptor;

import com.jbricx.ui.JBrickManager;

/**
 * This action undoes the last action
 */
public class UndoAction extends AbstractAction {

  /**
   * UndoAction constructor
   */
  public UndoAction(final JBrickManager manager) {
    super("&Undo@Ctrl+Z", ImageDescriptor.createFromFile(UndoAction.class, "/images/edit-undo.png"), manager);
    setToolTipText("Undo");
  }

  /**
   * Runs the action
   */
  public void run() {
    getManager().getTabFolder().undo();
  }
}
