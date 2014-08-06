package com.jbricx.swing.actions;

import com.jbricx.pjo.ActionControlClass;

import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;

import com.jbricx.swing.ui.JBricxManager;

/**
 * This action class responds to requests to save a file
 * 
 */
@SuppressWarnings("serial")
public class SaveAction extends JBricxAbstractAction {

  /**
   * SaveAction constructor
   */
  public SaveAction(final JBricxManager manager) {
	  super("", new ImageIcon(SaveAction.class.getResource("/icons/save.png")), manager);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
	  ActionControlClass.saveFile(getManager().getTabFolder().getSelection(),
  			false, getManager());
  }
}
