package com.jbricx.ui.findbrick;

import java.awt.Toolkit;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

import com.jbricx.communications.NXTConnectionManager;
import com.jbricx.communications.NXTManager;
import com.jbricx.communications.enums.ConnectionType;
import com.jbricx.ui.JBrickButtonUtil;

/**
 * @author Michael Goldstein
 * @author Priya Sankaran
 */
public class FindBrickComposite extends Composite {

  private ConnectionType ct;
  private Button cancel;
  private Button bluetooth;
  private Button usb;
  private Button connect;
  private Button save;
  JBrickButtonUtil buttonUtil = new JBrickButtonUtil();

  /**
   * Auto-generated main method to display this
   * org.eclipse.swt.widgets.Composite inside a new Shell.
   */
  public static void main(String[] args) {
    Shell shell;
    Display display;
    display = Display.getDefault();
    
    shell = new Shell(display);
    FindBrickComposite inst = new FindBrickComposite(shell, SWT.NULL);
    Point size = inst.getSize();
    shell.setLayout(new FillLayout());
    shell.layout();
    if (size.x == 0 && size.y == 0) {
      inst.pack();
      shell.pack();
    } else {
      Rectangle shellBounds = shell.computeTrim(0, 0, size.x, size.y);
      shell.setSize(shellBounds.width + 20, shellBounds.height + 20);
    }
    shell.open();
    while (!shell.isDisposed()) {
      if (!display.readAndDispatch()) {
        display.sleep();
      }
    }
  }

  public FindBrickComposite(Composite parent, int style) {
    super(parent, style);

    try {
      ct = FindBrickFileIO.getCT();

      Group informationGroup = new Group(this, SWT.NONE);
      informationGroup.setText("Information");
      informationGroup.setBounds(19, 10, 356, 80);

      Label info = new Label(informationGroup, SWT.WRAP);

      info.setText("To connect to the brick, select the communication method "
          + "and click Connect.  You can save your preference by clicking "
          + "the Save button so you do not need to come back to this screen "
          + "in the future.");

      Group connectionStatusGroup = new Group(this, SWT.NONE);
      connectionStatusGroup.setText("Connection satus:");
      connectionStatusGroup.setBounds(19, 100, 356, 50);

      final Label connectionInfo = new Label(connectionStatusGroup, SWT.WRAP);
      connectionInfo.setBounds(13, 20, 340, 25);

      if (NXTManager.getInstance().isConnected()) {
        connectionInfo.setText("Connected using "
            + NXTManager.getInstance());
      } else {
        connectionInfo.setText("Not Connected..!");
      }

      Group connectionTypeGroup = new Group(this, SWT.NONE);
      connectionTypeGroup.setText("Connection Type");
      connectionTypeGroup.setBounds(19, 160, 356, 74);

      usb = new Button(connectionTypeGroup, SWT.RADIO | SWT.LEFT);
      usb.setText("USB");
      usb.setBounds(12, 26, 65, 30);
      buttonUtil.setAccessibleString(usb, "Brick Type List");

      usb.addListener(SWT.Selection, new Listener() {

        public void handleEvent(Event event) {
          ct = ConnectionType.USB;
          System.out.println("USB selected");
        }
      });

      bluetooth = new Button(connectionTypeGroup, SWT.RADIO | SWT.LEFT);
      bluetooth.setText("Bluetooth");
      bluetooth.setSize(60, 30);
      bluetooth.setBounds(89, 26, 69, 30);
      buttonUtil.setAccessibleString(bluetooth, "Bluetooth");

      bluetooth.addListener(SWT.Selection, new Listener() {
        public void handleEvent(Event event) {
          ct = ConnectionType.BLUETOOTH;
          System.out.println("BT selected");
        }
      });

      connect = new Button(this, SWT.PUSH | SWT.CENTER);
      connect.setText("Connect");
      connect.setBounds(74, 250, 60, 30);
      buttonUtil.setAccessibleString(connect, "Connect");
      connect.addListener(SWT.Selection, new Listener() {

        public void handleEvent(Event event) {
          NXTConnectionManager nxtManager = NXTManager.getInstance();
          nxtManager.connect(ct);
          boolean isConnected = nxtManager.isConnected();
         
          if (!isConnected) {
            String status = "Connection attempted using " + ct + " but failed!";
            connectionInfo.setText(status);

            Toolkit.getDefaultToolkit().beep();
            buttonUtil.setAccessibleString(connectionInfo, status);
          } else {
            getParent().dispose();
          }
          nxtManager.notifyAllObservers(isConnected);
        }
      });

      save = new Button(this, SWT.PUSH | SWT.CENTER);
      save.setText("Save");
      save.setBounds(157, 250, 60, 30);
      buttonUtil.setAccessibleString(save, "Save");

      save.addListener(SWT.Selection, new Listener() {

        public void handleEvent(Event event) {
          FindBrickFileIO.saveCT(ct);
        }
      });

      cancel = new Button(this, SWT.PUSH | SWT.CENTER);
      cancel.setText("Quit");
      cancel.setSize(60, 30);
      cancel.setBounds(237, 250, 60, 30);
      buttonUtil.setAccessibleString(cancel, "Cancel");
      cancel.addListener(SWT.Selection, new Listener() {
        public void handleEvent(Event event) {
          getParent().dispose();
        }
      });

      if (ct == ConnectionType.BLUETOOTH) {
        bluetooth.setSelection(true);
      } else {
        usb.setSelection(true);
      }

      this.layout();
      pack();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
