package com.jbricx.actions;

/*
 * @author Priya Sankaran
 */
import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;

import com.jbricx.ui.DirectControl.DirectControlWindow;
import com.jbricx.ui.controller.Controller;
import com.jbricx.ui.joystick.JoystickComposite;
import com.jbricx.ui.joystick.JoystickUIWindow;

public class ControllerAction extends Action{
	/**
	   * PreferencesAction constructor
	   */
	  public ControllerAction() {
		  super("&Controller@Ctrl+J", ImageDescriptor.createFromFile(ControllerAction.class,
	        "/images/controller_icon.png"));
      setToolTipText("Controller");
	  }

	  /**
	   * Runs the action
	   */
	  public void run() {
		  Controller.showGUI() ;
	  }
}
