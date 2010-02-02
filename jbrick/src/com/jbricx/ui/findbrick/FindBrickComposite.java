package com.jbricx.ui.findbrick;

import java.io.FileInputStream;

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
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

import com.jbricx.ui.JBrickButtonUtil;

@SuppressWarnings("unused")
public class FindBrickComposite extends org.eclipse.swt.widgets.Composite {

	private Group rightMotor;
	private Group leftMotor;
	private Button cancel;
	private Button leJOS;
	private Button other;
	private Button pbForth;
	private Button brickOS;
	private Button standard;
	private Button ok;
	private Button help;
	private List brickType;
	private List portList;
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
		Display display = Display.getDefault();
		Shell shell = new Shell(display);
		FindBrickComposite inst = new FindBrickComposite(shell, SWT.NULL);
		Point size = inst.getSize();
		shell.setLayout(new FillLayout());
		shell.layout();
		if(size.x == 0 && size.y == 0) {
			inst.pack();
			shell.pack();
		} else {
			Rectangle shellBounds = shell.computeTrim(0, 0, size.x, size.y);
			shell.setSize(shellBounds.width, shellBounds.height);
		}
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
	}

	public FindBrickComposite(org.eclipse.swt.widgets.Composite parent, int style) {
		super(parent, style);
		initGUI();
	}

	private void initGUI() {
		try {
			{
				driveMode = new Group(this, SWT.NONE);
				driveMode.setText("Brick Type");
				driveMode.setBounds(217, 51, 153, 73);
				{
					brickType = new List(driveMode, SWT.NONE);
					brickType.setBounds(17, 28, 117, 24);
					//buttonUtil.setAccessibleString(brickType, "Brick Type List");

					brickType.addListener(SWT.Selection, new Listener() {
						public void handleEvent(Event event) {
							System.out.println("Selection: " + event.button);
							System.out.println("Brick Type list selected");
						}

					});
				}
			}
			{
				leftMotor = new Group(this, SWT.NONE);
				leftMotor.setText("Port");
				leftMotor.setBounds(25, 51, 159, 74);
				{
					portList = new List(leftMotor, SWT.NONE);
					portList.setBounds(17, 28, 121, 24);
					//buttonUtil.setAccessibleString(brickType, "Brick Type List");

					portList.addListener(SWT.Selection, new Listener() {
						public void handleEvent(Event event) {
							System.out.println("Portlist selected");
						}

					});
				}
			}
			{
				rightMotor = new Group(this, SWT.NONE);
				rightMotor.setText("Firmware");
				rightMotor.setBounds(19, 145, 356, 74);
				{
					standard = new Button(rightMotor, SWT.RADIO | SWT.LEFT);
					standard.setText("Standard");
					standard.setBounds(12, 26, 65, 30);
					buttonUtil.setAccessibleString(standard, "Brick Type List");

					standard.addListener(SWT.Selection, new Listener() {
						public void handleEvent(Event event) {
							System.out.println("Standard radio Button selected");
						}

					});
				}
				{
					brickOS = new Button(rightMotor, SWT.RADIO | SWT.LEFT);
					brickOS.setText("brickOS");
					brickOS.setSize(60, 30);
					brickOS.setBounds(89, 26, 60, 30);
					buttonUtil.setAccessibleString(brickOS, "BrickOS");

					brickOS.addListener(SWT.Selection, new Listener() {
						public void handleEvent(Event event) {
							System.out.println("brickOS radio Button selected");
						}

					});
				}
				{
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
				}
			}
			{
				
			}
			{
				cancel = new Button(this, SWT.PUSH | SWT.CENTER);
				cancel.setText("Cancel");
				cancel.setBounds(157, 231, 60, 30);
				buttonUtil.setAccessibleString(cancel, "Cancel");

				cancel.addListener(SWT.Selection, new Listener() {
					public void handleEvent(Event event) {
						System.out.println("Cancel Button selected");
					}

				});
			}
			{
				help = new Button(this, SWT.PUSH | SWT.CENTER);
				help.setText("Help");
				help.setSize(60, 30);
				help.setBounds(237, 231, 60, 30);
				buttonUtil.setAccessibleString(help, "Help");

				help.addListener(SWT.Selection, new Listener() {
					public void handleEvent(Event event) {
						System.out.println("Help Button selected");
					}

				});
			}
			{
				ok = new Button(this, SWT.PUSH | SWT.CENTER);
				ok.setText("Ok");
				ok.setBounds(74, 231, 60, 30);
				buttonUtil.setAccessibleString(ok, "OK");

				ok.addListener(SWT.Selection, new Listener() {
					public void handleEvent(Event event) {
						System.out.println("OK Button selected");
					}

				});
			}
			FormLayout thisLayout = new FormLayout();
			
			this.layout();
			pack();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
