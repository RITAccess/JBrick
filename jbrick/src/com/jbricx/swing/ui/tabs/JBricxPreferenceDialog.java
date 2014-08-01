package com.jbricx.swing.ui.tabs;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.FilenameFilter;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.w3c.dom.Document;

import com.jbricx.swing.ui.JBricxManager;
import com.jbricx.swing.ui.preferences.JFontChooser;
import com.jbricx.swing.ui.preferences.PreferenceStore;
import com.jbricx.swing.ui.preferences.PreferenceStore.Preference;
import com.jbricx.tools.XMLParser;

@SuppressWarnings("serial")
public class JBricxPreferenceDialog extends JDialog {
	private JPanel themePanel, colorPanel, fontPanel, miscPanel, nbcPanel, workspacePanel, buttonPanel;
	private JBricxManager manager;
	
	public JBricxPreferenceDialog(JBricxManager manager){
		this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));
		((JPanel)this.getContentPane()).setBorder(new EmptyBorder(7,7,7,7));
		this.setSize(new Dimension(550,660));
		this.manager = manager;
		// Setting up the Theme Panel
		themePanel = new ThemePane();
		themePanel.getAccessibleContext().setAccessibleName("Theme Selector");
		
		// Setting up the Color Panel
		colorPanel = new JPanel();
		colorPanel.getAccessibleContext().setAccessibleName("Theme Colors");
		colorPanel.setLayout(new GridLayout(Preference.COLOR.children.size(), 1));
		ColorPane[] colorButtons = new ColorPane[Preference.COLOR.children.size()];
		int index = 0;
		for (Preference colorPref : Preference.COLOR.children){
			colorButtons[index] = (ColorPane) colorPanel.add(new ColorPane(colorPref));
			index++;
		}
		
		// Setting up the Font Panel
		fontPanel = new FontPane();
		fontPanel.getAccessibleContext().setAccessibleName("Font Selector");
		
		// Setting up the Misc Panel
		miscPanel = new CheckBoxPane();
		miscPanel.getAccessibleContext().setAccessibleName("Miscellaneous Settings");
		
		// Setting up the NBC Panel
		nbcPanel = new DirectoryPane(Preference.NBCTOOL);
		nbcPanel.getAccessibleContext().setAccessibleName("NBC Compilier Location");
		
		// Setting up the Workspace Panel
		workspacePanel = new DirectoryPane(Preference.WORKSPACE);
		workspacePanel.getAccessibleContext().setAccessibleName("WorkSpace Location");
		
		// Setting up the button Panel
		buttonPanel = new ButtonPane(this);
		
		// Loop add / titled borders
		for(JPanel jp : new JPanel[]{themePanel, colorPanel, fontPanel, miscPanel, nbcPanel, workspacePanel}){
			jp.setBorder(
					new CompoundBorder(
							BorderFactory.createTitledBorder(jp.getAccessibleContext().getAccessibleName()),
							new EmptyBorder(3, 3, 3, 3)
							)
					);
			this.add(jp);
		}

		this.add(buttonPanel);
		this.pack();
	}
	
	public static void applyValues(){
		for(PreferencePanel pp : PreferencePanel.panels.values()){
			pp.saveValue();
		}
	}
	
	public static void resetValues() {
		for(PreferencePanel pp : PreferencePanel.panels.values()){
			pp.resetValue();
		}
	}
	
	public JBricxManager getManager(){
		return this.manager;
	}

}

@SuppressWarnings("serial")
class ColorPane extends PreferencePanel{

	JButton button;
	Preference pref;
	
