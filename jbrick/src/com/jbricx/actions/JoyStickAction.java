package com.jbricx.actions;

import org.eclipse.jface.resource.ImageDescriptor;

import com.jbricx.communications.NXT;
import com.jbricx.communications.NXTObserver;
import com.jbricx.ui.JBrickManager;
import com.jbricx.ui.joystick.JoystickUIWindow;

/**
 * @author Priya Sankaran
 * @author Abhishek Shrestha
 */
public class JoyStickAction extends AbstractAction implements NXTObserver {

  /**
   * PreferencesAction constructor
   */
  public JoyStickAction(JBrickManager manager) {
    super("&JoyStick@Ctrl+Shift+D", ImageDescriptor.createFromFile(
        JoyStickAction.class, "/images/joystick_icon.png"), manager);
    setToolTipText("Joystick");
    
    setEnabled(NXT.isFantomDriverLoaded());
  }

  /**
   * Runs the action
   */
  @Override
  public void run() {
    JoystickUIWindow joystick = new JoystickUIWindow(getManager().getShell());
    joystick.setBlockOnOpen(true);
    joystick.open();
  }

  public void update(boolean isConnected) {
    setEnabled(isConnected);
  }
}
