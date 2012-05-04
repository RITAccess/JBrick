/**
 * 
 */
package com.jbricx.ui.joystick;

import java.awt.Button;
import java.security.acl.Group;

import com.jbricx.ui.joystick.hardware.GamepadController;
import com.jbricx.ui.joystick.hardware.GamepadType;

/**
 * @author byktol
 */
public class GamepadRadioButton extends Button {
  private GamepadController gamepadController;

  public GamepadRadioButton(final GamepadController gc, final Group group,
          final String text, final int[] bounds) {
    super(group, SWT.RADIO | SWT.LEFT);
    this.gamepadController = gc;
    setText(text);
    setBounds(bounds[0], bounds[1], bounds[2], bounds[3]);

    addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        final GamepadType type = GamepadType.valueOf(GamepadRadioButton.this
                .getText().toUpperCase());
        gamepadController.setGamepadType(type);
        onSelect(type, e);
      }
    });
  }
  
  /** The class Button is not supposed to be subclassed. */
  @Override
  protected void checkSubclass() { /* Ignore class validator */ }

  /** Additional behavior */
  protected void onSelect(final GamepadType type, final SelectionEvent e) { }

  /**
   * @param gamepadController
   */
  public void setGamepadController(final GamepadController gamepadController) {
    this.gamepadController = gamepadController;
  }
}
