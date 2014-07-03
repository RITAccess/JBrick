package com.jbricx.swing.ui.preferences;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.prefs.Preferences;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.w3c.dom.Document;

import com.jbricx.swing.ui.JBricxManager;
import com.jbricx.swing.ui.preferences.PreferenceStore.Preference;
import com.jbricx.tools.XMLParser;

/**
 * Main class for the preference window. Also used as an Action Listener for preference window events.
 * @author Daniel Larsen
 *
 */
@SuppressWarnings("serial")
public class JBricxPreferencesWindow extends JDialog implements ActionListener {
	private JPanel mainArea;
	
	private JPanel otherOptionArea;
	private JPanel textOptionArea;
	private JPanel buttonOptionArea;

	private JPanel textOptionsArea;
	private JPanel otherOptionCenterArea;
	
	private GroupLayout textAreaGroupLayout;
	private GroupLayout otherOptionsGroupLayout;
	
	private Preferences prefs;
	
	//All of the panels and buttons - need to be global so the group layout can be configured.
	
	//Colors
	private JLabel foregroundLabel;
	private JButton foregroundButton;
	private JLabel backgroundLabel;
	private JButton backgroundButton;
	private JLabel commentLabel;
	private JButton commentButton;
	private JLabel keywordLabel;
	private JButton keywordButton;
	private JLabel operatorLabel;
	private JButton operatorButton;
	private JLabel stringLabel;
	private JButton stringButton;
	private JLabel lineNumberFGLabel;
	private JButton lineNumberFGButton;
	private JLabel lineNumberBGLabel;
	private JButton lineNumberBGButton;
	private JButton preProcessorButton;
	private JLabel preProcessorLabel;
	private JButton constantButton;
	private JLabel constantLabel;

	//Fonts
	private JLabel fontLabel;
	private JButton changeFontButton;
	private JTextArea currentFontText;
	
	//Directory
	private JTextField directoryTextArea;
	private JButton workspaceChangeButton;
	private JLabel workspaceLabel;

	private JCheckBox wordWrapBox;
	private JCheckBox autoCompileBox;
	private JCheckBox loadRecentlyBox;
	
	private JLabel toolLocationLabel;
	private JTextField toolLocationTextArea;
	private JButton toolLocationBrowseButton;
	
	private JLabel themeLocationLabel;
	private JTextField themeLocationTextArea;
	private JButton themeLocationBrowseButton;
	
	private JButton applyThemeButton;
	private JButton saveThemeButton;
	
	private JButton resetToDefaultButton;
	private JButton cancelButton;
	private JButton okButton;
	private JButton applyButton;

	private JBricxManager manager;

	/**
	 * Gets the preferences to use throughout the class. Also sets the size of the window. Calls the make components class that creates the rest of the objects.
	 * 
	 * @param manager Main window - Passed in to keep the dialog modal.
	 */
	public JBricxPreferencesWindow(JBricxManager manager){
		super(manager.getShell(),"Preferences",true);
		this.manager = manager;
		prefs = PreferenceStore.getPrefs();
		
		setLayout(new BorderLayout());
		mainArea = new JPanel();
		mainArea.setLayout(new BoxLayout(mainArea, BoxLayout.Y_AXIS));
		mainArea.setBorder(BorderFactory.createEmptyBorder(10,0,0,0));
		add(mainArea,BorderLayout.CENTER);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setSize((screenSize.width-screenSize.width/2),(screenSize.height-(screenSize.height/5)));
		makeComponents();
		this.pack();
	}
	
