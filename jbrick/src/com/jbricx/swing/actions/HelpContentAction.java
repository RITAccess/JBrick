package com.jbricx.swing.actions;

//import com.jbricx.help.HelpBrowser;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.ImageIcon;

import com.jbricx.swing.ui.JBricxManager;
import com.jbricx.swing.ui.browser.Browser;
import com.jbricx.swing.ui.preferences.JBricxPreferencesWindow;

@SuppressWarnings("serial")
public class HelpContentAction extends JBricxAbstractAction {

  public HelpContentAction(final JBricxManager manager) {
    super("", new ImageIcon(HelpContentAction.class.getResource("/images/help-browser.png")), manager);
  }

  /**
   * Shows an about box
   */
  public void actionPerformed(ActionEvent e){
	Browser helpBrowser = new Browser(getManager());
  }
  
  /*public void runPianoLink() {
    HelpBrowser hb = HelpBrowser.getInstance();
    hb.open();
    hb.getPianoHelpLink();
  }
  
  public void runJoistickLink() {
    HelpBrowser hb = HelpBrowser.getInstance();
    hb.open();
    hb.runJoistickLink();
  }
  */
}
