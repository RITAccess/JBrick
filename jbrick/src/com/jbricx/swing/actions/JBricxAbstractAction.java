/**
 * 
 */
package com.jbricx.swing.actions;

import javax.swing.AbstractAction;
import javax.swing.Icon;

import org.fife.ui.rtextarea.RTextScrollPane;

import com.jbricx.swing.ui.JBricxManager;
import com.jbricx.swing.ui.tabs.JBricxTabItem;

/**
 * @author Dan Larsen
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

  /**
   * 
   * @return the manager to access other components
   */
  protected JBricxManager getManager() {
    return manager;
  }

  /**
   * 
   * @param manager Manager to set for later retrieval
   */
  protected void setManager(JBricxManager manager) {
    this.manager = manager;
  }
  
  /**
   * 
   * @return the currently focused tab for actions such as cut/copy/paste/goto/etc
   */
  protected JBricxTabItem getTab(){
	  return (JBricxTabItem)((RTextScrollPane)getManager().getTabFolder().getSelectedComponent()).getViewport().getView();
  }
}