	/**
	 * Makes the top layout of components. Main Area is a Box layout comprised of 2 Border layouts (for text options, and other options)
	 * Inside each border layout is a Group Layout for all of the option items.
	 * 
	 * After layouts are made, all of the makeX() functions are called for all the items.
	 * 
	 * Finally @see buildLayout() is called, which puts all the objects instantiated in the make functions, into the layout. 
	 */
	private void makeComponents(){
		textOptionArea = new JPanel(new BorderLayout());
		otherOptionArea = new JPanel(new BorderLayout());
		buttonOptionArea = new JPanel(new BorderLayout());
		
		JLabel textOptionsLabel = new JLabel("<html>&nbsp &nbsp<u>   Text Options</u></html>");
		JLabel toolOptionsLabel = new JLabel("<html>&nbsp &nbsp<u>   Other Options</u></html>");
		
		textOptionArea.add(textOptionsLabel, BorderLayout.NORTH);

		otherOptionArea.add(toolOptionsLabel,BorderLayout.NORTH);
		
		//otherOptionArea.add(new JSeparator(SwingConstants.HORIZONTAL),BorderLayout.SOUTH);
		
		textOptionsArea = new JPanel();
		textAreaGroupLayout = new GroupLayout(textOptionsArea);
		textOptionsArea.setLayout(textAreaGroupLayout);
		textAreaGroupLayout.setAutoCreateGaps(true);
		textAreaGroupLayout.setAutoCreateContainerGaps(true);
		
		otherOptionCenterArea = new JPanel();
		otherOptionsGroupLayout = new GroupLayout(otherOptionCenterArea);
		otherOptionCenterArea.setLayout(otherOptionsGroupLayout);
		otherOptionsGroupLayout.setAutoCreateGaps(true);
		otherOptionsGroupLayout.setAutoCreateContainerGaps(true);
		
		//textOptionsArea//
		textOptionArea.add(textOptionsArea,BorderLayout.CENTER);	
		otherOptionArea.add(otherOptionCenterArea,BorderLayout.CENTER);

		
		//Three main areas inside the box layout
		mainArea.add(textOptionArea);
		mainArea.add(otherOptionArea);
		mainArea.add(buttonOptionArea);
		
		makeColorBoxes();
		makeFontBoxes();
		makeDirectoryBoxes();
		makeCheckBoxes();
		makeToolDirectory();
		makeThemeLocation();
		makeThemeButtons();
		makeBottomButtons();
		
		buildLayout();
		
	}



	/**
	 * This builds the lower layouts using a grouplayout. 
	 */
	private void buildLayout() {
		
		//Horizontal groups for text area
		GroupLayout.SequentialGroup hGroup = textAreaGroupLayout.createSequentialGroup();
		hGroup.addGroup(textAreaGroupLayout.createParallelGroup()
				.addComponent(foregroundLabel)
				.addComponent(backgroundLabel)
				.addComponent(commentLabel)
				.addComponent(stringLabel)
				.addComponent(keywordLabel)
				.addComponent(operatorLabel)
				.addComponent(lineNumberFGLabel)
				.addComponent(lineNumberBGLabel)
				.addComponent(preProcessorLabel)
				.addComponent(constantLabel)
				.addComponent(fontLabel)
				.addComponent(wordWrapBox)
		);	
		hGroup.addGroup(textAreaGroupLayout.createParallelGroup()
				.addComponent(foregroundButton)
				.addComponent(backgroundButton)
				.addComponent(commentButton)
				.addComponent(stringButton)
				.addComponent(keywordButton)
				.addComponent(operatorButton)
				.addComponent(lineNumberFGButton)
				.addComponent(lineNumberBGButton)
				.addComponent(preProcessorButton)
				.addComponent(constantButton)
				.addComponent(currentFontText)
		);
		hGroup.addGroup(textAreaGroupLayout.createParallelGroup()
				.addComponent(changeFontButton)
		);
		textAreaGroupLayout.setHorizontalGroup(hGroup);

		//Vertical groups for text area
		GroupLayout.SequentialGroup vGroup = textAreaGroupLayout.createSequentialGroup();
		vGroup.addGroup(textAreaGroupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
				.addComponent(foregroundLabel)
				.addComponent(foregroundButton)
		);
		vGroup.addGroup(textAreaGroupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
				.addComponent(backgroundLabel)
				.addComponent(backgroundButton)
		);
		vGroup.addGroup(textAreaGroupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
				.addComponent(commentLabel)
				.addComponent(commentButton)
		);
		vGroup.addGroup(textAreaGroupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
				.addComponent(stringLabel)
				.addComponent(stringButton)
		);
		vGroup.addGroup(textAreaGroupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
				.addComponent(keywordLabel)
				.addComponent(keywordButton)
		);
		vGroup.addGroup(textAreaGroupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
				.addComponent(operatorLabel)
				.addComponent(operatorButton)
		);
		vGroup.addGroup(textAreaGroupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
				.addComponent(lineNumberFGLabel)
				.addComponent(lineNumberFGButton)
		);
		vGroup.addGroup(textAreaGroupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
				.addComponent(lineNumberBGLabel)
				.addComponent(lineNumberBGButton)
		);
		vGroup.addGroup(textAreaGroupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
				.addComponent(preProcessorLabel)
				.addComponent(preProcessorButton)
		);
		vGroup.addGroup(textAreaGroupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
				.addComponent(constantLabel)
				.addComponent(constantButton)
		);
		vGroup.addGroup(textAreaGroupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
				.addComponent(fontLabel)
				.addComponent(currentFontText)
				.addComponent(changeFontButton)
		);
		vGroup.addGroup(textAreaGroupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
				.addComponent(wordWrapBox)	
		);
		textAreaGroupLayout.setVerticalGroup(vGroup);
		
		//Horizontal group for other area
		GroupLayout.SequentialGroup hGroup2 = otherOptionsGroupLayout.createSequentialGroup();
		hGroup2.addGroup(otherOptionsGroupLayout.createParallelGroup()
				.addComponent(autoCompileBox)
				.addComponent(loadRecentlyBox)
				.addComponent(toolLocationLabel)
				.addComponent(workspaceLabel)
				.addComponent(themeLocationLabel)
		);
		hGroup2.addGroup(otherOptionsGroupLayout.createParallelGroup()
				.addComponent(toolLocationTextArea)
				.addComponent(directoryTextArea)
				.addComponent(themeLocationTextArea)
		);
		hGroup2.addGroup(otherOptionsGroupLayout.createParallelGroup()
				.addComponent(toolLocationBrowseButton)
				.addComponent(workspaceChangeButton)
				.addComponent(themeLocationBrowseButton)
		);
		otherOptionsGroupLayout.setHorizontalGroup(hGroup2);
		
		//Vertical group for the other area
		GroupLayout.SequentialGroup vGroup2 = otherOptionsGroupLayout.createSequentialGroup();
		vGroup2.addGroup(otherOptionsGroupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
				.addComponent(autoCompileBox)
		);
		vGroup2.addComponent(loadRecentlyBox);
		vGroup2.addGroup(otherOptionsGroupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(toolLocationLabel)
				.addComponent(toolLocationTextArea)
				.addComponent(toolLocationBrowseButton)
		);
		vGroup2.addGroup(otherOptionsGroupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
			.addComponent(workspaceLabel)
			.addComponent(directoryTextArea)
			.addComponent(workspaceChangeButton)
		);
		vGroup2.addGroup(otherOptionsGroupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
			.addComponent(themeLocationLabel)
			.addComponent(themeLocationTextArea)
			.addComponent(themeLocationBrowseButton)
		);
		otherOptionsGroupLayout.setVerticalGroup(vGroup2);
		
	}


