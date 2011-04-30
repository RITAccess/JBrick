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
    wiiMain = new WiiMain();
    new Thread(wiiMain).start();
  }

  @Override
  public void stop() {
    wiiMain.killWiiThreads();
  }

  @Override
  public GamepadType getGamepadType() {
    return GamepadType.WII;
  }
  
}
