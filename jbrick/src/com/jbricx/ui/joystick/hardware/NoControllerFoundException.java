/**
 * 
 */
package com.jbricx.ui.joystick.hardware;


/**
 * @author byktol
 */
public class NoControllerFoundException extends Exception {
  private static final long serialVersionUID = 1L;

  public NoControllerFoundException() {
    super("The controller was not found.");
  }
}
