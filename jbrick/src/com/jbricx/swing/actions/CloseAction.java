package com.jbricx.swing.actions;

import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;

import com.jbricx.swing.ui.JBricxManager;
import com.jbricx.swing.ui.tabs.JBricxEditorTabFolder;

@SuppressWarnings("serial") 
public class CloseAction extends JBricxAbstractAction{

	/**
	 * CutAction constructor
	 */
	public CloseAction(final JBricxManager manager) {
		super("", new ImageIcon(CopyAction.class.getResource("/images/mail-mark-not-junk.png")), manager);
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
