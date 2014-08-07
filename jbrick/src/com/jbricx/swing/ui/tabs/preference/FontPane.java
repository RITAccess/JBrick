package com.jbricx.swing.ui.tabs.preference;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import com.jbricx.swing.ui.preferences.JFontChooser;
import com.jbricx.swing.ui.preferences.PreferenceStore;
import com.jbricx.swing.ui.preferences.PreferenceStore.Preference;

@SuppressWarnings("serial")
public class FontPane extends PreferencePanel {

	JTextArea textArea;
	JButton button;
	
	Font currentFont;
	
	public FontPane(){
		super(Preference.FONT);
		this.setLayout(new BorderLayout());
		textArea = new JTextArea();
		textArea.setBackground(((ColorPane)PreferencePanel.panels.get(Preference.BACKGROUND)).button.getBackground());
		textArea.setForeground(((ColorPane)PreferencePanel.panels.get(Preference.FOREGROUND)).button.getBackground());
		textArea.setEditable(false);
		currentFont = Font.decode(PreferenceStore.getString(Preference.FONT));

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
					currentFont = fontChooser.getSelectedFont();
					textArea.setText(currentFont.getName() +"-"+ currentFont.toString().split("=")[3].split(",")[0]+"-"+ currentFont.getSize());
					textArea.setFont(currentFont);
					fontChooser.setDefaultSelectedFont(currentFont);
					JBricxPreferenceDialog.isDirty = true;
				}
			}
		});
		this.add(BorderLayout.CENTER, textArea);
		this.add(BorderLayout.EAST, button);
	}
	@Override
	public void saveValue() {
		PreferenceStore.set(Preference.FONT, textArea.getText());
		PreferenceStore.set(Preference.FONTNAME, currentFont.getName());
		PreferenceStore.set(Preference.FONTSIZE, currentFont.getSize() + "");
		PreferenceStore.set(Preference.FONTSTYLE, currentFont.toString().split("=")[3].split(",")[0]);
	}

	@Override
	public void resetValue() {
		textArea.setText(PreferenceStore.getString(Preference.FONT));
		textArea.setFont(Font.decode(textArea.getText()));
	}
	@Override
	public JPanel createPanel() {
		this.getAccessibleContext().setAccessibleName("Font Selector");
		return this;
	}
	
}