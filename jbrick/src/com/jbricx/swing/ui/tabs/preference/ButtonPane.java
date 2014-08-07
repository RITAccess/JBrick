package com.jbricx.swing.ui.tabs.preference;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javafx.application.Platform;
import javafx.stage.FileChooser;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.w3c.dom.Document;

import com.jbricx.swing.ui.preferences.PreferenceStore;
import com.jbricx.swing.ui.preferences.PreferenceStore.Preference;
import com.jbricx.tools.XMLParser;

@SuppressWarnings("serial")
public class ButtonPane extends JPanel implements ActionListener {
	
	JButton resetToDefaultButton, cancelButton, okButton, applyButton, saveButton;
	JBricxPreferenceDialog window;
	
	public ButtonPane(JBricxPreferenceDialog dialog){
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

		saveButton = new JButton("Apply & Save");
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
			if (JBricxPreferenceDialog.isDirty) {
				int n = JOptionPane.showConfirmDialog(
					    this,
					    "Are you sure you wish to cancel? Any unsaved changes will be lost.",
					    "Preferences",
					    JOptionPane.YES_NO_OPTION);
				if (n == JOptionPane.YES_OPTION) {
					window.dispose();
				}
			} else {
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
		Platform.runLater(new Runnable(){
			@Override
			public void run() {
				FileChooser fChooser = new FileChooser();
		    	fChooser.setTitle("Save Theme");
		    	ThemePane tPane = (ThemePane) PreferencePanel.panels.get(Preference.THEMEXML);
		    	fChooser.setInitialFileName(tPane.model.getSelectedItem().toString());
		    	if (fChooser.getInitialDirectory() == null) {
		    		fChooser.setInitialDirectory(new File(tPane.textArea.getText()));
		    	}
		    	File file = fChooser.showSaveDialog(null);
		    	if (file != null) {
		    		String filepath = file.getAbsolutePath();
					if (!filepath.toLowerCase().endsWith(".xml")) {
						    filepath = filepath + ".xml";
					}
					if (filepath.endsWith(Preference.THEMEXML.defaultString)) {
						JOptionPane.showMessageDialog(window,
							    "Cannot overwrite the Default file.\n"
							    + "You edits will be applied but will not be saved.",
							    "Overwrite Defaults Error",
							    JOptionPane.ERROR_MESSAGE);
					} else {
						Document doc = PreferenceStore.buildPreferencesDocument();
						XMLParser.writeToFile(doc, filepath);
						((ThemePane) PreferencePanel.panels.get(Preference.THEMEXML)).setThemeFile(filepath);
					}	
		    	}
			}
		});
	}
}