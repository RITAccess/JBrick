/**
 * 
 */
package com.jbricx.ui.joystick;

import java.awt.Button;
import java.security.acl.Group;

import com.jbricx.communications.enums.Motor;

/**
 * Radio button used to select one of the three motor ports available on a Brick
 * 
 * @author byktol
 */
public class MotorRadioButton extends Button {

  public MotorRadioButton(Group group, String text, int[] bounds) {
    super(group, SWT.RADIO | SWT.LEFT);
    setText(text);
    setBounds(bounds[0], bounds[1], bounds[2], bounds[3]);

    addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        onSelect(Motor.valueOf("MOTOR_" + MotorRadioButton.this.getText()));
      }
    });
  }

  /** The class Button is not supposed to be subclassed. */
  @Override
  protected void checkSubclass() { /* Ignore class validator */ }
  protected void onSelect(Motor m) { }

}
