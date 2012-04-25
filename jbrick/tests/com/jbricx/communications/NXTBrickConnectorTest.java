/**
 * 
 */
package com.jbricx.communications;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import com.jbricx.swing.communications.NXTBrickConnector;
import com.jbricx.swing.communications.NXTConnection;

/**
 * @author byktol
 */
public class NXTBrickConnectorTest {
  private NXTBrickConnector brick;
  private NXTConnection conn;

  @Before
  public void setUp() throws Exception {
    brick = new NXTBrickConnector();
    conn = mock(NXTConnection.class);

    Field connectionField = brick.getClass().getDeclaredField("connection");
    connectionField.setAccessible(true);
    connectionField.set(brick, conn);
  }

  @After
  public void tearDown() throws Exception {
    brick = null;
    conn = null;
  }

  @Test
  public void testDisconnect() throws Exception {
    Thread thread = mock(Thread.class);

    Field threadField = brick.getClass().getDeclaredField("thread");
    threadField.setAccessible(true);
    threadField.set(brick, thread);

    brick.disconnect();
    verify(thread).interrupt();
    verify(conn).disconnect();
  }

  @Test
  public void testGetConnection() {
    assertEquals(conn, brick.getConnection());
  }

  @Test
  public void testCreatePollingThread() throws Exception {
    Method createThread = brick.getClass().getDeclaredMethod("createConnectionPollingThread", new Class<?>[0]);
    createThread.setAccessible(true);
    Thread thread = (Thread) createThread.invoke(brick, new Object[0]);
    assertFalse(thread.isAlive());
  }

  @Test
  public void testIsRunning() throws Exception {
    assertFalse(brick.isRunning());

    Thread thread = mock(Thread.class);
    Field threadField = brick.getClass().getDeclaredField("thread");
    threadField.setAccessible(true);
    threadField.set(brick, thread);

    assertFalse(brick.isRunning());
  }

}
