package com.jbricx.swing.ui.tabs.preference;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;

import javafx.application.Platform;
import javafx.stage.DirectoryChooser;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.jbricx.swing.ui.JBricxManager;
import com.jbricx.swing.ui.preferences.PreferenceStore;
import com.jbricx.swing.ui.preferences.PreferenceStore.Preference;

@SuppressWarnings("serial")
public class ThemePane extends PreferencePanel {
	
	JComboBox themeSelector;
	DefaultComboBoxModel model;
	JTextField textArea;
	JButton button;
	
	public ThemePane(final JBricxManager manager) {
		super(Preference.THEMEXML);
		this.setLayout(new BorderLayout());
		textArea = new JTextField(12);
		button = new JButton("Browse...");
		button.getAccessibleContext().setAccessibleName("Browse theme directory");
		model = new DefaultComboBoxModel();
		themeSelector = new JComboBox(model);
		themeSelector.getAccessibleContext().setAccessibleName("Select Theme");
		clearValues();
		button.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				Platform.runLater(new Runnable(){
					@Override
					public void run() {
						DirectoryChooser dChooser = new DirectoryChooser();
						if(dChooser.getInitialDirectory() != null){
							dChooser.setInitialDirectory(new File(PreferenceStore.getString(Preference.THEMEXML)));
						}
						dChooser.setTitle("Open Directory");
						File file = dChooser.showDialog(null);
						if (file != null) {
							textArea.setText(file.getAbsolutePath());	
							updateSelector();
							JBricxPreferenceDialog.isDirty = true;
						}
					}
				});
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
					JBricxPreferenceDialog.isDirty = true;
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
	
	@Override
	public void saveValue() {
		PreferenceStore.set(Preference.THEMEXML, textArea.getText() + "/" + themeSelector.getSelectedItem());
	}
	
	@Override
	public void resetValue() {}

	@Override
	public JPanel createPanel() {
		this.getAccessibleContext().setAccessibleName("Theme Selector");
		return this;
	}
}