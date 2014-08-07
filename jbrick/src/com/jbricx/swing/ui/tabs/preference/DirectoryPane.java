package com.jbricx.swing.ui.tabs.preference;

import java.awt.BorderLayout;
import java.awt.FileDialog;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.jbricx.swing.ui.JBricxManager;
import com.jbricx.swing.ui.preferences.PreferenceStore;
import com.jbricx.swing.ui.preferences.PreferenceStore.Preference;

@SuppressWarnings("serial")
public class DirectoryPane extends PreferencePanel{
	
	JTextField textArea;
	Preference pref;
	/**
	 * DirectoryPane allows user to choose a file or directory setting.
	 * @param pref - The preference assosiated with this object.
	 * @param onlyDir - only directories can be selected (otherwise files) .. must be defined for mac
	 */
	public DirectoryPane(Preference pref, final JBricxManager manager, final boolean onlyDir){
		super(pref);
		this.pref = pref;
		this.setLayout(new GridLayout(1,4));
		
		textArea = new JTextField(18);
		textArea.setText(PreferenceStore.getString(pref));
		textArea.getAccessibleContext().setAccessibleName(pref.toString() + " Directory text area");
		
		JButton button = new JButton("Browse...");
		button.getAccessibleContext().setAccessibleName("Change " + pref.toString() + " Button. Press Enter to browse.");
		button.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (onlyDir) {
					System.setProperty("apple.awt.fileDialogForDirectories", "true");
				}
				FileDialog fDialog = new FileDialog(manager.getShell(), "Open", FileDialog.LOAD);
				fDialog.setDirectory(PreferenceStore.getString(PreferenceStore.Preference.WORKSPACE));
				fDialog.setVisible(true);
				String filepath = fDialog.getFile();
				if (filepath != null) {
					textArea.setText(fDialog.getDirectory() + filepath);	
					JBricxPreferenceDialog.isDirty = true;
				}
				if (onlyDir) {
					System.setProperty("apple.awt.fileDialogForDirectories", "false");
				}
			}
		});
		this.setLayout(new BorderLayout());
		this.add(BorderLayout.CENTER, textArea);
		this.add(BorderLayout.EAST, button);
		this.setVisible(true);
	}

	@Override
	public void saveValue() {
		PreferenceStore.set(pref, textArea.getText());
	}
	
	@Override
	public void resetValue() {
		textArea.setText(PreferenceStore.getString(pref));
	}

	@Override
	public JPanel createPanel() {
		this.getAccessibleContext().setAccessibleName(pref.label);
		return this;
	}
}