package com.jbricx.actions;


import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;

import com.jbricx.pjo.JBrickEditor;



/**
 * This action class exits the application
 */
public class ExitAction extends Action {
  /**
   * ExitAction constructor
   */
  public ExitAction() {
//    super("E&xit@Alt+F4");
    super("E&xit@Ctrl+Q", ImageDescriptor.createFromFile(PasteAction.class,
    "/images/system-log-out.png"));
    setToolTipText("Exit");
  }

  /**
   * Exits the application
   */
  public void run() {
    JBrickEditor.getInstance().getMainWindow().close();
  }
}
