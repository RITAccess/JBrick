package com.jbricx.swing.actions;

import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;

import com.jbricx.swing.ui.JBricxManager;
/**
 * This action copies the current selection to the clipboard
 */
public class SelectAllAction extends JBricxAbstractAction {
  /**
   * CopyAction constructor
   */
  public SelectAllAction(final JBricxManager manager) {
	  super("", new ImageIcon("./resources/images/edit-selectall.png"), manager);
  }

  /**
   * Runs the action
   */
  public void actionPerformed(ActionEvent e) {
    // getManager().getTabFolder().selectAll();
  }
}
