package com.jbricx.swing.actions;

import java.awt.Font;
import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;

import com.jbricx.swing.ui.JBricxManager;
import com.jbricx.swing.ui.preferences.PreferenceStore;
import com.jbricx.swing.ui.preferences.PreferenceStore.Preference;

@SuppressWarnings("serial")
public class IncreaseFontSizeAction extends JBricxAbstractAction {

	JBricxManager manager;

	  /**
	   * IncreaseFontSizeAction constructor
	   */
	  public IncreaseFontSizeAction(final JBricxManager manager) {
		  super("", new ImageIcon(CopyAction.class.getResource("/icons/fontIncrease.png")), manager);
		  this.manager = manager;
	  }

	  /**
	   * Increases font size
	   */
	  public void actionPerformed(ActionEvent e) {
		  Font font = Font.decode(PreferenceStore.getString(Preference.FONT));
		  String fontStr = font.getName() +"-"+ font.toString().split("=")[3].split(",")[0]+"-"+ (font.getSize() + 5);
		  PreferenceStore.set(Preference.FONT, fontStr);
		  manager.updatePreferences();
	  }
}