	/**
	 * Creating every component for the color boxes
	 * 
	 */
	private void makeColorBoxes(){
		
		//Foreground Label and Button
		foregroundLabel = new JLabel("Foreground color: ");
		foregroundButton = new JButton();
		foregroundButton.getAccessibleContext().setAccessibleName("Foreground Color");
		foregroundButton.setMaximumSize(new Dimension(75,25));
		foregroundButton.setPreferredSize(new Dimension(75,25));
		foregroundButton.setMinimumSize(new Dimension(75,25));
		foregroundButton.setActionCommand("foregroundButton");
		foregroundButton.setBackground(PreferenceStore.getColor(Preference.FOREGROUND));
		foregroundButton.setContentAreaFilled(false);
		foregroundButton.setOpaque(true);
		foregroundButton.setBorder(BorderFactory.createLineBorder(Color.black));
		foregroundButton.addActionListener(this);
		
		//Background Label and Button
		backgroundLabel = new JLabel("Background color: ");
		backgroundButton = new JButton();
		backgroundButton.getAccessibleContext().setAccessibleName("Background Color");
		backgroundButton.setMaximumSize(new Dimension(75,25));
		backgroundButton.setPreferredSize(new Dimension(75,25));
		backgroundButton.setActionCommand("backgroundButton");
		backgroundButton.setBackground(PreferenceStore.getColor(Preference.BACKGROUND));
		backgroundButton.setContentAreaFilled(false);
		backgroundButton.setOpaque(true);
		backgroundButton.setBorder(BorderFactory.createLineBorder(Color.black));
		backgroundButton.addActionListener(this);
		
		//Comment Label and Button
		commentLabel = new JLabel("Comment color: ");
		commentButton = new JButton();
		commentButton.getAccessibleContext().setAccessibleName("Comment Color");
		commentButton.setMaximumSize(new Dimension(75,25));
		commentButton.setPreferredSize(new Dimension(75,25));
		commentButton.setActionCommand("commentButton");
		commentButton.setBackground(PreferenceStore.getColor(Preference.COMMENT));
		commentButton.setContentAreaFilled(false);
		commentButton.setOpaque(true);
		commentButton.setBorder(BorderFactory.createLineBorder(Color.black));
		commentButton.addActionListener(this);

		
		//Keyword Label and Button
		keywordLabel = new JLabel("Keyword color: ");
		keywordButton = new JButton();
		keywordButton.getAccessibleContext().setAccessibleName("Keyword Color");
		keywordButton.setMaximumSize(new Dimension(75,25));
		keywordButton.setPreferredSize(new Dimension(75,25));
		keywordButton.setActionCommand("keywordButton");
		keywordButton.setBackground(PreferenceStore.getColor(Preference.KEYWORD));
		keywordButton.setContentAreaFilled(false);
		keywordButton.setOpaque(true);
		keywordButton.setBorder(BorderFactory.createLineBorder(Color.black));
		keywordButton.addActionListener(this);
		
		//Operator Panel and Button
		operatorLabel = new JLabel("Operator color: ");
		operatorButton = new JButton();
		operatorButton.getAccessibleContext().setAccessibleName("Operator Color");
		operatorButton.setMaximumSize(new Dimension(75,25));
		operatorButton.setPreferredSize(new Dimension(75,25));
		operatorButton.setActionCommand("operatorButton");
		operatorButton.setBackground(PreferenceStore.getColor(Preference.OPERATOR));
		operatorButton.setContentAreaFilled(false);
		operatorButton.setOpaque(true);
		operatorButton.setBorder(BorderFactory.createLineBorder(Color.black));
		operatorButton.addActionListener(this);
		
		//String Panel and Button
		stringLabel = new JLabel("String color: ");
		stringButton = new JButton();
		stringButton.getAccessibleContext().setAccessibleName("String Color");
		stringButton.setMaximumSize(new Dimension(75,25));
		stringButton.setPreferredSize(new Dimension(75,25));
		stringButton.setActionCommand("stringButton");
		stringButton.setBackground(PreferenceStore.getColor(Preference.STRING));
		stringButton.setContentAreaFilled(false);
		stringButton.setOpaque(true);
		stringButton.setBorder(BorderFactory.createLineBorder(Color.black));
		stringButton.addActionListener(this);

		
		//Line number FG label and button
		lineNumberFGLabel = new JLabel("Line number FG color: ");
		lineNumberFGButton = new JButton();
		lineNumberFGButton.getAccessibleContext().setAccessibleName("Line Number Foreground Color");
		lineNumberFGButton.setMaximumSize(new Dimension(75,25));
		lineNumberFGButton.setPreferredSize(new Dimension(75,25));
		lineNumberFGButton.setActionCommand("lineNumberFGButton");
		lineNumberFGButton.setBackground(PreferenceStore.getColor(Preference.LINENUMBERFG));
		lineNumberFGButton.setContentAreaFilled(false);
		lineNumberFGButton.setOpaque(true);
		lineNumberFGButton.setBorder(BorderFactory.createLineBorder(Color.black));
		lineNumberFGButton.addActionListener(this);
		
		//Line number BG label and button
		lineNumberBGLabel = new JLabel("Line number BG color: ");
		lineNumberBGButton = new JButton();
		lineNumberBGButton.getAccessibleContext().setAccessibleName("Line Number Background Color");
		lineNumberBGButton.setMaximumSize(new Dimension(75,25));
		lineNumberBGButton.setPreferredSize(new Dimension(75,25));
		lineNumberBGButton.setActionCommand("lineNumberBGButton");
		lineNumberBGButton.setBackground(PreferenceStore.getColor(Preference.LINENUMBERBG));
		lineNumberBGButton.setContentAreaFilled(false);
		lineNumberBGButton.setOpaque(true);
		lineNumberBGButton.setBorder(BorderFactory.createLineBorder(Color.black));
		lineNumberBGButton.addActionListener(this);
		
		preProcessorLabel = new JLabel("Pre-Processor Color: ");
		preProcessorButton = new JButton();
		preProcessorButton.getAccessibleContext().setAccessibleName("Pre-Processor Color");
		preProcessorButton.setMaximumSize(new Dimension(75,25));
		preProcessorButton.setPreferredSize(new Dimension(75,25));
		preProcessorButton.setActionCommand("preProcessorButton");
		preProcessorButton.setBackground(PreferenceStore.getColor(Preference.PREPROCESSOR));
		preProcessorButton.setContentAreaFilled(false);
		preProcessorButton.setOpaque(true);
		preProcessorButton.setBorder(BorderFactory.createLineBorder(Color.black));
		preProcessorButton.addActionListener(this);
		
		constantLabel = new JLabel("Constant Color: ");
		constantButton = new JButton();
		constantButton.getAccessibleContext().setAccessibleName("Constant Color");
		constantButton.setMaximumSize(new Dimension(75,25));
		constantButton.setPreferredSize(new Dimension(75,25));
		constantButton.setActionCommand("constantButton");
		constantButton.setBackground(PreferenceStore.getColor(Preference.CONSTANT));
		constantButton.setContentAreaFilled(false);
		constantButton.setOpaque(true);
		constantButton.setBorder(BorderFactory.createLineBorder(Color.black));
		constantButton.addActionListener(this);
                
    }
	
