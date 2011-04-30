/**
 * 
 */
package com.jbricx.ui.joystick.hardware;

import net.java.games.input.Component.POV;

/**
 * The different directions a gamepad can by intented to go to.
 * 
 * @author byktol
 */
public enum GamepadDirection {
  NW    (-1, -1, POV.UP_LEFT),
  NORTH ( 0, -1, POV.UP),
  NE    ( 1, -1, POV.UP_RIGHT),
  WEST  (-1,  0, POV.LEFT),
  NONE  ( 0,  0, POV.CENTER),
  EAST  ( 1,  0, POV.RIGHT),
  SW    (-1,  1, POV.DOWN_LEFT),
  SOUTH ( 0,  1, POV.DOWN),
  SE    ( 1,  1, POV.DOWN_RIGHT),
  ;

  private final int x, y;
  private final float pov;

  private GamepadDirection(int x, int y, float pov) {
    this.x = x;
    this.y = y;
    this.pov = pov;
  }

  /**
   * @param povDirection
   * @return the direction based on a numerical value
   * @see POV
   */
  public static GamepadDirection getDirection(float povDirection) {

    for (GamepadDirection direction : values()) {
      if ((direction.pov == povDirection)) {
        return direction;
      }
    }

    // assume center
    return NONE;
  }

  /**
   * @param x
   * @param y
   * @return the direction based on the x and y coordinates.
   */
  public static GamepadDirection getDirection(int x, int y) {
    for (GamepadDirection direction : values()) {
      if (direction.y == y && direction.x == x) {
        return direction;
      }
    }

    // assume center
    return NONE;
  }
}