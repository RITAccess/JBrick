package com.jbricx.ui.findbrick;

/*
 * @author Priya Sankaran
 */
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

public class FindBrickUIWindow extends ApplicationWindow {

	public FindBrickUIWindow() {
		super(null);
		// TODO Auto-generated constructor stub
	}

	protected Control createContents(Composite parent) {
		getShell().setText("Find Brick");
		parent.setSize(400, 320);

		new FindBrickComposite(parent, SWT.NULL);

		return parent;
	}

}
