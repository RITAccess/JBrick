package actions;


import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;

import pjo.JBrickEditor;


/**
 * This action undoes the last action
 */
public class UndoAction extends Action {
  /**
   * UndoAction constructor
   */
  public UndoAction() {
    super("&Undo@Ctrl+Z", ImageDescriptor.createFromFile(UndoAction.class,
        "/images/edit-undo.png"));
    setToolTipText("Undo");
  }

  /**
   * Runs the action
   */
  public void run() {
    JBrickEditor.getMainWindow().getCurrentTabItem().getUndoManager().undo();
  }
}
