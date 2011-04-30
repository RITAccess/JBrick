package com.jbricx.actions;

import org.eclipse.jface.resource.ImageDescriptor;

import com.jbricx.communications.NXTManager;
import com.jbricx.communications.NXTObserver;
import com.jbricx.ui.JBrickManager;
import com.jbricx.ui.DirectControl.DirectControlUIWindow;

/**
 * This action opens the DirectControl window
 */
public class DirectControlAction extends AbstractAction implements NXTObserver {

  /**
   * DirectControlAction constructor
   */
  public DirectControlAction(JBrickManager manager) {
    super("&DirectControl@Ctrl+D", ImageDescriptor.createFromFile(
        AboutAction.class, "/images/direct_controller.png"), manager);
    setToolTipText("Direct Control");
//    setEnabled(NXTManager.isFantomDriverLoaded());
    setEnabled(false);
  }

  /**
   * Runs the action
   */
  @Override
  public void run() {
    DirectControlUIWindow control = new DirectControlUIWindow(getManager()
        .getShell());
    control.setBlockOnOpen(true);
    control.open();
  }

  public void update(boolean isConnected) {
    if (isEnabled() != isConnected) {
//      setEnabled(isConnected);
    }
  }
}
