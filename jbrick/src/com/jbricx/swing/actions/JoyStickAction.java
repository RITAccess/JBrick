//package com.jbricx.swing.actions;
//
//import com.jbricx.communications.NXTManager;
//import com.jbricx.communications.NXTObserver;
//import com.jbricx.ui.JBrickManager;
//import com.jbricx.ui.joystick.JoystickUIWindow;
//
///**
// * @author Priya Sankaran
// * @author Abhishek Shrestha
// */
//public class JoyStickAction extends AbstractAction implements NXTObserver {
//
//  /**
//   * PreferencesAction constructor
//   */
//  public JoyStickAction(JBrickManager manager) {
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
//}
