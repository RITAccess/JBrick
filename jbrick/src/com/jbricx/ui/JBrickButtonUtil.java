package com.jbricx.ui;

import org.eclipse.swt.accessibility.AccessibleAdapter;
import org.eclipse.swt.accessibility.AccessibleEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Label;

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
