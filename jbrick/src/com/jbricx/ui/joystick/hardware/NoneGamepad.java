/**
 * 
 */
package com.jbricx.ui.joystick.hardware;

/**
 * Dummy class for avoiding NullPointerExceptions in the application. The
 * rationale is to have an instance of this class instead of having to verify
 * whether or not a variable is null or has been assigned a value.
 * 
 * @author byktol
 */
public class NoneGamepad implements Gamepad {

  public NoneGamepad(final GamepadController gc) {
  }

  @Override
  public void stop() {
  }

  @Override
  public GamepadType getGamepadType() {
    return GamepadType.NONE;
  }

  @Override
  public void initialize() throws NoControllerFoundException {
  }
}
