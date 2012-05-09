package com.jbricx.swing.actions;

//import com.jbricx.help.HelpBrowser;

import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;

import com.jbricx.swing.ui.JBricxManager;

@SuppressWarnings("serial")
public class HelpContentAction extends JBricxAbstractAction {

  public HelpContentAction(final JBricxManager manager) {
    super("", new ImageIcon(HelpContentAction.class.getResource("/images/help-browser.png")), manager);
  }

  /**
   * Shows an about box
   */
  public void actionPerformed(ActionEvent e){
	  System.out.println("Help!");
	/*
    HelpBrowser hb = HelpBrowser.getInstance();
    hb.open();
    */
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
