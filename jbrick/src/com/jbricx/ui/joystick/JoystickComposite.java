package com.jbricx.ui.joystick;

import java.io.FileInputStream;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Scale;
import org.eclipse.swt.widgets.Shell;

import com.jbricx.ui.JBrickButtonUtil;


public class JoystickComposite extends org.eclipse.swt.widgets.Composite {

	private Group Movement;
	private Button rightMotor_A;
	private Button rightMotor_B;
	private Button rightMotor_C;
	private Button rightMotor_Reversed;
	private Group rightMotor;
	private Button leftMotor_Reversed;
	private Button leftMotor_C;
	private Button leftMotor_B;
	private Button leftMotor_A;
	private Group leftMotor;
	private Button driveSteer;
	private Scale speedBar;
	private Button help;
	private Group speed;
	private Button leftRight;
	private Group driveMode;
	private Button T2;
	private Button T1;
	private Button downRight;
	private Button down;
	private Button downLeft;
	private Button right;
	private Button centreStop;
	private Button Left;
	private Button UpLeft;
	private Button UpRight;
	private Button Up;
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
		Display display = Display.getDefault();
		Shell shell = new Shell(display);
		JoystickComposite inst = new JoystickComposite(shell, SWT.NULL);
		Point size = inst.getSize();
		shell.setLayout(new FillLayout());
		shell.layout();
		if (size.x == 0 && size.y == 0) {
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

	public JoystickComposite(org.eclipse.swt.widgets.Composite parent, int style) {
		super(parent, style);
		initGUI();
	}

	private void initGUI() {
		try {
			{
				Movement = new Group(this, SWT.NONE);
				Movement.setText("Movement");
				Movement.setBounds(14, 10, 115, 154);
				{
					UpLeft = new Button(Movement, SWT.PUSH | SWT.CENTER);
					UpLeft.setImage(new Image(null, new FileInputStream(
							"src/images/UpLeft.png")));

					UpLeft.setBounds(18, 36, 26, 26);
					buttonUtil.setAccessibleString(UpLeft, "Up Left Arrow");

					UpLeft.addListener(SWT.Selection, new Listener() {
						public void handleEvent(Event event) {
							System.out.println("up left arrow selected");
						}

					});
				}
				{
					Up = new Button(Movement, SWT.PUSH | SWT.CENTER);
					Up.setImage(new Image(null, new FileInputStream(
							"src/images/Up.png")));
					Up.setBounds(42, 36, 27, 26);
					buttonUtil.setAccessibleString(Up, "Up Arrow");

					Up.addListener(SWT.Selection, new Listener() {
						public void handleEvent(Event event) {
							System.out.println("up arrow selected");
						}

					});
				}
				{
					UpRight = new Button(Movement, SWT.PUSH | SWT.CENTER);
					UpRight.setImage(new Image(null, new FileInputStream(
							"src/images/UpRight.png")));
					UpRight.setBounds(67, 37, 27, 26);
					buttonUtil.setAccessibleString(UpRight, "Up Right Arrow");
					UpRight.addListener(SWT.Selection, new Listener() {
						public void handleEvent(Event event) {
							System.out.println("up right arrow selected");
						}

					});

				}
				{
					Left = new Button(Movement, SWT.PUSH | SWT.CENTER);
					Left.setImage(new Image(null, new FileInputStream(
							"src/images/left.png")));
					Left.setBounds(18, 59, 24, 30);
					buttonUtil.setAccessibleString(Left, "Left Arrow");
					Left.addListener(SWT.Selection, new Listener() {
						public void handleEvent(Event event) {
							System.out.println("left arrow selected");
						}

					});
				}
				{
					centreStop = new Button(Movement, SWT.PUSH | SWT.CENTER);
					centreStop.setImage(new Image(null, new FileInputStream(
							"src/images/centreStop.png")));
					centreStop.setBounds(41, 62, 27, 26);
					buttonUtil.setAccessibleString(centreStop, "Stop");
					centreStop.addListener(SWT.Selection, new Listener() {
						public void handleEvent(Event event) {
							System.out.println("centre stop button selected");
						}

					});
				}
				{
					right = new Button(Movement, SWT.PUSH | SWT.CENTER);
					right.setImage(new Image(null, new FileInputStream(
							"src/images/right.png")));
					right.setBounds(66, 59, 28, 30);
					buttonUtil.setAccessibleString(right, "Right Arrow");
					right.addListener(SWT.Selection, new Listener() {
						public void handleEvent(Event event) {
							System.out.println("right arrow selected");
						}

					});
				}
				{
					downLeft = new Button(Movement, SWT.PUSH | SWT.CENTER);
					downLeft.setImage(new Image(null, new FileInputStream(
							"src/images/downLeft.png")));
					downLeft.setBounds(18, 84, 24, 28);
					buttonUtil.setAccessibleString(downLeft, "Down Left Arrow");
					downLeft.addListener(SWT.Selection, new Listener() {
						public void handleEvent(Event event) {
							System.out.println("down left arrow selected");
						}

					});
				}
				{
					down = new Button(Movement, SWT.PUSH | SWT.CENTER);
					down.setImage(new Image(null, new FileInputStream(
							"src/images/down.png")));
					down.setBounds(43, 87, 25, 24);
					buttonUtil.setAccessibleString(down, "Down Arrow");
					down.addListener(SWT.Selection, new Listener() {
						public void handleEvent(Event event) {
							System.out.println("down arrow selected");
						}

					});
				}
				{
					downRight = new Button(Movement, SWT.PUSH | SWT.CENTER);
					downRight.setImage(new Image(null, new FileInputStream(
							"src/images/downRight.png")));
					downRight.setBounds(68, 86, 25, 26);
					buttonUtil.setAccessibleString(downRight,
							"Down Right Arrow");
					downRight.addListener(SWT.Selection, new Listener() {
						public void handleEvent(Event event) {
							System.out.println("down right arrow selected");
						}

					});
				}
				{
					T1 = new Button(Movement, SWT.PUSH | SWT.CENTER);
					T1.setImage(new Image(null, new FileInputStream(
							"src/images/T1.png")));
					T1.setBounds(30, 125, 24, 24);
					buttonUtil.setAccessibleString(T1, "T1");
					T1.addListener(SWT.Selection, new Listener() {
						public void handleEvent(Event event) {
							System.out.println("T1 selected");
						}

					});
				}
				{
					T2 = new Button(Movement, SWT.PUSH | SWT.CENTER);
					T2.setImage(new Image(null, new FileInputStream(
							"src/images/T2.png")));
					T2.setBounds(55, 124, 25, 24);
					buttonUtil.setAccessibleString(T2, "T2");
					T2.addListener(SWT.Selection, new Listener() {
						public void handleEvent(Event event) {
							System.out.println("T2 selected");
						}

					});
				}
			}
			{
				driveMode = new Group(this, SWT.NONE);
				driveMode.setText("Drive Mode");
				driveMode.setBounds(141, 46, 115, 84);
				{
					leftRight = new Button(driveMode, SWT.RADIO | SWT.LEFT);
					leftRight.setText("Left-Right");
					leftRight.setBounds(12, 21, 73, 30);
					buttonUtil.setAccessibleString(leftRight, "Left Right Drive Mode");
					leftRight.addListener(SWT.Selection, new Listener() {
						public void handleEvent(Event event) {
							System.out.println("left right selected");
						}

					});
				}
				{
					driveSteer = new Button(driveMode, SWT.RADIO | SWT.LEFT);
					driveSteer.setText("Drive-Steer");
					driveSteer.setBounds(12, 47, 80, 30);
					buttonUtil.setAccessibleString(driveSteer, "Drive Steer");
					driveSteer.addListener(SWT.Selection, new Listener() {
						public void handleEvent(Event event) {
							System.out.println("drive steer selected");
						}

					});
				}
			}
			{
				leftMotor = new Group(this, SWT.NONE);
				leftMotor.setText("Left Motor");
				leftMotor.setBounds(12, 176, 115, 74);
				{
					leftMotor_A = new Button(leftMotor, SWT.RADIO | SWT.LEFT);
					leftMotor_A.setText("A");
					leftMotor_A.setBounds(12, 22, 30, 24);
					buttonUtil.setAccessibleString(leftMotor_A, "Left Motor A");
					leftMotor_A.addListener(SWT.Selection, new Listener() {
						public void handleEvent(Event event) {
							System.out.println("left motor A selected");
						}

					});
				}
				{
					leftMotor_B = new Button(leftMotor, SWT.RADIO | SWT.LEFT);
					leftMotor_B.setText("B");
					leftMotor_B.setBounds(47, 22, 29, 24);
					buttonUtil.setAccessibleString(leftMotor_B, "Left Motor B");
					leftMotor_B.addListener(SWT.Selection, new Listener() {
						public void handleEvent(Event event) {
							System.out.println("left motor B selected");
						}

					});
				}
				{
					leftMotor_C = new Button(leftMotor, SWT.RADIO | SWT.LEFT);
					leftMotor_C.setText("C");
					leftMotor_C.setBounds(81, 22, 26, 24);
					buttonUtil.setAccessibleString(leftMotor_C, "Left Motor C");
					leftMotor_C.addListener(SWT.Selection, new Listener() {
						public void handleEvent(Event event) {
							System.out.println("left motor C selected");
						}

					});
				}
				{
					leftMotor_Reversed = new Button(leftMotor, SWT.CHECK
							| SWT.LEFT);
					leftMotor_Reversed.setText("Reversed");
					leftMotor_Reversed.setBounds(12, 48, 80, 20);
					buttonUtil.setAccessibleString(leftMotor_Reversed, "Left Motor Reversed");
					leftMotor_Reversed.addListener(SWT.Selection, new Listener() {
						public void handleEvent(Event event) {
							System.out.println("Left motor reversed");
						}

					});
				}
			}
			{
				rightMotor = new Group(this, SWT.NONE);
				rightMotor.setText("Right Motor");
				rightMotor.setBounds(142, 176, 115, 74);
				{
					rightMotor_A = new Button(rightMotor, SWT.RADIO | SWT.LEFT);
					rightMotor_A.setText("A");
					rightMotor_A.setBounds(12, 20, 27, 23);
					buttonUtil.setAccessibleString(rightMotor_A, "Right Motor A");
					rightMotor_A.addListener(SWT.Selection, new Listener() {
						public void handleEvent(Event event) {
							System.out.println("Right Motor A selected");
						}

					});
				}
				{
					rightMotor_B = new Button(rightMotor, SWT.RADIO | SWT.LEFT);
					rightMotor_B.setText("B");
					rightMotor_B.setBounds(48, 20, 26, 23);
					buttonUtil.setAccessibleString(rightMotor_B, "Right Motor B");
					rightMotor_B.addListener(SWT.Selection, new Listener() {
						public void handleEvent(Event event) {
							System.out.println("Right Motor B selected");
						}

					});
				}
				{
					rightMotor_C = new Button(rightMotor, SWT.RADIO | SWT.LEFT);
					rightMotor_C.setText("C");
					rightMotor_C.setBounds(82, 19, 24, 23);
					buttonUtil.setAccessibleString(rightMotor_C, "Right Motor C");
					rightMotor_C.addListener(SWT.Selection, new Listener() {
						public void handleEvent(Event event) {
							System.out.println("right motor C selected");
						}

					});
				}
				{
					rightMotor_Reversed = new Button(rightMotor, SWT.CHECK
							| SWT.LEFT);
					rightMotor_Reversed.setText("Reversed");
					rightMotor_Reversed.setBounds(12, 45, 70, 24);
					buttonUtil.setAccessibleString(rightMotor_Reversed, "Right Motor Reversed");
					rightMotor_Reversed.addListener(SWT.Selection, new Listener() {
						public void handleEvent(Event event) {
							System.out.println("Right Motor reversed");
						}

					});
				}
			}
			{
				speed = new Group(this, SWT.NONE);
				speed.setText("Speed");
				speed.setBounds(83, 266, 115, 59);
				{
					speedBar = new Scale(speed, SWT.NONE);
					speedBar.setBounds(12, 15, 85, 33);
					//buttonUtil.setAccessibleString(speedBar, "Speed Bar");
					speedBar.addListener(SWT.Selection, new Listener() {
						public void handleEvent(Event event) {
							System.out.println("Speed scale selected");
						}

					});
				}
			}
			{
				help = new Button(this, SWT.PUSH | SWT.CENTER);
				help.setText("HELP");
				help.setBounds(116, 337, 60, 25);
				buttonUtil.setAccessibleString(help, "Help");
				help.addListener(SWT.Selection, new Listener() {
					public void handleEvent(Event event) {
						System.out.println("Help button selected");
					}

				});
			}
			// FormLayout thisLayout = new FormLayout();

			this.layout();
			pack();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
