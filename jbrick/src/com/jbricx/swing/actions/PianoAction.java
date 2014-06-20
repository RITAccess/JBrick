package com.jbricx.swing.actions;

import java.awt.event.ActionEvent;

//import com.jbricx.ui.piano.PianoUIWindow;



import javax.swing.ImageIcon;

import com.jbricx.swing.ui.JBricxManager;

/**
 * @author Priya Sankaran
 * @author Abhishek Shrestha
 */
@SuppressWarnings("serial") 
public class PianoAction extends JBricxAbstractAction /*implements NXTObserver*/ {

  /**
   * PreferencesAction constructor
   */
  public PianoAction(JBricxManager manager) {
	  super("", new ImageIcon("./resources/images/piano_icon.png"), manager);
  }

  /**
   * Runs the action
   */
  @Override
  public void actionPerformed(ActionEvent e) {
	/*
    PianoUIWindow piano = new PianoUIWindow(getManager().getShell());
    piano.setBlockOnOpen(true);
    
    NXTManager.getInstance().register(piano);
    piano.open();
    */
  }
  /*
  public void update(boolean isConnected) {
    setEnabled(isConnected);
  }
 */
}
