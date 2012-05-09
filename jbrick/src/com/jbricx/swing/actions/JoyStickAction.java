package com.jbricx.swing.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import com.jbricx.swing.communications.NXTObserver;
import com.jbricx.swing.ui.JBricxManager;


/**
 * @author Priya Sankaran
 * @author Abhishek Shrestha
 */
public class JoyStickAction extends AbstractAction implements NXTObserver {

  /**
   * PreferencesAction constructor
   */
//  public JoyStickAction(JBricxManager manager) {
//    super("&JoyStick@Ctrl+Shift+D", ImageDescriptor.createFromFile(
//        JoyStickAction.class, "/images/joystick_icon.png"), manager);
//    setToolTipText("Joystick");
//    
//    setEnabled(NXTManager.isFantomDriverLoaded());
//  }
//
//  /**
//   * Runs the action
//   */
//  @Override
//  public void run() {
//    JoystickUIWindow joystick = new JoystickUIWindow(getManager().getShell());
//    joystick.setBlockOnOpen(true);
//    
//    NXTManager.getInstance().register(joystick);    
//    joystick.open();
//  }
//
//  public void update(boolean isConnected) {
//    setEnabled(isConnected);
//  }
//
//@Override
public void actionPerformed(ActionEvent arg0) {
//	// TODO Auto-generated method stub
//	
}

@Override
public void update(boolean isConnected) {
	// TODO Auto-generated method stub
	
}
}
