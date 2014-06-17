package com.jbricx.swing.actions;

import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;

import com.jbricx.swing.ui.JBricxManager;
import com.jbricx.swing.ui.browser.Browser;

@SuppressWarnings("serial")
public class HelpContentAction extends JBricxAbstractAction {

  public HelpContentAction(final JBricxManager manager) {
    super("", new ImageIcon(HelpContentAction.class.getResource("/images/help-browser.png")), manager);
  }

  /**
   * Shows an about box
   */
  public void actionPerformed(ActionEvent e){
	new Browser(getManager());
  }
}