	/**
	 * Creates the boxes associated with font selection
	 */
	public void makeFontBoxes(){
		fontLabel = new JLabel("Font");
		currentFontText = new JTextArea();
		currentFontText.setEditable(false);
		Font currentFont = Font.decode(PreferenceStore.getString(Preference.FONT));

		//Shows current font. Stuff in the middle delimits to get the style string which isnt easily attainable(is actually an int)
		currentFontText.setText(currentFont.getName() +"-"+ currentFont.toString().split("=")[3].split(",")[0]+"-"+ currentFont.getSize());
		currentFontText.setFont(currentFont);
		currentFontText.getAccessibleContext().setAccessibleName("Font Box");
		changeFontButton = new JButton("Change...");
		changeFontButton.setActionCommand("changeFont");
		changeFontButton.getAccessibleContext().setAccessibleName("Change Font Button. Press Enter");
		changeFontButton.addActionListener(this);
	}
	
	/**
	 * Makes the boxes associated with changing the workspace
	 */
	private void makeDirectoryBoxes() {
		workspaceLabel = new JLabel("Workspace directory");
		
		directoryTextArea = new JTextField(15);
		directoryTextArea.setText(PreferenceStore.getString(Preference.WORKSPACE));
		directoryTextArea.getAccessibleContext().setAccessibleName("Workspace Directory text area");
		
		workspaceChangeButton = new JButton("Browse...");
		workspaceChangeButton.setActionCommand("browseButton");
		workspaceChangeButton.getAccessibleContext().setAccessibleName("Change Workspace Button. Press Enter to browse.");
		workspaceChangeButton.addActionListener(this);
	}
	
	
	/**
	 * Populate the other boxes: wordwrap, autocompile, and recent files
	 */
	private void makeCheckBoxes() {
		wordWrapBox = new JCheckBox();
		wordWrapBox.setText("Word Wrap");
		wordWrapBox.setSelected(PreferenceStore.getBool(Preference.WRAP));
		wordWrapBox.getAccessibleContext().setAccessibleName("Word Wrap Check Box. Press Space to Toggle");
		
		autoCompileBox = new JCheckBox();
		autoCompileBox.setText("Auto Compile");
		autoCompileBox.setSelected(PreferenceStore.getBool(Preference.AUTOCOMPILE));
		autoCompileBox.getAccessibleContext().setAccessibleName("Auto Compile Box. Press Space to Toggle");
		
		loadRecentlyBox = new JCheckBox();
		loadRecentlyBox.setText("Load Recent Files");
		loadRecentlyBox.setSelected(prefs.getBoolean(PreferenceStore.BOOLRECENTFILES, PreferenceStore.BOOLRECENTFILES_DEFAULT));
		loadRecentlyBox.getAccessibleContext().setAccessibleName("Load Recent Files Box. Press Space to Toggle");
	}
	
