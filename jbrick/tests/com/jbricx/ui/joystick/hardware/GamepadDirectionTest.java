// 2-19, we believe that the game controller code was removed at some point
// and that these tests maybe should've been deleted when that happened.
// We are commenting them out for now

///**
// * 
// */
//package com.jbricx.ui.joystick.hardware;
//
//
///**
// * @author byktol
// */
//public class GamepadDirectionTest {
//
//  @Test
//  public void testDefaultDirectionFromCoords() {
//    GamepadDirection d = GamepadDirection.getDirection(-7, 3);
//    assertEquals(GamepadDirection.NONE, d);
//
//    d = GamepadDirection.getDirection(2, -1);
//    assertEquals(GamepadDirection.NONE, d);
//  }
//
//  @Test
//  public void testDirectionNoneFromCoords() {
//    GamepadDirection d = GamepadDirection.getDirection(0, 0);
//    assertEquals(GamepadDirection.NONE, d);
//  }
//
//  @Test
//  public void testDefaultDirectionFromPOV() {
//    GamepadDirection d = GamepadDirection.getDirection(-10f);
//    assertEquals(GamepadDirection.NONE, d);
//
//    d = GamepadDirection.getDirection(2f);
//    assertEquals(GamepadDirection.NONE, d);
//  }
//
//  @Test
//  public void testDirectionNoneFromPOV() {
//    GamepadDirection d = GamepadDirection.getDirection(POV.CENTER);
//    assertEquals(GamepadDirection.NONE, d);
//  }
//
//}
