package com.jbricx.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;

import com.jbricx.help.HelpBrowser;

public class HelpContentAction extends Action {

  public HelpContentAction() {
    super("&Help Content@F1", ImageDescriptor.createFromFile(AboutAction.class, "/images/help-browser.png"));
    setToolTipText("Help Content");
  }

  /**
   * Shows an about box
   */
  public void run() {
    HelpBrowser hb = new HelpBrowser();
    hb.show();
  }

}
