package com.jbricx.ui.piano;

import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.List;

public class savingPianoNotesFile {

  public void receivingNotes(List<PianoNote> noteList, String fileName) {
    String starting = "task main(){";
    String closing = "}";
    try {
      FileOutputStream fileOut = new FileOutputStream(fileName);
      PrintStream p;
      p = new PrintStream(fileOut);
      p.println(starting);

      for (int x = 0; x < noteList.size(); x++) {
        PianoNote pianoNote = (PianoNote) noteList.get(x);
        if (pianoNote.getTone() != 0) {
          p.println("  PlayTone(" + pianoNote.getTone() + ","
              + pianoNote.getNoteTime() + ");");
        }
        p.println("  Wait(" + pianoNote.getWaitTime() + ");");
      }

      p.println(closing);
      p.close();

    } catch (Exception e) {
      System.err.println("Error writing to file");
    }
  }

}
