package com.jbricx.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;

import com.jbricx.pjo.JBrickEditor;
/**
 * This class shows an About box
 */
public class AboutAction extends Action {
  /**
   * AboutAction constructor
   */
  public AboutAction() {
    super("&About@Ctrl+A", ImageDescriptor.createFromFile(AboutAction.class,
        "/images/help-browser.png"));
    setToolTipText("About");
  }

  /**
   * Shows an about box
   */
  public void run() {
    MessageDialog.openInformation(JBrickEditor.getApp().getMainWindow().getShell(),
        "About", "JBrick Editor--a NXC source code editor");
  }
}
