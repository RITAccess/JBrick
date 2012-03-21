/**
 * 
 */
package com.jbricx.swing.actions;

import javax.swing.AbstractAction;
import javax.swing.Icon;


import com.jbricx.swing.ui.*;

/**
 * @author byktol
 */
@SuppressWarnings("serial")
public abstract class JBricxAbstractAction extends AbstractAction {

  private JBricxManager manager;

  public JBricxAbstractAction(final JBricxManager manager) {
    setManager(manager);
  }

  public JBricxAbstractAction(final String text, Icon icon, final JBricxManager manager) {
    super(text, icon);
    setManager(manager);
  }

  public JBricxAbstractAction(final String text, int style, final JBricxManager manager) {
    super(text);
    setManager(manager);
  }

  public JBricxAbstractAction(final String text, final JBricxManager manager) {
    super(text);
    setManager(manager);
  }

  protected JBricxManager getManager() {
    return manager;
  }

  protected void setManager(JBricxManager manager) {
    this.manager = manager;
  }
}
