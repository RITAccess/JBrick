/**
 * 
 */
package com.jbricx.ui.piano;

/**
 * @author Abhishek Shrestha
 * 
 */
public class OctaveNotMappedException extends Exception {
  private static final long serialVersionUID = 1L;

  public OctaveNotMappedException(final String message) {
    super(message + " No octave has been mapped for this character!");
  }
}
