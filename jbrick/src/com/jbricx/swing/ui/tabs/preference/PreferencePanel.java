package com.jbricx.swing.ui.tabs.preference;

import java.util.HashMap;

import javax.swing.JPanel;

import com.jbricx.swing.ui.preferences.PreferenceStore.Preference;

/**
 * Preference Panel
 * Has a value to save.
 * adds itself to a panel list that gets updated on saveValues call
 * 	by JBricxPrefeerenceDialog
 */
@SuppressWarnings("serial")
abstract class PreferencePanel extends JPanel {
	static HashMap<Preference, PreferencePanel> panels = new HashMap<Preference, PreferencePanel>();
	PreferencePanel(Preference pref){
		panels.put(pref, this);
	}
	
	/**
	 * apply all values that have been stored in the panels hash
	 */
	static void applyValues() {
		for(PreferencePanel pp : panels.values()){
			pp.saveValue();
		}
	}
	
	/**
	 * reset all values that have been stored in the panels hash
	 */
	static void resetValues() {
		for(PreferencePanel pp : panels.values()){
			pp.resetValue();
		}
	}
	
	/**
	 * saveValue saves a preference to the Preference Store 
	 */
	public abstract void saveValue();
	
	/**
	 * resetValue resets a preference based on the theme provided and Preference Store
	 */
	public abstract void resetValue();
	
	/**
	 * default implementation for creating a panel
	 * @return
	 */
	public abstract JPanel createPanel();
}