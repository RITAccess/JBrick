/**
 * 
 */
package com.jbricx.ui.piano;

/**
 * @author user
 * 
 */
public class NoteNotFoundException extends Exception {
  private static final long serialVersionUID = 1L;

  public NoteNotFoundException(final String message) {
    super(message + " such a note does not exist!");
  }
}
