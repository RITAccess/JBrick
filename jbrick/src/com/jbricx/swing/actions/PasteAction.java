package com.jbricx.swing.actions;

import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;

import org.fife.ui.rtextarea.RTextScrollPane;

import com.jbricx.swing.ui.JBricxManager;
import com.jbricx.swing.ui.tabs.JBricxTabItem;

/**
 * This action paste the contents of the clipboard into the document
 */
@SuppressWarnings(value = { "serial" }) 
public class PasteAction extends JBricxAbstractAction {
  /**
   * PasteAction constructor
   */
  public PasteAction(final JBricxManager manager) {
	  super("", new ImageIcon(PasteAction.class.getResource("/images/edit-paste.png")), manager);
  }

  /**
   * Runs the action
   */
  public void actionPerformed(ActionEvent e) {
	  JBricxTabItem tab =(JBricxTabItem)((RTextScrollPane)getManager().getTabFolder().getSelectedComponent()).getViewport().getView();
	    tab.paste();
  }
}