	/**
	 * Makes items for the location of the NBC Tool
	 */
	private void makeToolDirectory(){
		toolLocationLabel = new JLabel("NBC Tool Location");
		
		toolLocationTextArea = new JTextField(15);
		toolLocationTextArea.setText(PreferenceStore.getString(Preference.NBCTOOL));
		toolLocationTextArea.getAccessibleContext().setAccessibleName("NBC Tool Location field");
		
		toolLocationBrowseButton = new JButton("Browse...");
		
		toolLocationBrowseButton.setActionCommand("toolLocationbutton");
		toolLocationBrowseButton.addActionListener(this);
		toolLocationBrowseButton.getAccessibleContext().setAccessibleName("Change NBC Tool Location Button. Press Enter to change");
	}
	
	/**
	 * Theme Location
	 */
	private void makeThemeLocation() {
		themeLocationLabel = new JLabel("Theme");
		
		themeLocationTextArea = new JTextField(15);
		themeLocationTextArea.setText(PreferenceStore.getString(Preference.THEMEXML));
		themeLocationTextArea.getAccessibleContext().setAccessibleName("Theme Location");
		
		themeLocationBrowseButton = new JButton("Browse...");
		
		themeLocationBrowseButton.setActionCommand("themeLocationbutton");
		themeLocationBrowseButton.addActionListener(this);
		themeLocationBrowseButton.getAccessibleContext().setAccessibleName("Change Theme Location Button. Press Enter to change");
	}
	
