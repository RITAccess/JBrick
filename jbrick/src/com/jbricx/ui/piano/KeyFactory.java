package com.jbricx.ui.piano;

import java.util.ArrayList;
import java.util.List;

/**
 * The Flyweight factory for creating the {@link Key}s
 * 
 * @author Abhishek Shrestha
 * 
 */
public class KeyFactory {
  private List<PianoKey> pianoKeys = new ArrayList<PianoKey>();
  private RestKey restKey;

  public KeyFactory() {
    initKeys();
  }

  private void initKeys() {
    /* we do not want the factory to create notes other than these */
    createPianoKey("A", 0, false);
    createPianoKey("A#", 1, true);
    createPianoKey("B", 2, false);
    createPianoKey("C", -9, false);
    createPianoKey("C#", -8, true);
    createPianoKey("D", -7, false);
    createPianoKey("D#", -6, true);
    createPianoKey("E", -5, false);
    createPianoKey("F", -4, false);
    createPianoKey("F#", -3, true);
    createPianoKey("G", -2, false);
    createPianoKey("G#", -1, true);
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

  /**
   * creates keys from the given parameters. This has been made private to
   * restrict the invalid key creation
   * 
   * @param keyLabel
   * @param step
   * @param isBlack
   */
  private PianoKey createPianoKey(String keyLabel, int step, boolean isBlack) {
    PianoKey pianoKey = null;
    for (PianoKey p : pianoKeys) {
      if (!p.getName().equals(keyLabel)) {
        pianoKey = new PianoKey(keyLabel, step, isBlack);
        pianoKeys.add(pianoKey);
        break;
      } else {
        pianoKey = p;
      }
    }
    return pianoKey;
  }

  /**
   * creates the Rest key (for the dead note i.e. with 0 frequency)
   * 
   * @return
   */
  public RestKey createRestKey() {
    /* there is no need to share the rest key */
    if (restKey != null) {
      restKey = new RestKey();
    }
    return restKey;
  }

  public List<PianoKey> getPianoKeys() {
    return pianoKeys;
  }

  public PianoKey getPianoKey(String keyName) throws NoteNotFoundException {
    PianoKey p = null;
    for (PianoKey pianoKey : pianoKeys) {
      if (pianoKey.getName().equals(keyName)) {
        p = pianoKey;
        break;
      }
    }
    if (p == null) {
      throw new NoteNotFoundException(keyName);
    } else {
      return p;
    }
  }
}
