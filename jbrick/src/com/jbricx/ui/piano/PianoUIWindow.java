package com.jbricx.ui.piano;

import org.eclipse.jface.dialogs.TrayDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

import com.jbricx.communications.NXTObserver;

/**
 * @author Priya Sankaran
 * @author Abhishek Shrestha
 */
public class PianoUIWindow extends TrayDialog implements NXTObserver {
  public Control pianoComposite1 =  null;

  public PianoUIWindow(final Shell shell) {
    super(shell);
  }

  PianoComposite pianoComposite;
  @Override
  protected Control createContents(Composite parent) {
    getShell().setText("Piano");
    pianoComposite= new PianoComposite(parent, SWT.NULL);
    return pianoComposite;
  }

  @Override
  public void update(boolean isConnected) {
    // TODO: do some saving implementation
      if(!isConnected){
            System.out.println("abishesk");
            //pianoComposite.disableButtons();
        }
  }
}
