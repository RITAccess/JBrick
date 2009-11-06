package  com.jbricx.actions;


import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;

import com.jbricx.pjo.JBrickEditor;



/**
 * This action paste the contents of the clipboard into the document
 */
public class PasteAction extends Action {
  /**
   * PasteAction constructor
   */
  public PasteAction() {
    super("&Paste@Ctrl+V", ImageDescriptor.createFromFile(PasteAction.class,
        "/images/edit-paste.png"));
    setToolTipText("Paste");
  }

  /**
   * Runs the action
   */
  public void run() {
    JBrickEditor.getApp().paste();
  }
}
