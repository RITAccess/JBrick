package com.jbricx.swing.actions;

import com.jbricx.swing.communications.NXTManager;
import com.jbricx.swing.communications.NXTObserver;
import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;

import com.jbricx.swing.ui.JBricxManager;
import com.jbricx.ui.DirectControl.DirectControlUIWindow;

/**
 * This action opens the DirectControl window
 */
public class DirectControlAction extends JBricxAbstractAction /*implements NXTObserver*/ {

  /**
   * DirectControlAction constructor
   */
  public DirectControlAction(JBricxManager manager) {
	  super("", new ImageIcon("./resources/images/direct_controller.png"), manager);
  }

  /**
   * Runs the action
   */
  @Override
  public void actionPerformed(ActionEvent e) {
	/*
    DirectControlUIWindow control = new DirectControlUIWindow(getManager()
        .getShell());
    control.setBlockOnOpen(true);
    control.open();
    */
  }

  /*
  public void update(boolean isConnected) {
    if (isEnabled() != isConnected) {
      setEnabled(isConnected);
    }
  }
  */
}
