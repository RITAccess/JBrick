/**
 * 
 */
package com.jbricx.ui.piano;

import java.text.DecimalFormat;

/**
 * A concrete Flyweight implementation of {@link Key}. {@link MusicNote} is the
 * intrinsic state while {@link Octave} is the extrinsic object
 * 
 * @see KeyFactory
 * @see Piano
 * @see MusicNote
 * @see Octave
 * 
 * @author Abhishek Shrestha
 */
public class PianoKey implements Key {
  private MusicNote musicNote; /* intrinsic state */

  public PianoKey(String name, int step, boolean isSharp) {
    musicNote = new MusicNote(name, step, isSharp);
  }

  public MusicNote getMusicNote() {
    return musicNote;
  }

  public String getName() {
    return musicNote.getName();
  }

  public boolean isBlack() {
    return musicNote.isSharp();
  }

  public boolean isWhite() {
    return !musicNote.isSharp();
  }

  @Override
  public float getFrequency(Octave octave) { /* octave is extrinsic state */
    /*
     * the formula to find frequency is:---------------------------------------
     * f = 2 ^ (n / 12) * Frequency of A note at Octave octave -----------------
     * f = 2 ^ (step / 12) * A_FREQUENCIES[octave]
     */
    DecimalFormat twoDecPlace = new DecimalFormat("#.##");
    double f = (Math.pow(2, (musicNote.getStep() / 12.d)) * octave
        .getFrequencyOfA());

    return Float.valueOf(twoDecPlace.format(f));
  }
}
