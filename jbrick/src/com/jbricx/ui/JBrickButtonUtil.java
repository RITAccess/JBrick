package com.jbricx.ui;

import java.awt.Button;
import java.awt.Label;

public class JBrickButtonUtil {

	public JBrickButtonUtil() {

	}

	public void setAccessibleString(Button button, final String value) {

		button.getAccessible().addAccessibleListener(new AccessibleAdapter() {
			public void getName(AccessibleEvent e) {
				e.result = value;
			}
		});
	}

	public void callListenerAction() {

	}

	public void setAccessibleString(Label label, final String value) {
		// TODO Auto-generated method stub
		label.getAccessible().addAccessibleListener(new AccessibleAdapter() {
			public void getName(AccessibleEvent e) {
				e.result = value;
			}
		});
	}
}
