package com.jbricx.ui.findbrick;

/*
 * @author Michael Goldstein
 * @author Priya Sankaran
 * 
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JComponent;
import javax.swing.JOptionPane;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

import com.jbricx.communications.NXTManager;
import com.jbricx.communications.NXT.ConnectionType;
import com.jbricx.communications.exceptions.AlreadyConnectedException;
import com.jbricx.communications.exceptions.NXTNotFoundException;
import com.jbricx.communications.exceptions.UnableToCreateNXTException;
import com.jbricx.ui.JBrickButtonUtil;

@SuppressWarnings("unused")
public class FindBrickComposite extends org.eclipse.swt.widgets.Composite {
	private FindBrickFileIO FBFIO;
	private ConnectionType ct;
	private Group rightMotor;
	private Group leftMotor;
	private Button cancel;
	//private Button leJOS;
	//private Button other;
	//private Button pbForth;
	private Button bluetooth;
	private Button usb;
	//private Button ok;
	private Button connect;
	private Button save;
	//private Button cancel;
	//private List brickType;
	//private List portList;
	private Label info;
	private StyledText searchBrick;
	private Group driveMode;
	
	JBrickButtonUtil buttonUtil = new JBrickButtonUtil();

	/**
	* Auto-generated main method to display this 
	* org.eclipse.swt.widgets.Composite inside a new Shell.
	*/
	public static void main(String[] args) {
		showGUI();
	}
	
	/**
	* Overriding checkSubclass allows this class to extend org.eclipse.swt.widgets.Composite
	*/	
	protected void checkSubclass() {
	}
	
	/**
	* Auto-generated method to display this 
	* org.eclipse.swt.widgets.Composite inside a new Shell.
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
		if(size.x == 0 && size.y == 0) {
			inst.pack();
			shell.pack();
		} else {
			Rectangle shellBounds = shell.computeTrim(0, 0, size.x, size.y);
			shell.setSize(shellBounds.width+20, shellBounds.height+20);
		}
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}		
	}

	public FindBrickComposite(org.eclipse.swt.widgets.Composite parent, int style) {
		super(parent, style);
		FBFIO = new FindBrickFileIO();
		initGUI();
	}

	private void initGUI() {
		try {
			{
				driveMode = new Group(this, SWT.NONE);
				driveMode.setText("Information");
				driveMode.setBounds(19, 35, 356, 80);
				{
					info=new Label(driveMode, SWT.WRAP);
					info.setBounds(3, 15, 340, 60);
					
					info.setText("To connect to the brick, select the communication method and click 'Connect'.  " +
							"You can save your preference by clicking the 'Save' button so you do not need to come back to this screen in the future.");
					buttonUtil.setAccessibleString(info, "To connect to the brick, select the communication method and click 'Connect'.  " +
							"You can save your preference by clicking the 'Save' button so you do not need to come back to this screen in the future.");
					//	brickType = new List(driveMode, SWT.NONE);
				//	brickType.setBounds(17, 28, 117, 24);
					//buttonUtil.setAccessibleString(brickType, "Brick Type List");

				//	brickType.addListener(SWT.Selection, new Listener() {
					//	public void handleEvent(Event event) {
					//		System.out.println("Selection: " + event.button);
					//		System.out.println("Brick Type list selected");
					//	}

					//});
				}
			}
			{
				//leftMotor = new Group(this, SWT.NONE);
				//leftMotor.setText("Port");
				//leftMotor.setBounds(25, 51, 159, 74);
				{
				//	portList = new List(leftMotor, SWT.NONE);
				//	portList.setBounds(17, 28, 121, 24);
					//buttonUtil.setAccessibleString(brickType, "Brick Type List");

				//	portList.addListener(SWT.Selection, new Listener() {
				//		public void handleEvent(Event event) {
				//			System.out.println("Portlist selected");
				//		}

				//	});
				}
			}
			{
				rightMotor = new Group(this, SWT.NONE);
				rightMotor.setText("Connection Type");
				rightMotor.setBounds(19, 145, 356, 74);
				{
					usb = new Button(rightMotor, SWT.RADIO | SWT.LEFT);
					usb.setText("USB");
					usb.setBounds(12, 26, 65, 30);
					buttonUtil.setAccessibleString(usb, "Brick Type List");
					
					usb.addListener(SWT.Selection, new Listener() {
						public void handleEvent(Event event) {
							ct=ConnectionType.USB;
							System.out.println("USB selected");

							
						}

					});
				}
				{
					bluetooth = new Button(rightMotor, SWT.RADIO | SWT.LEFT);
					bluetooth.setText("Bluetooth");
					bluetooth.setSize(60, 30);
					bluetooth.setBounds(89, 26, 69, 30);
					buttonUtil.setAccessibleString(bluetooth, "Bluetooth");

					bluetooth.addListener(SWT.Selection, new Listener() {
						public void handleEvent(Event event) {
							ct=ConnectionType.BLUETOOTH;
							System.out.println("Bluetooth radio Button selected");
							
						}

					});
				}
		/*		{
					pbForth = new Button(rightMotor, SWT.RADIO | SWT.LEFT);
					pbForth.setText("pbForth");
					pbForth.setBounds(162, 26, 60, 30);
					buttonUtil.setAccessibleString(pbForth, "pbForth");

					pbForth.addListener(SWT.Selection, new Listener() {
						public void handleEvent(Event event) {
							System.out.println("pbForth radio Button selected");
						}

					});
				}
				{
					leJOS = new Button(rightMotor, SWT.RADIO | SWT.LEFT);
					leJOS.setText("leJOS");
					leJOS.setBounds(234, 26, 60, 30);
					buttonUtil.setAccessibleString(leJOS, "leJOS");

					leJOS.addListener(SWT.Selection, new Listener() {
						public void handleEvent(Event event) {
							System.out.println("leJOS radio Button selected");
						}

					});
				}
				{
					other = new Button(rightMotor, SWT.RADIO | SWT.LEFT);
					other.setText("Other");
					other.setBounds(294, 26, 54, 30);
					buttonUtil.setAccessibleString(other, "Other");

					other.addListener(SWT.Selection, new Listener() {
						public void handleEvent(Event event) {
							System.out.println("Other radio Button selected");
						}

					});
				} */
			}
			{
				
			}
			{
				save = new Button(this, SWT.PUSH | SWT.CENTER);
				save.setText("Save");
				save.setBounds(157, 231, 60, 30);
				buttonUtil.setAccessibleString(save, "Save");

				save.addListener(SWT.Selection, new Listener() {
					public void handleEvent(Event event) {
						System.out.println("Save Button selected");
						
						FBFIO.saveCT(ct);
					}

				});
			}
			{
				cancel = new Button(this, SWT.PUSH | SWT.CENTER);
				cancel.setText("Quit");
				cancel.setSize(60, 30);
				cancel.setBounds(237, 231, 60, 30);
				buttonUtil.setAccessibleString(cancel, "Cancel");
				cancel.setEnabled(false);
				cancel.addListener(SWT.Selection, new Listener() {
					public void handleEvent(Event event) {
						System.out.println("Cancel Button selected");
						/*
						 * dispose works when only launching this gui, but does not work whne launching everything else
						 */
						
					}

				});
			}
			{
				connect = new Button(this, SWT.PUSH | SWT.CENTER);
				connect.setText("Connect");
				connect.setBounds(74, 231, 60, 30);
				buttonUtil.setAccessibleString(connect, "Connect");

				connect.addListener(SWT.Selection, new Listener() {

					
					public void handleEvent(Event event) {
						System.out.println("Attempting To Connect");
						try {
							//rightMotor.
							if(bluetooth.getSelection()){
								System.out.println("BT");
								NXTManager.connect("brick1", ConnectionType.BLUETOOTH);
								
							}else{
								System.out.println("USB");
								NXTManager.connect("brick1", ConnectionType.USB);
							}
							
						} catch (AlreadyConnectedException e) {
							JOptionPane.showMessageDialog(null, "Already Connected to the brick...");
							e.printStackTrace();
						} catch (NXTNotFoundException e) {
							JOptionPane.showMessageDialog(null, "No bricks found...");
							e.printStackTrace();
						} catch (UnableToCreateNXTException e) {
							JOptionPane.showMessageDialog(null, "Unable to create NXT...");
							e.printStackTrace();
						}
					}

				});
			}
			
			
			ct=FindBrickFileIO.getCT();
			
			if (ct==ConnectionType.BLUETOOTH){
				System.out.println("Read BT from file");
				bluetooth.setSelection(true);
			}else{
				System.out.println("Read ~BT from file");
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
