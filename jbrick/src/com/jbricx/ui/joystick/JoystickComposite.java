package com.jbricx.ui.joystick;

import java.io.FileInputStream;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Scale;
import org.eclipse.swt.widgets.Shell;

/**
 * This code was edited or generated using CloudGarden's Jigloo SWT/Swing GUI
 * Builder, which is free for non-commercial use. If Jigloo is being used
 * commercially (ie, by a corporation, company or business for any purpose
 * whatever) then you should purchase a license for each developer using Jigloo.
 * Please visit www.cloudgarden.com for details. Use of Jigloo implies
 * acceptance of these licensing terms. A COMMERCIAL LICENSE HAS NOT BEEN
 * PURCHASED FOR THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED LEGALLY FOR
 * ANY CORPORATE OR COMMERCIAL PURPOSE.
 */
public class JoystickComposite extends org.eclipse.swt.widgets.Composite {

	private Group Movement;
	private Button UpLeftArrow;
	private Button button4;
	private Button button3;
	private Button button2;
	private Button button1;
	private Group rightMotor;
	private Button reversed;
	private Button c;
	private Button b;
	private Button a;
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
	private Button Up;

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
				Movement.setBounds(14, 10, 115, 181);
				{
					UpLeftArrow = new Button(Movement, SWT.PUSH | SWT.CENTER);
					UpLeftArrow.setImage(new Image(null, new FileInputStream(
							"src/images/UpLeft.png")));
					UpLeftArrow.setBounds(18, 36, 26, 26);
				}
				{
					Up = new Button(Movement, SWT.PUSH | SWT.CENTER);
					Up.setImage(new Image(null, new FileInputStream(
							"src/images/Up.png")));
					Up.setBounds(42, 36, 27, 26);
				}
				{
					UpLeft = new Button(Movement, SWT.PUSH | SWT.CENTER);
					UpLeft.setImage(new Image(null, new FileInputStream(
							"src/images/UpRight.png")));
					UpLeft.setBounds(67, 37, 27, 26);
				}
				{
					Left = new Button(Movement, SWT.PUSH | SWT.CENTER);
					Left.setImage(new Image(null, new FileInputStream(
							"src/images/left.png")));
					Left.setBounds(18, 59, 24, 30);
				}
				{
					centreStop = new Button(Movement, SWT.PUSH | SWT.CENTER);
					centreStop.setImage(new Image(null, new FileInputStream(
							"src/images/centreStop.png")));
					centreStop.setBounds(41, 62, 27, 26);
				}
				{
					right = new Button(Movement, SWT.PUSH | SWT.CENTER);
					right.setImage(new Image(null, new FileInputStream(
							"src/images/right.png")));
					right.setBounds(66, 59, 28, 30);
				}
				{
					downLeft = new Button(Movement, SWT.PUSH | SWT.CENTER);
					downLeft.setImage(new Image(null, new FileInputStream(
							"src/images/downLeft.png")));
					downLeft.setBounds(18, 84, 24, 28);
				}
				{
					down = new Button(Movement, SWT.PUSH | SWT.CENTER);
					down.setImage(new Image(null, new FileInputStream(
							"src/images/down.png")));
					down.setBounds(43, 87, 25, 24);
				}
				{
					downRight = new Button(Movement, SWT.PUSH | SWT.CENTER);
					downRight.setImage(new Image(null, new FileInputStream(
							"src/images/downRight.png")));
					downRight.setBounds(68, 86, 25, 26);
				}
				{
					T1 = new Button(Movement, SWT.PUSH | SWT.CENTER);
					T1.setImage(new Image(null, new FileInputStream(
							"src/images/T1.png")));
					T1.setBounds(30, 125, 24, 24);
				}
				{
					T2 = new Button(Movement, SWT.PUSH | SWT.CENTER);
					T2.setImage(new Image(null, new FileInputStream(
							"src/images/T2.png")));
					T2.setBounds(55, 124, 25, 24);
				}
			}
			{
				driveMode = new Group(this, SWT.NONE);
				driveMode.setText("Drive Mode");
				driveMode.setBounds(14, 176, 115, 84);
				{
					leftRight = new Button(driveMode, SWT.RADIO | SWT.LEFT);
					leftRight.setText("Left-Right");
					leftRight.setBounds(12, 21, 73, 30);
				}
				{
					driveSteer = new Button(driveMode, SWT.RADIO | SWT.LEFT);
					driveSteer.setText("Drive-Steer");
					driveSteer.setBounds(12, 47, 80, 30);
				}
			}
			{
				leftMotor = new Group(this, SWT.NONE);
				leftMotor.setText("Left Motor");
				leftMotor.setBounds(14, 272, 115, 78);
				{
					a = new Button(leftMotor, SWT.RADIO | SWT.LEFT);
					a.setText("A");
					a.setBounds(12, 22, 30, 24);
				}
				{
					b = new Button(leftMotor, SWT.RADIO | SWT.LEFT);
					b.setText("B");
					b.setBounds(47, 22, 29, 24);
				}
				{
					c = new Button(leftMotor, SWT.RADIO | SWT.LEFT);
					c.setText("C");
					c.setBounds(81, 22, 26, 24);
				}
				{
					reversed = new Button(leftMotor, SWT.CHECK | SWT.LEFT);
					reversed.setText("Reversed");
					reversed.setBounds(12, 48, 80, 20);
				}
			}
			{
				rightMotor = new Group(this, SWT.NONE);
				rightMotor.setText("Right Motor");
				rightMotor.setBounds(14, 362, 115, 74);
				{
					button1 = new Button(rightMotor, SWT.RADIO | SWT.LEFT);
					button1.setText("A");
					button1.setBounds(12, 20, 27, 23);
				}
				{
					button2 = new Button(rightMotor, SWT.RADIO | SWT.LEFT);
					button2.setText("B");
					button2.setBounds(48, 20, 26, 23);
				}
				{
					button3 = new Button(rightMotor, SWT.RADIO | SWT.LEFT);
					button3.setText("C");
					button3.setBounds(82, 19, 24, 23);
				}
				{
					button4 = new Button(rightMotor, SWT.CHECK | SWT.LEFT);
					button4.setText("Reversed");
					button4.setBounds(12, 45, 70, 24);
				}
			}
			{
				speed = new Group(this, SWT.NONE);
				speed.setText("Speed");
				speed.setBounds(14, 448, 115, 59);
				{
					speedBar = new Scale(speed, SWT.NONE);
					speedBar.setBounds(12, 15, 85, 33);
				}
			}
			{
				help = new Button(this, SWT.PUSH | SWT.CENTER);
				help.setText("HELP");
				help.setBounds(38, 517, 60, 25);
			}
			FormLayout thisLayout = new FormLayout();

			this.layout();
			pack();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
