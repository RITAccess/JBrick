package com.jbricx.swing.actions;

import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;

import com.jbricx.swing.ui.JBricxManager;

/**
 * This action class responds to requests for a new file
 */
@SuppressWarnings("serial")
public class NewAction extends JBricxAbstractAction {

  /**
   * NewAction constructor
   */
  public NewAction(final JBricxManager manager) {
	  super("", new ImageIcon(NewAction.class.getResource("/icons/newDocument.png")), manager);
  }

  /**
   * Creates a new file
   */
  public void actionPerformed(ActionEvent e) {
    getManager().getTabFolder().openNewFile();
  }
}
