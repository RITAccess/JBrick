package com.jbricx.ui.piano;

/**
 * @author Priya Sankaran
 * @author Abhishek Shrestha
 */
import org.eclipse.jface.dialogs.TrayDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.jbricx.actions.HelpContentAction;
import com.jbricx.communications.NXTObserver;
import com.jbricx.ui.JBrickManager;

public class PianoUIWindow extends TrayDialog implements NXTObserver {

  private JBrickManager manager;
  public PianoUIWindow(final JBrickManager manager) {
    super(manager.getShell());
    this.manager = manager;
  }

  @Override
  protected Control createContents(Composite parent) {
    getShell().setText("Piano");

    return new PianoComposite(parent, SWT.NULL) {
      @Override
      protected void help() {
        new HelpContentAction(manager).run();
      }
    };
  }

  @Override
  public void update(boolean isConnected) {
    // TODO: do some saving implementation
  }
}
