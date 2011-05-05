/**
 * 
 */
package com.jbricx.ui.joystick.hardware;


/**
 * Defines the methods implemented by the different concrete gamepads.
 * 
 * @author byktol
 */
public interface Gamepad {
  /**
   * Stops the polling of the gamepad.
   */
  void stop();
  /**
   * @return the type of gamepad it's being implemented.
   */
  GamepadType getGamepadType();
  void initialize() throws NoControllerFoundException;
}