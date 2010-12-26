package com.jbricx.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;

import com.jbricx.ui.joystick.JoystickComposite;

/**
 * @author Priya Sankaran
 */
public class JoyStickAction extends Action {

  /**
   * PreferencesAction constructor
   */
  public JoyStickAction() {
    super("&JoyStick@Ctrl+Shift+D", ImageDescriptor.createFromFile(JoyStickAction.class, "/images/joystick_icon.png"));
    setToolTipText("Joystick");
  }

  /**
   * Runs the action
   */
  public void run() {
    //TODO: delete these comments.
    // JoystickUIWindow joystick = new JoystickUIWindow();
    // joystick.setBlockOnOpen(true);
    // joystick.open();

    JoystickComposite.showGUI();

  }
}
