package com.jbricx.ui.piano;

import java.util.List;

import com.jbricx.communications.NXTGadgetManager;
import com.jbricx.communications.NXTManager;

public class PianoRecordingPlayer implements Runnable {

  private PianoRecording records;

  public PianoRecordingPlayer(PianoRecording records) {
    this.records = records;
  }

  @Override
  public void run() {
    NXTGadgetManager nxt = NXTManager.getInstance();
    List<PianoTone> tones = records.getTones();

    try {
      for (PianoTone tone : tones) {
        nxt.playTone(tone.getFrequency(), tone.getDuration());
        if (tone.getFrequency() != 0) {
          Thread.sleep(tone.getDuration());
        }
      }
    } catch (Exception e) {
    }
  }
}
