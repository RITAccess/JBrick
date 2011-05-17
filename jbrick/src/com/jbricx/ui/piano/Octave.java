/**
 * 
 */
package com.jbricx.ui.piano;

/**
 * Represents the octave scale of music.
 * 
 * @author Abhishek Shrestha
 * 
 */
public class Octave {
  /* not all the notes can be played in octave 10 (beyond D) */
  final static float[] A_FREQUENCIES = { 27.5f, 55, 110, 220, 440, 880, 1760,
      3520, 7040, 14080 };
  /* usually octave 4 is the standard scale */
  public final int DEFAULT_OCTAVE = 4;
  public final static int MIN_OCTAVE = 0;
  public final static int MAX_OCTAVE = 9;

  private int value;

  public Octave() throws OctaveScaleOutofBoundsException {
    this(1);
  }

  public Octave(int value) throws OctaveScaleOutofBoundsException {
    if (value > MAX_OCTAVE || value < MIN_OCTAVE) {
      throw new OctaveScaleOutofBoundsException(value + "");
    } else {
      this.setValue(value);
    }
  }

  public void setValue(int value) {
    this.value = value;
  }

  public int getValue() {
    return value;
  }

  public float getFrequencyOfA() {
    return A_FREQUENCIES[value];
  }
}
