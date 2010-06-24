package com.jbricx.ui.joystick;

/*
 * @author Priya Sankaran
 */

import java.io.FileInputStream;
import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
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

import com.jbricx.communications.AbstractNXTBrick;
import com.jbricx.communications.NXTManager;
import com.jbricx.communications.NXT.ConnectionType;
import com.jbricx.communications.NXT.Motor;
import com.jbricx.communications.exceptions.AlreadyConnectedException;
import com.jbricx.communications.exceptions.NXTNotFoundException;
import com.jbricx.communications.exceptions.UnableToCreateNXTException;
import com.jbricx.ui.JBrickButtonUtil;
import com.jbricx.ui.findbrick.FindBrickFileIO;



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
	public static Scale speedBar;
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
	
	ArrayList<Button> buttonArray = new ArrayList<Button>();

	/**
	 * Auto-generated main method to display this
	 * org.eclipse.swt.widgets.Composite inside a new Shell.
	 */
	
	static Thread thread;
	
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
	
	static Display display;
	
	public static void showGUI() {
		
		
		
		display = Display.getDefault();
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
		
		
		connectNXT();
		connectJoypad();
		
		thread = new Thread(pollController);
		thread.start();
		Thread t2= new Thread(backupBeep);
		t2.start();
		
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		thread.stop();	
		t2.stop();
	}

	Shell shell;
	
	public JoystickComposite(org.eclipse.swt.widgets.Composite parent, int style) {
		super(parent, style);
		
		display = Display.getDefault();
		shell = new Shell(display);
		
		initGUI();
		
		//getShell()
		
//		while (!shell.isDisposed()) {
//			if (!display.readAndDispatch())
//				display.sleep();
//		}
//		thread.stop();	
		
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
	
					UpLeft.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent evt) {
							virtualJoypad = 0;
						}
					});
				}
				{
					Up = new Button(Movement, SWT.PUSH | SWT.CENTER);
					Up.setImage(new Image(null, new FileInputStream(
							"src/images/Up.png")));
					Up.setBounds(42, 36, 27, 26);
					buttonUtil.setAccessibleString(Up, "Up Arrow");

					Up.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent evt) {
							virtualJoypad = 1;
						}
					});
				}
				{
					UpRight = new Button(Movement, SWT.PUSH | SWT.CENTER);
					UpRight.setImage(new Image(null, new FileInputStream(
							"src/images/UpRight.png")));
					UpRight.setBounds(67, 37, 27, 26);
					buttonUtil.setAccessibleString(UpRight, "Up Right Arrow");
					UpRight.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent evt) {
							virtualJoypad = 2;
						}
					});
				}
				{
					Left = new Button(Movement, SWT.PUSH | SWT.CENTER);
					Left.setImage(new Image(null, new FileInputStream(
							"src/images/left.png")));
					Left.setBounds(18, 59, 24, 30);
					buttonUtil.setAccessibleString(Left, "Left Arrow");
					Left.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent evt) {
							virtualJoypad = 3;
						}
					});
				}
				{
					centreStop = new Button(Movement, SWT.PUSH | SWT.CENTER);
					centreStop.setImage(new Image(null, new FileInputStream(
							"src/images/centreStop.png")));
					centreStop.setBounds(41, 62, 27, 26);
					buttonUtil.setAccessibleString(centreStop, "Stop");
					centreStop.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent evt) {	
							virtualJoypad = 4;
						}
					});
				}
				{
					right = new Button(Movement, SWT.PUSH | SWT.CENTER);
					right.setImage(new Image(null, new FileInputStream(
							"src/images/right.png")));
					right.setBounds(66, 59, 28, 30);
					buttonUtil.setAccessibleString(right, "Right Arrow");
					right.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent evt) {
							virtualJoypad = 5;
						}
					});
				}
				{
					downLeft = new Button(Movement, SWT.PUSH | SWT.CENTER);
					downLeft.setImage(new Image(null, new FileInputStream(
							"src/images/downLeft.png")));
					downLeft.setBounds(18, 84, 24, 28);
					buttonUtil.setAccessibleString(downLeft, "Down Left Arrow");
					downLeft.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent evt) {
							virtualJoypad = 6;
						}
					});
				}
				{
					down = new Button(Movement, SWT.PUSH | SWT.CENTER);
					down.setImage(new Image(null, new FileInputStream(
							"src/images/down.png")));
					down.setBounds(43, 87, 25, 24);
					buttonUtil.setAccessibleString(down, "Down Arrow");
					down.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent evt) {
							virtualJoypad = 7;
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
					downRight.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent evt) {
							virtualJoypad = 8;
						}
					});
				}
