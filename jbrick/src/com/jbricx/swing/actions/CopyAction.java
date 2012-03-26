package com.jbricx.swing.actions;

import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;

import com.jbricx.swing.ui.JBricxManager;

/**
 * This action copies the current selection to the clipboard
 */
@SuppressWarnings("serial")
public class CopyAction extends JBricxAbstractAction {

	/**
	 * CopyAction constructor
	 */
	public CopyAction(final JBricxManager manager) {
		super("", new ImageIcon("./resources/images/edit-copy.png"), manager);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Implement when Tabs are done

	}
}
