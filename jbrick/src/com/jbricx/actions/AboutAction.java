package com.jbricx.actions;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;

import com.jbricx.ui.JBrickManager;

/**
 * This class shows an About box
 */
public class AboutAction extends AbstractAction {

  /**
   * AboutAction constructor
   */
  public AboutAction(final JBrickManager manager) {
    super("&About", ImageDescriptor.createFromFile(AboutAction.class, "/images/help-browser.png"), manager);
    setToolTipText("About");
  }

  /**
   * Shows an about box
   */
  public void run() {
    MessageDialog.openInformation(getManager().getShell(), "About", "JBrick Editor--a NXC source code editor");
  }
}
