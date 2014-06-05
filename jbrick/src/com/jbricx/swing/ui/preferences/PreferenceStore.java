package com.jbricx.swing.ui.preferences;

import java.awt.Color;
import java.util.prefs.Preferences;

import com.jbricx.tools.XMLParser;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

public class PreferenceStore {

	private static Preferences prefs;
	
	// Filter names for file dialogs
	public static final String FILTER_NAME = "NXC Files (*.nxc)";
	// TODO: add all file names
	// Filter extensions for file dialogs
	public static final String FILTER_EXTENSION = ".nxc";
	// TODO: add all file extensions
	
	// Preference names
	public static final String WRAP = "wrap";
	public static final String FONT = "font";
	public static final String WRKSPC = "workspace"; 
	public static final String AUTOCOMPILE = "autocompile";
	public static final String LINENUM = "linenumber";
	public static final String ICONSIZE = "iconsize";
	
	//Preference Defaults
	
	public static boolean WRAP_DEFAULT = false;
	public static String FONT_DEFAULT  = "Consolas-plain-20";
	public static final String WRKSPC_DEFAULT = System.getProperty("user.home")
			+ (System.getProperty("os.name").contains("OS X") ? "/Documents/"
					: "\\Documents\\");
	public static boolean AUTOCOMPILE_DEFAULT = false;
	public static boolean LINENUM_DEFAULT = true;
	public static String NBCTOOL_DEFAULT = "";
	public static final String THEMEXML_DEFAULT = "resources/config/Properties.xml";
	

	public static int ICONSIZE_DEFAULT = 44;

	public static int FOREGROUND_DEFAULT = Color.BLACK.getRGB();
	public static int BACKGROUND_DEFAULT = Color.WHITE.getRGB();
	public static int OPERATOR_DEFAULT = Color.MAGENTA.darker().getRGB();
	public static int COMMENT_DEFAULT = Color.GRAY.getRGB();
	public static int LINENUMBERFG_DEFAULT = Color.RED.getRGB();
	public static int LINENUMBERBG_DEFAULT = Color.WHITE.getRGB();
	public static int STRING_DEFAULT = Color.GREEN.getRGB();
	public static int KEYWORD_DEFAULT = Color.MAGENTA.darker().getRGB();
	public static int CONSTANT_DEFAULT = Color.BLUE.getRGB();
	public static int PREPROCESSOR_DEFAULT = Color.ORANGE.darker().getRGB();
	public static int CONTAINERS_DEFAULT = Color.RED.darker().getRGB();


	// Recent files to be loaded when app runs
	public static final String BOOLRECENTFILES = "boolrecentfiles";
	public static final boolean BOOLRECENTFILES_DEFAULT = true;
	public static final String RECENTFILES = "recentfiles"; //default is ""

	// Tools names for preferences
	public static final String BRICKTOOL = "brickTool";
	public static final String NEXTTOOL = "nextTool";
	public static final String NBCTOOL = "nbcTool";
	public static final String THEMEXML = "themeXML";

	// Configuration files
	//public static final String PREFERENCES_FILE = "JBrickEditor.properties";
	public static final String KEYWORDS_FILE = "config/KeyWords.xml";
	public static final String OPERATORS_FILE = "config/Operators.xml";
	public static final String CONSTANTS_FILE = "config/Constants.xml";
	public static final String AUTOCOMPLETE_FILE = "config/Autocomplete.xml";

	public static final String DEFAULT_FILE = "resources/config/Properties.xml";
	
	//Parser to parse the preference files
	public static Document currentDoc;
	
	//Colors and defaults
	

	public static enum Preference {
		FOREGROUND (PreferenceStore.FOREGROUND_DEFAULT),
	    BACKGROUND (PreferenceStore.BACKGROUND_DEFAULT),
	    COMMENT (PreferenceStore.COMMENT_DEFAULT),
	    KEYWORD (PreferenceStore.KEYWORD_DEFAULT),
	    OPERATOR (PreferenceStore.OPERATOR_DEFAULT),
	    STRING (PreferenceStore.STRING_DEFAULT),
	    LINENUMBERFG (PreferenceStore.LINENUMBERFG_DEFAULT),
	    LINENUMBERBG (PreferenceStore.LINENUMBERBG_DEFAULT),
	    CONSTANT (PreferenceStore.CONSTANT_DEFAULT),
	    PREPROCESSOR (PreferenceStore.PREPROCESSOR_DEFAULT),
	    CONTAINERS (PreferenceStore.CONTAINERS_DEFAULT),
	    WRAP (PreferenceStore.WRAP_DEFAULT),
	    FONT (PreferenceStore.FONT_DEFAULT),
	    AUTOCOMPILE (PreferenceStore.AUTOCOMPILE_DEFAULT),
	    BOOLRECENTFILES (PreferenceStore.BOOLRECENTFILES_DEFAULT),
		NBCTOOL (PreferenceStore.NBCTOOL_DEFAULT),
		WORKSPACE (PreferenceStore.WRKSPC_DEFAULT),
		THEMEXML (PreferenceStore.THEMEXML_DEFAULT);
		
