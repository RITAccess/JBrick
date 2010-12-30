package com.jbricx.actions;

import org.eclipse.jface.resource.ImageDescriptor;

import com.jbricx.pjo.ActionControlClass;
import com.jbricx.ui.JBrickManager;

/**
 * This action class responds to requests to save a file
 */
public class SaveAction extends AbstractAction {

  /**
   * SaveAction constructor
   */
  public SaveAction(final JBrickManager manager) {
    super("&Save@Ctrl+S", ImageDescriptor.createFromFile(SaveAction.class, "/images/document-save.png"), manager);

    setToolTipText("Save");
    // setAccelerator(SWT.CTRL + 's');
  }

  /**
   * Saves the file
   */
  @Override
  public void run() {
    getManager().setStatus("Saving File . . .");
    ActionControlClass.saveFile(getManager().getCurrentTabItem(), false, getManager(), getManager().getWorkspacePath());

    if (getManager().isAutoCompile()) {
      CompileAction compileAction = new CompileAction(getManager());
      compileAction.run();
    }
  }
}
