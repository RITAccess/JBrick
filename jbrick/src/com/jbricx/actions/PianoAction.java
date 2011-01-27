package com.jbricx.actions;

import com.jbricx.communications.NXTObserver;
import com.jbricx.ui.JBrickManager;
import org.eclipse.jface.resource.ImageDescriptor;
import com.jbricx.ui.piano.PianoUIWindow;

/**
 * @author Priya Sankaran
 * @author Abhishek Shrestha
 */
public class PianoAction extends AbstractAction implements NXTObserver {

  /**
   * PreferencesAction constructor
   */
  public PianoAction(JBrickManager manager) {
    super("&Piano@Ctrl+Alt+D", ImageDescriptor.createFromFile(PianoAction.class, "/images/piano_icon.png"), manager);
    setToolTipText("Piano");
  }

  /**
   * Runs the action
   */
  @Override
  public void run() {
    PianoUIWindow piano = new PianoUIWindow(getManager().getShell());
    piano.setBlockOnOpen(true);
    piano.open();
  }

  public void update(boolean isConnected) {
    setEnabled(isConnected);
  }
}
