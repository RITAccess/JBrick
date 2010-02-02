package com.jbricx.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;

import com.jbricx.pjo.JBrickEditor;
import com.jbricx.ui.findbrick.FindBrickUIWindow;
import com.jbricx.ui.joystick.JoystickUIWindow;
/**
 * This class shows an About box
 */
public class FindBrickAction extends Action {
  /**
   * AboutAction constructor
   */
  public FindBrickAction() {
    super("&FindBrick@Ctrl+f+b", ImageDescriptor.createFromFile(FindBrickAction.class,
        "/images/findBrick.png"));
    setToolTipText("Find Brick");
  }

  /**
   * Shows an about box
   */
  public void run() {
	  FindBrickUIWindow  findBrick = new  FindBrickUIWindow(); 
	  findBrick.setBlockOnOpen(true);
	  findBrick.open();
  }
}
