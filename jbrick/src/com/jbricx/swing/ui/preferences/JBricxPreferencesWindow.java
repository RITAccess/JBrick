package com.jbricx.swing.ui.preferences;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.TextAttribute;
import java.io.File;
import java.text.AttributedString;
import java.util.prefs.Preferences;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.jbricx.swing.ui.JBricxManager;

public class JBricxPreferencesWindow extends JDialog implements ActionListener {
	private JPanel mainArea;
	
	private JPanel otherOptionArea;
	private JPanel textOptionArea;

	private JPanel textOptionsArea;
	private JPanel lowerOptionArea;
	
	private GroupLayout textAreaGroupLayout;
	
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
	private JLabel wordWrapLabel;
	private JCheckBox autoCompileBox;
	private JLabel autoCompileLabel;
	private JCheckBox loadRecentlyBox;
	private JLabel loadRecentlyLabel;
	private JLabel toolLocationLabel;
	private JTextField toolLocationTextArea;
	private JButton toolLocationBrowseButton;
	
	
	public JBricxPreferencesWindow(JBricxManager manager){
		super(manager.getShell(),"Preferences",true);
		
		PreferenceStore prefClass = new PreferenceStore();
		prefs = prefClass.getPrefs();
		
		setLayout(new BorderLayout());
		mainArea = new JPanel();
		mainArea.setLayout(new BoxLayout(mainArea, BoxLayout.Y_AXIS));
		add(mainArea,BorderLayout.CENTER);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setSize((screenSize.width-screenSize.width/2),(screenSize.height-(screenSize.height/5)));
		makeComponents();
	}
	
	
	private void makeComponents(){
		textOptionArea = new JPanel(new BorderLayout());
		otherOptionArea = new JPanel(new BorderLayout());

		JLabel textOptionsLabel = new JLabel("<html><u>Text Options</u></html>");
		JLabel toolOptionsLabel = new JLabel("<html><u>Other Options</u></html>");
		
		textOptionArea.add(textOptionsLabel, BorderLayout.NORTH);
		otherOptionArea.add(toolOptionsLabel,BorderLayout.NORTH);
		
		textOptionsArea = new JPanel();
		textAreaGroupLayout = new GroupLayout(textOptionsArea);
		textOptionsArea.setLayout(textAreaGroupLayout);
		textAreaGroupLayout.setAutoCreateGaps(true);
		textAreaGroupLayout.setAutoCreateContainerGaps(true);
		
		lowerOptionArea = new JPanel();
		lowerOptionArea.setLayout(new BoxLayout(lowerOptionArea,BoxLayout.Y_AXIS));
		
		textOptionArea.add(textOptionsArea,BorderLayout.CENTER);	
		
		
		otherOptionArea.add(lowerOptionArea,BorderLayout.CENTER);

		
		//Two main areas inside the box layout
		mainArea.add(textOptionArea);
		mainArea.add(otherOptionArea);
		
		makeColorBoxes();
		makeFontBoxes();
		makeDirectoryBoxes();
		makeCheckBoxes();
		makeToolDirectory();
		
		buildLayout();
		
	}


	private void buildLayout() {
		
		
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
				.addComponent(workspaceLabel)
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
				.addComponent(directoryTextArea)
				.addComponent(wordWrapLabel)
		);
		
		hGroup.addGroup(textAreaGroupLayout.createParallelGroup()
				.addComponent(changeFontButton)
				.addComponent(workspaceChangeButton)
		);
		
