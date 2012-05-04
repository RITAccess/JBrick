package com.jbricx.ui.piano;

/**
 * A class that represents the real world music note.
 * 
 * @author Abhishek Shrestha
 * 
 */
public class MusicNote {
  private String name;
  /*
   * represent how far this note is from the A note. E.g. B is 2 notes away from
   * A note (in the same octave) hence its step is 2 whereas C is 3 notes away
   * in the next octave as well as -9 notes in an octave before see
   * http://en.wikipedia.org/wiki/Note
   */
  private int step;
  private boolean isSharp;

  public MusicNote(String name, int step, boolean isSharp) {
    setName(name);
    setStep(step);
    setSharp(isSharp);
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setStep(int step) {
    this.step = step;
  }

  public int getStep() {
    return step;
  }

  public void setSharp(boolean isSharp) {
    this.isSharp = isSharp;
  }

  public boolean isSharp() {
    return isSharp;
  }

  public boolean isFlat() {
    return isSharp;
  }
}
