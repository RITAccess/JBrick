package com.jbricx.ui.piano;

public class PianoNote {
  private int tone;
  // 16 for 1/1, 8 for 1/2, 4 for 1/4, 2 for 1/8, 1 for 1/16
  private int duration;
  private int noteTime = 40;
  private int waitTime = 48;

  PianoNote(int tone, int duration, int noteTime, int waitTime) {
    System.out.println("PianoNote()dur:" + duration);

    setTone(tone);
    setDuration(duration);
    this.noteTime = noteTime;
    this.waitTime = waitTime;
  }

  public int getTone() {
    return tone;
  }

  public void setTone(int tone) {
    this.tone = tone;
  }

  public void setDuration(int newDuration) {
    switch (newDuration) {
      case 1:
        duration = 16;
        break;
      case 2:
        duration = 8;
        break;
      case 4:
        duration = 4;
        break;
      case 8:
        duration = 2;
        break;
      case 16:
        duration = 1;
        break;
    }
  }

  public int getNoteTime() {
    return duration * noteTime;
  }

  public int getWaitTime() {
    return duration * waitTime;
  }
}
