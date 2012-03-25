package com.jbricx.swing.actions;

import java.awt.event.ActionEvent;
import java.io.IOException;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;

import com.jbricx.swing.ui.JBricxManager;
import com.jbricx.swing.ui.preferences.JBricxPreferencesWindow;



/**
 * This action displays the preferences dialog
 */
public class PreferencesAction extends JBricxAbstractAction {

	/**
	 * PreferencesAction constructor
	 */
	public PreferencesAction(final JBricxManager manager) {
		super("", new ImageIcon("./resources/images/preferences-desktop.png"),
				manager);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JBricxPreferencesWindow preferencwindow = new JBricxPreferencesWindow(
				getManager());
		preferencwindow.setVisible(true);
	}

}
