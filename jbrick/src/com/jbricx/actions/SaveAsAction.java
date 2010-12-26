package com.jbricx.actions;

import org.eclipse.jface.resource.ImageDescriptor;

import com.jbricx.pjo.ActionControlClass;
import com.jbricx.ui.JBrickManager;
import com.jbricx.ui.tabs.JBrickTabItem;

/**
 * This action class responds to requests to save a file as . . .
 */
public class SaveAsAction extends AbstractAction {

  /**
   * SaveAsAction constructor
   */
  public SaveAsAction(final JBrickManager manager) {
    super("Save As...", ImageDescriptor.createFromFile(SaveAsAction.class, "/images/document-save-as.png"), manager);
    setToolTipText("Save As");
  }

  /**
   * Saves the file
   */
  public void run() {
    JBrickTabItem tabItem = getManager().getCurrentTabItem();
    ActionControlClass.saveFile(tabItem, true);

    // TODO: Figure out why isAutoCompile() method part of the manager
    // interface. It makes no sense.
    if (getManager().isAutoCompile()) {
      CompileAction compileAction = new CompileAction(getManager());
      compileAction.run();
    }
  }
}
