package com.jbricx.ui.DirectControl;

import java.io.FileInputStream;
import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Scale;
import org.eclipse.swt.widgets.Shell;

import com.jbricx.communications.NXTGadgetManager;
import com.jbricx.communications.NXTManager;
import com.jbricx.communications.enums.Motor;
import com.jbricx.communications.enums.Sensor;
import com.jbricx.communications.enums.SensorMode;
import com.jbricx.communications.enums.SensorType;
import com.jbricx.ui.JBrickButtonUtil;

/**
 * @author Yuji Fujiki
 */
public class DirectControlWindow extends org.eclipse.swt.widgets.Composite {

  
  
  private int speedA = 60;
  private int speedB = 60;
  private int speedC = 60;
  private Group grpMotors = new Group(this, SWT.NONE);
  private ControllerButton btnFwdA;
  private ControllerButton btnBwdA;
  private ControllerButton btnFloatA;
  private ControllerButton btnStopA;
  private final Scale sclSpeedA = new Scale(grpMotors, SWT.NONE);
  private ControllerButton btnFwdB;
  private ControllerButton btnBwdB;
  private ControllerButton btnFloatB;
  private ControllerButton btnStopB;
  private final Scale sclSpeedB = new Scale(grpMotors, SWT.NONE);
  private ControllerButton btnFwdC;
  private ControllerButton btnBwdC;
  private ControllerButton btnFloatC;
  private ControllerButton btnStopC;
  private final Scale sclSpeedC = new Scale(grpMotors, SWT.NONE);

  
  JBrickButtonUtil buttonUtil = new JBrickButtonUtil();
  private ArrayList<Button> allButtons = new ArrayList<Button>();
  private ArrayList<Scale> allScale = new ArrayList<Scale>();
  private static NXTGadgetManager nxt = NXTManager.getInstance();
//  final Runnable timer = new Runnable() {
//
//    public void run() {
//      System.out.println("-");
//      btnGetWidgetSelected(null);
//      Display.getDefault().timerExec(500, this);
//      Display.getDefault().timerExec(500, this);
//    }
//  };

  /**
   * Auto-generated main method to display this
   * org.eclipse.swt.widgets.Composite inside a new Shell.
   */
//  public static void main(String[] args) {
//    showGUI();
//  }

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
  static Display display = Display.getDefault();

  public static void showGUI() {
    Shell shell = new Shell(display);
    DirectControlWindow inst = new DirectControlWindow(shell, SWT.NULL);
//    Point size = inst.getSize();
    shell.setLayout(new FillLayout());
    shell.layout();
//    if (size.x == 0 && size.y == 0) {
//      inst.pack();
//      shell.pack();
//    } else {
//      Rectangle shellBounds = shell.computeTrim(0, 0, size.x, size.y);
//      shell.setSize(shellBounds.width, shellBounds.height);
//    }

    shell.setSize(284, 200);
    shell.open();
    while (!shell.isDisposed()) {
      if (!display.readAndDispatch()) {
        display.sleep();
      }
    }
  }

  public DirectControlWindow(org.eclipse.swt.widgets.Composite parent, int style) {
    super(parent, style);
    initGUI();
  }