//				{
//					T1 = new Button(Movement, SWT.PUSH | SWT.CENTER);
//					T1.setImage(new Image(null, new FileInputStream(
//							"src/images/T1.png")));
//					T1.setBounds(30, 125, 24, 24);
//					buttonUtil.setAccessibleString(T1, "T1");
//					T1.addSelectionListener(new SelectionAdapter() {
//						public void widgetSelected(SelectionEvent evt) {
//							
//						}
//					});
//				}
//				{
//					T2 = new Button(Movement, SWT.PUSH | SWT.CENTER);
//					T2.setImage(new Image(null, new FileInputStream(
//							"src/images/T2.png")));
//					T2.setBounds(55, 124, 25, 24);
//					buttonUtil.setAccessibleString(T2, "T2");
//					T2.addSelectionListener(new SelectionAdapter() {
//						public void widgetSelected(SelectionEvent evt) {
//							
//						}
//					});
//				}
			}
			{
				driveMode = new Group(this, SWT.NONE);
				driveMode.setText("Drive Mode");
				driveMode.setBounds(141, 46, 115, 84);
				{
					leftRight = new Button(driveMode, SWT.RADIO | SWT.LEFT);
					leftRight.setText("Left-Right");
					leftRight.setBounds(12, 21, 73, 30);
					buttonUtil.setAccessibleString(leftRight,
							"Left Right Drive Mode");
					leftRight.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent evt) {
							
						}
					});
				}
				{
					driveSteer = new Button(driveMode, SWT.RADIO | SWT.LEFT);
					driveSteer.setText("Drive-Steer");
					driveSteer.setBounds(12, 47, 80, 30);
					buttonUtil.setAccessibleString(driveSteer, "Drive Steer");
					driveSteer.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent evt) {
							
						}
					});
				}
				driveSteer.setEnabled(false);
				leftRight.setSelection(true);
			}
			{
				leftMotor = new Group(this, SWT.NONE);
				leftMotor.setText("Left Motor");
				leftMotor.setBounds(12, 176, 115, 74);
				{
					leftMotor_A = new Button(leftMotor, SWT.RADIO | SWT.LEFT);
					leftMotor_A.setText("A");
					leftMotor_A.setBounds(12, 22, 30, 24);
					leftMotor_A.setSelection(true);
					buttonUtil.setAccessibleString(leftMotor_A, "Left Motor A");
					leftMotor_A.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent evt) {
							Motor_1_ID = Motor.MOTOR_A;
						}
					});
				}
				{
					leftMotor_B = new Button(leftMotor, SWT.RADIO | SWT.LEFT);
					leftMotor_B.setText("B");
					leftMotor_B.setBounds(47, 22, 29, 24);
					buttonUtil.setAccessibleString(leftMotor_B, "Left Motor B");
					leftMotor_B.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent evt) {
							Motor_1_ID = Motor.MOTOR_B;
						}
					});
				}
				{
					leftMotor_C = new Button(leftMotor, SWT.RADIO | SWT.LEFT);
					leftMotor_C.setText("C");
					leftMotor_C.setBounds(81, 22, 26, 24);
					buttonUtil.setAccessibleString(leftMotor_C, "Left Motor C");
					leftMotor_C.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent evt) {
							Motor_1_ID = Motor.MOTOR_C;
						}
					});
				}
				{
					leftMotor_Reversed = new Button(leftMotor, SWT.CHECK
							| SWT.LEFT);
					leftMotor_Reversed.setText("Reversed");
					leftMotor_Reversed.setBounds(12, 48, 80, 20);
					buttonUtil.setAccessibleString(leftMotor_Reversed,
							"Left Motor Reversed");
					leftMotor_Reversed.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent evt) {
							if(leftMotor_Reversed.getSelection()){
								Motor_1_DIR = -1;
							}else{
								Motor_1_DIR = 1;
							}
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
					buttonUtil.setAccessibleString(rightMotor_A,
							"Right Motor A");
					rightMotor_A.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent evt) {
							Motor_2_ID = Motor.MOTOR_A;
						}
					});
				}
				{
					rightMotor_B = new Button(rightMotor, SWT.RADIO | SWT.LEFT);
					rightMotor_B.setText("B");
					rightMotor_B.setBounds(48, 20, 26, 23);
					rightMotor_B.setSelection(true);
					buttonUtil.setAccessibleString(rightMotor_B,
							"Right Motor B");
					rightMotor_B.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent evt) {
							Motor_2_ID = Motor.MOTOR_B;
						}
					});
				}
				{
					rightMotor_C = new Button(rightMotor, SWT.RADIO | SWT.LEFT);
					rightMotor_C.setText("C");
					rightMotor_C.setBounds(82, 19, 24, 23);
					buttonUtil.setAccessibleString(rightMotor_C,
							"Right Motor C");
					rightMotor_C.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent evt) {
							Motor_2_ID = Motor.MOTOR_C;
						}
					});
				}
				{
					rightMotor_Reversed = new Button(rightMotor, SWT.CHECK
							| SWT.LEFT);
					rightMotor_Reversed.setText("Reversed");
					rightMotor_Reversed.setBounds(12, 45, 70, 24);
					buttonUtil.setAccessibleString(rightMotor_Reversed,
							"Right Motor Reversed");
					rightMotor_Reversed.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent evt) {
							if(rightMotor_Reversed.getSelection()){
								Motor_2_DIR = -1;
							}else{
								Motor_2_DIR = 1;
							}
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
					speedBar.setMinimum(60);
					speedBar.setMaximum(100);
					speedBar.setBounds(12, 15, 85, 33);
					// buttonUtil.setAccessibleString(speedBar, "Speed Bar");
					speedBar.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent evt) {
							System.out.println("Speed adjusted: " + speedBar.getSelection());
							motorSpeed = speedBar.getSelection();
						}
					});
				}
			}
			{
				help = new Button(this, SWT.PUSH | SWT.CENTER);
				help.setText("HELP");
				help.setBounds(116, 337, 60, 25);
				buttonUtil.setAccessibleString(help, "Help");
				help.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent evt) {
						
					}
				});
			}
			
			buttonArray.add(UpLeft);
			buttonArray.add(Up);
			buttonArray.add(UpRight);
			buttonArray.add(centreStop);
			buttonArray.add(Left);
			buttonArray.add(right);
			buttonArray.add(down);
			buttonArray.add(downRight);
			buttonArray.add(downLeft);
			buttonArray.add(T1);
			buttonArray.add(T2);
			buttonArray.add(help);
			
			
			FormLayout thisLayout = new FormLayout();

			this.layout();
			pack();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	private static void connectNXT(){
		
		String brickname = "brick2";
		try {
			nxt = NXTManager.connect(brickname, FindBrickFileIO.getCT());
			nxt.playTone(2000, 300);
			System.out.println("Joystick: Brick Connected!");
			nxt.playTone(3000, 300);
		} catch (AlreadyConnectedException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println("Joystick already Connected");
			try {
				nxt = NXTManager.getBrick(brickname);
				nxt.playTone(2000, 300);
				System.out.println("Joystick: Brick Connected!");
			} catch (NXTNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				System.out.println("Could not find brick");
			}
		} catch (NXTNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnableToCreateNXTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	private static void connectJoypad(){
		try{
			gpc = new GamePadController();
		}catch(Exception e){
			gpc = null;
		}
	}
	
	
	static GamePadController gpc;
	static AbstractNXTBrick nxt;
	
	
	
	static Motor Motor_1_ID = Motor.MOTOR_A;
	static Motor Motor_2_ID = Motor.MOTOR_C;
	static int Motor_1_DIR = 1;
	static int Motor_2_DIR = 1;
	
	static int motorSpeed = 60;
	
	
	static int virtualJoypad = 4;
	static int lastVirtualJoypadValue = 4;
	static int lastGPCValue = 4;
	
	
	static boolean useGUI = false;
	

	private static Runnable backupBeep = new Runnable() {
			public void run() {
				boolean playSound = false;
				int curDir;
				while (true) {
					playSound = !playSound;
					curDir = gpc.getXYStickDir();
					if (((curDir == 7) || (curDir == 8) || (curDir == 6))
							&& playSound) {
						nxt.playTone(1500, 600);
					}

					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
					}
				}
			}
		};
	
private static Runnable pollController = new Runnable(){
		
		@Override
		public void run() {
			
			//int motorSpeed = 100;
			int rotateSubtractor = 15;
			int spinSubtractor = 25;
			
			//int rotateSpeed = 70;
			
			
			
			while(true){
				
//				System.out.println("XY Direction: " + gpc.getXYStickDir() + " Button: " + gpc.isButtonPressed(1));
				//Directions 
				//middle = 4
				//up = 1
				//right = 5
				//down = 7
				//left = 3
				//UL = 0
				//UR = 2
				//DL = 6
				//DR = 8
				
				if(gpc != null){
					if(!gpc.poll()){
						gpc = null;
					}else{
						if(gpc.getXYStickDir() != lastGPCValue){
							useGUI = false;
							lastGPCValue = gpc.getXYStickDir();
						}
					}
				}else{
					useGUI= true;
				}
				
				
				if(virtualJoypad != lastVirtualJoypadValue){
					useGUI = true;
					lastVirtualJoypadValue = virtualJoypad;
				}
				
			
				
				
				int joypadValue;
				
				if (useGUI){
					joypadValue = lastVirtualJoypadValue;
				}else{
					joypadValue = lastGPCValue;
				}
			
				
				
				
				if(joypadValue == 1){
					//up
					
					nxt.motorOn(Motor_1_ID, motorSpeed * Motor_1_DIR);
					nxt.motorOn(Motor_2_ID, motorSpeed * Motor_2_DIR);
				}
				
				if(joypadValue == 0){
					//ul
					nxt.motorOn(Motor_1_ID, motorSpeed * Motor_1_DIR);
					nxt.motorOn(Motor_2_ID, (motorSpeed-rotateSubtractor) * Motor_2_DIR);
				}
				
				if(joypadValue == 2){
					//ur
					nxt.motorOn(Motor_1_ID, (motorSpeed-rotateSubtractor) * Motor_1_DIR);
					nxt.motorOn(Motor_2_ID, motorSpeed * Motor_2_DIR);
				}
				
				if(joypadValue == 7){
					//down
					nxt.motorOn(Motor_1_ID, -1 * motorSpeed * Motor_1_DIR);
					nxt.motorOn(Motor_2_ID, -1 * motorSpeed * Motor_2_DIR);
				}
				
				if(joypadValue == 3){
					//left
					nxt.motorOn(Motor_1_ID, 1 * (motorSpeed-spinSubtractor) * Motor_1_DIR);
					nxt.motorOn(Motor_2_ID, -1 * (motorSpeed-spinSubtractor) * Motor_2_DIR);
				}
				
				if(joypadValue == 5){
					//right
					nxt.motorOn(Motor_1_ID, -1 * (motorSpeed-spinSubtractor) * Motor_1_DIR);
					nxt.motorOn(Motor_2_ID, 1 * (motorSpeed-spinSubtractor) * Motor_2_DIR);
				}
				
				if(joypadValue == 6){
					//ll
					nxt.motorOn(Motor_1_ID, -1 * motorSpeed * Motor_1_DIR);
					nxt.motorOn(Motor_2_ID, -1 * (motorSpeed-rotateSubtractor) * Motor_2_DIR);
				}
				
				if(joypadValue == 8){
					//lr
					nxt.motorOn(Motor_1_ID, -1 * (motorSpeed-rotateSubtractor) * Motor_1_DIR);
					nxt.motorOn(Motor_2_ID, -1 * motorSpeed * Motor_2_DIR);
				}
				
				if(joypadValue == 4){
					//idle
					nxt.motorOff(Motor_1_ID);
					nxt.motorOff(Motor_2_ID);
				}
				
				if(gpc != null){
					
					if(gpc.isButtonPressed(1)){
						System.out.println("button 1 pressed");
						nxt.playTone(3000, 200);
					}
					
					if(gpc.isButtonPressed(2)){
						System.out.println("button 2 pressed");
						nxt.playTone(2000, 200);
					}
					
					//Speed up
					if(gpc.isButtonPressed(4)){
						motorSpeed+=10;
						if (motorSpeed>100){
							motorSpeed=100;
						}
						display.syncExec(
								  new Runnable() {
								    public void run(){
								    	speedBar.setSelection(motorSpeed);
								    }
								  });
						System.out.println(motorSpeed);
					}
					
					//Slow Down
					if(gpc.isButtonPressed(3)){
						motorSpeed-=10;
						if (motorSpeed<60){
							motorSpeed=60;
						}
						display.syncExec(
								  new Runnable() {
								    public void run(){
								    	speedBar.setSelection(motorSpeed);
								    }
								  });
						System.out.println(motorSpeed);
					}

				}
				
				//poll every 200ms
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {}
			}
		}
		
	};
}
