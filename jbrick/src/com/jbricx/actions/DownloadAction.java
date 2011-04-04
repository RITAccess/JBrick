package com.jbricx.actions;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;

import com.jbricx.communications.ExitStatus;
import com.jbricx.communications.NXTManager;
import com.jbricx.ui.JBrickManager;

/**
 * Downloads the current file into the brick.
 */
public class DownloadAction extends AbstractCompilerAction {

  /**
   * Constructor
   */
  public DownloadAction(final JBrickManager manager) {
    super("&Download@Ctrl+Alt+B", ImageDescriptor.createFromFile(
        AboutAction.class, "/images/download.png"), manager);
    setToolTipText("Download");
  }

  @Override
  protected ExitStatus doRun(final String filename) {
    return NXTManager.getInstance().downloadFile(filename);
  }

  @Override
  protected void onSuccess() {
    MessageDialog.openInformation(getManager().getShell(), "Download",
      "Download successful!");
  }

  @Override
  protected void onFailure() {
    MessageDialog.openInformation(getManager().getShell(), "Download",
      "Download failed!");
  }
}
