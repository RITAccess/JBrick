package com.jbricx.swing.actions;

import java.awt.Font;
import java.awt.event.ActionEvent;

import com.jbricx.swing.ui.JBricxManager;
import com.jbricx.swing.ui.preferences.PreferenceStore;
import com.jbricx.swing.ui.preferences.PreferenceStore.Preference;

@SuppressWarnings("serial")
public class DecreaseFontSizeAction extends JBricxAbstractAction {

	JBricxManager manager;
	
	  /**
	   * DecreaseFontSizeAction constructor
	   */
	  public DecreaseFontSizeAction(final JBricxManager manager) {
		  super("", null, manager);
		  this.manager = manager;
	  }

	  /**
	   * Decreases Font Size
	   */
	  public void actionPerformed(ActionEvent e) {
		  Font font = Font.decode(PreferenceStore.getString(Preference.FONT));
		  String fontStr = font.getName() +"-"+ font.toString().split("=")[3].split(",")[0]+"-"+ 
		  (font.getSize() > 8 ? (font.getSize() - 5) : (font.getSize()));
		  PreferenceStore.set(Preference.FONT, fontStr);
		  manager.updatePreferences();
	  }
}
