/**
 * 
 */
package com.jbricx.ui.joystick.hardware;

import java.util.ArrayList;
import java.util.List;

import com.jbricx.communications.NXTGadgetManager;
import com.jbricx.communications.NXTManager;
import com.jbricx.communications.enums.Motor;
import com.jbricx.ui.joystick.RecordJoystick;

/**
 * Defines the interactions between the NXT Brick, through the
 * {@link NXTGadgetManager}, and the different types of gamepads available.
 * This is supposed to work similar to a mediator pattern to avoid the direct
 * interaction of the GUI and the NXTBrick.
 * 
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

  private RecordJoystick recordJoystick = new RecordJoystick();
  private long waitStart = 0;

  /**
   * Move both of the motors forward, e.g. roughly equivalent to walking while
   * moving both feet forward.
   */
  public void forward() {
    directionLeft = 1 * reversedMotorLeft;
    directionRight = 1 * reversedMotorRight;
    recordJoystick.recordForward(motorLeft.getName().charAt(6), motorRight.getName().charAt(6), speed);
    move();
  }

  /**
   * Move both of the motors backwards, e.g. roughly equivalent to walking while
   * moving both feet backwards.
   */
  public void backwards() {
    directionLeft = -1 * reversedMotorLeft;
    directionRight = -1 * reversedMotorRight;
    recordJoystick.recordBackward(motorLeft.getName().charAt(6), motorRight.getName().charAt(6), speed);
    move();
  }

  /**
   * Moves only one motor while the other one is stopped. The movement seen from
   * the above would be in a Counter-Clockwise way. While keeping your left foot
   * stationary, try to walk forward with your right foot, you should be able to
   * draw circles while spinning using a leg as an axis.
   */
  public void turnForwardCCW() {
    directionLeft = 0;
    directionRight = 1 * reversedMotorRight;
    recordJoystick.recordTurnFwCCW(motorLeft.getName().charAt(6), motorRight.getName().charAt(6), speed);
    move();
  }

  /**
   * Moves only one motor while the other one is stopped. Use your right leg as
   * an axis and try to walk with your left leg. The result is a circle drawn
   * in a clockwise way.
   */
  public void turnForwardCW() {
    directionLeft = 1 * reversedMotorLeft;
    directionRight = 0;
    recordJoystick.recordTurnFwCW(motorLeft.getName().charAt(6), motorRight.getName().charAt(6), speed);
    move();
  }

  /**
   * Moves only one motor while the other one is stopped. Use your right leg as
   * an axis and try to walk backwards with your left leg. The result is a
   * circle drawn in a counter-clockwise way.
   */
  public void turnBackwardsCCW() {
    directionLeft = -1 * reversedMotorLeft;
    directionRight = 0;
    recordJoystick.recordTurnBwCCW(motorLeft.getName().charAt(6), motorRight.getName().charAt(6), speed);
    move();
  }

  /**
   * Moves only one motor while the other one is stopped. Use your left leg as
   * an axis and try to walk backwards with your right leg. The result is a
   * circle drawn in a clockwise way
   */
  public void turnBackwardsCW() {
    directionLeft = 0;
    directionRight = -1 * reversedMotorRight;
    recordJoystick.recordTurnBwCW(motorLeft.getName().charAt(6), motorRight.getName().charAt(6), speed);
    move();
  }

  /**
   * Moves both motors in different directions. Seen from the top, while both
   * motors are aligned in parallel, they draw a circle in a counter-clockwise
   * way.
   */
  public void rotateCCW() {
    directionLeft = -1 * reversedMotorLeft;
    directionRight = 1 * reversedMotorRight;
    recordJoystick.recordRotateCCW(motorLeft.getName().charAt(6), motorRight.getName().charAt(6), speed);
    move();
  }

  /**
   * Moves both motors in different directions. Seen from the top, while both
   * motors are aligned in parallel, they draw a circle in a clockwise way.
   */
  public void rotateCW() {
    directionLeft = 1 * reversedMotorLeft;
    directionRight = -1 * reversedMotorRight;
    recordJoystick.recordRotateCW(motorLeft.getName().charAt(6), motorRight.getName().charAt(6), speed);
    move();
  }

  /**
   * Starts the movement on both of the motors.
   */
  public void move() {
    waitStart = System.currentTimeMillis();
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

  /**
   * Decreases the motors' speed in intervals.
   */
  public void decreaseSpeed() {
    speed -= 10;
    if (speed < 60) {
      speed = 60;
    }
    notifyObservers();
  }

  /**
   * Increases the motors' speed in intervals.
   */
  public void increaseSpeed() {
    speed += 10;
    if (speed > 100) {
      speed = 100;
    }
    notifyObservers();
  }

  /**
   * Selects the gamepad in use.
   * 
   * @param type the gamepad that's going to be used.
   */
  public void setGamepadType(GamepadType type) {
    if (!gamepad.getGamepadType().equals(type)) {
      gamepad.stop();

      switch (type) {
        case XBOX:
          try {
            gamepad = new XboxGamepad(this);
            gamepad.initialize();
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

  /**
   * Stops the motors.
   */
  public void stop() {
	if (waitStart != 0)
		recordJoystick.recordWait(System.currentTimeMillis() - waitStart);
	
	waitStart = 0;
    directionLeft = 0;
    directionRight = 0;
    if (nxt.isConnected()) {
      nxt.motorOff(motorLeft);
      nxt.motorOff(motorRight);
    }
  }

  /**
   * Stop polling the current gamepad.
   */
  public void stopGamepadPolling() {
    gamepad.stop();
  }

  /**
   * @param m The left motor
   */
  public void setMotorLeft(Motor m) {
    this.motorLeft = m;
  }

  /**
   * @param m The right motor
   */
  public void setMotorRight(Motor m) {
    this.motorRight = m;
  }

  /**
   * @param reversed Whether the left motor is in a reverse position.
   */
  public void setReversedMotorLeft(boolean reversed) {
    this.reversedMotorLeft = reversed? -1 : 1;
  }

  /**
   * @param reversed Whether the left motor is in a reverse position.
   */
  public void setReversedMotorRight(boolean reversed) {
    this.reversedMotorRight = reversed? -1 : 1;
  }

  /**
   * @param speed The velocity the motors will move.
   */
  public void setSpeed(final int speed) {
    if (speed > 100) {
      this.speed = 100;
    } else if (speed < 60) {
      this.speed = 60;
    } else {
      this.speed = speed;
    }

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

  public RecordJoystick getRecordJoystick(){
	  return this.recordJoystick;
  }
}