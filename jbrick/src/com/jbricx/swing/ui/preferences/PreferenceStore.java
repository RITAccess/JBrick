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
	// TODO: add all file extension
	
	//Preference Defaults
	
	private static boolean WRAP_DEFAULT = false;
	private static String FONT_DEFAULT  = "Consolas-plain-20";
	private static String FONTNAME_DEFAULT = "Consolas";
	private static String FONTSIZE_DEFAULT = "20";
	private static String FONTSTYLE_DEFAULT = "plain";
	private static String WRKSPC_DEFAULT = System.getProperty("user.home")
			+ (System.getProperty("os.name").contains("OS X") ? "/Documents/"
					: "\\Documents\\");
	private static boolean AUTOSAVEONCOMPILE_DEFAULT = false;
	private static boolean LINENUM_DEFAULT = true;
	private static String NBCTOOL_DEFAULT = "";
	private static final String THEMEXML_DEFAULT = "resources/preferences/Default.xml";

	private static int FOREGROUND_DEFAULT = Color.BLACK.getRGB();
	private static int BACKGROUND_DEFAULT = Color.WHITE.getRGB();
	private static int OPERATOR_DEFAULT = Color.MAGENTA.darker().getRGB();
	private static int COMMENT_DEFAULT = Color.GRAY.getRGB();
	private static int LINENUMBERFG_DEFAULT = Color.RED.getRGB();
	private static int LINENUMBERBG_DEFAULT = Color.WHITE.getRGB();
	private static int STRING_DEFAULT = Color.GREEN.getRGB();
	private static int KEYWORD_DEFAULT = Color.MAGENTA.darker().getRGB();
	private static int CONSTANT_DEFAULT = Color.BLUE.getRGB();
	private static int PREPROCESSOR_DEFAULT = Color.ORANGE.darker().getRGB();
	private static int CONTAINERS_DEFAULT = Color.RED.darker().getRGB();


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

	public static final String DEFAULT_FILE = "resources/preferences/Default.xml";
	
	public static final String FILEDEBUG_DEFAULT = "";
	
	//Parser to parse the preference files
	public static Document currentDoc;
	
	
	public enum PreferenceType {
		INTEGER, BOOLEAN, STRING;
	}

	//Colors and defaults
	public static enum Preference {
		PROPERTIES(null),
			COLOR(PROPERTIES),
				FOREGROUND (COLOR, PreferenceStore.FOREGROUND_DEFAULT, "Foreground"),
				BACKGROUND (COLOR, PreferenceStore.BACKGROUND_DEFAULT, "Background"),
				COMMENT (COLOR, PreferenceStore.COMMENT_DEFAULT, "Comment"),
				KEYWORD (COLOR, PreferenceStore.KEYWORD_DEFAULT, "Keyword"),
			    OPERATOR (COLOR, PreferenceStore.OPERATOR_DEFAULT, "Operator"),
			    STRING (COLOR, PreferenceStore.STRING_DEFAULT, "String"),
			    LINENUMBERFG (COLOR, PreferenceStore.LINENUMBERFG_DEFAULT, "Line Number Foreground"),
			    LINENUMBERBG (COLOR, PreferenceStore.LINENUMBERBG_DEFAULT, "Line Number Background"),
			    CONSTANT (COLOR, PreferenceStore.CONSTANT_DEFAULT, "Constant"),
			    PREPROCESSOR (COLOR, PreferenceStore.PREPROCESSOR_DEFAULT, "Preprocessor"),
			    CONTAINERS (COLOR, PreferenceStore.CONTAINERS_DEFAULT, "Container"),
			FONT(PROPERTIES, PreferenceStore.FONT_DEFAULT, "Font"),
				FONTNAME (FONT, PreferenceStore.FONTNAME_DEFAULT, "Font Name"),
				FONTSIZE (FONT, PreferenceStore.FONTSIZE_DEFAULT, "Font Size"),
				FONTSTYLE (FONT, PreferenceStore.FONTSTYLE_DEFAULT, "Font Style"),
		    MISC(PROPERTIES),
		    	WRAP (MISC, PreferenceStore.WRAP_DEFAULT, "Word Wrap"),
			    AUTOSAVEONCOMPILE (MISC, PreferenceStore.AUTOSAVEONCOMPILE_DEFAULT, "Auto Save on Compile"),
			    BOOLRECENTFILES (MISC, PreferenceStore.BOOLRECENTFILES_DEFAULT, "Recent Files"),
			    LINENUM (MISC, PreferenceStore.LINENUM_DEFAULT, "Line Number"),
				NBCTOOL (MISC, PreferenceStore.NBCTOOL_DEFAULT, "NBC Compiler Location"),
				WORKSPACE (MISC, PreferenceStore.WRKSPC_DEFAULT, "Workspace Location"),
			THEME(PROPERTIES),
				THEMEXML (MISC, PreferenceStore.THEMEXML_DEFAULT, "Theme Location"),
		;
		
		private Preference parent = null;
		public ArrayList<Preference> children = new ArrayList<Preference>();
		public Integer defaultInt = null;
		public Boolean defaultBool = null;
		public String defaultString = "";
		public String label; // friendly label name
		
		public PreferenceType type;
		
		Preference(Preference parent) {
			this.parent = parent;
			if (this.parent != null) {
				this.parent.children.add(this);
			}
		}
		
		Preference(Preference parent, String defaultString, String label) {
			this(parent);
			this.defaultString = defaultString;
			this.label = label;
			if (this.type == null) { this.type = PreferenceType.STRING; } 
		}

		Preference(Preference parent, Integer defaultInt, String label) {
			this(parent, defaultInt.toString(), label);
			this.defaultInt = defaultInt;
			this.type = PreferenceType.INTEGER;
		}

		Preference(Preference parent, Boolean defaultBool, String label) {
			this(parent, defaultBool.toString(), label);
			this.defaultBool = defaultBool;
			this.type = PreferenceType.BOOLEAN;
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
			return this.defaultString != "" ? prefs.get(this.toString(), this.defaultString) : "";
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
		
		if(currentDoc.getDoctype() != null){
			if(!prefs.getBoolean("ranPreviously", false)){
				resetToDefaults();
			}
			setDefaultsFromFile(currentDoc);
		}
	}
	
	/**
	 * set the properties to default
	 */
	public static void resetToDefaults(){
		currentDoc = XMLParser.xmlParse(DEFAULT_FILE);
		if(!currentDoc.equals(null)){
			setPrefsFromFile(currentDoc);
		}
	}
	
	
	/**
	 * 	Sets the default values that are used when resetting to defaults.
	 * @param Document to read values from.
	 */
	private static void setDefaultsFromFile(Document doc) {
		
		Node tempNode;
		for (Preference parent : Preference.PROPERTIES.children) {
			for (Preference p : parent.children) {
				if (parent != Preference.THEME) {
					tempNode = XMLParser.retrieve(doc, parent.toString().toLowerCase(), p.toString().toLowerCase());
					PreferenceStore.set(p, tempNode.getTextContent() == null ? p.defaultString : tempNode.getTextContent());
					switch (p) {
					case FOREGROUND : FOREGROUND_DEFAULT = Integer.parseInt(tempNode.getTextContent()); break;
					case BACKGROUND : BACKGROUND_DEFAULT = Integer.parseInt(tempNode.getTextContent()); break;
					case COMMENT : COMMENT_DEFAULT = Integer.parseInt(tempNode.getTextContent()); break;
					case KEYWORD : KEYWORD_DEFAULT = Integer.parseInt(tempNode.getTextContent()); break;
					case OPERATOR : OPERATOR_DEFAULT = Integer.parseInt(tempNode.getTextContent()); break;
					case STRING : STRING_DEFAULT = Integer.parseInt(tempNode.getTextContent()); break;
					case LINENUMBERFG : LINENUMBERFG_DEFAULT = Integer.parseInt(tempNode.getTextContent()); break;
					case LINENUMBERBG : LINENUMBERBG_DEFAULT = Integer.parseInt(tempNode.getTextContent()); break;
					case CONSTANT : CONSTANT_DEFAULT = Integer.parseInt(tempNode.getTextContent()); break;
					case FONTNAME : FONTNAME_DEFAULT = (tempNode = XMLParser.retrieve(doc, "font", "fontname")).getTextContent(); break;
					case FONTSIZE : FONTSIZE_DEFAULT = (tempNode = XMLParser.retrieve(doc, "font", "fontsize")).getTextContent(); break;
					case FONTSTYLE : FONTSTYLE_DEFAULT = (tempNode = XMLParser.retrieve(doc, "font", "fontstyle")).getTextContent(); break;
					case WRAP : WRAP_DEFAULT = Boolean.parseBoolean(tempNode.getTextContent()); break;
					case AUTOSAVEONCOMPILE : AUTOSAVEONCOMPILE_DEFAULT = Boolean.parseBoolean(tempNode.getTextContent()); break;
					case LINENUM : LINENUM_DEFAULT = Boolean.parseBoolean(tempNode.getTextContent()); break;
					case NBCTOOL : NBCTOOL_DEFAULT = tempNode.getTextContent(); break;
					case WORKSPACE : WRKSPC_DEFAULT = tempNode.getTextContent(); break;
					//case THEMEXML : THEMEXML_DEFAULT = tempNode.getTextContent(); break;
					default:
						break;
					}
				}
			}
			// comprised of other settings
			FONT_DEFAULT = FONTNAME_DEFAULT + "-" + FONTSTYLE_DEFAULT + "-" + FONTSIZE_DEFAULT;
		}
	}
	
	/**
	 * Writes preferences to the java preferences file from a XML document.
	 * 
	 * @param XML document to read from.
	 */
	static void setPrefsFromFile(Document doc) {
		
		Node tempNode;
		
		for (Preference parent : Preference.PROPERTIES.children) {
			for (Preference p : parent.children) {
				if (parent != Preference.MISC) { // misc settings are not set for themes
					tempNode = XMLParser.retrieve(doc, parent.toString().toLowerCase(), p.toString().toLowerCase());
					if (tempNode != null) {
						switch (p.type) {
						case INTEGER : prefs.putInt(p.toString(), Integer.parseInt(tempNode.getTextContent()));
						case BOOLEAN : prefs.putBoolean(p.toString(), Boolean.parseBoolean(tempNode.getTextContent()));
						case STRING : prefs.put(p.toString(), tempNode.getTextContent());
						}
						
					}
				}
			}
		}
		prefs.put(Preference.FONT.toString(), PreferenceStore.getString(Preference.FONTNAME) + 
				"-" + PreferenceStore.getString(Preference.FONTSTYLE) + 
				"-" + PreferenceStore.getString(Preference.FONTSIZE));

	}
	
	/**
	 * Loads a theme from a file
	 * @param Theme file location
	 */
	public static void LoadTheme(String fileLoc){
		currentDoc = XMLParser.xmlParse(fileLoc);
		if(currentDoc != null){
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
	
	public static void set(Preference key, Object value){
		if (value instanceof String){ prefs.put(key.toString(), (String) value); }  
		else if (value instanceof Integer){ prefs.putInt(key.toString(), (Integer) value); }  
		else if (value instanceof Boolean){ prefs.putBoolean(key.toString(), (Boolean) value); }
	}
	
	/**
	 * build preferences document
	 * @return doc - document that holds all preference information
	 */
	public static Document buildPreferencesDocument(){
		 
		Document doc = null;
		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			doc = docBuilder.newDocument();
			Node nodeTree = PreferenceStore.Preference.PROPERTIES.makeNode(doc);
			doc.appendChild(nodeTree);
		} catch (ParserConfigurationException e) {
			System.err.println("ERROR IN CREATING DOCUMENT");
		}
		return doc;
	}
}
