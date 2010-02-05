package com.jbricx.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;

import com.jbricx.pjo.JBrickEditor;
import com.jbricx.ui.joystick.JoystickUIWindow;
import com.jbricx.ui.methodTemplate.MethodTemplateUIWindow;
/**
 * This class shows an About box
 */
public class MethodTemplateAction extends Action {
  /**
   * AboutAction constructor
   */
  public MethodTemplateAction() {
    super("&MethodTemplate@Ctrl+M", ImageDescriptor.createFromFile(MethodTemplateAction.class,
        "/images/help-browser.png"));
    setToolTipText("Method Template");
  }

  /**
   * Shows an about box
   */
  public void run() {
	  MethodTemplateUIWindow  methodTemplate = new  MethodTemplateUIWindow(); 
	  methodTemplate.setBlockOnOpen(true);
	  methodTemplate.open();
  }
}
