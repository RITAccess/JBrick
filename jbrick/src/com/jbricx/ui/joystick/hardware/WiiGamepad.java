/**
 * 
 */
package com.jbricx.ui.joystick.hardware;

import com.jbricx.ui.joystick.wii.WiiMain;

/**
 * @author byktol
 */
public class WiiGamepad implements Gamepad {
  private WiiMain wiiMain;

  public WiiGamepad(final GamepadController gc) {
  }

  @Override
  public void stop() {
    wiiMain.killWiiThreads();
  }

  @Override
  public GamepadType getGamepadType() {
    return GamepadType.WII;
  }

  @Override
  public void initialize() throws NoControllerFoundException {
    wiiMain = new WiiMain();
    new Thread(wiiMain).start();
  }
}
