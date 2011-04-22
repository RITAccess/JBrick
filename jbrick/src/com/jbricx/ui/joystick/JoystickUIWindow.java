package com.jbricx.ui.joystick;

/**
 * @author Priya Sankaran
 * @author Abhishek Shrestha
 */
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.TrayDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

import com.jbricx.communications.NXTManager;
import com.jbricx.communications.NXTObserver;

public class JoystickUIWindow extends TrayDialog implements NXTObserver {
  private Shell shell;

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
    parent.setSize(300, 410);

    return new JoystickComposite(parent, SWT.NULL);
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
    JoystickComposite.stopMotors();
  }

}
