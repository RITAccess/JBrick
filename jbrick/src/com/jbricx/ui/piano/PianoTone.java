package com.jbricx.ui.piano;

/**
 * 
 * @author Abhishek Shrestha
 * 
 */
public class PianoTone {
  public static final int DURATION_FACTOR = 1000; /* milliseconds */
  /* of a second i.e. 1000/x milliseconds */
  public static final int[] DURATIONS = { 1, 2, 4, 8, 16 };
  private int frequency;
  private int duration;

  public PianoTone(int frequency, int duration) {
    setFrequency(frequency);
    setDuration(duration);
  }

  /**
   * 
   * @param duration
   *          in milliseconds
   */
  public void setDuration(int duration) {
    this.duration = duration;
  }

  public int getDuration() {
    return duration;
  }

  public void setFrequency(int frequency) {
    this.frequency = frequency;
  }

  public int getFrequency() {
    return frequency;
  }
}
