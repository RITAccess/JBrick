package com.jbricx.tools;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;


@SuppressWarnings("serial")
public class AccessibleWidget extends JLabel {
	public AccessibleWidget(String string) {
		super(string);
	}

	public AccessibleWidget() {
		super(" ");
	}

	public void readLabel(String text, JFrame window, JComponent previousComponent){
		this.setText(text);
		/*
		this.requestFocus();
		if (previousComponent.getFocusListeners().length > 2){
			FocusListener fl = previousComponent.getFocusListeners()[1];
			previousComponent.removeFocusListener(fl);
			previousComponent.requestFocusInWindow();
			previousComponent.addFocusListener(fl);
		}
		*/
	}
}
