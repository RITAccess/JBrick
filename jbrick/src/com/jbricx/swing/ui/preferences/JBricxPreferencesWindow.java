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

import com.jbricx.swing.ui.JBricxManager;

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
		PreferenceStore prefClass = new PreferenceStore();
		prefs = prefClass.getPrefs();
		
		setLayout(new BorderLayout());
		mainArea = new JPanel();
		mainArea.setLayout(new BoxLayout(mainArea, BoxLayout.Y_AXIS));
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

		JLabel textOptionsLabel = new JLabel("<html><u>Text Options</u></html>");
		JLabel toolOptionsLabel = new JLabel("<html><u>Other Options</u></html>");
		
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

		
		//Two main areas inside the box layout
		mainArea.add(textOptionArea);
		mainArea.add(otherOptionArea);
		
		makeColorBoxes();
		makeFontBoxes();
		makeDirectoryBoxes();
		makeCheckBoxes();
		makeToolDirectory();
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
		);

		hGroup2.addGroup(otherOptionsGroupLayout.createParallelGroup()
				.addComponent(toolLocationTextArea)
				.addComponent(directoryTextArea)
		);
		hGroup2.addGroup(otherOptionsGroupLayout.createParallelGroup()
				.addComponent(toolLocationBrowseButton)
				.addComponent(workspaceChangeButton)
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
		foregroundButton.setMaximumSize(new Dimension(75,25));
		foregroundButton.setPreferredSize(new Dimension(75/Users/eknelson17/Documents/jbrick/jbrick/src/com/jbricx/swing/ui/preferences/JBricxPreferencesWindow.java,25));
		foregroundButton.setMinimumSize(new Dimension(75,25));
		foregroundButton.setActionCommand("foregroundButton");
		foregroundButton.setBackground(new Color(prefs.getInt(PreferenceStore.ColorFor.FOREGROUND.toString(), PreferenceStore.FOREGROUND_DEFAULT)));
		foregroundButton.setContentAreaFilled(false);
		foregroundButton.setOpaque(true);
		foregroundButton.setBorder(BorderFactory.createLineBorder(Color.black));
		foregroundButton.addActionListener(this);
		
		//Background Label and Button
		backgroundLabel = new JLabel("Background color: ");
		backgroundButton = new JButton();
		backgroundButton.setMaximumSize(new Dimension(75,25));
		backgroundButton.setPreferredSize(new Dimension(75,25));
		backgroundButton.setActionCommand("backgroundButton");
		backgroundButton.setBackground(new Color(prefs.getInt(PreferenceStore.ColorFor.BACKGROUND.toString(), PreferenceStore.BACKGROUND_DEFAULT)));
		backgroundButton.setContentAreaFilled(false);
		backgroundButton.setOpaque(true);
		backgroundButton.setBorder(BorderFactory.createLineBorder(Color.black));
		backgroundButton.addActionListener(this);
		
		//Comment Label and Button
		commentLabel = new JLabel("Comment color: ");
		commentButton = new JButton();
		commentButton.setMaximumSize(new Dimension(75,25));
		commentButton.setPreferredSize(new Dimension(75,25));
		commentButton.setActionCommand("commentButton");
		commentButton.setBackground(new Color(prefs.getInt(PreferenceStore.ColorFor.COMMENT.toString(), PreferenceStore.COMMENT_DEFAULT)));
		commentButton.setContentAreaFilled(false);
		commentButton.setOpaque(true);
		commentButton.setBorder(BorderFactory.createLineBorder(Color.black));
		commentButton.addActionListener(this);

		
		//Keyword Label and Button
		keywordLabel = new JLabel("Keyword color: ");
		keywordButton = new JButton();
		keywordButton.setMaximumSize(new Dimension(75,25));
		keywordButton.setPreferredSize(new Dimension(75,25));
		keywordButton.setActionCommand("keywordButton");
		keywordButton.setBackground(new Color(prefs.getInt(PreferenceStore.ColorFor.KEYWORD.toString(), PreferenceStore.KEYWORD_DEFAULT)));
		keywordButton.setContentAreaFilled(false);
		keywordButton.setOpaque(true);
		keywordButton.setBorder(BorderFactory.createLineBorder(Color.black));
		keywordButton.addActionListener(this);
		
		//Operator Panel and Button
		operatorLabel = new JLabel("Operator color: ");
		operatorButton = new JButton();
		operatorButton.setMaximumSize(new Dimension(75,25));
		operatorButton.setPreferredSize(new Dimension(75,25));
		operatorButton.setActionCommand("operatorButton");
		operatorButton.setBackground(new Color(prefs.getInt(PreferenceStore.ColorFor.OPERATOR.toString(), PreferenceStore.OPERATOR_DEFAULT)));
		operatorButton.setContentAreaFilled(false);
		operatorButton.setOpaque(true);
		operatorButton.setBorder(BorderFactory.createLineBorder(Color.black));
		operatorButton.addActionListener(this);
		
		//String Panel and Button
		stringLabel = new JLabel("String color: ");
		stringButton = new JButton();
		stringButton.setMaximumSize(new Dimension(75,25));
		stringButton.setPreferredSize(new Dimension(75,25));
		stringButton.setActionCommand("stringButton");
		stringButton.setBackground(new Color(prefs.getInt(PreferenceStore.ColorFor.STRING.toString(), PreferenceStore.STRING_DEFAULT)));
		stringButton.setContentAreaFilled(false);
		stringButton.setOpaque(true);
		stringButton.setBorder(BorderFactory.createLineBorder(Color.black));
		stringButton.addActionListener(this);

		
		//Line number FG label and button
		lineNumberFGLabel = new JLabel("Line number FG color: ");
		lineNumberFGButton = new JButton();
		lineNumberFGButton.setMaximumSize(new Dimension(75,25));
		lineNumberFGButton.setPreferredSize(new Dimension(75,25));
		lineNumberFGButton.setActionCommand("lineNumberFGButton");
		lineNumberFGButton.setBackground(new Color(prefs.getInt(PreferenceStore.ColorFor.LINENUMBERFG.toString(), PreferenceStore.LINENUMBERFG_DEFAULT)));
		lineNumberFGButton.setContentAreaFilled(false);
		lineNumberFGButton.setOpaque(true);
		lineNumberFGButton.setBorder(BorderFactory.createLineBorder(Color.black));
		lineNumberFGButton.addActionListener(this);
		
		//Line number BG label and button
		lineNumberBGLabel = new JLabel("Line number BG color: ");
		lineNumberBGButton = new JButton();
		lineNumberBGButton.setMaximumSize(new Dimension(75,25));
		lineNumberBGButton.setPreferredSize(new Dimension(75,25));
		lineNumberBGButton.setActionCommand("lineNumberBGButton");
		lineNumberBGButton.setBackground(new Color(prefs.getInt(PreferenceStore.ColorFor.LINENUMBERBG.toString(), PreferenceStore.LINENUMBERBG_DEFAULT)));
		lineNumberBGButton.setContentAreaFilled(false);
		lineNumberBGButton.setOpaque(true);
		lineNumberBGButton.setBorder(BorderFactory.createLineBorder(Color.black));
		lineNumberBGButton.addActionListener(this);
                
    }
	
	/**
	 * Creates the boxes associated with font selection
	 */
	public void makeFontBoxes(){
		fontLabel = new JLabel("Font");
		currentFontText = new JTextArea();
		currentFontText.setEditable(false);
		Font currentFont = Font.decode(prefs.get(PreferenceStore.FONT, PreferenceStore.FONT_DEFAULT));
		
		//Shows current font. Stuff in the middle delimits to get the style string which isnt easily attainable(is actually an int)
		currentFontText.setText(currentFont.getName() +"-"+ currentFont.toString().split("=")[3].split(",")[0]+"-"+ currentFont.getSize());
		currentFontText.setFont(currentFont);
		changeFontButton = new JButton("Change...");
		changeFontButton.setActionCommand("changeFont");
		changeFontButton.addActionListener(this);
	}
	
	/**
	 * Makes the boxes associated with changing the workspace
	 */
	private void makeDirectoryBoxes() {
		workspaceLabel = new JLabel("Workspace directory");
		
		directoryTextArea = new JTextField();
		directoryTextArea.setText(prefs.get(PreferenceStore.WRKSPC, PreferenceStore.WRKSPC_DEFAULT));
		
		workspaceChangeButton = new JButton("Browse...");
		workspaceChangeButton.setActionCommand("browseButton");
		workspaceChangeButton.addActionListener(this);
	}
	
	
	/**
	 * Populate the other boxes: wordwrap, autocompile, and recent files
	 */
	private void makeCheckBoxes() {
		wordWrapBox = new JCheckBox();
		wordWrapBox.setText("Word Wrap");

		autoCompileBox = new JCheckBox();
		autoCompileBox.setText("Auto Compile");

		loadRecentlyBox = new JCheckBox();
		loadRecentlyBox.setText("Load Recent Files");
	}
	
	/**
	 * Makes items for the location of the NBC Tool
	 */
	private void makeToolDirectory(){
		toolLocationLabel = new JLabel("NBC Tool Location");
		
		toolLocationTextArea = new JTextField();
		toolLocationTextArea.setText(prefs.get(PreferenceStore.NBCTOOL, PreferenceStore.NBCTOOL_DEFAULT));
		
		toolLocationBrowseButton = new JButton("Browse...");
		toolLocationBrowseButton.setActionCommand("toolLocationbutton");
		toolLocationBrowseButton.addActionListener(this);
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
		
		cancelButton = new JButton("Cancel");
		cancelButton.setActionCommand("cancel");
		cancelButton.addActionListener(this);
		
		okButton = new JButton("OK");
		okButton.setActionCommand("OK");
		okButton.addActionListener(this);
		
		applyButton = new JButton("Apply");
		applyButton.setActionCommand("apply");
		okButton.addActionListener(this);
		
		bottomPane.add(Box.createRigidArea(new Dimension(10,0)));
		bottomPane.add(resetToDefaultButton);
		bottomPane.add(Box.createHorizontalGlue());
		bottomPane.add(cancelButton);
		bottomPane.add(Box.createHorizontalGlue());
		bottomPane.add(applyButton);
		bottomPane.add(okButton);
		bottomPane.add(Box.createRigidArea(new Dimension(10,0)));
		
		add(bottomPane,BorderLayout.SOUTH);
		
	}
	
	/**
	 * Goes through all the values and sets what is there to the new preference value.
	 */
	private void saveValues(){
		prefs.putInt(PreferenceStore.ColorFor.FOREGROUND.toString(), foregroundButton.getBackground().getRGB());
		prefs.putInt(PreferenceStore.ColorFor.BACKGROUND.toString(),backgroundButton.getBackground().getRGB());
		prefs.putInt(PreferenceStore.ColorFor.COMMENT.toString(),commentButton.getBackground().getRGB());
		prefs.putInt(PreferenceStore.ColorFor.KEYWORD.toString(),keywordButton.getBackground().getRGB());
		prefs.putInt(PreferenceStore.ColorFor.OPERATOR.toString(),operatorButton.getBackground().getRGB());
		prefs.putInt(PreferenceStore.ColorFor.STRING.toString(),stringButton.getBackground().getRGB());
		prefs.putInt(PreferenceStore.ColorFor.LINENUMBERFG.toString(),lineNumberFGButton.getBackground().getRGB());
		prefs.putInt(PreferenceStore.ColorFor.LINENUMBERBG.toString(),lineNumberBGButton.getBackground().getRGB());
		
		prefs.put(PreferenceStore.FONT.toString(),currentFontText.getText());
		
		prefs.putBoolean(PreferenceStore.WRAP,wordWrapBox.isSelected());
		prefs.putBoolean(PreferenceStore.AUTOCOMPILE, autoCompileBox.isSelected());
		prefs.putBoolean(PreferenceStore.RECENTFILES, loadRecentlyBox.isSelected());
		
		prefs.put(PreferenceStore.NBCTOOL,toolLocationTextArea.getText());
		prefs.put(PreferenceStore.WRKSPC, directoryTextArea.getText());
	}
	
	/**
	 * Resets all of the pref values to the defaults listed in the preferencestore class. Also changes the objects in the preference window to reflect that.
	 */
	private void resetDefaults(){
		prefs.putInt(PreferenceStore.ColorFor.FOREGROUND.toString(), PreferenceStore.FOREGROUND_DEFAULT);
		foregroundButton.setBackground(new Color(PreferenceStore.FOREGROUND_DEFAULT));
		
		prefs.putInt(PreferenceStore.ColorFor.BACKGROUND.toString(),PreferenceStore.BACKGROUND_DEFAULT);
		backgroundButton.setBackground(new Color(PreferenceStore.BACKGROUND_DEFAULT));
		
		prefs.putInt(PreferenceStore.ColorFor.COMMENT.toString(),PreferenceStore.COMMENT_DEFAULT);
		commentButton.setBackground(new Color(PreferenceStore.COMMENT_DEFAULT));
		
		prefs.putInt(PreferenceStore.ColorFor.KEYWORD.toString(),PreferenceStore.KEYWORD_DEFAULT);
		keywordButton.setBackground(new Color(PreferenceStore.KEYWORD_DEFAULT));
		
		prefs.putInt(PreferenceStore.ColorFor.OPERATOR.toString(),PreferenceStore.OPERATOR_DEFAULT);
		operatorButton.setBackground(new Color(PreferenceStore.OPERATOR_DEFAULT));
		
		prefs.putInt(PreferenceStore.ColorFor.STRING.toString(),PreferenceStore.STRING_DEFAULT);
		stringButton.setBackground(new Color(PreferenceStore.STRING_DEFAULT));
		
		prefs.putInt(PreferenceStore.ColorFor.LINENUMBERFG.toString(),PreferenceStore.LINENUMBERFG_DEFAULT);
		lineNumberFGButton.setBackground(new Color(PreferenceStore.LINENUMBERFG_DEFAULT));
		
		prefs.putInt(PreferenceStore.ColorFor.LINENUMBERBG.toString(),PreferenceStore.LINENUMBERBG_DEFAULT);
		lineNumberBGButton.setBackground(new Color(PreferenceStore.LINENUMBERBG_DEFAULT));
		
		prefs.put(PreferenceStore.FONT.toString(),PreferenceStore.FONT_DEFAULT);
		currentFontText.setText(PreferenceStore.FONT_DEFAULT);
		currentFontText.setFont(Font.decode(PreferenceStore.FONT_DEFAULT));
		
		prefs.putBoolean(PreferenceStore.WRAP,PreferenceStore.WRAP_DEFAULT);
		wordWrapBox.setSelected(PreferenceStore.WRAP_DEFAULT);
		
		prefs.putBoolean(PreferenceStore.AUTOCOMPILE,PreferenceStore.AUTOCOMPILE_DEFAULT);
		autoCompileBox.setSelected(PreferenceStore.AUTOCOMPILE_DEFAULT);
		
		prefs.putBoolean(PreferenceStore.RECENTFILES,PreferenceStore.BOOLRECENTFILES_DEFAULT);
		loadRecentlyBox.setSelected(PreferenceStore.BOOLRECENTFILES_DEFAULT);
		
		prefs.put(PreferenceStore.NBCTOOL,PreferenceStore.NBCTOOL_DEFAULT);
		toolLocationTextArea.setText(PreferenceStore.NBCTOOL_DEFAULT);
		
		prefs.put(PreferenceStore.WRKSPC, PreferenceStore.WRKSPC_DEFAULT);
		directoryTextArea.setText(PreferenceStore.WRKSPC_DEFAULT);
		
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
				|| arg0.getActionCommand().equals("lineNumberBGButton") || arg0.getActionCommand().equals("operatorButton")){
				Color newColor = JColorChooser.showDialog(this, "Choose a color", ((JButton)arg0.getSource()).getBackground());
				((JButton)arg0.getSource()).setBackground(newColor);
		
		// the font button was picked, bring up the font change dialog and change the font.
		}else if(arg0.getActionCommand().equals("changeFont")){
			JFontChooser fontChooser = new JFontChooser();
			int result = fontChooser.showDialog(this);
			if (result == JFontChooser.OK_OPTION) {
				Font currentFont = fontChooser.getSelectedFont();
				currentFontText.setText(currentFont.getName() +"-"+ currentFont.toString().split("=")[3].split(",")[0]+"-"+ currentFont.getSize());
				currentFontText.setFont(currentFont);		
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
			
		//User wishes to cancel any changes and close window
		}else if(arg0.getActionCommand().equals("cancel")){
			int n = JOptionPane.showConfirmDialog(
				    this,
				    "Are you sure you wish to cancel? Any unsaved changes will be lost.",
				    "Preferences",
				    JOptionPane.YES_NO_OPTION);
			if(n == JOptionPane.YES_OPTION){
				manager.updatePreferences();
				this.dispose();
			}
		// User wishes to leave. Saves changes and leaves	
		}else if(arg0.getActionCommand().equals("OK")){
			saveValues();
			manager.updatePreferences();
			this.dispose();
		//User wishes to reset all items to default. Makes sure with another dialog, then does so if necessary.
		}else if(arg0.getActionCommand().equals("reset")){
			int n = JOptionPane.showConfirmDialog(
				    this,
				    "Are you sure you wish to reset to defaults?",
				    "Preferences",
				    JOptionPane.YES_NO_OPTION);
			if(n == JOptionPane.YES_OPTION){	
				resetDefaults();
			}
		//User wishes to apply settings but stay in window.
		}else if(arg0.getActionCommand().equals("apply")){
			saveValues();
		}
	}

	
}
