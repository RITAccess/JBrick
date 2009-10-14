package actions;


import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;

import pjo.JBrickEditor;


/**
 * This action redoes the last action
 */
public class RedoAction extends Action {
  /**
   * RedoAction constructor
   */
  public RedoAction() {
    super("&Redo@Ctrl+Y", ImageDescriptor.createFromFile(RedoAction.class,
        "/images/redo.jpg"));
    setToolTipText("Redo");
  }

  /**
   * Runs the action
   */
  public void run() {
    JBrickEditor.getApp().redo();
  }
}
