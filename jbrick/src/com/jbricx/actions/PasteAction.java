package  com.jbricx.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;

import com.jbricx.pjo.JBrickEditor;

//TODO: Remove dependency with the JBrickEditor class.
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
    //TODO: figure out how this is supposed to work.
    JBrickEditor.getInstance().paste();
  }
}
