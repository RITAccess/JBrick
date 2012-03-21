package com.jbricx.swing.actions;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;

import com.jbricx.communications.ExitStatus;
import com.jbricx.communications.NXTManager;
import com.jbricx.ui.JBrickManager;

/**
 * Compiles the current file.
 */
public class CompileAction extends AbstractCompilerAction {

  /**
   * Constructor
   */
  public CompileAction(final JBrickManager manager) {
    super("&Compile@Ctrl+Shift+B", ImageDescriptor.createFromFile(
        CompileAction.class, "/images/compile.png"), manager);
    setToolTipText("Compile");
  }

  @Override
  public ExitStatus doRun(final String filename) {
    return NXTManager.getInstance().compile(filename);
  }

  @Override
  public void onSuccess() {
    MessageDialog.openInformation(getManager().getShell(), "Compile",
      "Compile was a success!");
  }

  @Override
  public void onFailure() {
    MessageDialog.openError(getManager().getShell(), "Compile",
      "Errors found during compilation.");
  }
}
