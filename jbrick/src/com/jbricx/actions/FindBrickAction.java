package com.jbricx.actions;

import org.eclipse.jface.resource.ImageDescriptor;

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
    super("&FindBrick@Ctrl+B", ImageDescriptor.createFromFile(FindBrickAction.class, "/images/findBrick.png"), manager);
    setToolTipText("Find Brick");
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
}
