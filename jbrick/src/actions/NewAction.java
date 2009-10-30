package actions;


import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;

import pjo.JBrickEditor;


/**
 * This action class reponds to requests for a new file
 */
public class NewAction extends Action {
  /**
   * NewAction constructor
   */
  public NewAction() {
    super("&New@Ctrl+N", ImageDescriptor.createFromFile(NewAction.class,
        "/images/document-new.png"));
    setToolTipText("New");
  }

  /**
   * Creates a new file
   */
  public void run() {
    JBrickEditor.getMainWindow().openNewFile();
  }
}
