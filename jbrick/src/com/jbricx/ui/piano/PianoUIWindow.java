package com.jbricx.ui.piano;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.TrayDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

import com.jbricx.communications.NXTManager;
import com.jbricx.communications.NXTObserver;

/**
 * @author Priya Sankaran
 * @author Abhishek Shrestha
 */
public class PianoUIWindow extends TrayDialog implements NXTObserver {
  private Shell shell = null;

  public PianoUIWindow(Shell shell) {
    super(shell);
    this.shell = shell;
  }

  PianoComposite pianoComposite;
  PianoController pianoController;

  @Override
  protected Control createContents(Composite parent) {
    getShell().setText("Piano");
    pianoComposite = null;
    try {
      pianoController = new PianoController(shell, "C", true);
      pianoComposite = new PianoComposite(parent, SWT.NULL, pianoController);
    } catch (NoteNotFoundException e) {
      e.printStackTrace();
    }
    return pianoComposite;
  }

  public void handleShellCloseEvent() {
    onDisconnect();
    super.handleShellCloseEvent();
  }

  @Override
  public void update(boolean isConnected) {
    if (!isConnected) {
      onDisconnect();

      /* save the notes that have been played */
      pianoController.saveNotes();
      MessageDialog.openInformation(shell, "Connection Status",
          "Piano: NXTBrick has been disconnected!"
              + " The notes (if played) have been copied to clipboard.\r\n"
              + " The paino window will be closed now!");
      this.getShell().dispose(); /* finally close the piano dialog */
    }
  }

  /**
   * Unregisters this observer.
   */
  protected void onDisconnect() {
    NXTManager.getInstance().unregister(this);
  }
}
