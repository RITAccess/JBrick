package com.jbricx.swing.ui.tabs.preference;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JCheckBox;
import javax.swing.JPanel;

import com.jbricx.swing.ui.preferences.PreferenceStore;
import com.jbricx.swing.ui.preferences.PreferenceStore.Preference;

@SuppressWarnings("serial")
public class CheckboxPane extends PreferencePanel implements ItemListener {

	private JCheckBox wordWrapBox;
	private JCheckBox autoSaveOnCompileBox;
	private JCheckBox loadRecentlyBox;
	
	public CheckboxPane() {
		super(Preference.MISC);
		wordWrapBox = new JCheckBox();
		wordWrapBox.setText("Word Wrap");
		wordWrapBox.setSelected(PreferenceStore.getBool(Preference.WRAP));
		wordWrapBox.getAccessibleContext().setAccessibleName("Word Wrap Check Box. Press Space to Toggle");
		wordWrapBox.addItemListener(this);
		
		autoSaveOnCompileBox = new JCheckBox();
		autoSaveOnCompileBox.setText("Auto Save on Compile");
		autoSaveOnCompileBox.setSelected(PreferenceStore.getBool(Preference.AUTOSAVEONCOMPILE));
		autoSaveOnCompileBox.getAccessibleContext().setAccessibleName("Auto Save on Compile Box. Press Space to Toggle");
		autoSaveOnCompileBox.addItemListener(this);
		
		loadRecentlyBox = new JCheckBox();
		loadRecentlyBox.setText("Load Recent Files");
		loadRecentlyBox.setSelected(PreferenceStore.getBool(Preference.BOOLRECENTFILES));
		loadRecentlyBox.getAccessibleContext().setAccessibleName("Load Recent Files Box. Press Space to Toggle");
		loadRecentlyBox.addItemListener(this);
		
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

	@Override
	public void itemStateChanged(ItemEvent arg0) {
		JBricxPreferenceDialog.isDirty = true;
	}

	@Override
	public JPanel createPanel() {
		this.getAccessibleContext().setAccessibleName("Miscellaneous Settings");
		return this;
	}
}