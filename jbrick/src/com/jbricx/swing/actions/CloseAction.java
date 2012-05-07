package com.jbricx.swing.actions;

import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;

import com.jbricx.swing.ui.JBricxManager;
import com.jbricx.swing.ui.tabs.JBricxEditorTabFolder;

public class CloseAction extends JBricxAbstractAction{

	/**
	 * CutAction constructor
	 */
	public CloseAction(final JBricxManager manager) {
		super("", null,/*new ImageIcon(CopyAction.class.getResource("/images/edit-cut.png")),*/ manager);
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		JBricxEditorTabFolder tabFolder = getManager().getTabFolder();
		int selectedIndex = tabFolder.getSelectedIndex();
		if(tabFolder.closeFile(selectedIndex)){
			tabFolder.remove(selectedIndex);
		}
		
	}

}
