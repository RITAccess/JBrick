package com.jbricx.actions;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;

import com.jbricx.communications.ExitStatus;
import com.jbricx.communications.NXTConnectionManager;
import com.jbricx.communications.NXTManager;
import com.jbricx.pjo.ActionControlClass;
import com.jbricx.ui.JBrickManager;

public class DownloadAction extends AbstractAction {

  /**
   * AboutAction constructor
   */
  public DownloadAction(final JBrickManager manager) {
    super("&Download@Ctrl+Alt+B", ImageDescriptor.createFromFile(
        AboutAction.class, "/images/download.png"), manager);
    setToolTipText("Download");
  }

  /**
   * Shows an about box
   */
  public void run() {
    String filename = getManager().getTabFolder().getCurrentFilename();

    // ask the user to save the file before downloading
    if (getManager().getTabFolder().getSelection().getDocument().isDirty()) {
      boolean doSave = MessageDialog.openConfirm(getManager().getShell(), "",
          "The file you are downloading is not saved. Proceed after saving?");

      if (doSave) {
        ActionControlClass.saveFile(getManager().getTabFolder().getSelection(),
            false, getManager(), filename);
        doDownload(filename);
      }
    } else {
      doDownload(filename);
    }
  }

  private void doDownload(String filename) {
    NXTConnectionManager nxt = NXTManager.getInstance();

    ExitStatus e = nxt.downloadFile(filename);

    if (e.isOk()) {
      MessageDialog.openInformation(getManager().getShell(), "Download",
          "Downloading was a success!!");
    } else {
      MessageDialog.openInformation(getManager().getShell(), "Download",
          "Downloading failed: \n" + e.getMesage());
    }
  }
}
