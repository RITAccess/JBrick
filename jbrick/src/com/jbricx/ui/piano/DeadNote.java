/**
 * 
 */
package com.jbricx.ui.piano;

/**
 * A note that has no frequency and used to represent the rest.
 * 
 * @author Abhishek Shrestha
 * 
 */
public class DeadNote implements MusicNote {

  /**
   * @see com.jbricx.ui.piano.MusicNote#getFrequency(com.jbricx.ui.piano.Octave)
   */
  @Override
  public float getFrequency(Octave octave) {
    return 0;
  }

}
