package com.jbricx.ui.piano;

import java.text.DecimalFormat;

/**
 * A concrete Flyweight implementation of {@link MusicNote}.
 * 
 * @author Abhishek Shrestha
 * 
 */
public class MusicNoteImpl implements MusicNote {
  private String name;
  private int step;
  private boolean isSharp;

  public MusicNoteImpl(String name, int step, boolean isSharp) {
    setName(name);
    setStep(step);
    setSharp(isSharp);
  }

  @Override
  public float getFrequency(Octave octave) {
    /*
     * the formula to find frequency is:---------------------------------------
     * f = 2 ^ (n / 12) * Frequency of A note at Octave octave -----------------
     * f = 2 ^ (step / 12) * A_FREQUENCIES[octave]
     */
    DecimalFormat twoDecPlace = new DecimalFormat("#.##");
    double f = (Math.pow(2, (step / 12.d)) * octave.getFrequencyOfA());

    return Float.valueOf(twoDecPlace.format(f));
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
