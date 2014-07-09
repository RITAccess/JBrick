package com.jbricx.swing.actions;

import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;

import com.jbricx.piano.PianoWindow;
import com.jbricx.swing.ui.JBricxManager;

/**
 * @author Priya Sankaran
 * @author Abhishek Shrestha
 * 
 * @author Melissa Young
 */
@SuppressWarnings("serial") 
public class PianoAction extends JBricxAbstractAction{

  /**
   * PianoAction constructor
   */
  public PianoAction(final JBricxManager manager) {
	  super("", new ImageIcon(PianoAction.class.getResource("/images/piano_icon.png")), manager);
  }

  /**
   * Runs the action
   */
  @Override
  public void actionPerformed(ActionEvent e) {
	PianoWindow pianoWindow = new PianoWindow();
	pianoWindow.setUpPiano();
	pianoWindow.setVisible(true);
  }
}
