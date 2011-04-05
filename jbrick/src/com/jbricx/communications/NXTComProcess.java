/**
 * 
 */
package com.jbricx.communications;

import com.jbricx.communications.enums.ConnectionType;
import com.jbricx.communications.exceptions.NXTNotFoundException;
import com.jbricx.communications.exceptions.UnableToCreateNXTException;

/**
 * Manages a single connection to the brick and polls the port to verify it's
 * connected.
 * 
 * @author byktol
 */
public class NXTComProcess {
  private static final int sleepTime = 3000;

  /**
   * Low-level abstraction to the interactions with the brick.
   */
  private NXTConnection connection;
  /**
   * Poll the port for the specific connection.
   */
  private Thread thread;

  /**
   * Creates a thread used for polling the port/connection status.
   * @return a new thread that polls the connection.
   */
  private Thread createConnectionPollingThread() {
    return new Thread() {
      @Override
      public void run() {
        boolean running = true;
        while (running) {

          try {
            Thread.sleep(sleepTime);
            running = connection.isConnected();

            if (!running) {
              NXTManager.getInstance().disconnect(connection.getConnectionType().getName());
              NXTManager.getInstance().verifyLastDisconnect();
            }

          } catch (InterruptedException e) {
            // IGNORE: The disconnect() method intentionally interrupts the
            // thread.
          }

        } // end of while
      } // end of run()
    };
  }

  /**
   * Creates a new connection of the specified type.
   * @param type the type of connection to create.
   * @return whether the connection to the brick was successful or not.
   * @see ConnectionType
   */
  public boolean connect(final ConnectionType type) {
    boolean success = false;
    try {
      connection = new NXTConnectionImpl(type.getName());
      connection.playSound(2000, 200);

      if (thread == null || !thread.isAlive()) {
        thread = createConnectionPollingThread();
        thread.start();        
      }
      success = true;

    } catch (final NXTNotFoundException e) {
      e.printStackTrace();
      success = false;

    } catch (final UnableToCreateNXTException e) {
      e.printStackTrace();
      success = false;

    }

    return success;
  }

  /**
   * Disconnects the brick.
   */
  public void disconnect() {
    if (connection != null) {
      connection.disconnect();
    }

    if (thread != null) {
      thread.interrupt();
    }
  }

  /**
   * @return the low-level connection interface
   */
  public NXTConnection getConnection() {
    return connection;
  }

  /**
   * @return whether the connection to the brick is active or not.
   */
  public boolean isRunning() {
    return (thread != null) && thread.isAlive();
  }

  /**
   * @return whether the Lego's Fantom Driver was successfully loaded. This
   * depends on the class implementing the low-level interface.
   */
  protected static boolean isFantomDriverLoaded() {
    return NXTConnectionImpl.isFantomDriverLoaded();
  }
}