	/**
	 * Makes Theme related buttons: "Apply Theme", "Save Theme"
	 */
	public void makeThemeButtons() {
		JPanel themePane = new JPanel();
		themePane.setLayout(new BoxLayout(themePane, BoxLayout.LINE_AXIS));
		
		applyThemeButton = new JButton("Apply Theme");
		applyThemeButton.setActionCommand("applytheme");
		applyThemeButton.addActionListener(this);
		applyThemeButton.getAccessibleContext().setAccessibleName("Apply Theme Button. Apply Theme to current Preferences");
		
		saveThemeButton = new JButton("Save Theme");
		saveThemeButton.setActionCommand("savetheme");
		saveThemeButton.addActionListener(this);
		saveThemeButton.getAccessibleContext().setAccessibleName("Save Theme Button. Save Theme to a file");
		
		themePane.add(Box.createRigidArea(new Dimension(10,0)));
		themePane.add(Box.createHorizontalGlue());
		themePane.add(applyThemeButton);
		themePane.add(Box.createHorizontalGlue());
		themePane.add(saveThemeButton);
		themePane.add(Box.createHorizontalGlue());
		themePane.add(Box.createRigidArea(new Dimension(10,0)));
		themePane.setBorder(BorderFactory.createEmptyBorder(0,0,5,0));

		buttonOptionArea.add(themePane,BorderLayout.NORTH);
	}
	
	/**
	 * Makes the bottom "Reset to Default", "Cancel", "Apply" and "OK"
	 */
	private void makeBottomButtons() {
		JPanel bottomPane = new JPanel();
		bottomPane.setLayout(new BoxLayout(bottomPane, BoxLayout.LINE_AXIS));
		
		resetToDefaultButton = new JButton("Reset to Defaults");
		resetToDefaultButton.setActionCommand("reset");
		resetToDefaultButton.addActionListener(this);
		resetToDefaultButton.getAccessibleContext().setAccessibleName("Reset Button. Reset all values to Default");
		
		cancelButton = new JButton("Cancel");
		cancelButton.setActionCommand("cancel");
		cancelButton.addActionListener(this);
		cancelButton.getAccessibleContext().setAccessibleName("Cancel button. Cancel any changes made and exit");
		
		okButton = new JButton("OK");
		okButton.setActionCommand("OK");
		okButton.addActionListener(this);
		okButton.getAccessibleContext().setAccessibleName("Ok Button. Press to accept any changes");
		
		applyButton = new JButton("Apply");
		applyButton.setActionCommand("apply");
		applyButton.addActionListener(this);
		applyButton.getAccessibleContext().setAccessibleName("Apply button. Press to apply any changes");
		
		bottomPane.add(Box.createRigidArea(new Dimension(10,0)));
		bottomPane.add(resetToDefaultButton);
		bottomPane.add(Box.createHorizontalGlue());
		bottomPane.add(cancelButton);
		bottomPane.add(Box.createHorizontalGlue());
		bottomPane.add(applyButton);
		bottomPane.add(okButton);
		bottomPane.add(Box.createRigidArea(new Dimension(10,0)));
		bottomPane.setBorder(BorderFactory.createEmptyBorder(0,0,5,0));
			
		buttonOptionArea.add(bottomPane,BorderLayout.SOUTH);
		
	}
	
