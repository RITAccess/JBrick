package com.jbricx.swing.ui.tabs.preference;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.jbricx.swing.ui.preferences.PreferenceStore;
import com.jbricx.swing.ui.preferences.PreferenceStore.Preference;

/**
 * ColorPane - generates a simple color settings pane to be placed in the JBricxPreferences
 *
 */
@SuppressWarnings("serial")
public class ColorPane extends PreferencePanel{

	JButton button;
	Preference pref;
	
	public ColorPane(Preference pref){
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
					JBricxPreferenceDialog.isDirty = true;
				}
			}
		});
		this.setLayout(new BorderLayout());
		this.add(BorderLayout.WEST, label);
		this.add(BorderLayout.EAST, button);
		this.setVisible(true);
	}

	@Override
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

	@Override
	public JPanel createPanel() {
		this.getAccessibleContext().setAccessibleName(pref.label + " Color");
		return this;
	}
	
	public static JPanel createPanels() {
		JPanel colorPanel = new JPanel();
		colorPanel.getAccessibleContext().setAccessibleName("Theme Colors");
		colorPanel.setLayout(new GridLayout(Preference.COLOR.children.size(), 1));
		ColorPane[] colorButtons = new ColorPane[Preference.COLOR.children.size()];
		int index = 0;
		for (Preference colorPref : Preference.COLOR.children){
			colorButtons[index] = (ColorPane) colorPanel.add(new ColorPane(colorPref));
			index++;
		}
		return colorPanel;
	}
}