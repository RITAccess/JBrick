package com.jbricx.ui.joystick.hardware;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.internal.verification.Times;

import com.jbricx.communications.NXTGadgetManager;
import com.jbricx.communications.enums.Motor;

public class GamepadControllerTest {
  private static final String DIRECTION_RIGHT_PROPERTY = "directionRight";
  private static final String DIRECTION_LEFT_PROPERTY = "directionLeft";

  private GamepadController gc;
  private GamepadControllerObserver observer;
  private NXTGadgetManager nxt;

  @Before
  public void setUp() throws Exception {
    gc = new GamepadController();
    nxt = mock(NXTGadgetManager.class);
    observer = mock(GamepadControllerObserver.class);

    // Set a mock object for the NXTGadgetManager interface
    final Field nxtmng = gc.getClass().getDeclaredField("nxt");
    nxtmng.setAccessible(true);
    nxtmng.set(gc, nxt);

    // Set a mock observer of type GamepadControllerObserver
    gc.registerObserver(observer);
  }

  @After
  public void tearDown() throws Exception {
    gc = null;
    nxt = null;
    observer = null;
  }

  @Test
  public void testDefaults() throws Exception {
    // Default right motor is B
    assertEquals(Motor.MOTOR_B, gc.getMotorRight());
    // Default left motor is C
    assertEquals(Motor.MOTOR_C, gc.getMotorLeft());
    // Default gamepad type is NONE
    assertEquals(GamepadType.NONE, gc.getGamepadType());
    // Default reversed motor left is false
    assertEquals(false, gc.getReversedMotorLeft());
    // Default reversed motor right is false
    assertEquals(false, gc.getReversedMotorRight());
  }

  @Test
  public void testSpeedLimitUpper() throws Exception {
    gc.setSpeed(100);
    // We just set it to 100
    assertEquals(100, gc.getSpeed());
    // Decrease speed
    gc.increaseSpeed();
    // Speed has to be the maximum 100
    assertEquals(100, gc.getSpeed());
    // Observers are updated
    verify(observer).updateInterface();

    // Set the speed above the limit
    gc.setSpeed(999);
    // The upper (maximum) limit is 100
    assertEquals(100, gc.getSpeed());
    // he number of actual invocations is 2.
    // Notice a call to setSpeed() at the top of this test. That's why.
    verify(nxt, new Times(2)).motorOn(gc.getMotorLeft(), 0);
    verify(nxt, new Times(2)).motorOn(gc.getMotorRight(), 0);
  }

  @Test
  public void testSpeedLimitLower() throws Exception {
    // Default speed is set to 60
    assertEquals(60, gc.getSpeed());
    // Decrease speed
    gc.decreaseSpeed();
    // Speed has to be the minimum 60
    assertEquals(60, gc.getSpeed());
    // Observers are updated
    verify(observer).updateInterface();

    // Set the speed above the limit
    gc.setSpeed(0);
    // The lower (minimum) limit is 60
    assertEquals(60, gc.getSpeed());
    // The motors were turned on
    verify(nxt).motorOn(gc.getMotorLeft(), 0);
    verify(nxt).motorOn(gc.getMotorRight(), 0);
  }

  /* moving, stopping, changing gamepad */
  @Test
  public void testSettingMotors() throws Exception {
    gc.setMotorLeft(Motor.MOTOR_A);
    assertEquals(Motor.MOTOR_A, gc.getMotorLeft());

    gc.setMotorRight(Motor.MOTOR_C);
    assertEquals(Motor.MOTOR_C, gc.getMotorRight());

    gc.setReversedMotorLeft(true);
    assertEquals(true, gc.getReversedMotorLeft());
    gc.setReversedMotorLeft(false);
    assertEquals(false, gc.getReversedMotorLeft());
    
    gc.setReversedMotorRight(true);
    assertEquals(true, gc.getReversedMotorRight());
    gc.setReversedMotorRight(false);
    assertEquals(false, gc.getReversedMotorRight());
  }

