package actions;


import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;

import pjo.JBrickEditor;


/**
 * This action class prints the document
 */
public class PrintAction extends Action {
  /**
   * PrintAction constructor
   */
  public PrintAction() {
    super("&Print...@Ctrl+P", ImageDescriptor.createFromFile(PrintAction.class,
        "/images/print.jpg"));
    setToolTipText("Print");
  }

  /**
   * Prints the document
   */
  public void run() {
    JBrickEditor.getApp().print();
  }
}
