package com.jbricx.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;

import com.jbricx.pjo.JBrickEditor;

//TODO: Remove the dependency with the JBrickEditor class.
/**
 * This action cuts the current selection to the clipboard
 */
public class CutAction extends Action {

  /**
   * CutAction constructor
   */
  public CutAction() {
    super("Cu&t@Ctrl+X", ImageDescriptor.createFromFile(CutAction.class, "/images/edit-cut.png"));
    setToolTipText("Cut");
  }

  /**
   * Runs the action
   */
  public void run() {
    //TODO: why?
    JBrickEditor.getInstance().cut();
  }
}
