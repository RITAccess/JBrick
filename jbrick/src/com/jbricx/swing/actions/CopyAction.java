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
public class CopyAction extends JBricxAbstractAction {

	/**
	 * CopyAction constructor
	 */
	public CopyAction(final JBricxManager manager) {
		super("", new ImageIcon(CopyAction.class.getResource(
				"/icons/editCopy.png")), manager);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JBricxTabItem tab =(JBricxTabItem)((RTextScrollPane)getManager().getTabFolder().getSelectedComponent()).getViewport().getView();
	    tab.copy();
	}
}
