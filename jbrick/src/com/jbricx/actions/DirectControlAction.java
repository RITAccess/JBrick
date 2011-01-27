package com.jbricx.actions;

import com.jbricx.communications.NXTObserver;
import com.jbricx.ui.DirectControl.DirectControlUIWindow;
import com.jbricx.ui.JBrickManager;
import org.eclipse.jface.resource.ImageDescriptor;

/**
 * This action opens the DirectControl window
 */
public class DirectControlAction extends AbstractAction implements NXTObserver {

  /**
   * DirectControlAction constructor
   */
  public DirectControlAction(JBrickManager manager) {
    super("&DirectControl@Ctrl+D", ImageDescriptor.createFromFile(AboutAction.class, "/images/direct_control.png"), manager);
    setToolTipText("Direct Control");
  }

  /**
   * Runs the action
   */
  @Override
  public void run() {
    if (getManager().getNXTManager().isBrickConnected()) {
      DirectControlUIWindow control = new DirectControlUIWindow(getManager().getShell());
      control.setBlockOnOpen(true);
      control.open();
    } else {
      // disable all the brick related icons
      getManager().getNXTManager().notifyAllObservers(false);
    }
  }

  public void update(boolean isConnected) {
    setEnabled(isConnected);
  }
}
