package com.jbricx.swing.actions;

import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;

import com.jbricx.swing.ui.JBricxManager;

/**
 * This action paste the contents of the clipboard into the document
 */
public class PasteAction extends JBricxAbstractAction {
  /**
   * PasteAction constructor
   */
  public PasteAction(final JBricxManager manager) {
	  super("", new ImageIcon("./resources/images/edit-paste.png"), manager);
  }

  /**
   * Runs the action
   */
  public void actionPerformed(ActionEvent e) {
    //getManager().getTabFolder().paste();
  }
}
