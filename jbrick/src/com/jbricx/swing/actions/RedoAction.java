package com.jbricx.swing.actions;

import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;

import org.fife.ui.rtextarea.RTextScrollPane;

import com.jbricx.swing.ui.JBricxManager;
import com.jbricx.swing.ui.tabs.JBricxTabItem;

/**
 * This action redoes the last action
 */
@SuppressWarnings("serial")
public class RedoAction extends JBricxAbstractAction {

  /**
   * RedoAction constructor
   */
  public RedoAction(final JBricxManager manager) {
	  super("", new ImageIcon(RedoAction.class.getResource("/icons/editRedo.png")), manager);
  }

  /**
   * Runs the action
   */
  public void actionPerformed(ActionEvent e)  {
	  JBricxTabItem tab =(JBricxTabItem)((RTextScrollPane)getManager().getTabFolder().getSelectedComponent()).getViewport().getView();
	    tab.redoLastAction();
  }
}