		public int defaultColor;
		public boolean defaultBool;
		public String defaultString;
		
		Preference(int defaultColor){
			this.defaultColor = defaultColor;
		}
		
		Preference(boolean defaultBool){
			this.defaultBool = defaultBool;
		}
		
		Preference(String defaultString){
			this.defaultString = defaultString;
		}

	}

	/**
	 * Constructor for preference store.
	 */
	public PreferenceStore(){
		final String NAME = "allPreferences";
		prefs = Preferences.userRoot().node(NAME);
		setPreferencesAndDefaults();
	}
	
	/**
	 * First time setup for setting default values and preferences. If the program has never been run on this machine before, will load defaults.
	 */
	public static void setPreferencesAndDefaults() {

		currentDoc = XMLParser.xmlParse(DEFAULT_FILE);

		if(!currentDoc.equals(null)){
			if(!prefs.getBoolean("ranPreviously", false)){
				setPrefsFromFile(currentDoc);
			}
			setDefaultsFromFile(currentDoc);
		}
	}
	
	
	/**
	 * 	Sets the default values that are used when resetting to defaults.
	 * @param Document to read values from.
	 */
	private static void setDefaultsFromFile(Document doc) {
		
		Node tempNode;
		
		//set color settings
		tempNode = XMLParser.retrieve(doc, "color", "foreground");
		FOREGROUND_DEFAULT = Integer.parseInt(tempNode.getTextContent());
		tempNode = XMLParser.retrieve(doc, "color", "background");
		BACKGROUND_DEFAULT = Integer.parseInt(tempNode.getTextContent());
		tempNode = XMLParser.retrieve(doc, "color", "operator");
		OPERATOR_DEFAULT = Integer.parseInt(tempNode.getTextContent());
		tempNode = XMLParser.retrieve(doc, "color", "comment");
		COMMENT_DEFAULT = Integer.parseInt(tempNode.getTextContent());
		tempNode = XMLParser.retrieve(doc, "color", "keyword");
		KEYWORD_DEFAULT = Integer.parseInt(tempNode.getTextContent());
		tempNode = XMLParser.retrieve(doc, "color", "string");
		STRING_DEFAULT = Integer.parseInt(tempNode.getTextContent());
		tempNode = XMLParser.retrieve(doc, "color", "line-fg");
		LINENUMBERFG_DEFAULT = Integer.parseInt(tempNode.getTextContent());
		tempNode = XMLParser.retrieve(doc, "color", "line-bg");
		LINENUMBERBG_DEFAULT = Integer.parseInt(tempNode.getTextContent());
		tempNode = XMLParser.retrieve(doc, "color", "constant");
		CONSTANT_DEFAULT = Integer.parseInt(tempNode.getTextContent());
		tempNode = XMLParser.retrieve(doc, "color", "preprocessor");
		PREPROCESSOR_DEFAULT = Integer.parseInt(tempNode.getTextContent());
		tempNode = XMLParser.retrieve(doc, "color", "containers");
		CONTAINERS_DEFAULT = Integer.parseInt(tempNode.getTextContent());
		
		
		//set font settings
		String fontTemp ="";
		tempNode = XMLParser.retrieve(doc, "font", "name");
		fontTemp += tempNode.getTextContent() + "-";
		tempNode = XMLParser.retrieve(doc, "font", "style");
		fontTemp += tempNode.getTextContent() + "-";
		tempNode = XMLParser.retrieve(doc, "font", "size");
		fontTemp += tempNode.getTextContent();
		FONT_DEFAULT = fontTemp;
		
		//icon size setting
		tempNode = XMLParser.retrieve(doc, "icon", "size");
		ICONSIZE_DEFAULT = Integer.parseInt(tempNode.getTextContent());
		
		//set misc settings
		tempNode = XMLParser.retrieve(doc, "misc", "word-wrap");
		WRAP_DEFAULT = Boolean.parseBoolean(tempNode.getTextContent());
		tempNode = XMLParser.retrieve(doc, "misc", "auto-compile");
		AUTOCOMPILE_DEFAULT = Boolean.parseBoolean(tempNode.getTextContent());
		tempNode = XMLParser.retrieve(doc, "misc", "line-num");
		LINENUM_DEFAULT = Boolean.parseBoolean(tempNode.getTextContent());
		tempNode = XMLParser.retrieve(doc, "misc", "nbc-loc");
		NBCTOOL_DEFAULT = tempNode.getTextContent();

		prefs.putBoolean("ranPreviously",true);	
	}
	
