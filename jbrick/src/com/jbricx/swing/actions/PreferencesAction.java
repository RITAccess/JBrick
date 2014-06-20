package com.jbricx.swing.actions;

import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;

import com.jbricx.swing.ui.JBricxManager;
import com.jbricx.swing.ui.preferences.JBricxPreferencesWindow;



/**
 * This action displays the preferences dialog
 */
@SuppressWarnings("serial")
public class PreferencesAction extends JBricxAbstractAction {

	/**
	 * PreferencesAction constructor
	 */
	public PreferencesAction(final JBricxManager manager) {
		super("", new ImageIcon(PreferencesAction.class.getResource("/images/preferences-desktop.png")),
				manager);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JBricxPreferencesWindow preferencwindow = new JBricxPreferencesWindow(
				getManager());
		preferencwindow.setVisible(true);
	}

}
