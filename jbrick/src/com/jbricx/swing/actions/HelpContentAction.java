package com.jbricx.swing.actions;

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
    HelpBrowser hb = HelpBrowser.getInstance();
    hb.open();
  }
  
  public void runPianoLink() {
    HelpBrowser hb = HelpBrowser.getInstance();
    hb.open();
    hb.getPianoHelpLink();
  }
  
  public void runJoistickLink() {
    HelpBrowser hb = HelpBrowser.getInstance();
    hb.open();
    hb.runJoistickLink();
  }
  
}