	ColorPane(Preference pref){
		super(pref);
		this.pref = pref;
		JLabel label = new JLabel(pref.label + ": ");
		button = new JButton();
		button.getAccessibleContext().setAccessibleName(pref.toString() + " Color");
		button.setBackground(PreferenceStore.getColor(pref));
		button.setContentAreaFilled(false);
		button.setOpaque(true);
		button.setSize(new Dimension(120,20));
		button.setPreferredSize(new Dimension(120,20));
		button.setBorder(BorderFactory.createLineBorder(Color.black));
		button.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Color newColor = JColorChooser.showDialog(ColorPane.this, "Choose a color", ColorPane.this.button.getBackground());
				
				if(newColor != null)
				{
					ColorPane.this.button.setBackground(newColor);
					if (ColorPane.this.pref == Preference.BACKGROUND) {
						((FontPane)PreferencePanel.panels.get(Preference.FONT)).textArea.setBackground(newColor);
					}
					if (ColorPane.this.pref == Preference.FOREGROUND) {
						((FontPane)PreferencePanel.panels.get(Preference.FONT)).textArea.setForeground(newColor);
					}
				}
			}
		});
		this.setLayout(new BorderLayout());
		this.add(BorderLayout.WEST, label);
		this.add(BorderLayout.EAST, button);
		this.setVisible(true);
	}

	public void saveValue() {
		PreferenceStore.set(this.pref, this.button.getBackground().getRGB());
	}

	@Override
	public void resetValue() {
		button.setBackground(PreferenceStore.getColor(pref));
		if (this.pref == Preference.BACKGROUND) {
			((FontPane)PreferencePanel.panels.get(Preference.FONT)).textArea.setBackground(
					this.button.getBackground());
		}
		if (this.pref == Preference.FOREGROUND) {
			((FontPane)PreferencePanel.panels.get(Preference.FONT)).textArea.setForeground(
					this.button.getBackground());
		}
	}
}

@SuppressWarnings("serial")
class DirectoryPane extends PreferencePanel{
	
	JTextField textArea;
	Preference pref;
	
	DirectoryPane(Preference pref){
		super(pref);
		this.pref = pref;
		this.setLayout(new GridLayout(1,4));
		
		textArea = new JTextField();
		textArea.setText(PreferenceStore.getString(pref));
		textArea.getAccessibleContext().setAccessibleName(pref.toString() + " Directory text area");
		
		JButton button = new JButton("Browse...");
		button.getAccessibleContext().setAccessibleName("Change " + pref.toString() + " Button. Press Enter to browse.");
		button.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				final JFileChooser fc = new JFileChooser();
				fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
				int returnVal = fc.showOpenDialog(DirectoryPane.this);
				//They picked a file
				if(returnVal == JFileChooser.APPROVE_OPTION){
					File selectedDir = fc.getSelectedFile();
					textArea.setText(selectedDir.getAbsolutePath());	
				}
			}
		});
		this.setLayout(new BorderLayout());
		this.add(BorderLayout.CENTER, textArea);
		this.add(BorderLayout.EAST, button);
		this.setVisible(true);
	}

	public void saveValue() {
		PreferenceStore.set(pref, textArea.getText());
	}

	@Override
	public void resetValue() {
		textArea.setText(PreferenceStore.getString(pref));
	}
}

@SuppressWarnings("serial")
class ThemePane extends PreferencePanel {
	
	JComboBox themeSelector;
	DefaultComboBoxModel model;
	JTextField textArea;
	JButton button;
	
	ThemePane() {
		super(Preference.THEMEXML);
		this.setLayout(new BorderLayout());
		textArea = new JTextField(12);
		button = new JButton("Browse...");
		model = new DefaultComboBoxModel();
		themeSelector = new JComboBox(model);
		clearValues();
		button.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				final JFileChooser fc = new JFileChooser(PreferenceStore.getString(Preference.THEMEXML));
				fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int returnVal = fc.showOpenDialog(ThemePane.this);
				//They picked a file
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File selectedDir = fc.getSelectedFile();
					textArea.setText(selectedDir.getAbsolutePath());
					updateSelector();
				}
			}
		});
		themeSelector.addItemListener(new ItemListener(){
			@Override
			public void itemStateChanged(ItemEvent event) {
				if (event.getStateChange() == ItemEvent.SELECTED){
					PreferenceStore.LoadTheme(
							textArea.getText() + "/" + event.getItem().toString()	
							);
					saveValue();
					JBricxPreferenceDialog.resetValues();
				}
			}
		});
		this.add(BorderLayout.WEST, themeSelector);
		this.add(BorderLayout.CENTER, textArea);
		this.add(BorderLayout.EAST, button);
	}
	
	private String[] getXMLs(String string){
		return new File(string).list();
	}
	
	private void updateSelector() {
		model.removeAllElements();
		for (String e : getXMLs(textArea.getText())) {
			if (e.contains(".xml")){
				model.addElement(e);
			}
		}
	}

	public void saveValue() {
		PreferenceStore.set(Preference.THEMEXML, textArea.getText() + "/" + themeSelector.getSelectedItem());
	}

	public void clearValues() {
		File file = new File(PreferenceStore.getString(Preference.THEMEXML));
		textArea.setText(file.getParent());
		updateSelector();
		themeSelector.setSelectedIndex(model.getIndexOf(file.getName()));
	}
	
	public void resetToDefault(){
		File file = new File(Preference.THEMEXML.defaultString);
		textArea.setText(file.getParent());
		updateSelector();
		themeSelector.setSelectedIndex(model.getIndexOf(file.getName()));
	}
	
	public void setThemeFile(String filepath) {
		File file = new File(filepath);
		textArea.setText(file.getParent());
		updateSelector();
		themeSelector.setSelectedIndex(model.getIndexOf(file.getName()));
	}
	
	public void resetValue() {}
}

