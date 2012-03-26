package com.jbricx.ui.DirectControl;

/**
 * @author Abhishek Shrestha
 */
import java.awt.Composite;

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
