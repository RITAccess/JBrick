package  com.jbricx.actions;


import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;

import com.jbricx.pjo.JBrickEditor;


//test
/**
 * This action copies the current selection to the clipboard
 */
public class SelectAllAction extends Action {
  /**
   * CopyAction constructor
   */
  public SelectAllAction() {
    super("&Select All@Ctrl+A", ImageDescriptor.createFromFile(SelectAllAction.class,
        "/images/edit-selectall.png"));
    setToolTipText("Select All");
  }

  /**
   * Runs the action
   */
  public void run() {
    JBrickEditor.getApp().selectAll();
  }
}
