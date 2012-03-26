/**
 * 
 */
package com.jbricx.ui.joystick.hardware;



/**
 * @author byktol
 */
public class XboxGamepadTest {
  GamepadController gc;
  XboxGamepad xbox;
  GamepadConnector conn;

  @Before
  public void setUp() throws Exception {
    gc = mock(GamepadController.class);
    conn = mock(GamepadConnector.class);

    xbox = new XboxGamepad(gc);
    xbox.setGamepad(conn);
    when(conn.poll()).thenReturn(true);
  }

  @After
  public void tearDown() throws Exception {
    xbox.stop();
    xbox = null;
    gc = null;
    conn = null;
  }

  @Test
  public void testTiltNorthWest() throws Exception {
    // Stub what would happen when polling the controller
    when(conn.getXYStickDirection()).thenReturn(GamepadDirection.NW);
    when(conn.getButtons()).thenReturn(new boolean[0]);
    // Start the actual polling
    xbox.initialize();
    // Let the XboxGamepad class poll the mock GamepadConnector
    Thread.sleep(210);
    // Verify the gamepad's response. The polling is an infinite loop thread.
    verify(gc, new AtLeast(1)).turnForwardCCW();
  }
  
  @Test
  public void testTiltNorth() throws Exception {
    when(conn.getXYStickDirection()).thenReturn(GamepadDirection.NORTH);
    when(conn.getButtons()).thenReturn(new boolean[0]);
    xbox.initialize();
    Thread.sleep(210);
    verify(gc, new AtLeast(1)).forward();
  }
  
  @Test
  public void testTiltNorthEast() throws Exception {
    when(conn.getXYStickDirection()).thenReturn(GamepadDirection.NE);
    when(conn.getButtons()).thenReturn(new boolean[0]);
    xbox.initialize();
    Thread.sleep(210);
    verify(gc, new AtLeast(1)).turnForwardCW();
  }
  
  @Test
  public void testTiltEast() throws Exception {
    when(conn.getXYStickDirection()).thenReturn(GamepadDirection.EAST);
    when(conn.getButtons()).thenReturn(new boolean[0]);
    xbox.initialize();
    Thread.sleep(210);
    verify(gc, new AtLeast(1)).rotateCW();
  }
  
  @Test
  public void testTiltSouthEast() throws Exception {
    when(conn.getXYStickDirection()).thenReturn(GamepadDirection.SE);
    when(conn.getButtons()).thenReturn(new boolean[0]);
    xbox.initialize();
    Thread.sleep(210);
    verify(gc, new AtLeast(1)).turnBackwardsCW();
  }
  
  @Test
  public void testTiltSouth() throws Exception {
    when(conn.getXYStickDirection()).thenReturn(GamepadDirection.SOUTH);
    when(conn.getButtons()).thenReturn(new boolean[0]);
    xbox.initialize();
    Thread.sleep(210);
    verify(gc, new AtLeast(1)).backwards();
  }
  
  @Test
  public void testTiltSouthWest() throws Exception {
    when(conn.getXYStickDirection()).thenReturn(GamepadDirection.SW);
    when(conn.getButtons()).thenReturn(new boolean[0]);
    xbox.initialize();
    Thread.sleep(210);
    verify(gc, new AtLeast(1)).turnBackwardsCCW();
  }
  
  @Test
  public void testTiltWest() throws Exception {
    when(conn.getXYStickDirection()).thenReturn(GamepadDirection.WEST);
    when(conn.getButtons()).thenReturn(new boolean[0]);
    xbox.initialize();
    Thread.sleep(210);
    verify(gc, new AtLeast(1)).rotateCCW();
  }

  @Test
  public void testTiltCenter() throws Exception {
    when(conn.getXYStickDirection()).thenReturn(GamepadDirection.NONE);
    when(conn.getButtons()).thenReturn(new boolean[0]);
    xbox.initialize();
    Thread.sleep(210);
    verify(gc, new AtLeast(1)).stop();
  }

  @Test
  public void testPressButtonA() throws Exception {
    when(conn.getXYStickDirection()).thenReturn(GamepadDirection.NONE);
    when(conn.getButtons()).thenReturn(new boolean[] {true});
    xbox.initialize();
    Thread.sleep(210);
    verify(gc, new AtLeast(1)).honk();
  }

  @Test
  public void testPressButtonB() throws Exception {
    when(conn.getXYStickDirection()).thenReturn(GamepadDirection.NONE);
    when(conn.getButtons()).thenReturn(new boolean[] {false, true});
    xbox.initialize();
    Thread.sleep(210);
    verify(gc, new AtLeast(1)).honk2();
  }

  @Test
  public void testPressButtonX() throws Exception {
    when(conn.getXYStickDirection()).thenReturn(GamepadDirection.NONE);
    when(conn.getButtons()).thenReturn(new boolean[] {false, false, true});
    xbox.initialize();
    Thread.sleep(210);
    verify(gc, new AtLeast(1)).honk3();
  }
  
  @Test
  public void testPressButtonY() throws Exception {
    when(conn.getXYStickDirection()).thenReturn(GamepadDirection.NONE);
    when(conn.getButtons()).thenReturn(new boolean[] {false, false, false, true});
    xbox.initialize();
    Thread.sleep(210);
    verify(gc, new AtLeast(1)).honk4();
  }

  @Test
  public void testPressButtonLB() throws Exception {
    when(conn.getXYStickDirection()).thenReturn(GamepadDirection.NONE);
    when(conn.getButtons()).thenReturn(new boolean[] {false, false, false, false, true});
    xbox.initialize();
    Thread.sleep(210);
    verify(gc, new AtLeast(1)).decreaseSpeed();
  }

  @Test
  public void testPressButtonRB() throws Exception {
    when(conn.getXYStickDirection()).thenReturn(GamepadDirection.NONE);
    when(conn.getButtons()).thenReturn(new boolean[] {false, false, false, false, false, true});
    xbox.initialize();
    Thread.sleep(210);
    verify(gc, new AtLeast(1)).increaseSpeed();
  }

  @Test
  public void testNoGamepad() throws Exception {
    when(conn.poll()).thenReturn(false);
    xbox.initialize();

    verify(gc, new Times(0)).forward();
    verify(gc, new Times(0)).increaseSpeed();
  }

  @Test
  public void testGamepadType() throws Exception {
    assertEquals(GamepadType.XBOX, xbox.getGamepadType());
  }
}
