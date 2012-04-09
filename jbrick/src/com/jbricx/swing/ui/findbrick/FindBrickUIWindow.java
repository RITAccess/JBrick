package com.jbricx.swing.ui.findbrick;

import java.awt.Composite;

/**
 * @author Priya Sankaran
 */
public class FindBrickUIWindow extends TrayDialog {

	public FindBrickUIWindow(Shell parentShell) {
		super(parentShell);		
	}

  @Override
	protected Control createContents(Composite parent) {
		getShell().setText("Find Brick");
		parent.setSize(400, 320);

		return new FindBrickComposite(parent, SWT.NULL);
	}

}
