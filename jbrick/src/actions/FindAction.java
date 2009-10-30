package actions;


import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;

import pjo.JBrickEditor;
import ui.FindReplaceDialog;

/**
 * This action searches text
 */
public class FindAction extends Action {
  /**
   * FindAction constructor
   */
  public FindAction() {
    super("&Find@Ctrl+F", ImageDescriptor.createFromFile(FindAction.class,
        "/images/edit-find.png"));
    setToolTipText("Find");
  }

  /**
   * Runs the action
   */
  public void run() {
    FindReplaceDialog dlg = new FindReplaceDialog(JBrickEditor.getApp()
        .getMainWindow().getShell(), JBrickEditor.getMainWindow().getCurrentTabItem().getDocument(),
        JBrickEditor.getApp().getMainWindow().getCurrentTabItem().getViewer());
    dlg.open();
  }
}
