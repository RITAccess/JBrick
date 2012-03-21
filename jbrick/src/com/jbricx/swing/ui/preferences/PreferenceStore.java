package com.jbricx.swing.ui.preferences;

import java.util.prefs.Preferences;

public class PreferenceStore {

	private static Preferences prefs;
	
	// Filter names for file dialogs
	public static final String[] FILTER_NAMES = { "NXC Files (*.nxc)",
			"All Files (*.*)" };
	// TODO: add all file names
	// Filter extensions for file dialogs
	public static final String[] FILTER_EXTENSIONS = { "*.nxc", "*.*" };
	// TODO: add all file extensions
	// Preference names
	public static final String WRAP = "wrap";
	public static final String FONT = "font";
	public static final String WRKSPC = "workspace";
	public static final String AUTOCOMPILE = "autocompile";

	// Recent files to be loaded when app runs
	public static final String BOOLRECENTFILES = "boolrecentfiles";
	public static final String RECENTFILES = "recentfiles";

	// Tools names for preferences
	public static final String BRICKTOOL = "brickTool";
	public static final String NEXTTOOL = "nextTool";
	public static final String NBCTOOL = "nbcTool";

	// Configuration files
	//public static final String PREFERENCES_FILE = "JBrickEditor.properties";
	public static final String KEYWORDS_FILE = "config/KeyWords.xml";
	public static final String OPERATORS_FILE = "config/Operators.xml";
	public static final String CONSTANTS_FILE = "config/Constants.xml";
	public static final String AUTOCOMPLETE_FILE = "config/Autocomplete.xml";

	
	public PreferenceStore(){
		setPreferences();
	}
	
	static void setPreferences() {
		final String NAME = "allPreferences";
		prefs = Preferences.userRoot().node(NAME);
		prefs.putBoolean(WRAP, false);
	}
	
	public Preferences getPrefs(){
		return prefs;
	}
}
