package com.jbricx.ui.piano;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.jbricx.actions.AbstractAction;
import com.jbricx.actions.SavePianoRecording;
import com.jbricx.communications.NXTGadgetManager;
import com.jbricx.communications.NXTManager;

/**
 * Handles the overall interaction of the GUI with the model classes
 * 
 * @author Abhishek Shrestha
 * 
 */
public class PianoController {
  private Piano piano;
  private PianoRecording records;
  private SavePianoRecording savePianoAction;
  private NXTGadgetManager nxt = NXTManager.getInstance();

  private Map<String, PianoKey> kbToNoteMap = new HashMap<String, PianoKey>();
  private Map<String, Octave> kbToOctaveMap = new HashMap<String, Octave>();
  private Map<String, Integer> kbToKeyIndexMap = new HashMap<String, Integer>();

  private int waitDuration = 500;
  private float noteLengthFactor = 0.25f;
  private int toneDuration = 1000;

  public PianoController(Shell shell, String startKey, boolean isContinuous)
      throws NoteNotFoundException {
    records = new PianoRecording();
    savePianoAction = new SavePianoRecording(shell, records);

    piano = new Piano(4, 5, startKey);
    mapKeyboardNotesTo(isContinuous);
    setToneDuration(toneDuration);
    setWaitDuration(waitDuration);
    setNoteLengthFactor(noteLengthFactor);
  }

  public void performAction(AbstractAction action) {
    action.run();
  }

  protected void clearRecords() {
    records.clearKeys();
  }

  public void playRecords() {
    PianoRecordingPlayer pianoRecordingPlayer = new PianoRecordingPlayer(
        records);
    Display.getCurrent().asyncExec(pianoRecordingPlayer);
  }

  protected void copyRecords() {
    String recStr = records.getRecordingStr();

    Clipboard systemClipboard = Toolkit.getDefaultToolkit()
        .getSystemClipboard();
    Transferable transferableText = new StringSelection(recStr);
    systemClipboard.setContents(transferableText, null);
  }

  private void play(Key key, Octave octave) {
    int frequency = (int) key.getFrequency(octave);
    int duration = (int) (toneDuration * getNoteLengthFactor());
    
    int waitDur = (int) (waitDuration * getNoteLengthFactor());
    PianoTone tone = new PianoTone(frequency, duration);
    records.addKey(tone, waitDur);
   	nxt.playTone(frequency, duration);
  }

  /**
   * performs the actual playing of note to the NXTBrick
   * 
   * @param pianoKey
   */
  public void play(Key pianoKey, int oldOctaveIndex) {
    int o = piano.getStartOctave() + oldOctaveIndex;
    try {
      Octave octave = new Octave(o);
      play(pianoKey, octave);
    } catch (OctaveScaleOutofBoundsException e) {
      e.printStackTrace();
    }
  }

  /**
   * converts the given character to actual {@link PianoKey}
   * 
   * @param key
   *          the character pressed
   */
  public int play(char key) throws KeyNotMappedExeption,
      OctaveNotMappedException, KeyIndexNotMappedException {
    PianoKey pianoKey = kbToNoteMap.get("" + key);
    if (pianoKey != null) {
      Octave octave = kbToOctaveMap.get("" + key);
      if (octave != null) {
        Integer keyIndex = kbToKeyIndexMap.get("" + key);
        if (keyIndex != null) {
          play(pianoKey, octave);
          return keyIndex;
        } else {
          throw new KeyIndexNotMappedException("" + key);
        }
      } else {
        throw new OctaveNotMappedException("" + key);
      }
    } else {
      throw new KeyNotMappedExeption("" + key);
    }
  }

  /**
   * adds a rest note
   */
  public void playRest() {
    /* doesn't really matter what octave play a dead note! Its FOREVER dead! ;) */
    try {
      play(piano.getRestKey(), new Octave());
    } catch (OctaveScaleOutofBoundsException e) {
      e.printStackTrace();
    }
  }

  public void saveNotes() {
    savePianoAction.perform();
  }

  public int getStartOctave() {
    return piano.getStartOctave();
  }

  public void setStartOctave(int octave) throws OctaveScaleOutofBoundsException {
    int pianoMaxOctave = piano.getMaxOctave().getValue();
    int pianoMinOctave = piano.getMinOctave().getValue();

    if ((octave + piano.getOctaveScaleBlock()) > pianoMaxOctave
        || (octave - piano.getOctaveScaleBlock()) < (pianoMinOctave - 1)) {
      throw new OctaveScaleOutofBoundsException(octave + " Octave Scale Range("
          + pianoMinOctave + ", " + (pianoMaxOctave - 1) + ")");
    } else {
      int oldOctave = piano.getStartOctave();
      int changeInOctave = octave - oldOctave;

      piano.setStartOctave(octave);
      int endOctave = octave + piano.getOctaveScaleBlock();
      piano.setEndOctave(endOctave);
      updateOctaveMap(changeInOctave);
    }
  }

  public int getEndOctave() {
    return piano.getEndOctave();
  }

  public int getOctaveScaleBlock() {
    return piano.getOctaveScaleBlock();
  }

  public int getOctaveSlideStart() {
    return piano.getMinOctave().getValue();
  }

  public int getOctaveSlideLimit() {
    return piano.getMaxOctave().getValue() - piano.getOctaveScaleBlock();
  }

  public List<PianoKey> getKeys() {
    return piano.getKeys();
  }

  public void setWaitDuration(int waitDuration) {
    this.waitDuration = waitDuration;
  }

