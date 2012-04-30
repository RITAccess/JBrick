/**
 * 
 */
package com.jbricx.ui.piano;

/**
 * @author Abhishek Shrestha
 * 
 */
public class KeyNotMappedExeption extends Exception {

  private static final long serialVersionUID = 1L;

  public KeyNotMappedExeption(final String message) {
    super(message + " No Piano key has been mapped for this character!");
  }
}