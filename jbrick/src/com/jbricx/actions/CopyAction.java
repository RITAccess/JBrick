package  com.jbricx.actions;

import org.eclipse.jface.resource.ImageDescriptor;

import com.jbricx.ui.JBrickManager;

/**
 * This action copies the current selection to the clipboard
 */
public class CopyAction extends AbstractAction {
  /**
   * CopyAction constructor
   */
  public CopyAction(final JBrickManager manager) {
    super("&Copy@Ctrl+C", ImageDescriptor.createFromFile(CopyAction.class,
        "/images/edit-copy.png"), manager);
    setToolTipText("Copy");
  }

  /**
   * Runs the action
   */
  public void run() {
    getManager().copy();
  }
}
