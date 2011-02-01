package com.jbricx.ui.piano;

/**
 * @author Priya Sankaran
 * @author Abhishek Shrestha
 */
import org.eclipse.jface.dialogs.TrayDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

import com.jbricx.communications.NXTObserver;

public class PianoUIWindow extends TrayDialog implements NXTObserver {

  public PianoUIWindow(Shell parentShell) {
    super(parentShell);
  }

  @Override
  protected Control createContents(Composite parent) {
    getShell().setText("Piano");

    return new PianoComposite(parent, SWT.NULL);
  }

  @Override
  public void update(boolean isConnected) {
    // TODO: do some saving implementation
  }
}
