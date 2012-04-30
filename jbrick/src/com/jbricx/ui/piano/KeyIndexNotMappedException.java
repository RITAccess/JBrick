/**
 * 
 */
package com.jbricx.ui.piano;

/**
 * @author Abhishek Shrestha
 * 
 */
public class KeyIndexNotMappedException extends Exception {
  private static final long serialVersionUID = 1L;

  public KeyIndexNotMappedException(String message) {
    super(message + " No keyIndex has been mapped for this character!");
  }
}