  public int getWaitDuration() {
    return waitDuration;
  }

  public void setToneDuration(float toneDuration) {
    this.toneDuration = (int) toneDuration;
  }

  public int getToneDuration() {
    return this.toneDuration;
  }

  public void setNoteLengthFactor(float lenFactor) {
    noteLengthFactor = lenFactor;
  }

  public float getNoteLengthFactor() {
    return noteLengthFactor;
  }

  /**
   * maps the keyboard keys to particular music note
   * 
   * @param whiteNoteChars
   *          array of chars to be mapped as regular note (representation of
   *          white key in physical Piano)
   * @param blackNotesChars
   *          array of chars to be mapped as sharp note (representation of black
   *          key in physical Piano)
   * @param continueScale
   *          a boolean indicating whether to treat the second array of
   *          whiteNoteChars/balckNoteChars as continued octave or start new
   *          octave scale
   */
  private void createKeyboardNoteMap(char[][] whiteNoteChars,
      char[][] blackNotesChars, boolean continueScale) {
    /*
     * is capable of mapping the keyboard keys to actual music notes handling
     * also considering the scale factor no matter how less keys have been
     * supplied
     */
    List<PianoKey> pianoKeys = getKeys();
    int limit = pianoKeys.size();
    int w = 0; /* white note character index */
    int b = 0; /* black note character index */
    int keyIndex = 0; /* counter for keys generated */
    /* the character array (whiteNoteChars[] & black NoteChars) index to process */
    int arrayIndex = 0;
    int startOctave = piano.getStartOctave();

    for (int octave = startOctave; octave <= piano.getEndOctave(); octave++) {
      try {
        Octave o = new Octave(octave);
        for (int key = 0; key < limit; key++) {
          PianoKey pianoKey = pianoKeys.get(key);
          /* lets process both the white and black notes */
          if (pianoKey.isBlack()) {
            b++; /* simply go to the next blackNoteChar */

            /* ensures the fail-safeness of algorithm */
            if (b < blackNotesChars[arrayIndex].length) {
              /* keyboard character mapped to piano key and so on... */
              kbToNoteMap.put("" + blackNotesChars[arrayIndex][b], pianoKey);
              kbToOctaveMap.put("" + blackNotesChars[arrayIndex][b], o);
              kbToKeyIndexMap
                  .put("" + blackNotesChars[arrayIndex][b], keyIndex);
            }
          } else { /* white note */
            /* ensures the fail-safeness of algorithm */
            if (w < whiteNoteChars[arrayIndex].length) {
              /* keyboard character mapped to piano key */
              kbToNoteMap.put("" + whiteNoteChars[arrayIndex][w], pianoKey);
              kbToOctaveMap.put("" + whiteNoteChars[arrayIndex][w], o);
              kbToKeyIndexMap.put("" + whiteNoteChars[arrayIndex][w], keyIndex);
            }

            /* check if this note has sharp note */
            if (!pianoKeys.get((key + 1) % limit).isBlack()) {
              /*
               * since it does not have a sharp note (like B and E notes) we
               * have increment the count to the next character for
               * blackNotesChar
               */
              b++;
            }
            w++; /* simply go to the next whiteNoteChar */
          }
          keyIndex++;
        }
        /*
         * if continuous scale is not used then after each octave we have to
         * reset the counters for the positions of characters for black and
         * white keys as well as the array index
         */
        if (!continueScale) {
          arrayIndex++;
          w = 0;
          b = 0;
        }
      } catch (OctaveScaleOutofBoundsException e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * maps characters (keyboard keys)to {@link PianoKey1}.
   * 
   * @param continueScale
   *          if true starts the new octave in the same row else starts in new
   *          row
   */
  public void mapKeyboardNotesTo(boolean continueScale) {
    if (continueScale) {
      /* FILL ONLY 0th INDEX OF THE ARRAY */
      char[][] blackKeys = { { '1', '2', '3', '4', '5', '6', '7', '8', '9',
          '0', '-', '=', 'a', 's', 'd', 'f', 'g', 'h', 'j', 'k', 'l', ';', '\'' } };
      char[][] whiteKeys = { { 'q', 'w', 'e', 'r', 't', 'y', 'u', 'i', 'o',
          'p', '[', ']', 'z', 'x', 'c', 'v', 'b', 'n', 'm', ',', '.', '/' } };
      createKeyboardNoteMap(whiteKeys, blackKeys, true);
    } else {
      /* SUPPLY THE NEXT ROW (NEW OCTAVE) IN 1ST INDEX */
      char[][] blackKeys = {
          { '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '-', '=' },
          { 'a', 's', 'd', 'f', 'g', 'h', 'j', 'k', 'l', ';', '\'' } };
      char[][] whiteKeys = {
          { 'q', 'w', 'e', 'r', 't', 'y', 'u', 'i', 'o', 'p', '[', ']' },
          { 'z', 'x', 'c', 'v', 'b', 'n', 'm', ',', '.', '/' } };
      createKeyboardNoteMap(whiteKeys, blackKeys, false);
    }
  }

  private void updateOctaveMap(int changeInOctave) {
    Iterator<String> keys = kbToOctaveMap.keySet().iterator();

    while (keys.hasNext()) {
      String key = keys.next();
      int oldOctave = kbToOctaveMap.get(key).getValue();
      try {
        kbToOctaveMap.put(key, new Octave(oldOctave + changeInOctave));
      } catch (OctaveScaleOutofBoundsException e) {
        e.printStackTrace();
      }
    }
  }
}
