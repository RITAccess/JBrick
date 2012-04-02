package com.jbricx.swing.actions;

import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;

import com.jbricx.swing.ui.JBricxManager;

/**
 * This action undoes the last action
 */
public class UndoAction extends JBricxAbstractAction {

  /**
   * UndoAction constructor
   */
  public UndoAction(final JBricxManager manager) {
	  super("", new ImageIcon("./resources/images/edit-undo.png"), manager);
  }

  /**
   * Runs the action
   */
  public void actionPerformed(ActionEvent e){
    //getManager().getTabFolder().undo();
  }
}
