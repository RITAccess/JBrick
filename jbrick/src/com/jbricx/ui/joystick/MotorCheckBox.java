/**
 * 
 */
package com.jbricx.ui.joystick;

import java.awt.Button;
import java.security.acl.Group;

/**
 * Displays a checkbox for selecting whether the associated motor is going to
 * work in reverse.
 * 
 * @author byktol
 */
public class MotorCheckBox extends Button {

  public MotorCheckBox(final Group group, final String text, final int[] bounds) {
    super(group, SWT.CHECK | SWT.LEFT);
    setText(text);
    setBounds(bounds[0], bounds[1], bounds[2], bounds[3]);

    addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        onCheck(MotorCheckBox.this.getSelection());
      }
    });
  }

  /** The class Button is not supposed to be subclassed. */
  @Override
  protected void checkSubclass() { /* Ignore class validator */ }
  protected void onCheck(boolean checked) { }
}
