package com.jbricx.ui.joystick;

import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

public class JoystickUIWindow extends ApplicationWindow {

	public JoystickUIWindow() {
		super(null);
		// TODO Auto-generated constructor stub
	}

	protected Control createContents(Composite parent) {
		getShell().setText("Joystick");
		parent.setSize(300, 410);

		new JoystickComposite(parent, SWT.NULL);

		return parent;
	}

}