@SuppressWarnings("serial")
class CheckBoxPane extends PreferencePanel {

	private JCheckBox wordWrapBox;
	private JCheckBox autoSaveOnCompileBox;
	private JCheckBox loadRecentlyBox;
	
	CheckBoxPane() {
		super(Preference.MISC);
		wordWrapBox = new JCheckBox();
		wordWrapBox.setText("Word Wrap");
		wordWrapBox.setSelected(PreferenceStore.getBool(Preference.WRAP));
		wordWrapBox.getAccessibleContext().setAccessibleName("Word Wrap Check Box. Press Space to Toggle");
		
		autoSaveOnCompileBox = new JCheckBox();
		autoSaveOnCompileBox.setText("Auto Save on Compile");
		autoSaveOnCompileBox.setSelected(PreferenceStore.getBool(Preference.AUTOSAVEONCOMPILE));
		autoSaveOnCompileBox.getAccessibleContext().setAccessibleName("Auto Save on Compile Box. Press Space to Toggle");
		
		loadRecentlyBox = new JCheckBox();
		loadRecentlyBox.setText("Load Recent Files");
		loadRecentlyBox.setSelected(PreferenceStore.getBool(Preference.BOOLRECENTFILES));
		loadRecentlyBox.getAccessibleContext().setAccessibleName("Load Recent Files Box. Press Space to Toggle");
		
		this.add(wordWrapBox);
		this.add(autoSaveOnCompileBox);
		this.add(loadRecentlyBox);
	}

	@Override
	public void saveValue() {
		PreferenceStore.set(Preference.WRAP, wordWrapBox.isSelected());
		PreferenceStore.set(Preference.AUTOSAVEONCOMPILE, autoSaveOnCompileBox.isSelected());
		PreferenceStore.set(Preference.BOOLRECENTFILES, loadRecentlyBox.isSelected());
	}

	@Override
	public void resetValue() {
		wordWrapBox.setSelected(PreferenceStore.getBool(Preference.WRAP));
		autoSaveOnCompileBox.setSelected(PreferenceStore.getBool(Preference.AUTOSAVEONCOMPILE));
		loadRecentlyBox.setSelected(PreferenceStore.getBool(Preference.BOOLRECENTFILES));
	}
}

@SuppressWarnings("serial")
class FontPane extends PreferencePanel {

	JTextArea textArea;
	JButton button;
	
	FontPane(){
		super(Preference.FONT);
		this.setLayout(new BorderLayout());
		textArea = new JTextArea();
		textArea.setBackground(((ColorPane)PreferencePanel.panels.get(Preference.BACKGROUND)).button.getBackground());
		textArea.setForeground(((ColorPane)PreferencePanel.panels.get(Preference.FOREGROUND)).button.getBackground());
		textArea.setEditable(false);
		Font currentFont = Font.decode(PreferenceStore.getString(Preference.FONT));

		//Shows current font. Stuff in the middle delimits to get the style string which isnt easily attainable(is actually an int)
		textArea.setText(currentFont.getName() +"-"+ currentFont.toString().split("=")[3].split(",")[0]+"-"+ currentFont.getSize());
		textArea.setFont(currentFont);
		textArea.getAccessibleContext().setAccessibleName("Font Box");
		button = new JButton("Change...");
		button.getAccessibleContext().setAccessibleName("Change Font Button. Press Enter");
		button.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFontChooser fontChooser = new JFontChooser();
				int result = fontChooser.showDialog(FontPane.this);
				if (result == JFontChooser.OK_OPTION) {  
					Font currentFont = fontChooser.getSelectedFont();
					textArea.setText(currentFont.getName() +"-"+ currentFont.toString().split("=")[3].split(",")[0]+"-"+ currentFont.getSize());
					textArea.setFont(currentFont);
					fontChooser.setDefaultSelectedFont(currentFont);
				}
			}
		});
		this.add(BorderLayout.CENTER, textArea);
		this.add(BorderLayout.EAST, button);
	}
	@Override
	public void saveValue() {
		PreferenceStore.set(Preference.FONT, textArea.getText());
	}

	@Override
	public void resetValue() {
		textArea.setText(PreferenceStore.getString(Preference.FONT));
		textArea.setFont(Font.decode(textArea.getText()));
	}
	
}