		textAreaGroupLayout.setHorizontalGroup(hGroup);

		
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
				.addComponent(workspaceLabel)
				.addComponent(directoryTextArea)
				.addComponent(workspaceChangeButton)
		);
		vGroup.addGroup(textAreaGroupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
				.addComponent(wordWrapBox)
				.addComponent(wordWrapLabel)
		);
		
		
		textAreaGroupLayout.setVerticalGroup(vGroup);
		
		
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
		foregroundButton.setPreferredSize(new Dimension(75,25));
		foregroundButton.setMinimumSize(new Dimension(75,25));
		foregroundButton.setActionCommand("foregroundButton");
		foregroundButton.setBackground(new Color(prefs.getInt(PreferenceStore.ColorFor.FOREGROUND.toString(), PreferenceStore.FOREGROUND_DEFAULT)));
		foregroundButton.addActionListener(this);
		
		//Background Label and Button
		backgroundLabel = new JLabel("Background color: ");
		backgroundButton = new JButton();
		backgroundButton.setMaximumSize(new Dimension(75,25));
		backgroundButton.setPreferredSize(new Dimension(75,25));
		backgroundButton.setActionCommand("backgroundButton");
		backgroundButton.setBackground(new Color(prefs.getInt(PreferenceStore.ColorFor.BACKGROUND.toString(), PreferenceStore.BACKGROUND_DEFAULT)));
		backgroundButton.addActionListener(this);
		
		//Comment Label and Button
		commentLabel = new JLabel("Comment color: ");
		commentButton = new JButton();
		commentButton.setMaximumSize(new Dimension(75,25));
		commentButton.setPreferredSize(new Dimension(75,25));
		commentButton.setActionCommand("commentButton");
		commentButton.setBackground(new Color(prefs.getInt(PreferenceStore.ColorFor.COMMENT.toString(), PreferenceStore.COMMENT_DEFAULT)));
		commentButton.addActionListener(this);

		
		//Keyword Label and Button
		keywordLabel = new JLabel("Keyword color: ");
		keywordButton = new JButton();
		keywordButton.setMaximumSize(new Dimension(75,25));
		keywordButton.setPreferredSize(new Dimension(75,25));
		keywordButton.setActionCommand("keywordButton");
		keywordButton.setBackground(new Color(prefs.getInt(PreferenceStore.ColorFor.KEYWORD.toString(), PreferenceStore.KEYWORD_DEFAULT)));
		keywordButton.addActionListener(this);
		
		//Operator Panel and Button
		operatorLabel = new JLabel("Operator color: ");
		operatorButton = new JButton();
		operatorButton.setMaximumSize(new Dimension(75,25));
		operatorButton.setPreferredSize(new Dimension(75,25));
		operatorButton.setActionCommand("operatorButton");
		operatorButton.setBackground(new Color(prefs.getInt(PreferenceStore.ColorFor.OPERATOR.toString(), PreferenceStore.OPERATOR_DEFAULT)));
		operatorButton.addActionListener(this);
		
		//String Panel and Button
		stringLabel = new JLabel("String color: ");
		stringButton = new JButton();
		stringButton.setMaximumSize(new Dimension(75,25));
		stringButton.setPreferredSize(new Dimension(75,25));
		stringButton.setActionCommand("stringButton");
		stringButton.setBackground(new Color(prefs.getInt(PreferenceStore.ColorFor.STRING.toString(), PreferenceStore.STRING_DEFAULT)));
		stringButton.addActionListener(this);

		
		//Line number FG label and button
		lineNumberFGLabel = new JLabel("Line number FG color: ");
		lineNumberFGButton = new JButton();
		lineNumberFGButton.setMaximumSize(new Dimension(75,25));
		lineNumberFGButton.setPreferredSize(new Dimension(75,25));
		lineNumberFGButton.setActionCommand("lineNumberFGButton");
		lineNumberFGButton.setBackground(new Color(prefs.getInt(PreferenceStore.ColorFor.LINENUMBERFG.toString(), PreferenceStore.LINENUMBERFG_DEFAULT)));
		lineNumberFGButton.addActionListener(this);
		
		//Line number BG label and button
		lineNumberBGLabel = new JLabel("Line number BG color: ");
		lineNumberBGButton = new JButton();
		lineNumberBGButton.setMaximumSize(new Dimension(75,25));
		lineNumberBGButton.setPreferredSize(new Dimension(75,25));
		lineNumberBGButton.setActionCommand("lineNumberBGButton");
		lineNumberBGButton.setBackground(new Color(prefs.getInt(PreferenceStore.ColorFor.LINENUMBERBG.toString(), PreferenceStore.LINENUMBERBG_DEFAULT)));
		lineNumberBGButton.addActionListener(this);
                
    }
	
	/**
	 * Creates the boxes associated with font selection
	 */
	public void makeFontBoxes(){
		fontLabel = new JLabel("Font");
		currentFontText = new JTextArea();
		//JScrollPane fontScroller = new JScrollPane(currentFontText);
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
		wordWrapLabel = new JLabel("Word Wrap");
				
		autoCompileBox = new JCheckBox();
		autoCompileLabel = new JLabel("Auto Compile");

		loadRecentlyBox = new JCheckBox();
		loadRecentlyLabel = new JLabel("Load Recently Opened Files");
		
		
	}
	
	/**
	 * Makes items for the location of the NBC Tool
	 */
	private void makeToolDirectory(){
		toolLocationLabel = new JLabel("NBC Tool Location");
		
		toolLocationTextArea = new JTextField();
		toolLocationTextArea.setText(prefs.get(PreferenceStore.NBCTOOL, PreferenceStore.NBCTOOL));
		
		toolLocationBrowseButton = new JButton("Browse...");
		
	}

	@Override
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
			}
		
		// the browse button was chosen, open a file dialog to change the directory.	
		}else if(arg0.getActionCommand().equals("browseButton")){
			final JFileChooser fc = new JFileChooser();
			fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			int returnVal = fc.showOpenDialog(this);
			//They picked a file
			if(returnVal == JFileChooser.APPROVE_OPTION){
				File selectedDir = fc.getSelectedFile();
				directoryTextArea.setText(selectedDir.getAbsolutePath());
				
			}
		}
	}

	
}
