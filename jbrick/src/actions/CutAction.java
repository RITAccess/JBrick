package  actions;


import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;

import pjo.JBrickEditor;


/**
 * This action cuts the current selection to the clipboard
 */
public class CutAction extends Action {
  /**
   * CutAction constructor
   */
  public CutAction() {
    super("Cu&t@Ctrl+X", ImageDescriptor.createFromFile(CutAction.class,
        "/images/edit-cut.png"));
    setToolTipText("Cut");
  }

  /**
   * Runs the action
   */
  public void run() {
    JBrickEditor.getApp().cut();
  }
}
