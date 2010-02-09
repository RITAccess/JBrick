package com.jbricx.actions;

/*
 * @author Priya Sankaran
 */
import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;

import com.jbricx.ui.joystick.JoystickUIWindow;

public class JoyStickAction extends Action{
	/**
	   * PreferencesAction constructor
	   */
	  public JoyStickAction() {
		  super("&JoyStick@Ctrl+D", ImageDescriptor.createFromFile(JoyStickAction.class,
	        "/images/joystick_icon.png"));
      setToolTipText("Joystick");
	  }

	  /**
	   * Runs the action
	   */
	  public void run() {
		  JoystickUIWindow  joystick = new  JoystickUIWindow(); 
		  joystick.setBlockOnOpen(true);
		  joystick.open();
	  }
}