	/**
	 * Goes through all the values and sets what is there to the new preference value.
	 */
	private void saveValues(){
		PreferenceStore.set(Preference.FOREGROUND, foregroundButton.getBackground().getRGB());
		PreferenceStore.set(Preference.BACKGROUND, backgroundButton.getBackground().getRGB());
		PreferenceStore.set(Preference.COMMENT, commentButton.getBackground().getRGB());
		PreferenceStore.set(Preference.KEYWORD, keywordButton.getBackground().getRGB());
		PreferenceStore.set(Preference.OPERATOR, operatorButton.getBackground().getRGB());
		PreferenceStore.set(Preference.STRING, stringButton.getBackground().getRGB());
		PreferenceStore.set(Preference.LINENUMBERFG, lineNumberFGButton.getBackground().getRGB());
		PreferenceStore.set(Preference.LINENUMBERBG, lineNumberBGButton.getBackground().getRGB());
		PreferenceStore.set(Preference.PREPROCESSOR, preProcessorButton.getBackground().getRGB());
		PreferenceStore.set(Preference.CONSTANT, constantButton.getBackground().getRGB());

		PreferenceStore.set(Preference.FONT, currentFontText.getText());

		PreferenceStore.set(Preference.WRAP, wordWrapBox.isSelected());
		PreferenceStore.set(Preference.AUTOCOMPILE, autoCompileBox.isSelected());
		PreferenceStore.set(Preference.BOOLRECENTFILES, loadRecentlyBox.isSelected());

		PreferenceStore.set(Preference.NBCTOOL, toolLocationTextArea.getText());
		PreferenceStore.set(Preference.WORKSPACE, directoryTextArea.getText());
		PreferenceStore.set(Preference.THEMEXML, themeLocationTextArea.getText());
	}
	
	/**
	 * Resets all of the pref values to the defaults listed in the preferencestore class. Also changes the objects in the preference window to reflect that.
	 */
	private void resetDefaults(){
		PreferenceStore.resetToDefaults();
		this.setJPanelsFromPreferences();
	}
	
