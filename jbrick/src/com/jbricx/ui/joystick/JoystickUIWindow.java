package com.jbricx.ui.joystick;

import java.awt.Composite;

import com.jbricx.communications.NXTManager;
import com.jbricx.communications.NXTObserver;
import com.jbricx.ui.joystick.hardware.GamepadController;

/**
 * @author Priya Sankaran
 * @author Abhishek Shrestha
 */
public class JoystickUIWindow extends TrayDialog implements NXTObserver {
  private Shell shell;
  private final GamepadController gamepadController = new GamepadController();

  public JoystickUIWindow(Shell parentShell) {
    super(parentShell);
    shell = parentShell;
  }

  @Override
  public void handleShellCloseEvent() {
    onDisconnect();
    super.handleShellCloseEvent();
  }

  @Override
  protected Control createContents(Composite parent) {
    getShell().setText("Joystick");
    parent.setSize(280, 420);

    return new JoystickComposite(parent, SWT.NULL, gamepadController);
  }

  @Override
  public void update(boolean isConnected) {
    if (!isConnected) {
      onDisconnect();

      // Display an error message
      MessageDialog.openInformation(shell, "Connection Status",
              "JoyStick: NXTBrick has been disconnected!"
              + " The Joystick window will be closed now!");

      // Dynamically close the joystick dialog
      this.getShell().dispose();
    }
  }

  /**
   * Unregisters this observer and stops the NXT motors.
   */
  protected void onDisconnect() {
    // Unregister this observer
    NXTManager.getInstance().unregister(this);

    // Stop the motors
    gamepadController.stop();
    gamepadController.stopGamepadPolling();
  }
}
