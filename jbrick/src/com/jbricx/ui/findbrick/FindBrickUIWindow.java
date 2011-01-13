package com.jbricx.ui.findbrick;

/*
 * @author Priya Sankaran
 */
import org.eclipse.jface.dialogs.TrayDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

public class FindBrickUIWindow extends TrayDialog {

	public FindBrickUIWindow(Shell parentShell) {
		super(parentShell);		
	}

  @Override
	protected Control createContents(Composite parent) {
		getShell().setText("Find Brick");
		parent.setSize(400, 320);

		return new FindBrickComposite(parent, SWT.NULL);
		//return parent;
	}

}