@SuppressWarnings("serial")
class ButtonPane extends JPanel implements ActionListener {
	
	JButton resetToDefaultButton, cancelButton, okButton, applyButton, saveButton;
	JBricxPreferenceDialog window;
	
	ButtonPane(JBricxPreferenceDialog dialog){
		this.window = dialog;
		resetToDefaultButton = new JButton("Reset to Defaults");
		resetToDefaultButton.addActionListener(this);
		resetToDefaultButton.getAccessibleContext().setAccessibleName("Reset Button. Reset all values to Default");
		
		cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(this);
		cancelButton.getAccessibleContext().setAccessibleName("Cancel button. Cancel any changes made and exit");
		
		okButton = new JButton("OK");
		okButton.addActionListener(this);
		okButton.getAccessibleContext().setAccessibleName("Ok Button. Press to accept any changes");
		
		applyButton = new JButton("Apply");
		applyButton.addActionListener(this);
		applyButton.getAccessibleContext().setAccessibleName("Apply button. Press to apply any changes");

		saveButton = new JButton("Save & Apply");
		saveButton.addActionListener(this);
		saveButton.getAccessibleContext().setAccessibleName("Save button. Press to save any to a theme");
		
		this.add(resetToDefaultButton);
		this.add(cancelButton);
		this.add(okButton);
		this.add(applyButton);
		this.add(saveButton);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == resetToDefaultButton) {
			int n = JOptionPane.showConfirmDialog(
				    this,
				    "Are you sure you wish to reset to defaults?",
				    "Preferences",
				    JOptionPane.YES_NO_OPTION);
			if(n == JOptionPane.YES_OPTION){
				PreferenceStore.resetToDefaults();
				((ThemePane) PreferencePanel.panels.get(Preference.THEMEXML)).resetToDefault();
			}
		}
		if (event.getSource() == cancelButton) {
			int n = JOptionPane.showConfirmDialog(
				    this,
				    "Are you sure you wish to cancel? Any unsaved changes will be lost.",
				    "Preferences",
				    JOptionPane.YES_NO_OPTION);
			if(n == JOptionPane.YES_OPTION){
				window.dispose();
			}
		}
		if (event.getSource() == okButton) {
			JBricxPreferenceDialog.applyValues();
			window.getManager().updatePreferences(); //Should automatically Update
			window.dispose();
		}
		if (event.getSource() == applyButton) {
			JBricxPreferenceDialog.applyValues();
			window.getManager().updatePreferences(); //Should automatically Update
		}
		if (event.getSource() == saveButton) {
			JBricxPreferenceDialog.applyValues();
			window.getManager().updatePreferences(); //Should automatically Update
			saveTheme();
		}
	}
	
	public void saveTheme(){
		FileDialog fDialog = new FileDialog(window, "Save", FileDialog.SAVE);
		fDialog.setFile("*.xml");
		fDialog.setVisible(true);
		String filepath = fDialog.getFile();
		if (filepath != null) {
			if (filepath.toLowerCase().endsWith("default.xml")) {
				JOptionPane.showMessageDialog(window,
					    "Cannot overwrite the Default.xml file.\n"
					    + "You edits will be applied but will not be saved.",
					    "Overwrite Defaults Error",
					    JOptionPane.ERROR_MESSAGE);
			} else { 
				filepath = fDialog.getDirectory() + "/" + filepath;
				if (!filepath.toLowerCase().endsWith(".xml")) {
				    filepath = filepath + ".xml";
				}
				Document doc = PreferenceStore.buildPreferencesDocument();
				XMLParser.writeToFile(doc, filepath);
				((ThemePane) PreferencePanel.panels.get(Preference.THEMEXML)).setThemeFile(filepath);
			}	
		}
	}
}

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
	public abstract void saveValue();
	public abstract void resetValue();
}