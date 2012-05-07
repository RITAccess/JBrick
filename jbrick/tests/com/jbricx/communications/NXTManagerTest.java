/**
 * 
 */
package com.jbricx.communications;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

/**
 * @author byktol
 */
public class NXTManagerTest {
  private NXTManager nxtManager;
  private NXTObserver observer;
  private NXTBrickConnector connector;

  @Before
  public void setUp() throws Exception {
    Field instance = NXTManager.class.getDeclaredField("nxtManager");
    instance.setAccessible(true);

    Constructor<NXTManager> constructor = NXTManager.class.getDeclaredConstructor(new Class<?>[0]);
    constructor.setAccessible(true);
    nxtManager = (NXTManager) constructor.newInstance();
    instance.set(nxtManager, nxtManager);

    observer = mock(NXTObserver.class);
    connector = mock(NXTBrickConnector.class);
    NXTConnection connection = mock(NXTConnection.class);
    when(connection.getConnectionType()).thenReturn(ConnectionType.USB);
    when(connector.getConnection()).thenReturn(connection);

    Field connField = nxtManager.getClass().getDeclaredField("connections");
    connField.setAccessible(true);
    @SuppressWarnings("unchecked")
    Map<String, NXTBrickConnector> connections = (Map<String, NXTBrickConnector>) connField.get(nxtManager);
    connections.put(ConnectionType.USB.toString(), connector);

    Field currentField = nxtManager.getClass().getDeclaredField("currentConnection");
    currentField.setAccessible(true);
    currentField.set(nxtManager, ConnectionType.USB.toString());
  }

  @After
  public void tearDown() throws Exception {
    Field instance = nxtManager.getClass().getDeclaredField("nxtManager");
    instance.setAccessible(true);
    instance.set(nxtManager, null);
  }

  /**
   * Test method for {@link com.jbricx.communications.NXTManager#getInstance()}.
   */
  @Test
  public void testGetInstance() {
    NXTManager instance = NXTManager.getInstance();

    assertTrue(instance instanceof NXTManager);
    assertTrue(instance instanceof NXTGadgetManager);
    assertTrue(instance instanceof NXTConnectionManager);
  }

  /**
   * Test method for {@link com.jbricx.communications.NXTManager#disconnect()}.
   */
  @Test
  public void testDisconnect() throws Exception {
    Field connField = nxtManager.getClass().getDeclaredField("connections");
    connField.setAccessible(true);
    @SuppressWarnings("unchecked")
    Map<String, NXTBrickConnector> connections = (Map<String, NXTBrickConnector>) connField.get(nxtManager);
    nxtManager.disconnect();
    assertEquals(0, connections.size());
    verify(connector).disconnect();
  }

  /**
   * Test method for {@link com.jbricx.communications.NXTManager#unregister(com.jbricx.communications.NXTObserver)} &
   * {@link com.jbricx.communications.NXTManager#register(com.jbricx.communications.NXTObserver)}.
   */
  @Test
  public void testObserverRegistration() throws Exception {
    nxtManager.register(observer);
    Field observers = nxtManager.getClass().getDeclaredField("nxtObservers");
    observers.setAccessible(true);

    @SuppressWarnings("unchecked")
    List<NXTObserver> nxtObservers = (List<NXTObserver>) observers.get(nxtManager);
    assertEquals(1, nxtObservers.size());
    assertEquals(observer, nxtObservers.get(0));

    nxtManager.unregister(observer);
    assertEquals(0, nxtObservers.size());    
  }

  /**
   * Test method for {@link com.jbricx.communications.NXTManager#notifyAllObservers(boolean)}.
   * @throws Exception 
   */
  @Test
  public void testNotifyAllObservers() throws Exception {
    nxtManager.register(observer);
    Field observers = nxtManager.getClass().getDeclaredField("nxtObservers");
    observers.setAccessible(true);

    @SuppressWarnings("unchecked")
    List<NXTObserver> nxtObservers = (List<NXTObserver>) observers.get(nxtManager);
    assertEquals(1, nxtObservers.size());

    nxtManager.notifyAllObservers(true);
    verify(observer).update(true);
  }

  /**
   * Test method for {@link com.jbricx.communications.NXTManager#compile(java.lang.String)}.
   * @throws Exception 
   */
  @Test
  public void testCompile() throws Exception {
    Field compilerField = nxtManager.getClass().getDeclaredField("compilerRunner");
    compilerField.setAccessible(true);
    CompilerRunner compilerRunner = mock(CompilerRunner.class);
    compilerField.set(nxtManager, compilerRunner);

    String filename = "filename.nxc";
    nxtManager.compile(filename);
    verify(compilerRunner).compile(filename);
  }

  /**
   * Test method for {@link com.jbricx.communications.NXTManager#playTone(int, int)}.
   */
  @Test
  public void testPlayTone() {
    nxtManager.playTone(200, 300);
    verify(connector.getConnection()).playSound(200, 300);
  }

  /**
   * Test method for {@link com.jbricx.communications.NXTManager#isConnected()}.
   */
  @Test
  public void testIsConnected() {
    assertFalse(nxtManager.isConnected());
    verify(connector.getConnection()).isConnected();
  }

  /**
   * Test method for {@link com.jbricx.communications.NXTManager#stopPolling()}.
   */
  @Test
  public void testStopPolling() {
    nxtManager.stopPolling();
    verify(connector).disconnect();
  }

}
