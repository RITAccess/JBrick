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
   * Whether this connection is active or not.
   */
  private volatile boolean running;

  /**
   * Creates the thread used for polling the port/connection status.
   * @return a new thread that polls the connection.
   */
  private Thread createConnectionPollingThread() {
    return new Thread() {
      @Override
      public void run() {
        running = true;
        while (running) {

          try {
            Thread.sleep(sleepTime);
            running = connection.isConnected();

            // FIXME: I just don't think this is a good idea.
            NXTManager.getInstance().notifyAllObservers(running);

          } catch (InterruptedException e) {
            e.printStackTrace();
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
      running = success = true;

    } catch (final NXTNotFoundException e) {
      e.printStackTrace();
      running = false;

    } catch (final UnableToCreateNXTException e) {
      e.printStackTrace();
      running = false;

    } finally {
      
      //FIXME: I don't believe this is a good practice.
      //FIXME: What happens when 2 different bricks are connected?
      NXTManager.getInstance().notifyAllObservers(running);
    }

    return success;
  }

  /**
   * Disconnects the brick.
   */
  public void disconnect() {
    running = false;

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
    return running;
  }

  /**
   * @return whether the Lego's Fantom Driver was successfully loaded. This
   * depends on the class implementing the low-level interface.
   */
  protected static boolean isFantomDriverLoaded() {
    return NXTConnectionImpl.isFantomDriverLoaded();
  }
}
