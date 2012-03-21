package com.jbricx.swing.ui.preferences;

import java.awt.Color;
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
	
	//Preference Defaults
	
	public static final boolean WRAP_DEFAULT = false;
	public static final String FONT_DEFAULT  = "Seqoe UI-plain-9";
	public static final boolean AUTOCOMPILE_DEFAULT = false;
 

	// Recent files to be loaded when app runs
	public static final String BOOLRECENTFILES = "boolrecentfiles";
	public static final boolean BOOLRECENTFILES_DEFAULT = false;
	public static final String RECENTFILES = "recentfiles"; //default is ""

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

	
	//Colors and defaults
	
	public static enum ColorFor {
		FOREGROUND,
	    BACKGROUND,
	    COMMENT,
	    KEYWORD,
	    OPERATOR,
	    STRING,
	    LINENUMBERFG,
	    LINENUMBERBG;
	}
	
	public static final int FOREGROUND_DEFAULT = Color.BLACK.getRGB();
	public static final int BACKGROUND_DEFAULT = Color.WHITE.getRGB();
	public static final int OPERATOR_DEFAULT = Color.ORANGE.getRGB();
	public static final int COMMENT_DEFAULT = Color.GRAY.getRGB();
	public static final int LINENUMBERFG_DEFAULT = Color.RED.getRGB();
	public static final int LINENUMBERBG_DEFAULT = Color.WHITE.getRGB();
	public static final int STRING_DEFAULT = Color.GREEN.getRGB();
	public static final int KEYWORD_DEFAULT = Color.BLUE.getRGB();

	
	public PreferenceStore(){
		setPreferencesAndDefaults();
	}
	
	static void setPreferencesAndDefaults() {
		final String NAME = "allPreferences";
		prefs = Preferences.userRoot().node(NAME);
		
		prefs.putInt(ColorFor.FOREGROUND.toString(), FOREGROUND_DEFAULT);
		prefs.putInt(ColorFor.BACKGROUND.toString(), BACKGROUND_DEFAULT);
		prefs.putInt(ColorFor.OPERATOR.toString(), OPERATOR_DEFAULT);
		prefs.putInt(ColorFor.COMMENT.toString(), COMMENT_DEFAULT);
		prefs.putInt(ColorFor.KEYWORD.toString(), KEYWORD_DEFAULT);
		prefs.putInt(ColorFor.STRING.toString(), STRING_DEFAULT);
		prefs.putInt(ColorFor.LINENUMBERFG.toString(), LINENUMBERFG_DEFAULT);
		prefs.putInt(ColorFor.LINENUMBERBG.toString(), LINENUMBERBG_DEFAULT);
		
		prefs.putBoolean(WRAP,WRAP_DEFAULT);
		prefs.put(FONT, FONT_DEFAULT);
		prefs.putBoolean(AUTOCOMPILE, AUTOCOMPILE_DEFAULT);
		
	}
	
	public Preferences getPrefs(){
		return prefs;
	}
}
