package com.jbricx.swing.actions;

import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;

import com.jbricx.swing.ui.JBricxManager;

/**
 * This action cuts the current selection to the clipboard
 */
@SuppressWarnings("serial")
public class CutAction extends JBricxAbstractAction {

	/**
	 * CutAction constructor
	 */
	public CutAction(final JBricxManager manager) {
		super("", new ImageIcon("./resources/images/edit-cut.png"), manager);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Implement logic when Tabs are done

	}
}
