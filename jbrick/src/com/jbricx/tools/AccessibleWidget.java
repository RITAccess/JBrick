package com.jbricx.tools;

import javax.swing.JComponent;
import javax.swing.JLabel;

import com.jbricx.piano.PianoWindow;

@SuppressWarnings("serial")
public class AccessibleWidget extends JLabel {
	public AccessibleWidget(String string) {
		super(string);
	}

	public AccessibleWidget() {
		super(" ");
	}

	public void readLabel(String text, PianoWindow window, JComponent previousComponent){
		this.setText(text);
		this.requestFocus();
		
		//previousComponent.requestFocusInWindow();
		//this.setText(" ");
	}
}
