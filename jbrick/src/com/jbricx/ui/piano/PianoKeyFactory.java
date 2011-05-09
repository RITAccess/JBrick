package com.jbricx.ui.piano;

import java.util.ArrayList;
import java.util.List;

/**
 * The Flyweight factory for explicitly creating the {@link PianoKey}s.
 * 
 * @author Abhishek Shrestha
 * 
 */
public class PianoKeyFactory {
  private List<PianoKey> pianoKeys = new ArrayList<PianoKey>();

  public PianoKeyFactory() {
    initKeys();
  }

  private void initKeys() {
    createKey("A", 0, false);
    createKey("A#", 1, true);
    createKey("B", 2, false);
    createKey("C", -9, false);
    createKey("C#", -8, true);
    createKey("D", -7, false);
    createKey("D#", -6, true);
    createKey("E", -5, false);
    createKey("F", -4, false);
    createKey("F#", -3, true);
    createKey("G", -2, false);
    createKey("G#", -1, true);
  }

  /**
   * checks if the supplied string representing a note is valid
   * 
   * @param note
   *          the note to be checked
   * @return true if the note exists else false
   */
  public boolean isValidNote(String note) {
    boolean isValid = false;
    for (PianoKey k : pianoKeys) {
      if (k.getName().equals(note)) {
        isValid = true;
        break;
      }
    }
    return isValid;
  }

  private void createKey(String keyLabel, int step, boolean isBlack) {
    PianoKey pianoKey = new PianoKey(keyLabel, step, isBlack);
    if (!pianoKeys.contains(pianoKey)) {
      pianoKeys.add(pianoKey);
    }
  }

  public List<PianoKey> getKeys() {
    return pianoKeys;
  }
}
