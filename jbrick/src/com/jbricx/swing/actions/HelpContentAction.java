package com.jbricx.swing.actions;

import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;

import com.jbricx.swing.ui.JBricxManager;
import com.jbricx.swing.ui.browser.HelpWindow;

@SuppressWarnings("serial")
public class HelpContentAction extends JBricxAbstractAction {

	/**
	 * opens the Help Content window
	 * @param manager
	 */
  public HelpContentAction(final JBricxManager manager) {
    super("", new ImageIcon(HelpContentAction.class.getResource("/icons/helpBrowser.png")), manager);
  }

  
  public void actionPerformed(ActionEvent e){
	  HelpWindow.openHelpWindow("Home",this.getManager().getShell());
  }
}
