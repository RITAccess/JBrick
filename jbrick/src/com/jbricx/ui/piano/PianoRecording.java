package com.jbricx.ui.piano;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Handles the piano recording and saving.
 * 
 * @author Corey Cooney
 * @author Abhishek Shrestha
 * 
 */
public class PianoRecording {
  private List<PianoNote> notes;

  public static final String START_OF_FILE = "task main(){";
  public static final String END_OF_FILE = "}";
  public static final String LINE_SEPARATOR = System
      .getProperty("line.separator");
  public static final String END_OF_LINE = ");";
  public static final String PLAY_TONE = "PlayTone(";
  public static final String TONE_SEPARATOR = ", ";
  public static final String WAIT = "Wait(";

  PianoRecording() {
    notes = new ArrayList<PianoNote>();
  }

  public void addKey(int tone, int duration, int noteTime, int waitTime) {    
    notes.add(new PianoNote(tone, duration, noteTime, waitTime));
  }

  public void addKey(PianoNote key) {
    notes.add(key);
  }

  public void clearKeys() {
    notes = new ArrayList<PianoNote>();
  }

  public List<PianoNote> getNotes() {
    return notes;
  }

  /**
   * returns a string equivalent of all the current notes
   * 
   * @return current notes as string
   */
  public String getRecordingStr() {
    StringBuilder recordStr = new StringBuilder();

    Iterator<PianoNote> iterator = notes.iterator();
    while (iterator.hasNext()) {
      PianoNote note = iterator.next();
      recordStr.append(getConvertedNote(note));
    }

    return recordStr.toString();
  }

  /**
   * saves the current notes to a file specified by filepath
   * 
   * @param filePath
   *          the path of the file where the notes are to be saved
   */
  public void saveNotesToFile(String filePath) {
    try {
      OutputStream outputStream = new BufferedOutputStream(
          new FileOutputStream(filePath));

      PrintStream printStream = new PrintStream(outputStream);
      printStream.println(START_OF_FILE);

      Iterator<PianoNote> iterator = notes.iterator();
      while (iterator.hasNext()) {
        PianoNote note = iterator.next();
        printStream.print(getConvertedNote(note));
      }

      printStream.println(END_OF_FILE);

      printStream.flush();
      printStream.close();
      outputStream.close();

    } catch (FileNotFoundException e) {
      System.err.println("Error writing to file " + filePath);
    } catch (IOException e) {
      System.err.println("Error closing file " + filePath);
    }
  }

  /**
   * converts the given note to a standard format (PlayTone(tone,
   * noteFrequency); followed by Wait(interval))
   * 
   * @param note
   *          the {@link PianoNote} to be converted
   * @return a string representing the converted note
   */
  protected String getConvertedNote(PianoNote note) {
    StringBuilder convertedNote = new StringBuilder();
    int tone = note.getTone();

    if (tone != 0) {
      convertedNote.append(PLAY_TONE + tone + TONE_SEPARATOR
          + note.getNoteTime() + END_OF_LINE + LINE_SEPARATOR);

      convertedNote.append(WAIT + note.getWaitTime() + END_OF_LINE
          + LINE_SEPARATOR);
    }
    return convertedNote.toString();
  }
}
