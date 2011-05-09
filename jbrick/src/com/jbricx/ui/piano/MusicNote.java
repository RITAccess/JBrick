package com.jbricx.ui.piano;

/**
 * An abstract Flyweight to represent a music note.
 * 
 * @author Abhishek Shrestha
 * 
 */
public interface MusicNote {
  /**
   * return a float value representing frequency of the music note depending upon
   * the context or the extrinsic state
   * 
   * @param octave
   */
  public float getFrequency(Octave octave);
}
