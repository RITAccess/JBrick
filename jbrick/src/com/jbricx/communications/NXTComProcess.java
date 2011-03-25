/**
 * 
 */
package com.jbricx.communications;

import com.jbricx.communications.enums.ConnectionType;
import com.jbricx.communications.exceptions.NXTNotFoundException;
import com.jbricx.communications.exceptions.UnableToCreateNXTException;

/**
 * @author byktol
 */
public class NXTComProcess {
  private static final int sleepTime = 3000;

  private NXTConnection connection;
  private Thread thread;
  private volatile boolean running;

  /**
   * @param name
   * @return
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
   * 
   * @param type
   * @return
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

    } catch (NXTNotFoundException e) {
      e.printStackTrace();
      running = false;

    } catch (UnableToCreateNXTException e) {

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
   * @return the connection
   */
  public NXTConnection getConnection() {
    return connection;
  }

  /**
   * @return the running
   */
  public boolean isRunning() {
    return running;
  }

  /**
   * @return
   */
  protected static boolean isFantomDriverLoaded() {
    return NXTConnectionImpl.isFantomDriverLoaded();
  }
}
