package com.jbricx.swing.actions;

import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;

import org.fife.ui.rtextarea.RTextScrollPane;

import com.jbricx.swing.ui.JBricxManager;
import com.jbricx.swing.ui.tabs.JBricxTabItem;
/**
 * This action copies the current selection to the clipboard
 */
@SuppressWarnings("serial")
public class SelectAllAction extends JBricxAbstractAction {
  /**
   * CopyAction constructor
   */
  public SelectAllAction(final JBricxManager manager) {
	  super("", new ImageIcon(SelectAllAction.class.getResource("/images/edit-selectall.png")), manager);
  }

  /**
   * Runs the action
   */
  public void actionPerformed(ActionEvent e) {
	  JBricxTabItem tab =(JBricxTabItem)((RTextScrollPane)getManager().getTabFolder().getSelectedComponent()).getViewport().getView();
	    tab.selectAll();
  }
}
