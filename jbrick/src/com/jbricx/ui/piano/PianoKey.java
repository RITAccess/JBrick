/**
 * 
 */
package com.jbricx.ui.piano;

/**
 * For better modeling of piano, instead of having to directly expose the
 * {@link MusicNote} and its properties like "sharp" represent it in the way
 * keys appear in real piano (white and balck).
 * 
 * @author Abhishek Shrestha
 */
public class PianoKey extends MusicNoteImpl {

  public PianoKey(String name, int step, boolean isSharp) {
    super(name, step, isSharp);
  }

  public boolean isBlack() {
    return isSharp();
  }

  public boolean isWhite() {
    return !isSharp();
  }
}
