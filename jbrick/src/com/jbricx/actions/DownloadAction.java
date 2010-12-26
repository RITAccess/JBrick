package com.jbricx.actions;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;

import com.jbricx.communications.AbstractNXTBrick;
import com.jbricx.communications.BrickCreator;
import com.jbricx.communications.ExitStatus;
import com.jbricx.ui.JBrickManager;

public class DownloadAction extends AbstractAction {

  /**
   * AboutAction constructor
   */
  public DownloadAction(final JBrickManager manager) {
    super("&Download@Ctrl+Alt+B",
        ImageDescriptor.createFromFile(AboutAction.class, "/images/media-playback-start.png"), manager);
    setToolTipText("Download");
  }

  /**
   * Shows an about box
   */
  public void run() {

    // System.out.println(JBrickEditor.getMainWindow().getCurrentTabItem().getDocument().getFileName());
    AbstractNXTBrick nxt = BrickCreator.createBrick();

    // nxt.NXTConnect(NXT.ConnectionType.USB);
    ExitStatus e = nxt.downloadFile(getManager().getCurrentTabItem().getDocument().getFileName());

    if (e.isOk()) {
      MessageDialog.openInformation(getManager().getShell(), "Download", "Downloading was a success!!");
    } else {
      MessageDialog.openInformation(getManager().getShell(), "Download", "Downloading failed: \n" + e.getMesage());
    }

  }
}
