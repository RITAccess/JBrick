package com.jbricx.swing.actions;

import org.eclipse.jface.resource.ImageDescriptor;

import com.jbricx.communications.NXTManager;
import com.jbricx.ui.JBrickManager;
import com.jbricx.ui.findbrick.FindBrickUIWindow;

/**
 * This class shows an About box
 */
public class FindBrickAction extends AbstractAction {

  /**
   * AboutAction constructor
   */
  public FindBrickAction(JBrickManager manager) {
    super("&FindBrick@Ctrl+B", ImageDescriptor.createFromFile(
        FindBrickAction.class, "/images/find_brick.png"), manager);
    setToolTipText("Find Brick");
    setEnabled(NXTManager.isFantomDriverLoaded());
    // try {
    // NXTManager.getInstance().getFantom();
    // } catch (FantomDriverNotFoundException e) {
    // // TODO: notify the user that the fantom driver is missing
    // setEnabled(false);
    // }
  }

  /**
   * Shows an about box
   */
  @Override
  public void run() {
    FindBrickUIWindow findBrick = new FindBrickUIWindow(getManager().getShell());
    findBrick.setBlockOnOpen(true);
    findBrick.open();

  }

  public void update(boolean isDriverAvailable) {
    setEnabled(isDriverAvailable);
  }
}
