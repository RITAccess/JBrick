package com.jbricx.ui.piano;


/**
 * A concrete Flyweight implementation of {@link MusicNote}.
 * 
 * @author Abhishek Shrestha
 * 
 */
public class MusicNote {
  private String name;
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
