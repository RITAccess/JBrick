package com.jbricx.actions;

import org.eclipse.jface.resource.ImageDescriptor;

import com.jbricx.ui.JBrickManager;

/**
 * This action class exits the application
 */
public class ExitAction extends AbstractAction {
  /**
   * ExitAction constructor
   */
  public ExitAction(final JBrickManager manager) {
    super("E&xit@Ctrl+Q", ImageDescriptor.createFromFile(PasteAction.class, "/images/system-log-out.png"), manager);
    setToolTipText("Exit");
  }

  /**
   * Exits the application
   */
  public void run() {
    getManager().close();
  }
}
