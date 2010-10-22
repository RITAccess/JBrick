package com.jbricx.actions;

/*
 * @author Priya Sankaran
 */
import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;

import com.jbricx.ui.piano.PianoComposite;

public class PianoAction extends Action{
	/**
	   * PreferencesAction constructor
	   */ 
	  public PianoAction() {
		  super("&Piano@Ctrl+P", ImageDescriptor.createFromFile(PianoAction.class, "/images/piano_icon.png"));
		  setToolTipText("Piano");
	  }

	  /**
	   * Runs the action
	   */
	  public void run() {
//		  JoystickUIWindow  joystick = new  JoystickUIWindow(); 
//		  joystick.setBlockOnOpen(true);
//		  joystick.open();
		  
		  
		  PianoComposite.showGUI() ;
		  
	  }
}
