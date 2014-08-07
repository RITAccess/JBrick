package com.jbricx.swing.ui.tabs.preference;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javafx.application.Platform;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

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
				
				Platform.runLater(new Runnable(){
					@Override
					public void run() {
						if(onlyDir){
							DirectoryChooser dChooser = new DirectoryChooser();
							if(dChooser.getInitialDirectory() != null){
								dChooser.setInitialDirectory(new File(PreferenceStore.getString(PreferenceStore.Preference.WORKSPACE)));
							}
							dChooser.setTitle("Open Directory");
							File file = dChooser.showDialog(null);
							if (file != null) {
								textArea.setText(file.getAbsolutePath());	
								JBricxPreferenceDialog.isDirty = true;
							}
						}
						else{
							FileChooser fChooser = new FileChooser();
							fChooser.setTitle("Open");
							File file = fChooser.showOpenDialog(null);
							if(file != null){
								textArea.setText(file.getAbsolutePath());
								JBricxPreferenceDialog.isDirty = true;
							}
						}
					}
				});
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