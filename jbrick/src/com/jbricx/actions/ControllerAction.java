package com.jbricx.actions;

import com.jbricx.ui.JBrickManager;
import org.eclipse.jface.resource.ImageDescriptor;

import com.jbricx.ui.controller.ControllerUIWindow;

/**
 * @author Priya Sankaran
 * @author Abhishek Shrestha
 */
public class ControllerAction extends AbstractAction {

  /**
   * PreferencesAction constructor
   */
  public ControllerAction(JBrickManager manager) {
    super("&Controller@Ctrl+J", ImageDescriptor.createFromFile(ControllerAction.class, "/images/controller_icon.png"), manager);
    setToolTipText("Controller");
  }

  /**
   * Runs the action
   */
  @Override
  public void run() {
    ControllerUIWindow controller = new ControllerUIWindow(getManager().getShell());
    controller.setBlockOnOpen(true);
    controller.open();
  }
}
