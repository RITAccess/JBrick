package com.jbricx.ui.piano;

import java.util.ArrayList;
import java.util.List;

/**
 * The Flyweight Piano client.
 * 
 * @author Abhishek Shrestha
 * 
 */
public class Piano {
  private int octaveScaleBlock;
  private Octave minOctave;
  private Octave maxOctave;
  private Octave startOctave;
  private Octave endOctave;
  private String startNote;
  private KeyFactory pianoKeyFactory;
  /* notes below 2 sound all the same */
  public static final int MIN_OCTAVE = 3;
  /*
   * notes above 8 are too shrill for your ears! also make sure this value is
   * smaller than Octave.MAX_OCTAVE
   */
  public static final int MAX_OCTAVE = 8;

  public Piano() throws NoteNotFoundException {
    this(4);
  }

  public Piano(int toOctave) throws NoteNotFoundException {
    this(toOctave, toOctave + 1, "C");
  }

  public Piano(int fromOctave, int toOctave, String startNote)
      throws NoteNotFoundException {
    pianoKeyFactory = new KeyFactory();
    if (pianoKeyFactory.isValidNote(startNote)) {
      try {
        setMinOctave(new Octave(MIN_OCTAVE));
        setMaxOctave(new Octave(MAX_OCTAVE));

        setStartOctave(fromOctave);

        setEndOctave(toOctave);
        setStartNote(startNote);
        setOctaveScaleBlock(endOctave.getValue() - startOctave.getValue());
      } catch (OctaveScaleOutofBoundsException e) {
        e.printStackTrace();
      }
    } else {
      throw new NoteNotFoundException(startNote);
    }
  }

  public int getStartOctave() {
    return startOctave.getValue();
  }

  public void setStartOctave(int startOctave) {
    try {
      this.startOctave = new Octave(startOctave);
    } catch (OctaveScaleOutofBoundsException e) {
      e.printStackTrace();
    }
  }

  public int getEndOctave() {
    return endOctave.getValue();
  }

  public void setEndOctave(int endOctave) {
    try {
      this.endOctave = new Octave(endOctave);
    } catch (OctaveScaleOutofBoundsException e) {
      e.printStackTrace();
    }
  }

  public void setOctaveScaleBlock(int octaveScaleBlock) {
    this.octaveScaleBlock = octaveScaleBlock;
  }

  public int getOctaveScaleBlock() {
    return octaveScaleBlock;
  }

  public List<PianoKey> getKeys() {
    List<PianoKey> pianoKeys = pianoKeyFactory.getPianoKeys();

    if (!startNote.equals("A")) {
      List<PianoKey> pianoKeys1 = new ArrayList<PianoKey>();
      List<PianoKey> pianoKeys2 = new ArrayList<PianoKey>();
      boolean isStartKeyFound = false;

      /*
       * reshuffle the piano key list so as to start out with whatever note has
       * been specified
       */
      for (PianoKey pianoKey : pianoKeys) {
        if (pianoKey.getName().equals(startNote) || isStartKeyFound) {
          isStartKeyFound = true;
          pianoKeys1.add(pianoKey);
        } else {
          pianoKeys2.add(pianoKey);
        }
      }
      pianoKeys.clear();
      pianoKeys.addAll(pianoKeys1);
      pianoKeys.addAll(pianoKeys2);
    }
    return pianoKeys;
  }

  public RestKey getRestKey() {
    return pianoKeyFactory.createRestKey();
  }

  public void setStartNote(String startNote) {
    this.startNote = startNote;
  }

  public String getStartNote() {
    return startNote;
  }

  public void setMinOctave(Octave minOctave) {
    this.minOctave = minOctave;
  }

  public Octave getMinOctave() {
    return minOctave;
  }

  public void setMaxOctave(Octave maxOctave) {
    this.maxOctave = maxOctave;
  }

  public Octave getMaxOctave() {
    return maxOctave;
  }
}
