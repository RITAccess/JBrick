package com.jbricx.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.widgets.Display;

import com.jbricx.ui.joystick.JoystickUIWindow;

public class JoyStickAction extends Action{
	/**
	   * PreferencesAction constructor
	   */
	  public JoyStickAction() {
		  super("&Download@Ctrl+D", ImageDescriptor.createFromFile(JoyStickAction.class,
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
