/**
 * 
 */
package com.jbricx.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;

import com.jbricx.ui.JBrickManager;

/**
 * @author byktol
 */
public abstract class AbstractAction extends Action {

  private JBrickManager manager;

  public AbstractAction(final JBrickManager manager) {
    super();
    setManager(manager);
  }

  public AbstractAction(final String text, final ImageDescriptor image, final JBrickManager manager) {
    super(text, image);
    setManager(manager);
  }

  public AbstractAction(final String text, int style, final JBrickManager manager) {
    super(text, style);
    setManager(manager);
  }

  public AbstractAction(final String text, final JBrickManager manager) {
    super(text);
    setManager(manager);
  }

  protected JBrickManager getManager() {
    return manager;
  }

  protected void setManager(JBrickManager manager) {
    this.manager = manager;
  }
}
