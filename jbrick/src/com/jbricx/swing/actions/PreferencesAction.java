package com.jbricx.swing.actions;

import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;

import com.jbricx.swing.ui.JBricxManager;
import com.jbricx.swing.ui.tabs.preference.JBricxPreferenceDialog;



/**
 * This action displays the preferences dialog
 */
@SuppressWarnings("serial")
public class PreferencesAction extends JBricxAbstractAction {

	/**
	 * PreferencesAction constructor
	 */
	public PreferencesAction(final JBricxManager manager) {
		super("", new ImageIcon(PreferencesAction.class.getResource("/icons/preferences.png")),
				manager);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JBricxPreferenceDialog.openPreference(getManager());
	}
}
