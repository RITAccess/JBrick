package com.jbricx.ui.controller;

/**
 * @author Priya Sankaran
 * @author Abhishek Shrestha
 */
import org.eclipse.jface.dialogs.TrayDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

public class ControllerUIWindow extends TrayDialog {

  public ControllerUIWindow(Shell parentShell) {
    super(parentShell);
  }

  @Override
  protected Control createContents(Composite parent) {
    getShell().setText("Direct Controller");
    return new ControllerComposite(parent, SWT.NULL);
  }
}
