package com.jbricx.ui.DirectControl;

/**
 * @author Abhishek Shrestha
 */
import org.eclipse.jface.dialogs.TrayDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

public class DirectControlUIWindow extends TrayDialog {

  public DirectControlUIWindow(Shell parentShell) {
    super(parentShell);
  }

  @Override
  protected Control createContents(Composite parent) {
    getShell().setText("Direct Controller");
    return new DirectControlWindow(parent, SWT.NULL);
  }
}
