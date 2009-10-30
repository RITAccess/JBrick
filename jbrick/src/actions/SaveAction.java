package actions;


import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;

import pjo.ActionControlClass;
import pjo.JBrickEditor;


/**
 * This action class responds to requests to save a file
 */
public class SaveAction extends Action {
  /**
   * SaveAction constructor
   */
  public SaveAction() {
    super("&Save", ImageDescriptor.createFromFile(SaveAction.class,
        "/images/document-save.png"));
    
    setToolTipText("Save");
    setAccelerator(SWT.CTRL + 'S');
  }

  /**
   * Saves the file
   */
  public void run() {
	  System.out.println("Saving File . . .");
	  ActionControlClass.saveFile(JBrickEditor.getMainWindow().getCurrentTabItem()) ;
  }
}
