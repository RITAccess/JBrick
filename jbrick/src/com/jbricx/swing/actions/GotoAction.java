package com.jbricx.swing.actions;

import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;

import com.jbricx.swing.ui.JBricxManager;

/**
 * This action searches text
 */
@SuppressWarnings("serial")
public class GotoAction extends JBricxAbstractAction {
  /**
   * FindAction constructor
   */
  public GotoAction(final JBricxManager manager) {
	  super("", new ImageIcon("./resources/images/edit-find.png"), manager);
  }

  /**
   * Runs the action
   */
  public void actionPerformed(ActionEvent arg0) {
    /*GotoDialog dlg = new GotoDialog(getManager().getShell(), getManager()
        .getTabFolder().getSelection().getDocument(), getManager()
        .getTabFolder().getSourceViewer());
    dlg.openUp();
    */
  }
}
