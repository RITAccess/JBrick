package com.jbricx.ui.joystick;

/*
 * @author Priya Sankaran
 */
import org.eclipse.jface.dialogs.TrayDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

public class JoystickUIWindow extends TrayDialog {

  public JoystickUIWindow(Shell parentShell) {
    super(parentShell);
  }

  @Override
  protected Control createContents(Composite parent) {
    getShell().setText("Joystick");
    parent.setSize(300, 410);

    return new JoystickComposite(parent, SWT.NULL);
  }
}