  @Test
  public void testForward() throws Exception {
    Field directionLeft = gc.getClass().getDeclaredField(DIRECTION_LEFT_PROPERTY);
    directionLeft.setAccessible(true);

    Field directionRight = gc.getClass().getDeclaredField(DIRECTION_RIGHT_PROPERTY);
    directionRight.setAccessible(true);

    gc.forward();
    assertEquals(1, directionLeft.getInt(gc));
    assertEquals(1, directionRight.getInt(gc));
    verify(nxt).motorOn(gc.getMotorLeft(), gc.getSpeed());
    verify(nxt).motorOn(gc.getMotorRight(), gc.getSpeed());
  }

  @Test
  public void testBackwards() throws Exception {
    Field directionLeft = gc.getClass().getDeclaredField(DIRECTION_LEFT_PROPERTY);
    directionLeft.setAccessible(true);
    
    Field directionRight = gc.getClass().getDeclaredField(DIRECTION_RIGHT_PROPERTY);
    directionRight.setAccessible(true);

    gc.backwards();
    assertEquals(-1, directionLeft.getInt(gc));
    assertEquals(-1, directionRight.getInt(gc));
    verify(nxt).motorOn(gc.getMotorLeft(), -gc.getSpeed());
    verify(nxt).motorOn(gc.getMotorRight(), -gc.getSpeed());
  }

  @Test
  public void testReversedForwards() throws Exception {
    Field reversedLeft = gc.getClass().getDeclaredField("reversedMotorLeft");
    reversedLeft.setAccessible(true);
    gc.setReversedMotorLeft(true);

    Field reversedRight = gc.getClass().getDeclaredField("reversedMotorRight");
    reversedRight.setAccessible(true);
    gc.setReversedMotorRight(true);

    gc.forward();
    assertEquals(-1, reversedLeft.getInt(gc));
    assertEquals(-1, reversedRight.getInt(gc));
    verify(nxt).motorOn(gc.getMotorLeft(), -gc.getSpeed());
    verify(nxt).motorOn(gc.getMotorRight(), -gc.getSpeed());
  }
  
  @Test
  public void testReversedBackwards() throws Exception {
    Field reversedLeft = gc.getClass().getDeclaredField("reversedMotorLeft");
    reversedLeft.setAccessible(true);
    gc.setReversedMotorLeft(true);

    Field reversedRight = gc.getClass().getDeclaredField("reversedMotorRight");
    reversedRight.setAccessible(true);
    gc.setReversedMotorRight(true);

    gc.backwards();
    assertEquals(-1, reversedLeft.getInt(gc));
    assertEquals(-1, reversedRight.getInt(gc));
    verify(nxt).motorOn(gc.getMotorLeft(), gc.getSpeed());
    verify(nxt).motorOn(gc.getMotorRight(), gc.getSpeed());
  }

  @Test
  public void testRotateCW() throws Exception {
    Field directionLeft = gc.getClass().getDeclaredField(DIRECTION_LEFT_PROPERTY);
    directionLeft.setAccessible(true);

    Field directionRight = gc.getClass().getDeclaredField(DIRECTION_RIGHT_PROPERTY);
    directionRight.setAccessible(true);

    gc.rotateCW();
    assertEquals(1, directionLeft.getInt(gc));
    assertEquals(-1, directionRight.getInt(gc));
    verify(nxt).motorOn(gc.getMotorLeft(), gc.getSpeed());
    verify(nxt).motorOn(gc.getMotorRight(), -gc.getSpeed());
  }

  @Test
  public void testRotateCCW() throws Exception {
    Field directionLeft = gc.getClass().getDeclaredField(DIRECTION_LEFT_PROPERTY);
    directionLeft.setAccessible(true);

    Field directionRight = gc.getClass().getDeclaredField(DIRECTION_RIGHT_PROPERTY);
    directionRight.setAccessible(true);

    gc.rotateCCW();
    assertEquals(-1, directionLeft.getInt(gc));
    assertEquals(1, directionRight.getInt(gc));
    verify(nxt).motorOn(gc.getMotorLeft(), -gc.getSpeed());
    verify(nxt).motorOn(gc.getMotorRight(), gc.getSpeed());
  }

  @Test
  public void testForwardCW() throws Exception {
    Field directionLeft = gc.getClass().getDeclaredField(DIRECTION_LEFT_PROPERTY);
    directionLeft.setAccessible(true);

    Field directionRight = gc.getClass().getDeclaredField(DIRECTION_RIGHT_PROPERTY);
    directionRight.setAccessible(true);

    gc.turnForwardCW();
    assertEquals(1, directionLeft.getInt(gc));
    assertEquals(0, directionRight.getInt(gc));
    verify(nxt).motorOn(gc.getMotorLeft(), gc.getSpeed());
    verify(nxt).motorOn(gc.getMotorRight(), 0);
  }
  
