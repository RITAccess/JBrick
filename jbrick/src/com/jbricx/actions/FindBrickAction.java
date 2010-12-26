package com.jbricx.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;

import com.jbricx.ui.findbrick.FindBrickUIWindow;

/**
 * This class shows an About box
 */
public class FindBrickAction extends Action {
  /**
   * AboutAction constructor
   */
  public FindBrickAction() {
    super("&FindBrick@Ctrl+B", ImageDescriptor.createFromFile(FindBrickAction.class, "/images/findBrick.png"));
    setToolTipText("Find Brick");
  }

  /**
   * Shows an about box
   */
  public void run() {
    FindBrickUIWindow findBrick = new FindBrickUIWindow();
    findBrick.setBlockOnOpen(true);
    findBrick.open();
  }
}
