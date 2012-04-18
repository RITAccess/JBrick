package com.jbricx.swing.actions;

import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;

import org.fife.ui.rtextarea.RTextScrollPane;

import com.jbricx.swing.ui.JBricxManager;
import com.jbricx.swing.ui.tabs.FindReplaceDialog;
import com.jbricx.swing.ui.tabs.JBricxTabItem;

/**
 * This action searches text
 */
@SuppressWarnings("serial")
public class FindAction extends JBricxAbstractAction {
	
	/**
	 * FindAction constructor
	 */
	public FindAction(final JBricxManager manager) {
		super("", new ImageIcon("./resources/images/edit-find.png"), manager);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Add logic to open and run find window

		// Old code from run method
		JBricxTabItem tab =(JBricxTabItem)((RTextScrollPane)getManager().getTabFolder().getSelectedComponent()).getViewport().getView();
		 FindReplaceDialog dlg = new FindReplaceDialog(getManager().getShell(),true,tab);
		dlg.setVisible(true);
		 // FindReplaceDialog(getManager().getShell(),
		// getManager().getTabFolder().getSelection().getDocument(),
		// getManager().getTabFolder().getSourceViewer());
		// dlg.open();
	}
}
