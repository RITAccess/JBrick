/**
 * 
 */
package com.jbricx.swing.communications;

import com.jbricx.communications.enums.ConnectionType;

/**
 * Defines the methods for connecting and disconnecting to a Lego Brick.
 * 
 * @author byktol
 */
public interface NXTConnectionManager {

  ExitStatus compile(String filename);

  ExitStatus run(String filename);

  ExitStatus getRunningProgram();

  ExitStatus downloadFile(String filename);

  ExitStatus deleteFile(String filename);

  boolean isConnected();

  NXTConnectionManager connect(ConnectionType type);

  void disconnect();

  /**
   * does the physical disconnect and also removes from the list
   * 
   * @param name
   */
  void disconnect(String name);

  void notifyAllObservers(boolean connected);
}
