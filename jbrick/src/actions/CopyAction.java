package  actions;


import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;

import pjo.JBrickEditor;


/**
 * This action copies the current selection to the clipboard
 */
public class CopyAction extends Action {
  /**
   * CopyAction constructor
   */
  public CopyAction() {
    super("&Copy@Ctrl+C", ImageDescriptor.createFromFile(CopyAction.class,
        "/images/edit-copy.png"));
    setToolTipText("Copy");
  }

  /**
   * Runs the action
   */
  public void run() {
    JBrickEditor.getApp().copy();
  }
}
