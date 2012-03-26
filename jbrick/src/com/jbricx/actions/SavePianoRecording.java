package com.jbricx.actions;

import java.awt.FileDialog;
import java.io.File;

import com.jbricx.pjo.FileExtensionConstants;
import com.jbricx.ui.piano.PianoRecording;

public class SavePianoRecording {
  private Shell shell;
  private PianoRecording records;

  public SavePianoRecording(Shell sh, PianoRecording records) {
    shell = sh;
    this.records = records;
  }

  public void perform() {
    String fileName = null;

    try {
      FileDialog dlg = new FileDialog(shell, SWT.SAVE);
      dlg.setFilterNames(FileExtensionConstants.FILTER_NAMES);
      dlg.setFilterExtensions(FileExtensionConstants.FILTER_EXTENSIONS);
      fileName = dlg.open();

      File file = new File(fileName);
      if (file.exists()) {
        boolean overwrite = MessageDialog.openQuestion(shell,
            "Confirm over write", fileName
                + " already exists. Do you want to replace it?");
        if (!overwrite) {
          fileName = null;
        } else {
          records.saveTonesToFile(fileName);
          MessageDialog.openInformation(shell, "File Save Status", fileName
              + " has been saved successfully!");
        }
      } else {
        records.saveTonesToFile(fileName);
        MessageDialog.openInformation(shell, "File Save Status", fileName
            + " has been saved successfully!");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
