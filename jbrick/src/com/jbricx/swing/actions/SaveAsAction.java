package com.jbricx.swing.actions;

import java.awt.event.ActionEvent;

import com.jbricx.pjo.ActionControlClass;

import javax.swing.ImageIcon;

import com.jbricx.swing.ui.JBricxManager;

/**
 * This action class responds to requests to save a file as . . .
 */
@SuppressWarnings("serial")
public class SaveAsAction extends JBricxAbstractAction {

	/**
	 * SaveAsAction constructor
	 */
	public SaveAsAction(final JBricxManager manager) {
		super("", new ImageIcon(SaveAsAction.class.getResource("/icons/saveAs.png")), manager);
	}

	/**
	 * Saves the file
	 */
	public void actionPerformed(ActionEvent e) {
		ActionControlClass.saveFile(getManager().getTabFolder()
				.getSelection(), true, getManager());
	}
}