	/**
	 * Writes preferences to the java preferences file from a XML document.
	 * 
	 * @param XML document to read from.
	 */
	static void setPrefsFromFile(Document doc) {
		
		Node tempNode;
		
		//set color settings
		tempNode = XMLParser.retrieve(doc, "color", "foreground");
		prefs.putInt(Preference.FOREGROUND.toString(), Integer.parseInt(tempNode.getTextContent()));
		tempNode = XMLParser.retrieve(doc, "color", "background");
		prefs.putInt(Preference.BACKGROUND.toString(), Integer.parseInt(tempNode.getTextContent()));
		tempNode = XMLParser.retrieve(doc, "color", "operator");
		prefs.putInt(Preference.OPERATOR.toString(), Integer.parseInt(tempNode.getTextContent()));
		tempNode = XMLParser.retrieve(doc, "color", "comment");
		prefs.putInt(Preference.COMMENT.toString(), Integer.parseInt(tempNode.getTextContent()));
		tempNode = XMLParser.retrieve(doc, "color", "keyword");
		prefs.putInt(Preference.KEYWORD.toString(), Integer.parseInt(tempNode.getTextContent()));
		tempNode = XMLParser.retrieve(doc, "color", "string");
		prefs.putInt(Preference.STRING.toString(), Integer.parseInt(tempNode.getTextContent()));
		tempNode = XMLParser.retrieve(doc, "color", "line-fg");
		prefs.putInt(Preference.LINENUMBERFG.toString(), Integer.parseInt(tempNode.getTextContent()));
		tempNode = XMLParser.retrieve(doc, "color", "line-bg");
		prefs.putInt(Preference.LINENUMBERBG.toString(), Integer.parseInt(tempNode.getTextContent()));
		tempNode = XMLParser.retrieve(doc, "color", "constant");
		prefs.putInt(Preference.CONSTANT.toString(), Integer.parseInt(tempNode.getTextContent()));
		tempNode = XMLParser.retrieve(doc, "color", "preprocessor");
		prefs.putInt(Preference.PREPROCESSOR.toString(),Integer.parseInt(tempNode.getTextContent()));
		tempNode = XMLParser.retrieve(doc, "color", "containers");
		prefs.putInt(Preference.CONTAINERS.toString(),Integer.parseInt(tempNode.getTextContent()));
		
		
		//set font settings
		String fontTemp ="";
		tempNode = XMLParser.retrieve(doc, "font", "name");
		fontTemp += tempNode.getTextContent() + "-";
		tempNode = XMLParser.retrieve(doc, "font", "style");
		fontTemp += tempNode.getTextContent() + "-";
		tempNode = XMLParser.retrieve(doc, "font", "size");
		fontTemp += tempNode.getTextContent();
		prefs.put(FONT, fontTemp);
		
		//icon size setting
		tempNode = XMLParser.retrieve(doc, "icon", "size");
		prefs.putBoolean(ICONSIZE, Boolean.parseBoolean(tempNode.getTextContent()));
		
		//set misc settings
		tempNode = XMLParser.retrieve(doc, "misc", "word-wrap");
		prefs.putBoolean(WRAP, Boolean.parseBoolean(tempNode.getTextContent()));
		tempNode = XMLParser.retrieve(doc, "misc", "auto-compile");
		prefs.putBoolean(AUTOCOMPILE, Boolean.parseBoolean(tempNode.getTextContent()));
		tempNode = XMLParser.retrieve(doc, "misc", "line-num");
		prefs.putBoolean(LINENUM, Boolean.parseBoolean(tempNode.getTextContent()));
		tempNode = XMLParser.retrieve(doc, "misc", "nbc-loc");
		prefs.put(NBCTOOL,tempNode.getTextContent());
		prefs.put(WRKSPC,WRKSPC_DEFAULT);

		prefs.putBoolean("ranPreviously",true);	
	}
	
	/**
	 * Loads a theme from a file
	 * @param Theme file location
	 */
	public static void LoadTheme(String fileLoc){
		currentDoc = XMLParser.xmlParse(fileLoc);
		if(!currentDoc.equals(null)){
			setPrefsFromFile(currentDoc);
		}
		else{
			System.err.println("Could not find file.");
		}
	}
	
	public static Preferences getPrefs(){
		return prefs;
	}
	
	/**
	 * get Preference Color
	 * @param color
	 * @return
	 */
	public static Color getColor(Preference key){
		return new Color(prefs.getInt(key.toString(), key.defaultColor));
	}
	
	/**
	 * get Preference boolean
	 * @param key
	 * @return
	 */
	public static Boolean getBool(Preference key){
		return prefs.getBoolean(key.toString(), key.defaultBool);
	}
	
	/**
	 * get Preference String
	 * @param key
	 * @return
	 */
	public static String getString(Preference key){
		return prefs.get(key.toString(), key.defaultString);
	}
}
