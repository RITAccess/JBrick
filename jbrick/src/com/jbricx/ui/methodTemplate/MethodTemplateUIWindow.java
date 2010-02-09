package com.jbricx.ui.methodTemplate;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;

import com.jbricx.ui.joystick.JoystickComposite;

public class MethodTemplateUIWindow extends ApplicationWindow {
	public MethodTemplateUIWindow() {
		super(null);
		// TODO Auto-generated constructor stub
	}

	protected Control createContents(Composite parent) {
		getShell().setText("Method Template");
		
		parent.setSize(300, 410);

		new MethodTemplateComposite(parent, SWT.NULL);
		return parent;
	}

}
