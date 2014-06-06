package com.jbricx.swing.ui.preferences;

import java.awt.Color;
import java.util.ArrayList;
import java.util.prefs.Preferences;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import com.jbricx.tools.XMLParser;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
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
	public static String FONTNAME_DEFAULT = "Consolas";
	public static String FONTSIZE_DEFAULT = "20";
	public static String FONTSTYLE_DEFAULT = "plain";
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
		PROPERTIES(null),
			COLOR(PROPERTIES),
				FOREGROUND (COLOR, PreferenceStore.FOREGROUND_DEFAULT),
				BACKGROUND (COLOR, PreferenceStore.BACKGROUND_DEFAULT),
				COMMENT (COLOR, PreferenceStore.COMMENT_DEFAULT),
				KEYWORD (COLOR, PreferenceStore.KEYWORD_DEFAULT),
			    OPERATOR (COLOR, PreferenceStore.OPERATOR_DEFAULT),
			    STRING (COLOR, PreferenceStore.STRING_DEFAULT),
			    LINENUMBERFG (COLOR, PreferenceStore.LINENUMBERFG_DEFAULT),
			    LINENUMBERBG (COLOR, PreferenceStore.LINENUMBERBG_DEFAULT),
			    CONSTANT (COLOR, PreferenceStore.CONSTANT_DEFAULT),
			    PREPROCESSOR (COLOR, PreferenceStore.PREPROCESSOR_DEFAULT),
			    CONTAINERS (COLOR, PreferenceStore.CONTAINERS_DEFAULT),
			FONT(PROPERTIES),
				FONTNAME (FONT, PreferenceStore.FONTNAME_DEFAULT),
				FONTSIZE (FONT, PreferenceStore.FONTSIZE_DEFAULT),
				FONTSTYLE (FONT, PreferenceStore.FONTSTYLE_DEFAULT),
			ICON(PROPERTIES),
		    	ICONSIZE (ICON, PreferenceStore.ICONSIZE_DEFAULT),
		    MISC(PROPERTIES),
		    	WRAP (MISC, PreferenceStore.WRAP_DEFAULT),
			    AUTOCOMPILE (MISC, PreferenceStore.AUTOCOMPILE_DEFAULT),
			    BOOLRECENTFILES (MISC, PreferenceStore.BOOLRECENTFILES_DEFAULT),
			    LINENUM (MISC, PreferenceStore.LINENUM_DEFAULT),
				NBCTOOL (MISC, PreferenceStore.NBCTOOL_DEFAULT),
				WORKSPACE (MISC, PreferenceStore.WRKSPC_DEFAULT),
				THEMEXML (MISC, PreferenceStore.THEMEXML_DEFAULT);
		
		private Preference parent = null;
		private ArrayList<Preference> children = new ArrayList<Preference>();
		public Integer defaultInt = null;
		public Boolean defaultBool = null;
		public String defaultString = "";
		
		Preference(Preference parent) {
			this.parent = parent;
			if (this.parent != null) {
				this.parent.children.add(this);
			}
		}
		
		Preference(Preference parent, String defaultString) {
			this(parent);
			this.defaultString = defaultString;
		}
		
		Preference(Preference parent, Integer defaultInt) {
			this(parent, defaultInt.toString());
			this.defaultInt = defaultInt;
		}
		
		Preference(Preference parent, Boolean defaultBool) {
			this(parent, defaultBool.toString());
			this.defaultBool = defaultBool;
		}
		
		/**
		 * make nodes recursively
		 * @param doc - document that node will be contained in
		 * @return node that contains all information
		 */
		public Node makeNode(Document doc) {
			Element p = doc.createElement(this.toString().toLowerCase());
			for (Preference child : children){
				Node c = child.makeNode(doc);
				c.appendChild(doc.createTextNode(child.getPref()));
				p.appendChild(c);
			}
			return p;
		}
		
		public String getPref() {
			return this.defaultString != "" ? prefs.get(this.toString().toLowerCase(), this.defaultString) : "";
			
		}
	}

	/**
	 * Constructor for preference store. If the program has never been run on this machine before, will load defaults.
	 */
	public PreferenceStore(){
		final String NAME = "allPreferences";
		prefs = Preferences.userRoot().node(NAME);
		setPreferencesAndDefaults();
	}
	
	/**
	 * Sets preferences to defaults
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
		tempNode = XMLParser.retrieve(doc, "color", "linenumberfg");
		LINENUMBERFG_DEFAULT = Integer.parseInt(tempNode.getTextContent());
		tempNode = XMLParser.retrieve(doc, "color", "linenumberbg");
		LINENUMBERBG_DEFAULT = Integer.parseInt(tempNode.getTextContent());
		tempNode = XMLParser.retrieve(doc, "color", "constant");
		CONSTANT_DEFAULT = Integer.parseInt(tempNode.getTextContent());
		tempNode = XMLParser.retrieve(doc, "color", "preprocessor");
		PREPROCESSOR_DEFAULT = Integer.parseInt(tempNode.getTextContent());
		tempNode = XMLParser.retrieve(doc, "color", "containers");
		CONTAINERS_DEFAULT = Integer.parseInt(tempNode.getTextContent());
		
		
		//set font settings
		String fontTemp ="";
		tempNode = XMLParser.retrieve(doc, "font", "fontname");
		fontTemp += tempNode.getTextContent() + "-";
		tempNode = XMLParser.retrieve(doc, "font", "fontstyle");
		fontTemp += tempNode.getTextContent() + "-";
		tempNode = XMLParser.retrieve(doc, "font", "fontsize");
		fontTemp += tempNode.getTextContent();
		FONT_DEFAULT = fontTemp;
		
		//icon size setting
		tempNode = XMLParser.retrieve(doc, "icon", "iconsize");
		ICONSIZE_DEFAULT = Integer.parseInt(tempNode.getTextContent());
		
		//set misc settings
		tempNode = XMLParser.retrieve(doc, "misc", "wrap");
		WRAP_DEFAULT = Boolean.parseBoolean(tempNode.getTextContent());
		tempNode = XMLParser.retrieve(doc, "misc", "autocompile");
		AUTOCOMPILE_DEFAULT = Boolean.parseBoolean(tempNode.getTextContent());
		tempNode = XMLParser.retrieve(doc, "misc", "linenum");
		LINENUM_DEFAULT = Boolean.parseBoolean(tempNode.getTextContent());
		tempNode = XMLParser.retrieve(doc, "misc", "nbctool");
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
		tempNode = XMLParser.retrieve(doc, "color", "linenumberfg");
		prefs.putInt(Preference.LINENUMBERFG.toString(), Integer.parseInt(tempNode.getTextContent()));
		tempNode = XMLParser.retrieve(doc, "color", "linenumberbg");
		prefs.putInt(Preference.LINENUMBERBG.toString(), Integer.parseInt(tempNode.getTextContent()));
		tempNode = XMLParser.retrieve(doc, "color", "constant");
		prefs.putInt(Preference.CONSTANT.toString(), Integer.parseInt(tempNode.getTextContent()));
		tempNode = XMLParser.retrieve(doc, "color", "preprocessor");
		prefs.putInt(Preference.PREPROCESSOR.toString(),Integer.parseInt(tempNode.getTextContent()));
		tempNode = XMLParser.retrieve(doc, "color", "containers");
		prefs.putInt(Preference.CONTAINERS.toString(),Integer.parseInt(tempNode.getTextContent()));
		
		
		//set font settings
		String fontTemp ="";
		tempNode = XMLParser.retrieve(doc, "font", "fontname");
		fontTemp += tempNode.getTextContent() + "-";
		tempNode = XMLParser.retrieve(doc, "font", "fontstyle");
		fontTemp += tempNode.getTextContent() + "-";
		tempNode = XMLParser.retrieve(doc, "font", "fontsize");
		fontTemp += tempNode.getTextContent();
		prefs.put(FONT, fontTemp);
		
		//icon size setting
		tempNode = XMLParser.retrieve(doc, "icon", "iconsize");
		prefs.putBoolean(ICONSIZE, Boolean.parseBoolean(tempNode.getTextContent()));
		
		//set misc settings
		tempNode = XMLParser.retrieve(doc, "misc", "wrap");
		prefs.putBoolean(WRAP, Boolean.parseBoolean(tempNode.getTextContent()));
		tempNode = XMLParser.retrieve(doc, "misc", "autocompile");
		prefs.putBoolean(AUTOCOMPILE, Boolean.parseBoolean(tempNode.getTextContent()));
		tempNode = XMLParser.retrieve(doc, "misc", "linenum");
		prefs.putBoolean(LINENUM, Boolean.parseBoolean(tempNode.getTextContent()));
		tempNode = XMLParser.retrieve(doc, "misc", "nbctool");
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
		return new Color(getInt(key));
	}
	
	/**
	 * get Preference Integer
	 * @param int
	 * @return
	 */
	public static Integer getInt(Preference key){
		return prefs.getInt(key.toString(), key.defaultInt);
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
	
	public static Document buildPreferencesDocument(){
		 
		Document doc = null;
		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			doc = docBuilder.newDocument();
			Node nodeTree = PreferenceStore.Preference.PROPERTIES.makeNode(doc);
			doc.appendChild(nodeTree);
			XMLParser.writeToFile(doc, "");
			System.out.println();
		} catch (ParserConfigurationException e) {
			System.err.println("ERROR IN CREATING DOCUMENT");
		}
		return doc;
	}
}
