package com.jbricx.actions;

import org.eclipse.jface.resource.ImageDescriptor;

import com.jbricx.help.HelpBrowser;
import com.jbricx.ui.JBrickManager;

public class HelpContentAction extends AbstractAction {

  public HelpContentAction(final JBrickManager manager) {
    super("&Help Content@F1", ImageDescriptor.createFromFile(AboutAction.class, "/images/help-browser.png"), manager);
    setToolTipText("Help Content");
  }

  /**
   * Shows an about box
   */
  public void run() {
    HelpBrowser hb = new HelpBrowser(getManager().getShell());
    hb.show();
  }

}
