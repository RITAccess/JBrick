package com.jbricx.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;

import com.jbricx.ui.piano.PianoComposite;

/**
 * @author Priya Sankaran
 */
public class PianoAction extends Action {
  /**
   * PreferencesAction constructor
   */
  public PianoAction() {
    super("&Piano@Ctrl+Alt+D", ImageDescriptor.createFromFile(PianoAction.class, "/images/piano_icon.png"));
    setToolTipText("Piano");
  }

  /**
   * Runs the action
   */
  public void run() {
    //TODO: delete these comments.
    // JoystickUIWindow joystick = new JoystickUIWindow();
    // joystick.setBlockOnOpen(true);
    // joystick.open();

    PianoComposite.showGUI();

  }
}
