package com.jbricx.ui.piano;

/**
 * Another concrete Flyweight implementation. It is used to denote a dead note
 * which has no frequency.
 * 
 * @author Abhishek Shrestha
 * @see PianoKey
 * 
 */
public class RestKey implements Key {

  @Override
  public float getFrequency(Octave octave) {
    return 0;
  }
}
