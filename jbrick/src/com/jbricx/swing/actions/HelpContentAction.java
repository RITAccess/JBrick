package com.jbricx.swing.actions;

//import com.jbricx.help.HelpBrowser;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

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
    if (Desktop.isDesktopSupported()){
      String htmlFilePath = "help/html/gettingstarted.html";
      File htmlFile = new File(htmlFilePath);
      try {
        Desktop.getDesktop().open(htmlFile);
      } catch (IOException e1) {
        e1.printStackTrace();
      }
    }
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
