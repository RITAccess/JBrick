package com.jbricx.actions;

import com.jbricx.communications.NXTObserver;
import com.jbricx.ui.JBrickManager;
import org.eclipse.jface.resource.ImageDescriptor;

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
    super("&JoyStick@Ctrl+Shift+D", ImageDescriptor.createFromFile(JoyStickAction.class, "/images/joystick_icon.png"), manager);
    setToolTipText("Joystick");
  }

  /**
   * Runs the action
   */
  @Override
  public void run() {
    if (getManager().getNXTManager().isBrickConnected()) {
      JoystickUIWindow joystick = new JoystickUIWindow(getManager().getShell());
      joystick.setBlockOnOpen(true);
      joystick.open();
    } else {
      // disable all the brick related icons
      getManager().getNXTManager().notifyAllObservers(false);
    }
  }

  public void update(boolean isConnected) {
    setEnabled(isConnected);
  }
}
