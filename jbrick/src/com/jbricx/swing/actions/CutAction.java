package com.jbricx.swing.actions;

import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;

import org.fife.ui.rtextarea.RTextScrollPane;

import com.jbricx.swing.ui.JBricxManager;
import com.jbricx.swing.ui.tabs.JBricxTabItem;

/**
 * This action cuts the current selection to the clipboard
 */
@SuppressWarnings("serial")
public class CutAction extends JBricxAbstractAction {

	/**
	 * CutAction constructor
	 */
	public CutAction(final JBricxManager manager) {
		super("", new ImageIcon(CopyAction.class.getResource("/icons/editCut.png")), manager);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JBricxTabItem tab =(JBricxTabItem)((RTextScrollPane)getManager().getTabFolder().getSelectedComponent()).getViewport().getView();
	    tab.cut();

	}
}
