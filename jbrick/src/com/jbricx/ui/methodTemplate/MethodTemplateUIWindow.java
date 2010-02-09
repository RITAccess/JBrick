package com.jbricx.ui.methodTemplate;

import java.io.IOException;

import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

public class MethodTemplateUIWindow extends ApplicationWindow {
	public MethodTemplateUIWindow() {
		super(null);
		// TODO Auto-generated constructor stub
	}

	protected Control createContents(Composite parent) {
		getShell().setText("Method Template");

		parent.setSize(300, 410);

		try {
			MethodTemplateComposite.showGUI();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return parent;
	}

}
