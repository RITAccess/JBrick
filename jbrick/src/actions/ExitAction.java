package actions;


import org.eclipse.jface.action.Action;

import pjo.JBrickEditor;


/**
 * This action class exits the application
 */
public class ExitAction extends Action {
  /**
   * ExitAction constructor
   */
  public ExitAction() {
    super("E&xit@Alt+F4");
    setToolTipText("Exit");
  }

  /**
   * Exits the application
   */
  public void run() {
    JBrickEditor.getApp().getMainWindow().close();
  }
}
