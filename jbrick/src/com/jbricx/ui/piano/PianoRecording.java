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
  private List<PianoTone> tones;

  public static final String START_OF_FILE = "task main(){";
  public static final String END_OF_FILE = "}";
  public static final String LINE_SEPARATOR = System
      .getProperty("line.separator");
  public static final String END_OF_LINE = ");";
  public static final String PLAY_TONE = "PlayTone(";
  public static final String TONE_SEPARATOR = ", ";
  public static final String WAIT = "Wait(";

  PianoRecording() {
    tones = new ArrayList<PianoTone>();
  }

  public void addKey(PianoTone key, int waitDuration) {
    tones.add(key);
    if (key.getFrequency() != 0) {
      tones.add(new PianoTone(0, waitDuration));
    }
  }

  public void clearKeys() {
    tones = new ArrayList<PianoTone>();
  }

  public List<PianoTone> getTones() {
    return tones;
  }

  /**
   * returns a string equivalent of all the current tones
   * 
   * @return current tones as string
   */
  public String getRecordingStr() {
    StringBuilder recordStr = new StringBuilder();

    Iterator<PianoTone> iterator = tones.iterator();
    while (iterator.hasNext()) {
      PianoTone tone = iterator.next();
      recordStr.append(getConvertedTone(tone));
    }

    return recordStr.toString();
  }

  /**
   * saves the current tones to a file specified by filepath
   * 
   * @param filePath
   *          the path of the file where the tones are to be saved
   */
  public void saveTonesToFile(String filePath) {
    try {
      OutputStream outputStream = new BufferedOutputStream(
          new FileOutputStream(filePath));

      PrintStream printStream = new PrintStream(outputStream);
      printStream.println(START_OF_FILE);

      Iterator<PianoTone> iterator = tones.iterator();
      while (iterator.hasNext()) {
        PianoTone tone = iterator.next();
        printStream.print(getConvertedTone(tone));
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
   * converts the given tone to a standard format (PlayTone(tone,
   * toneFrequency); followed by Wait(interval))
   * 
   * @param tone
   *          the {@link PianoTone} to be converted
   * @return a string representing the converted tone
   */
  protected String getConvertedTone(PianoTone tone) {
    StringBuilder convertedTone = new StringBuilder();

    if (tone.getFrequency() != 0) {      
      convertedTone.append(PLAY_TONE + tone.getFrequency() + TONE_SEPARATOR
          + tone.getDuration() + END_OF_LINE + LINE_SEPARATOR);
    } else {
      /* NXT does not wait for a note to finish so put a WAIT or for Rest Note */
      convertedTone.append(WAIT + tone.getDuration() + END_OF_LINE
          + LINE_SEPARATOR);
    }
    return convertedTone.toString();
  }
}
