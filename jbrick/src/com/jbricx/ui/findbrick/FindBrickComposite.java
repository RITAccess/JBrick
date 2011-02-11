package com.jbricx.ui.findbrick;

/*
 * @author Michael Goldstein
 * @author Priya Sankaran
 * 
 */
import java.awt.Toolkit;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

import com.jbricx.communications.AbstractNXTBrick;
import com.jbricx.communications.NXT.ConnectionType;
import com.jbricx.communications.NXTManager;
import com.jbricx.ui.JBrickButtonUtil;

@SuppressWarnings("unused")
public class FindBrickComposite extends Composite {

  private FindBrickFileIO FBFIO;
  private ConnectionType ct;
  private Group rightMotor;
  private Button cancel;
  private Button bluetooth;
  private Button usb;
  private Button connect;
  private Button save;
  private Label info;
  private Label connectionInfo;
  private Group driveMode;
  private Group connectionGrp;
  JBrickButtonUtil buttonUtil = new JBrickButtonUtil();

  /**
   * Auto-generated main method to display this
   * org.eclipse.swt.widgets.Composite inside a new Shell.
   */
  public static void main(String[] args) {
    showGUI();
  }

  /**
   * Overriding checkSubclass allows this class to extend
   * org.eclipse.swt.widgets.Composite
   */
  protected void checkSubclass() {
  }

  /**
   * Auto-generated method to display this org.eclipse.swt.widgets.Composite
   * inside a new Shell.
   */
  public static void showGUI() {
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
    FBFIO = new FindBrickFileIO();
    initGUI();
  }

  private void initGUI() {
    try {
      ct = FindBrickFileIO.getCT();

      driveMode = new Group(this, SWT.NONE);
      driveMode.setText("Information");
      driveMode.setBounds(19, 10, 356, 80);

      info = new Label(driveMode, SWT.WRAP);
      info.setBounds(3, 15, 340, 60);

      info.setText("To connect to the brick, select the communication method "
          + "and click 'Connect'.  You can save your preference by clicking "
          + "the 'Save' button so you do not need to come back to this screen "
          + "in the future.");

      connectionGrp = new Group(this, SWT.NONE);
      connectionGrp.setText("Connection satus:");
      connectionGrp.setBounds(19, 100, 356, 50);

      connectionInfo = new Label(connectionGrp, SWT.WRAP);
      connectionInfo.setBounds(13, 20, 340, 25);

      if (NXTManager.getInstance().isConnected()) {
        connectionInfo.setText("Connected using "
            + NXTManager.getInstance().getConnectionType().toString());
      } else {
        connectionInfo.setText("Not Connected..!");
      }
      buttonUtil.setAccessibleString(connectionInfo, "Connection status:");

      buttonUtil.setAccessibleString(info,
          "To connect to the brick, select the "
              + "communication method and click 'Connect'. You can save your "
              + "preference by clicking the 'Save' button so you do not "
              + "need to come back to this screen in the future.");

      rightMotor = new Group(this, SWT.NONE);
      rightMotor.setText("Connection Type");
      rightMotor.setBounds(19, 160, 356, 74);

      usb = new Button(rightMotor, SWT.RADIO | SWT.LEFT);
      usb.setText("USB");
      usb.setBounds(12, 26, 65, 30);
      buttonUtil.setAccessibleString(usb, "Brick Type List");

      usb.addListener(SWT.Selection, new Listener() {

        public void handleEvent(Event event) {
          ct = ConnectionType.USB;
          System.out.println("USB selected");
        }
      });

      bluetooth = new Button(rightMotor, SWT.RADIO | SWT.LEFT);
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

      save = new Button(this, SWT.PUSH | SWT.CENTER);
      save.setText("Save");
      save.setBounds(157, 250, 60, 30);
      buttonUtil.setAccessibleString(save, "Save");

      save.addListener(SWT.Selection, new Listener() {

        public void handleEvent(Event event) {
          FBFIO.saveCT(ct);
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

      connect = new Button(this, SWT.PUSH | SWT.CENTER);
      connect.setText("Connect");
      connect.setBounds(74, 250, 60, 30);
      buttonUtil.setAccessibleString(connect, "Connect");

      connect.addListener(SWT.Selection, new Listener() {

        public void handleEvent(Event event) {
          AbstractNXTBrick nxtManager = NXTManager.getInstance();
          nxtManager.connect(ct);
          boolean isConnected = nxtManager.isConnected();
         
          if (!isConnected) {
            String status = "Connection attempted using " + ct + " but failed!";
            connectionInfo.setText(status);

            Toolkit.getDefaultToolkit().beep();
            buttonUtil.setAccessibleString(connectionInfo, status);
          } else {
            nxtManager.playTone(1000, 300);
            getParent().dispose();
          }
          nxtManager.notifyAllObservers(isConnected);
        }
      });

      if (ct == ConnectionType.BLUETOOTH) {
        bluetooth.setSelection(true);
      } else {
        usb.setSelection(true);
      }

      FormLayout thisLayout = new FormLayout();
      this.layout();
      pack();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
