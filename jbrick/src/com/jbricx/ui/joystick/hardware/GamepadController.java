/**
 * 
 */
package com.jbricx.ui.joystick.hardware;

import java.util.ArrayList;
import java.util.List;

import com.jbricx.communications.NXTGadgetManager;
import com.jbricx.communications.NXTManager;
import com.jbricx.communications.enums.Motor;

/**
 * @author byktol
 */
public class GamepadController {
  private List<GamepadControllerObserver> observers = new ArrayList<GamepadControllerObserver>(1);
  private Motor motorLeft = Motor.MOTOR_C;
  private Motor motorRight = Motor.MOTOR_B;
  private int reversedMotorLeft = 1;
  private int reversedMotorRight = 1;
  private int speed = 60;
  private Gamepad gamepad = new NoneGamepad(null);

  private NXTGadgetManager nxt = NXTManager.getInstance();
  private int directionLeft = 0;
  private int directionRight = 0;

  public void forward() {
    directionLeft = 1 * reversedMotorLeft;
    directionRight = 1 * reversedMotorRight;
    move();
  }

  public void backwards() {
    directionLeft = -1 * reversedMotorLeft;
    directionRight = -1 * reversedMotorRight;
    move();
  }

  public void turnForwardCCW() {
    directionLeft = 0;
    directionRight = 1 * reversedMotorRight;
    move();
  }

  public void turnForwardCW() {
    directionLeft = 1 * reversedMotorLeft;
    directionRight = 0;
    move();
  }

  public void turnBackwardsCCW() {
    directionLeft = -1 * reversedMotorLeft;
    directionRight = 0;
    move();
  }
  
  public void turnBackwardsCW() {
    directionRight = -1 * reversedMotorRight;
    directionLeft = 0;
    move();
  }

  public void rotateCCW() {
    directionLeft = -1 * reversedMotorLeft;
    directionRight = 1 * reversedMotorRight;
    move();
  }

  public void rotateCW() {
    directionLeft = 1 * reversedMotorLeft;
    directionRight = -1 * reversedMotorRight;
    move();
  }

  public void move() {
    nxt.motorOn(motorLeft, speed * directionLeft);
    nxt.motorOn(motorRight, speed * directionRight);
  }

  public void honk() {
    nxt.playTone(3000, 200);
  }

  public void honk2() {
    nxt.playTone(2000, 200);
  }

  public void honk3() {
    nxt.playTone(350, 320);
  }
  
  public void honk4() {
    nxt.playTone(147, 160);
  }

  public void decreaseSpeed() {
    speed -= 10;
    if (speed < 60) {
      speed = 60;
    }
    notifyObservers();
  }

  public void increaseSpeed() {
    speed += 10;
    if (speed > 100) {
      speed = 100;
    }
    notifyObservers();
  }

  public void setGamepadType(GamepadType type) {
    if (!gamepad.getGamepadType().equals(type)) {
      gamepad.stop();

      switch (type) {
        case XBOX:
          try {
            gamepad = new XboxGamepad(this);
          } catch (NoControllerFoundException e) {
            e.printStackTrace();
            setGamepadType(GamepadType.NONE);
          }
          break;
        case WII:
          gamepad = new WiiGamepad(this);
          break;
        default:
          gamepad = new NoneGamepad(this);
      }
      notifyObservers();
    }
  }

  public void stop() {
    directionLeft = 0;
    directionRight = 0;
    nxt.motorOff(motorLeft);
    nxt.motorOff(motorRight);
  }

  public void stopGamepadPolling() {
    gamepad.stop();
  }

  public void setMotorLeft(Motor m) {
    this.motorLeft = m;
  }

  public void setMotorRight(Motor m) {
    this.motorRight = m;
  }

  public void setReversedMotorLeft(boolean reversed) {
    this.reversedMotorLeft = reversed? -1 : 1;
  }

  public void setReversedMotorRight(boolean reversed) {
    this.reversedMotorRight = reversed? -1 : 1;
  }

  public void setSpeed(final int speed) {
    this.speed = speed;
    move();
  }

  public void registerObserver(final GamepadControllerObserver o) {
    this.observers.add(o);
  }

  public void notifyObservers() {
    for (GamepadControllerObserver o : observers) {
      o.updateInterface();
    }
  }

  /**
   * @return the motorLeft
   */
  public Motor getMotorLeft() {
    return motorLeft;
  }

  /**
   * @return the motorRight
   */
  public Motor getMotorRight() {
    return motorRight;
  }

  /**
   * @return the reversedMotorLeft
   */
  public boolean getReversedMotorLeft() {
    return reversedMotorLeft == -1;
  }

  /**
   * @return the reversedMotorRight
   */
  public boolean getReversedMotorRight() {
    return reversedMotorRight == -1;
  }

  /**
   * @return the speed
   */
  public int getSpeed() {
    return speed;
  }

  /**
   * @return the gamepad type
   */
  public GamepadType getGamepadType() {
    return gamepad.getGamepadType();
  }
  
}