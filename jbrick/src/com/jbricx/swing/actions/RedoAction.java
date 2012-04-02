package com.jbricx.swing.actions;

import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;

import com.jbricx.swing.ui.JBricxManager;

/**
 * This action redoes the last action
 */
public class RedoAction extends JBricxAbstractAction {

  /**
   * RedoAction constructor
   */
  public RedoAction(final JBricxManager manager) {
	  super("", new ImageIcon("./resources/images/edit-redo.png"), manager);
  }

  /**
   * Runs the action
   */
  public void actionPerformed(ActionEvent e)  {
    //getManager().getTabFolder().redo();
  }

  /*public void enableRedo() {
    setEnabled(true);
  }

  public void disableRedo() {
    setEnabled(false);
  }*/
}