	/**
	 * sets all preference jpanel, jtextarea, and background items to how they are in preferences
	 */
	private void setJPanelsFromPreferences(){
		foregroundButton.setBackground(PreferenceStore.getColor(Preference.FOREGROUND));
		backgroundButton.setBackground(PreferenceStore.getColor(Preference.BACKGROUND));
		commentButton.setBackground(PreferenceStore.getColor(Preference.COMMENT));
		keywordButton.setBackground(PreferenceStore.getColor(Preference.KEYWORD));
		operatorButton.setBackground(PreferenceStore.getColor(Preference.OPERATOR));
		stringButton.setBackground(PreferenceStore.getColor(Preference.STRING));
		lineNumberFGButton.setBackground(PreferenceStore.getColor(Preference.LINENUMBERFG));
		lineNumberBGButton.setBackground(PreferenceStore.getColor(Preference.LINENUMBERBG));
		preProcessorButton.setBackground(PreferenceStore.getColor(Preference.PREPROCESSOR));
		constantButton.setBackground(PreferenceStore.getColor(Preference.CONSTANT));
		currentFontText.setText(PreferenceStore.getString(Preference.FONT));
		currentFontText.setFont(Font.decode(currentFontText.getText()));
		wordWrapBox.setSelected(PreferenceStore.getBool(Preference.WRAP));
		autoCompileBox.setSelected(PreferenceStore.getBool(Preference.AUTOCOMPILE));
		loadRecentlyBox.setSelected(PreferenceStore.getBool(Preference.BOOLRECENTFILES));
		toolLocationTextArea.setText(PreferenceStore.getString(Preference.NBCTOOL));
		directoryTextArea.setText(PreferenceStore.getString(Preference.WORKSPACE));
		themeLocationTextArea.setText(PreferenceStore.getString(Preference.THEMEXML));		
		this.pack();
	}
	

	
	/**
	 * Action Listener method for all of the buttons.
	 */
	public void actionPerformed(ActionEvent arg0) {
		//A color button was picked - change the buttons color to what was chosen.
		if(arg0.getActionCommand().equals("backgroundButton") || arg0.getActionCommand().equals("foregroundButton")
				|| arg0.getActionCommand().equals("commentButton") || arg0.getActionCommand().equals("stringButton")
				|| arg0.getActionCommand().equals("keywordButton") || arg0.getActionCommand().equals("lineNumberFGButton")
				|| arg0.getActionCommand().equals("lineNumberBGButton") || arg0.getActionCommand().equals("operatorButton")
				|| arg0.getActionCommand().equals("constantButton") || arg0.getActionCommand().equals("preProcessorButton")){
				
				Color newColor = JColorChooser.showDialog(this, "Choose a color", ((JButton)arg0.getSource()).getBackground());
				
				if(newColor != null)
				{
					((JButton)arg0.getSource()).setBackground(newColor);
				}
		
		// the font button was picked, bring up the font change dialog and change the font.
		}else if(arg0.getActionCommand().equals("changeFont")){
			JFontChooser fontChooser = new JFontChooser();
			int result = fontChooser.showDialog(this);
			if (result == JFontChooser.OK_OPTION) {  
				Font currentFont = fontChooser.getSelectedFont();
				currentFontText.setText(currentFont.getName() +"-"+ currentFont.toString().split("=")[3].split(",")[0]+"-"+ currentFont.getSize());
				currentFontText.setFont(currentFont);
				fontChooser.setDefaultSelectedFont(currentFont);
				this.pack();
			}
		
		// the browse button was chosen for the home directory, open a file dialog to change the directory.	
		}else if(arg0.getActionCommand().equals("browseButton")){
			final JFileChooser fc = new JFileChooser();
			fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			int returnVal = fc.showOpenDialog(this);
			//They picked a file
			if(returnVal == JFileChooser.APPROVE_OPTION){
				File selectedDir = fc.getSelectedFile();
				directoryTextArea.setText(selectedDir.getAbsolutePath());	
			}
			
		// the browse button was chosen for tool location. open file dialog to change directory.	
		}else if(arg0.getActionCommand().equals("toolLocationbutton")){
			final JFileChooser fc = new JFileChooser();
			fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
			int returnVal = fc.showOpenDialog(this);
			//They picked a file
			if(returnVal == JFileChooser.APPROVE_OPTION){
				File selectedDir = fc.getSelectedFile();
				toolLocationTextArea.setText(selectedDir.getAbsolutePath());	
			}
			
		// the browse button was chosen for theme location. open file dialog to change director 
		}else if(arg0.getActionCommand().equals("themeLocationbutton")){
			final JFileChooser fc = new JFileChooser("resources/preferences");
			fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
			int returnVal = fc.showOpenDialog(this);
			//They picked a file
			if(returnVal == JFileChooser.APPROVE_OPTION){
				File selectedDir = fc.getSelectedFile();
				themeLocationTextArea.setText(selectedDir.getAbsolutePath());
			}
		//User wishes to cancel any changes and close window
		}else if(arg0.getActionCommand().equals("cancel")){
			int n = JOptionPane.showConfirmDialog(
				    this,
				    "Are you sure you wish to cancel? Any unsaved changes will be lost.",
				    "Preferences",
				    JOptionPane.YES_NO_OPTION);
			if(n == JOptionPane.YES_OPTION){
				this.dispose();
			}
		// User wishes to leave. Saves changes and leaves	
		}else if(arg0.getActionCommand().equals("OK")){
			saveValues();
			manager.updatePreferences(); //Should automatically Update
			this.dispose();
		//User wishes to reset all items to default. Makes sure with another dialog, then does so if necessary.
		}else if(arg0.getActionCommand().equals("reset")){
			int n = JOptionPane.showConfirmDialog(
				    this,
				    "Are you sure you wish to reset to defaults?",
				    "Preferences",
				    JOptionPane.YES_NO_OPTION);
			if(n == JOptionPane.YES_OPTION){	
					//PreferenceStore.setPreferencesAndDefaults();
					//setJPanelsFromPreferences();
					resetDefaults();
			}
		//User wishes to apply settings but stay in window.
		}else if(arg0.getActionCommand().equals("apply")){
			saveValues();
			manager.updatePreferences();
		}else if(arg0.getActionCommand().equals("applytheme")){
			if(themeLocationTextArea.getText().equalsIgnoreCase(""))
				themeLocationTextArea.setText(PreferenceStore.getString(Preference.THEMEXML));
			PreferenceStore.LoadTheme(themeLocationTextArea.getText());
			PreferenceStore.set(PreferenceStore.Preference.THEMEXML, themeLocationTextArea.getText());
			setJPanelsFromPreferences();
			saveValues();
			manager.updatePreferences();
		}else if(arg0.getActionCommand().equals("savetheme")){
			final JFileChooser fc = new JFileChooser("resources/preferences");
			fc.setFileSelectionMode(JFileChooser.SAVE_DIALOG);
			int returnVal = fc.showSaveDialog(this);
			//They picked a file
			if(returnVal == JFileChooser.APPROVE_OPTION){
				themeLocationTextArea.setText(fc.getSelectedFile().getAbsolutePath());
				Document doc = PreferenceStore.buildPreferencesDocument();
				XMLParser.writeToFile(doc, themeLocationTextArea.getText());
			}
		}
	}	
}