  @Test
  public void testForwardCCW() throws Exception {
    Field directionLeft = gc.getClass().getDeclaredField(DIRECTION_LEFT_PROPERTY);
    directionLeft.setAccessible(true);

    Field directionRight = gc.getClass().getDeclaredField(DIRECTION_RIGHT_PROPERTY);
    directionRight.setAccessible(true);

    gc.turnForwardCCW();
    assertEquals(0, directionLeft.getInt(gc));
    assertEquals(1, directionRight.getInt(gc));
    verify(nxt).motorOn(gc.getMotorLeft(), 0);
    verify(nxt).motorOn(gc.getMotorRight(), gc.getSpeed());
  }
  
  @Test
  public void testBackwardsCW() throws Exception {
    Field directionLeft = gc.getClass().getDeclaredField(DIRECTION_LEFT_PROPERTY);
    directionLeft.setAccessible(true);

    Field directionRight = gc.getClass().getDeclaredField(DIRECTION_RIGHT_PROPERTY);
    directionRight.setAccessible(true);

    gc.turnBackwardsCW();
    assertEquals(0, directionLeft.getInt(gc));
    assertEquals(-1, directionRight.getInt(gc));
    verify(nxt).motorOn(gc.getMotorLeft(), 0);
    verify(nxt).motorOn(gc.getMotorRight(), -gc.getSpeed());
  }
  
  @Test
  public void testBackwardsCCW() throws Exception {
    Field directionLeft = gc.getClass().getDeclaredField(DIRECTION_LEFT_PROPERTY);
    directionLeft.setAccessible(true);

    Field directionRight = gc.getClass().getDeclaredField(DIRECTION_RIGHT_PROPERTY);
    directionRight.setAccessible(true);

    gc.turnBackwardsCCW();
    assertEquals(-1, directionLeft.getInt(gc));
    assertEquals(0, directionRight.getInt(gc));
    verify(nxt).motorOn(gc.getMotorLeft(), -gc.getSpeed());
    verify(nxt).motorOn(gc.getMotorRight(), 0);
  }

  @Test
  public void testStop() throws Exception {
    Field directionLeft = gc.getClass().getDeclaredField(DIRECTION_LEFT_PROPERTY);
    directionLeft.setAccessible(true);

    Field directionRight = gc.getClass().getDeclaredField(DIRECTION_RIGHT_PROPERTY);
    directionRight.setAccessible(true);

    when(nxt.isConnected()).thenReturn(true);
    gc.stop();
    assertEquals(0, directionLeft.getInt(gc));
    assertEquals(0, directionRight.getInt(gc));
    verify(nxt).motorOff(gc.getMotorLeft());
    verify(nxt).motorOff(gc.getMotorRight());
  }

  /**
   * @throws Exception
   */
  @Test
  public void testStopWhenNotConnected() throws Exception {
    Field directionLeft = gc.getClass().getDeclaredField(DIRECTION_LEFT_PROPERTY);
    directionLeft.setAccessible(true);
    
    Field directionRight = gc.getClass().getDeclaredField(DIRECTION_RIGHT_PROPERTY);
    directionRight.setAccessible(true);

    /* When physically disconnecting a brick, the methods to stop the motors
     * shouldn't be invoked because there's no active connection. */
    when(nxt.isConnected()).thenReturn(false);
    gc.stop();
    assertEquals(0, directionLeft.getInt(gc));
    assertEquals(0, directionRight.getInt(gc));
    verify(nxt, never()).motorOff(gc.getMotorLeft());
    verify(nxt, never()).motorOff(gc.getMotorRight());
  }

  @Test
  public void testHonks() throws Exception {
    gc.honk();
    verify(nxt).playTone(3000, 200);
    gc.honk2();
    verify(nxt).playTone(2000, 200);
    gc.honk3();
    verify(nxt).playTone(350, 320);
    gc.honk4();
    verify(nxt).playTone(147, 160);
  }
}