  private void initGUI() {
	  grpMotors.setText("Motors");
	  grpMotors.setBounds(10, 10, 250, 135);
	  
	  // Direct controllers for Motor A
	  {
		  Label lblMotorA = new Label(grpMotors, SWT.LEFT);
		  lblMotorA.setImage(new Image(display, "icons/16x16/directControl/a.png"));
		  lblMotorA.setBounds(9, 27, 16, 16);
		  lblMotorA.setToolTipText("Motor A");
		  
		  btnFwdA = new ControllerButton(grpMotors, new Image(display, "icons/16x16/directControl/fwd.png"), 32, 20, 30, 30, "Forward");
		  btnFwdA.addMouseListener(new MouseListener(){

			@Override
			public void mouseDoubleClick(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			public void mouseDown(MouseEvent arg0) {
				speedA = Math.abs(speedA);
				nxt.motorOn(Motor.MOTOR_A, speedA);
				btnBwdA.setSelection(false);
				btnFloatA.setSelection(false);
				btnStopA.setSelection(false);
			}

			public void mouseUp(MouseEvent arg0) {
				btnFwdA.setSelection(true);
			}
			  
		  });

		  btnBwdA = new ControllerButton(grpMotors, new Image(display, "icons/16x16/directControl/bwd.png"), 65, 20, 30, 30, "Backward");
		  btnBwdA.addMouseListener(new MouseListener(){

				@Override
				public void mouseDoubleClick(MouseEvent arg0) {
					// TODO Auto-generated method stub
					
				}

				public void mouseDown(MouseEvent arg0) {
					nxt.motorOff(Motor.MOTOR_A);
					speedA = Math.abs(speedA) * (-1);
					nxt.motorOn(Motor.MOTOR_A, speedA);
					btnFwdA.setSelection(false);
					btnFloatA.setSelection(false);
					btnStopA.setSelection(false);
				}

				public void mouseUp(MouseEvent arg0) {
					btnBwdA.setSelection(true);
				}
				  
			  });

		  btnFloatA = new ControllerButton(grpMotors, new Image(display, "icons/16x16/directControl/float.png"), 98, 20, 30, 30, "Float");
		  btnFloatA.addMouseListener(new MouseListener(){

			@Override
			public void mouseDoubleClick(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			public void mouseDown(MouseEvent arg0) {
				nxt.motorOff(Motor.MOTOR_A);
				btnFwdA.setSelection(false);
				btnBwdA.setSelection(false);
				btnStopA.setSelection(false);
			}

			@Override
			public void mouseUp(MouseEvent arg0) {
				btnFloatA.setSelection(true);				
			}
		  });

		  btnStopA = new ControllerButton(grpMotors, new Image(display, "icons/16x16/directControl/stop.png"), 131, 20, 30, 30, "Stop");
		  btnStopA.setSelection(true);
		  btnStopA.addMouseListener(new MouseListener(){			  

			@Override
			public void mouseDoubleClick(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			public void mouseDown(MouseEvent arg0) {
				nxt.motorOn(Motor.MOTOR_A, speedA * 2 *(-1));
				nxt.motorOff(Motor.MOTOR_A);
				btnFwdA.setSelection(false);
				btnBwdA.setSelection(false);
				btnFloatA.setSelection(false);
			}
			
			@Override
			public void mouseUp(MouseEvent arg0) {
				btnStopA.setSelection(true);
			}
			  
		  });
		  
		  sclSpeedA.setBounds(163, 15, 85, 32);
		  sclSpeedA.setMinimum(60);
		  sclSpeedA.setMaximum(130);
		  sclSpeedA.setToolTipText("Speed");
		  sclSpeedA.addSelectionListener(new SelectionListener(){

			public void widgetDefaultSelected(SelectionEvent arg0) {
			}

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				speedA = sclSpeedA.getSelection();	
			}
			  
		  });
	  }

	  // Direct controllers for Motor B
	  {
		  Label lblMotorB = new Label(grpMotors, SWT.LEFT);
		  lblMotorB.setImage(new Image(display, "icons/16x16/directControl/b.png"));
		  lblMotorB.setBounds(9, 65, 16, 16);
		  lblMotorB.setToolTipText("Motor B");
		  
		  btnFwdB = new ControllerButton(grpMotors, new Image(display, "icons/16x16/directControl/fwd.png"), 32, 58, 30, 30, "Forward");
		  btnFwdB.addMouseListener(new MouseListener(){

				@Override
				public void mouseDoubleClick(MouseEvent arg0) {
					// TODO Auto-generated method stub
					
				}

				public void mouseDown(MouseEvent arg0) {
					nxt.motorOff(Motor.MOTOR_B);				
					nxt.motorOn(Motor.MOTOR_B, speedB);
					btnBwdB.setSelection(false);
					btnFloatB.setSelection(false);
					btnStopB.setSelection(false);
				}

				public void mouseUp(MouseEvent arg0) {
					btnFwdB.setSelection(true);
				}
				  
			  });

		  btnBwdB = new ControllerButton(grpMotors, new Image(display, "icons/16x16/directControl/bwd.png"), 65, 58, 30, 30, "Backward");
		  btnBwdB.addMouseListener(new MouseListener(){

			@Override
			public void mouseDoubleClick(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			public void mouseDown(MouseEvent arg0) {
				nxt.motorOff(Motor.MOTOR_B);				
				speedB = Math.abs(speedB) * (-1);
				nxt.motorOn(Motor.MOTOR_B, speedB);
				btnFwdB.setSelection(false);
				btnFloatB.setSelection(false);
				btnStopB.setSelection(false);
			}

			public void mouseUp(MouseEvent arg0) {
				btnBwdB.setSelection(true);
			}
			  
		  });
		  
		  btnFloatB = new ControllerButton(grpMotors, new Image(display, "icons/16x16/directControl/float.png"), 98, 58, 30, 30, "Float");
		  btnFloatB.addMouseListener(new MouseListener(){

			@Override
			public void mouseDoubleClick(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			public void mouseDown(MouseEvent arg0) {
				nxt.motorOff(Motor.MOTOR_B);				
				btnFwdB.setSelection(false);
				btnBwdB.setSelection(false);
				btnStopB.setSelection(false);
			}

			public void mouseUp(MouseEvent arg0) {
				btnFloatB.setSelection(true);
			}
			  
		  });

		  btnStopB = new ControllerButton(grpMotors, new Image(display, "icons/16x16/directControl/stop.png"), 131, 58, 30, 30, "Stop");
		  btnStopB.setSelection(true);
		  btnStopB.addMouseListener(new MouseListener(){

			@Override
			public void mouseDoubleClick(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			public void mouseDown(MouseEvent arg0) {
				nxt.motorOn(Motor.MOTOR_B, speedB * 2 * (-1));
				nxt.motorOff(Motor.MOTOR_B);				
				btnFwdB.setSelection(false);
				btnFloatB.setSelection(false);
				btnBwdB.setSelection(false);
			}

			public void mouseUp(MouseEvent arg0) {
				btnStopB.setSelection(true);
			}
			  
		  });

		  sclSpeedB.setBounds(163, 53, 85, 32);
		  sclSpeedB.setMinimum(60);
		  sclSpeedB.setMaximum(130);
		  sclSpeedB.setToolTipText("Speed");
		  sclSpeedB.addSelectionListener(new SelectionListener(){

			public void widgetDefaultSelected(SelectionEvent arg0) {
			}

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				speedB = sclSpeedB.getSelection();	
			}
			  
		  });
	  }

	  // Direct controllers for Motor B
	  {
		  Label lblMotorC = new Label(grpMotors, SWT.LEFT);
		  lblMotorC.setImage(new Image(display, "icons/16x16/directControl/c.png"));
		  lblMotorC.setBounds(9, 103, 16, 16);
		  lblMotorC.setToolTipText("Motor C");


		  btnFwdC = new ControllerButton(grpMotors, new Image(display, "icons/16x16/directControl/fwd.png"), 32, 95, 30, 30, "Forward");
		  btnFwdC.addMouseListener(new MouseListener(){

			@Override
			public void mouseDoubleClick(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			public void mouseDown(MouseEvent arg0) {
				nxt.motorOff(Motor.MOTOR_C);				
				nxt.motorOn(Motor.MOTOR_C, speedC);
				btnBwdC.setSelection(false);
				btnFloatC.setSelection(false);
				btnStopC.setSelection(false);
			}

			public void mouseUp(MouseEvent arg0) {
				btnFwdC.setSelection(true);
			}
			  
		  });

		  btnBwdC = new ControllerButton(grpMotors, new Image(display, "icons/16x16/directControl/bwd.png"), 65, 95, 30, 30, "Backward");
		  btnBwdC.addMouseListener(new MouseListener(){

			@Override
			public void mouseDoubleClick(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			public void mouseDown(MouseEvent arg0) {
				nxt.motorOff(Motor.MOTOR_C);	
				speedC = Math.abs(speedC) * (-1);
				nxt.motorOn(Motor.MOTOR_C, speedC);
				btnFwdC.setSelection(false);
				btnFloatC.setSelection(false);
				btnStopC.setSelection(false);
			}

			public void mouseUp(MouseEvent arg0) {
				btnBwdC.setSelection(true);
			}
			  
		  });
		  
		  btnFloatC = new ControllerButton(grpMotors, new Image(display, "icons/16x16/directControl/float.png"), 98, 95, 30, 30, "Float");
		  btnFloatC.addMouseListener(new MouseListener(){

			@Override
			public void mouseDoubleClick(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			public void mouseDown(MouseEvent arg0) {
				nxt.motorOff(Motor.MOTOR_C);
				btnFwdC.setSelection(false);
				btnBwdC.setSelection(false);
				btnStopC.setSelection(false);
			}

			public void mouseUp(MouseEvent arg0) {
				btnFloatC.setSelection(true);
			}
			  
		  });

		  btnStopC = new ControllerButton(grpMotors, new Image(display, "icons/16x16/directControl/stop.png"), 131, 95, 30, 30, "Stop");
		  btnStopC.setSelection(true);
		  btnStopC.addMouseListener(new MouseListener(){

			@Override
			public void mouseDoubleClick(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			public void mouseDown(MouseEvent arg0) {
				nxt.motorOn(Motor.MOTOR_C, speedC * 2 * (-1));
				nxt.motorOff(Motor.MOTOR_C);				
				btnFwdC.setSelection(false);
				btnFloatC.setSelection(false);
				btnBwdC.setSelection(false);
			}

			public void mouseUp(MouseEvent arg0) {
				btnStopC.setSelection(true);
			}
			  
		  });

		  sclSpeedC.setBounds(163, 90, 85, 32);
		  sclSpeedC.setMinimum(60);
		  sclSpeedC.setMaximum(130);
		  sclSpeedC.setToolTipText("Speed");
		  sclSpeedC.addSelectionListener(new SelectionListener(){

			public void widgetDefaultSelected(SelectionEvent arg0) {
			}

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				speedC = sclSpeedC.getSelection();	
			}
			  
		  });
	  }
  }
  
//  public void moveFwd(Motor motor, int speed){
//		nxt.motorOn(motor, speed);
//  }
}
