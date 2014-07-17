package com.jbricx.swing.actions;

import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;

import com.jbricx.swing.ui.JBricxManager;
import com.jbricx.swing.ui.browser.HelpWindow;

@SuppressWarnings("serial")
public class HelpContentAction extends JBricxAbstractAction {
	
  public HelpContentAction(final JBricxManager manager) {
    super("", new ImageIcon(HelpContentAction.class.getResource("/icons/helpBrowser.png")), manager);
  }

  /**
   * Opens Browser panel
   */
  public void actionPerformed(ActionEvent e){
	  HelpWindow.openHelpWindow("Home");
  }
}
