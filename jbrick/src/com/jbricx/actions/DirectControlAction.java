package com.jbricx.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;

import com.jbricx.ui.DirectControl.DirectControlWindow;

;

/**
 * This action opens the joystick window
 */
public class DirectControlAction extends Action {
  /**
   * CopyAction constructor
   */
  public DirectControlAction() {
    super("&DirectControl@Ctrl+D", ImageDescriptor.createFromFile(AboutAction.class, "/images/direct_control.png"));
    setToolTipText("Direct Control");
  }

  /**
   * Runs the action
   */
  public void run() {
    DirectControlWindow.showGUI();
  }
}
