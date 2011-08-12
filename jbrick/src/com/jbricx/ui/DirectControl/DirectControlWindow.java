package com.jbricx.ui.DirectControl;

import java.awt.Color;
import java.io.FileInputStream;
import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
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

  private int speedA, speedB, speedC = 60;
  Group grpMotors = new Group(this, SWT.NONE);
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
//				nxt.motorOff(Motor.MOTOR_A);				
//				nxt.motorOn(Motor.MOTOR_A, speedA);
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
//					nxt.motorOff(Motor.MOTOR_A);				
//					nxt.motorOn(Motor.MOTOR_A, speedA * (-1));
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
//				nxt.motorOff(Motor.MOTOR_A);
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
//				nxt.motorOn(Motor.MOTOR_A, speedA * (-1));
//				nxt.motorOff(Motor.MOTOR_A);
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
//				nxt.motorOff(Motor.MOTOR_B);				
//				nxt.motorOn(Motor.MOTOR_B, speedB);
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
//				nxt.motorOff(Motor.MOTOR_B);				
//				nxt.motorOn(Motor.MOTOR_B, speedB);
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
//				nxt.motorOff(Motor.MOTOR_B);				
//				nxt.motorOn(Motor.MOTOR_B, speedB);
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
//				nxt.motorOff(Motor.MOTOR_B);				
//				nxt.motorOn(Motor.MOTOR_B, speedB);
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
//				nxt.motorOff(Motor.MOTOR_C);				
//				nxt.motorOn(Motor.MOTOR_C, speedC);
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
//				nxt.motorOff(Motor.MOTOR_C);				
//				nxt.motorOn(Motor.MOTOR_C, speedC);
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
//				nxt.motorOff(Motor.MOTOR_C);				
//				nxt.motorOn(Motor.MOTOR_C, speedC);
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
//				nxt.motorOff(Motor.MOTOR_C);				
//				nxt.motorOn(Motor.MOTOR_C, speedC);
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

	  
//    try {
//      FormLayout thisLayout = new FormLayout();
//      this.setLayout(thisLayout);
//      Group sensorsGroup = new Group(this, SWT.SHADOW_ETCHED_IN);
//      sensorsGroup.setBounds(10, 10, 200, 300);
//      {
//        btnGet = new Button(this, SWT.PUSH | SWT.CENTER);
//        FormData btnGetLData = new FormData();
//        btnGetLData.left = new FormAttachment(0, 1000, 260);
//        btnGetLData.top = new FormAttachment(0, 1000, 172);
//        btnGetLData.width = 71;
//        btnGetLData.height = 28;
//        btnGet.setLayoutData(btnGetLData);
//        btnGet.setText("Get");
//        buttonUtil.setAccessibleString(btnGet, "Get");
//        btnGet.addMouseListener(new MouseListener(){
//
//			@Override
//			public void mouseDoubleClick(MouseEvent arg0) {
//				// TODO Auto-generated method stub
//				
//			}
//
//			public void mouseDown(MouseEvent arg0) {
//				nxt.motorOn(motorLeft, 70);
//				nxt.motorOn(motorRight, 70);
//			}
//
//			public void mouseUp(MouseEvent arg0) {
//				nxt.motorOff(motorLeft);
//				nxt.motorOff(motorRight);				
//			}
//        	
//        });
//      }
//      {
//        poll = new Button(this, SWT.CHECK | SWT.CENTER);
//        FormData btnPoll = new FormData();
//        btnPoll.left = new FormAttachment(0, 1000, 340);
//        btnPoll.top = new FormAttachment(0, 1000, 172);
//        btnPoll.width = 40;
//        btnPoll.height = 28;
//        poll.setLayoutData(btnPoll);
//        poll.setText("Poll");
//        // buttonUtil.setAccessibleString(poll, "Poll");
//        poll.addSelectionListener(new SelectionAdapter() {
//
//          public void widgetSelected(SelectionEvent evt) {
//            pollSelected();
//          }
//        });
//      }
//      {
//        FormData scale1LData = new FormData();
//        scale1LData.left = new FormAttachment(0, 1000, 82);
//        scale1LData.top = new FormAttachment(0, 1000, 199);
//        scale1LData.width = 300;
//        scale1LData.height = 42;
//        scaleA = new Scale(sensorsGroup, SWT.NONE);
//        scaleA.setLayoutData(scale1LData);
//        scaleA.setMinimum(0);
//        scaleA.setMaximum(100);
//        scaleA.setSelection(50);
//        scaleA.addSelectionListener(new SelectionListener() {
//
//          @Override
//          public void widgetSelected(SelectionEvent arg0) {
//            updateSpeed(Motor.MOTOR_A, fixScale(scaleA.getSelection()));
//          }
//
//          @Override
//          public void widgetDefaultSelected(SelectionEvent arg0) {
//          }
//        });
//
//      }
//      {
//        cmbSensor1 = new Combo(sensorsGroup, SWT.NONE);
//        FormData combo1LData = new FormData();
//        combo1LData.left = new FormAttachment(0, 1000, 32);
//        combo1LData.top = new FormAttachment(0, 1000, 26);
//        combo1LData.width = 75;
//        combo1LData.height = 26;
//        cmbSensor1.setLayoutData(combo1LData);
//        for (SensorType t : SensorType.values()) {
//          cmbSensor1.add(t.getName());
//        }
//        cmbSensor1.setText(cmbSensor1.getItem(0));
//        cmbSensor1.addSelectionListener(new SelectionListener() {
//
//          @Override
//          public void widgetSelected(SelectionEvent arg0) {
//            updateMode(1, cmbSensor1.getText());
//          }
//
//          @Override
//          public void widgetDefaultSelected(SelectionEvent arg0) {
//          }
//        });
//
//      }
//      {
//        cmbSensor2 = new Combo(sensorsGroup, SWT.NONE);
//        FormData combo2LData = new FormData();
//        combo2LData.left = new FormAttachment(0, 1000, 32);
//        combo2LData.top = new FormAttachment(0, 1000, 64);
//        combo2LData.width = 75;
//        combo2LData.height = 26;
//        cmbSensor2.setLayoutData(combo2LData);
//        for (SensorType t : SensorType.values()) {
//          cmbSensor2.add(t.getName());
//        }
//        cmbSensor2.setText(cmbSensor2.getItem(0));
//        cmbSensor2.addSelectionListener(new SelectionListener() {
//
//          @Override
//          public void widgetSelected(SelectionEvent arg0) {
//            updateMode(2, cmbSensor2.getText());
//          }
//
//          @Override
//          public void widgetDefaultSelected(SelectionEvent arg0) {
//          }
//        });
//      }
//      {
//        cmbSensor3 = new Combo(sensorsGroup, SWT.NONE);
//        FormData combo3LData = new FormData();
//        combo3LData.left = new FormAttachment(0, 1000, 32);
//        combo3LData.top = new FormAttachment(0, 1000, 102);
//        combo3LData.width = 75;
//        combo3LData.height = 26;
//        cmbSensor3.setLayoutData(combo3LData);
//        for (SensorType t : SensorType.values()) {
//          cmbSensor3.add(t.getName());
//        }
//        cmbSensor3.setText(cmbSensor3.getItem(0));
//        cmbSensor3.addSelectionListener(new SelectionListener() {
//
//          @Override
//          public void widgetSelected(SelectionEvent arg0) {
//            updateMode(3, cmbSensor3.getText());
//          }
//
//          @Override
//          public void widgetDefaultSelected(SelectionEvent arg0) {
//          }
//        });
//      }
//      {
//        cmbSensor4 = new Combo(sensorsGroup, SWT.NONE);
//        FormData combo4LData = new FormData();
//        combo4LData.left = new FormAttachment(0, 1000, 32);
//        combo4LData.top = new FormAttachment(0, 1000, 140);
//        combo4LData.width = 75;
//        combo4LData.height = 26;
//        cmbSensor4.setLayoutData(combo4LData);
//        for (SensorType t : SensorType.values()) {
//          cmbSensor4.add(t.getName());
//        }
//        cmbSensor4.setText(cmbSensor4.getItem(0));
//        cmbSensor4.addSelectionListener(new SelectionListener() {
//
//          @Override
//          public void widgetSelected(SelectionEvent arg0) {
//            updateMode(4, cmbSensor4.getText());
//          }
//
//          @Override
//          public void widgetDefaultSelected(SelectionEvent arg0) {
//          }
//        });
//      }
//      {
//        cmbSensorType1 = new Combo(sensorsGroup, SWT.NONE);
//        FormData combo5LData = new FormData();
//        combo5LData.left = new FormAttachment(0, 1000, 145);
//        combo5LData.top = new FormAttachment(0, 1000, 26);
//        combo5LData.width = 80;
//        combo5LData.height = 26;
//        cmbSensorType1.setLayoutData(combo5LData);
//        for (SensorMode m : SensorMode.values()) {
//          cmbSensorType1.add(m.getName());
//        }
//        cmbSensorType1.setText(cmbSensorType1.getItem(0));
//      }
//      {
//        cmbSensorType2 = new Combo(sensorsGroup, SWT.NONE);
//        FormData combo6LData = new FormData();
//        combo6LData.left = new FormAttachment(0, 1000, 145);
//        combo6LData.top = new FormAttachment(0, 1000, 64);
//        combo6LData.width = 80;
//        combo6LData.height = 26;
//        cmbSensorType2.setLayoutData(combo6LData);
//        for (SensorMode m : SensorMode.values()) {
//          cmbSensorType2.add(m.getName());
//        }
//        cmbSensorType2.setText(cmbSensorType2.getItem(0));
//      }
//      {
//        cmbSensorType3 = new Combo(sensorsGroup, SWT.NONE);
//        FormData combo7LData = new FormData();
//        combo7LData.left = new FormAttachment(0, 1000, 145);
//        combo7LData.top = new FormAttachment(0, 1000, 102);
//        combo7LData.width = 80;
//        combo7LData.height = 26;
//        cmbSensorType3.setLayoutData(combo7LData);
//        for (SensorMode m : SensorMode.values()) {
//          cmbSensorType3.add(m.getName());
//        }
//        cmbSensorType3.setText(cmbSensorType3.getItem(0));
//      }
//      {
//        cmbSensorType4 = new Combo(sensorsGroup, SWT.NONE);
//        FormData combo8LData = new FormData();
//        combo8LData.left = new FormAttachment(0, 1000, 145);
//        combo8LData.top = new FormAttachment(0, 1000, 140);
//        combo8LData.width = 80;
//        combo8LData.height = 26;
//        cmbSensorType4.setLayoutData(combo8LData);
//        for (SensorMode m : SensorMode.values()) {
//          cmbSensorType4.add(m.getName());
//        }
//        cmbSensorType4.setText(cmbSensorType4.getItem(0));
//      }
//      // {
//      // btnARight = new Button(this, SWT.PUSH | SWT.CENTER);
//      // FormData button1LData = new FormData();
//      // button1LData.left = new FormAttachment(0, 1000, 32);
//      // button1LData.top = new FormAttachment(0, 1000, 204);
//      // button1LData.width = 40;
//      // button1LData.height = 31;
//      // btnARight.setLayoutData(button1LData);
//      // buttonUtil.setAccessibleString(btnARight, "button A right arrow");
//      // Image image = new Image(null, new
//      // FileInputStream("src/images/RightArrow.png")) ;
//      // Image scaledImage = new Image(display,
//      // image.getImageData().scaledTo(35, 25)) ;
//      // btnARight.setImage(scaledImage) ;
//      // btnARight.addSelectionListener(new SelectionAdapter() {
//      // public void widgetSelected(SelectionEvent evt) {
//      // button1WidgetSelected(evt);
//      // }
//      // });
//      // }
//      // {
//      // btnALeft = new Button(this, SWT.PUSH | SWT.CENTER);
//      // FormData button2LData1 = new FormData();
//      // button2LData1.left = new FormAttachment(0, 1000, 82);
//      // button2LData1.top = new FormAttachment(0, 1000, 204);
//      // button2LData1.width = 40;
//      // button2LData1.height = 31;
//      // btnALeft.setLayoutData(button2LData1);
//      // buttonUtil.setAccessibleString(btnALeft, "button A left arrow");
//      // Image image = new Image(null, new
//      // FileInputStream("src/images/LeftArrow.png")) ;
//      // Image scaledImage = new Image(display,
//      // image.getImageData().scaledTo(35, 25)) ;
//      // btnALeft.setImage(scaledImage);
//      // btnALeft.addSelectionListener(new SelectionAdapter() {
//      // public void widgetSelected(SelectionEvent evt) {
//      // button1WidgetSelected(evt) ;
//      // }
//      // });
//      // }
//      {
//        btnARed = new Button(this, SWT.PUSH | SWT.CENTER);
//        FormData button3LData = new FormData();
//        button3LData.left = new FormAttachment(0, 1000, 32);
//        button3LData.top = new FormAttachment(0, 1000, 204);
//        button3LData.width = 40;
//        button3LData.height = 31;
//        btnARed.setLayoutData(button3LData);
//        buttonUtil.setAccessibleString(btnARed, "button A red");
//        Image image = new Image(null, new FileInputStream(
//            "src/images/RedBox.png"));
//        Image scaledImage = new Image(display, image.getImageData().scaledTo(
//            35, 25));
//        btnARed.setImage(scaledImage);
//        btnARed.addSelectionListener(new SelectionAdapter() {
//
//          public void widgetSelected(SelectionEvent evt) {
//            nxt.motorOff(Motor.MOTOR_A);
//            scaleA.setSelection(50);
//          }
//        });
//      }
//      // {
//      // btnAYellow = new Button(this, SWT.PUSH | SWT.CENTER);
//      // FormData button4LData = new FormData();
//      // button4LData.left = new FormAttachment(0, 1000, 184);
//      // button4LData.top = new FormAttachment(0, 1000, 204);
//      // button4LData.width = 40;
//      // button4LData.height = 31;
//      // btnAYellow.setLayoutData(button4LData);
//      // buttonUtil.setAccessibleString(btnAYellow, "button A yellow");
//      // Image image = new Image(null, new
//      // FileInputStream("src/images/YellowBox.png")) ;
//      // Image scaledImage = new Image(display,
//      // image.getImageData().scaledTo(35, 25)) ;
//      // btnAYellow.setImage(scaledImage);
//      // btnAYellow.addSelectionListener(new SelectionAdapter() {
//      // public void widgetSelected(SelectionEvent evt) {
//      // button1WidgetSelected(evt);
//      // }
//      // });
//      // }
//      // {
//      // btnBRight = new Button(this, SWT.PUSH | SWT.CENTER);
//      // FormData button5LData = new FormData();
//      // button5LData.left = new FormAttachment(0, 1000, 32);
//      // button5LData.top = new FormAttachment(0, 1000, 250);
//      // button5LData.width = 40;
//      // button5LData.height = 31;
//      // btnBRight.setLayoutData(button5LData);
//      // buttonUtil.setAccessibleString(btnBRight, "button B right arrow");
//      // Image image = new Image(null, new
//      // FileInputStream("src/images/RightArrow.png")) ;
//      // Image scaledImage = new Image(display,
//      // image.getImageData().scaledTo(35, 25)) ;
//      // btnBRight.setImage(scaledImage) ;
//      // btnBRight.addSelectionListener(new SelectionAdapter() {
//      // public void widgetSelected(SelectionEvent evt) {
//      // button1WidgetSelected(evt);
//      // }
//      // });
//      // }
//      // {
//      // btnBLeft = new Button(this, SWT.PUSH | SWT.CENTER);
//      // FormData button6LData = new FormData();
//      // button6LData.left = new FormAttachment(0, 1000, 82);
//      // button6LData.top = new FormAttachment(0, 1000, 250);
//      // button6LData.width = 40;
//      // button6LData.height = 31;
//      // btnBLeft.setLayoutData(button6LData);
//      // buttonUtil.setAccessibleString(btnBLeft, "button B left arrow");
//      // Image image = new Image(null, new
//      // FileInputStream("src/images/LeftArrow.png")) ;
//      // Image scaledImage = new Image(display,
//      // image.getImageData().scaledTo(35, 25)) ;
//      // btnBLeft.setImage(scaledImage);
//      // btnBLeft.addSelectionListener(new SelectionAdapter() {
//      // public void widgetSelected(SelectionEvent evt) {
//      // button1WidgetSelected(evt);
//      // }
//      // });
//      // }
//      {
//        btnBRed = new Button(this, SWT.PUSH | SWT.CENTER);
//        FormData button7LData = new FormData();
//        button7LData.left = new FormAttachment(0, 1000, 32);
//        button7LData.top = new FormAttachment(0, 1000, 250);
//        button7LData.width = 40;
//        button7LData.height = 31;
//        btnBRed.setLayoutData(button7LData);
//        buttonUtil.setAccessibleString(btnBRed, "button B red");
//        Image image = new Image(null, new FileInputStream(
//            "src/images/RedBox.png"));
//        Image scaledImage = new Image(display, image.getImageData().scaledTo(
//            35, 25));
//        btnBRed.setImage(scaledImage);
//        btnBRed.addSelectionListener(new SelectionAdapter() {
//
//          public void widgetSelected(SelectionEvent evt) {
//            nxt.motorOff(Motor.MOTOR_B);
//            scaleB.setSelection(50);
//          }
//        });
//      }
//      // {
//      // btnBYellow = new Button(this, SWT.PUSH | SWT.CENTER);
//      // FormData button8LData = new FormData();
//      // button8LData.left = new FormAttachment(0, 1000, 184);
//      // button8LData.top = new FormAttachment(0, 1000, 250);
//      // button8LData.width = 40;
//      // button8LData.height = 31;
//      // btnBYellow.setLayoutData(button8LData);
//      // buttonUtil.setAccessibleString(btnBYellow, "button B yellow");
//      // Image image = new Image(null, new
//      // FileInputStream("src/images/YellowBox.png")) ;
//      // Image scaledImage = new Image(display,
//      // image.getImageData().scaledTo(35, 25)) ;
//      // btnBYellow.setImage(scaledImage);
//      // btnBYellow.addSelectionListener(new SelectionAdapter() {
//      // public void widgetSelected(SelectionEvent evt) {
//      // button1WidgetSelected(evt);
//      // }
//      // });
//      // }
//      {
//        FormData scale2LData = new FormData();
//        scale2LData.left = new FormAttachment(0, 1000, 82);
//        scale2LData.top = new FormAttachment(0, 1000, 245);
//        scale2LData.width = 300;
//        scale2LData.height = 42;
//        scaleB = new Scale(this, SWT.NONE);
//        scaleB.setLayoutData(scale2LData);
//        scaleB.setMinimum(0);
//        scaleB.setMaximum(100);
//        scaleB.setSelection(50);
//        scaleB.addSelectionListener(new SelectionListener() {
//
//          @Override
//          public void widgetSelected(SelectionEvent arg0) {
//            updateSpeed(Motor.MOTOR_B, fixScale(scaleB.getSelection()));
//          }
//
//          @Override
//          public void widgetDefaultSelected(SelectionEvent arg0) {
//          }
//        });
//      }
//      {
//        FormData scale3LData = new FormData();
//        scale3LData.left = new FormAttachment(0, 1000, 82);
//        scale3LData.top = new FormAttachment(0, 1000, 293);
//        scale3LData.width = 300;
//        scale3LData.height = 42;
//        scaleC = new Scale(this, SWT.NONE);
//        scaleC.setLayoutData(scale3LData);
//        scaleC.setMinimum(0);
//        scaleC.setMaximum(100);
//        scaleC.setSelection(50);
//        scaleC.addSelectionListener(new SelectionListener() {
//
//          @Override
//          public void widgetSelected(SelectionEvent arg0) {
//            updateSpeed(Motor.MOTOR_C, fixScale(scaleC.getSelection()));
//          }
//
//          @Override
//          public void widgetDefaultSelected(SelectionEvent arg0) {
//          }
//        });
//      }
//      // {
//      // btnCRight = new Button(this, SWT.PUSH | SWT.CENTER);
//      // FormData button9LData = new FormData();
//      // button9LData.left = new FormAttachment(0, 1000, 32);
//      // button9LData.top = new FormAttachment(0, 1000, 298);
//      // button9LData.width = 40;
//      // button9LData.height = 31;
//      // btnCRight.setLayoutData(button9LData);
//      // buttonUtil.setAccessibleString(btnCRight, "button C right arrow");
//      // Image image = new Image(null, new
//      // FileInputStream("src/images/RightArrow.png")) ;
//      // Image scaledImage = new Image(display,
//      // image.getImageData().scaledTo(35, 25)) ;
//      // btnCRight.setImage(scaledImage) ;
//      // btnCRight.addSelectionListener(new SelectionAdapter() {
//      // public void widgetSelected(SelectionEvent evt) {
//      // button1WidgetSelected(evt);
//      // }
//      // });
//      // }
//      // {
//      // btnCLeft = new Button(this, SWT.PUSH | SWT.CENTER);
//      // FormData button10LData = new FormData();
//      // button10LData.left = new FormAttachment(0, 1000, 82);
//      // button10LData.top = new FormAttachment(0, 1000, 298);
//      // button10LData.width = 40;
//      // button10LData.height = 31;
//      // btnCLeft.setLayoutData(button10LData);
//      // buttonUtil.setAccessibleString(btnCLeft, "button C left arrow");
//      // Image image = new Image(null, new
//      // FileInputStream("src/images/LeftArrow.png")) ;
//      // Image scaledImage = new Image(display,
//      // image.getImageData().scaledTo(35, 25)) ;
//      // btnCLeft.setImage(scaledImage);
//      // btnCLeft.addSelectionListener(new SelectionAdapter() {
//      // public void widgetSelected(SelectionEvent evt) {
//      // button1WidgetSelected(evt);
//      // }
//      // });
//      // }
//      {
//        btnCRed = new Button(this, SWT.PUSH | SWT.CENTER);
//        FormData button11LData = new FormData();
//        button11LData.left = new FormAttachment(0, 1000, 32);
//        button11LData.top = new FormAttachment(0, 1000, 298);
//        button11LData.width = 40;
//        button11LData.height = 31;
//        btnCRed.setLayoutData(button11LData);
//        buttonUtil.setAccessibleString(btnCRed, "button C red");
//        Image image = new Image(null, new FileInputStream(
//            "src/images/RedBox.png"));
//        Image scaledImage = new Image(display, image.getImageData().scaledTo(
//            35, 25));
//        btnCRed.setImage(scaledImage);
//        btnCRed.addSelectionListener(new SelectionAdapter() {
//
//          public void widgetSelected(SelectionEvent evt) {
//            nxt.motorOff(Motor.MOTOR_C);
//            scaleC.setSelection(50);
//          }
//        });
//      }
//      // {
//      // btnCYellow = new Button(this, SWT.PUSH | SWT.CENTER);
//      // FormData button12LData = new FormData();
//      // button12LData.left = new FormAttachment(0, 1000, 184);
//      // button12LData.top = new FormAttachment(0, 1000, 298);
//      // button12LData.width = 40;
//      // button12LData.height = 31;
//      // btnCYellow.setLayoutData(button12LData);
//      // buttonUtil.setAccessibleString(btnCYellow, "button C yellow");
//      // Image image = new Image(null, new
//      // FileInputStream("src/images/YellowBox.png")) ;
//      // Image scaledImage = new Image(display,
//      // image.getImageData().scaledTo(35, 25)) ;
//      // btnCYellow.setImage(scaledImage);
//      // btnCYellow.addSelectionListener(new SelectionAdapter() {
//      // public void widgetSelected(SelectionEvent evt) {
//      // button1WidgetSelected(evt);
//      // }
//      // });
//      // }
//      {
//        label1 = new Label(this, SWT.NONE);
//        FormData label1LData = new FormData();
//        label1LData.left = new FormAttachment(0, 1000, 12);
//        label1LData.top = new FormAttachment(0, 1000, 26);
//        label1LData.width = 14;
//        label1LData.height = 26;
//        label1.setLayoutData(label1LData);
//        label1.setText("1");
//        label1.setFont(new org.eclipse.swt.graphics.Font(display,
//            "Courier New", 14, SWT.NORMAL));
//      }
//      {
//        label2 = new Label(this, SWT.NONE);
//        FormData label2LData = new FormData();
//        label2LData.left = new FormAttachment(0, 1000, 12);
//        label2LData.top = new FormAttachment(0, 1000, 64);
//        label2LData.width = 14;
//        label2LData.height = 26;
//        label2.setLayoutData(label2LData);
//        label2.setText("2");
//        label2.setFont(new org.eclipse.swt.graphics.Font(display,
//            "Courier New", 14, SWT.NORMAL));
//      }
//      {
//        label3 = new Label(this, SWT.NONE);
//        FormData label3LData = new FormData();
//        label3LData.left = new FormAttachment(0, 1000, 12);
//        label3LData.top = new FormAttachment(0, 1000, 102);
//        label3LData.width = 14;
//        label3LData.height = 26;
//        label3.setLayoutData(label3LData);
//        label3.setText("3");
//        label3.setFont(new org.eclipse.swt.graphics.Font(display,
//            "Courier New", 14, SWT.NORMAL));
//      }
//      {
//        label4 = new Label(this, SWT.NONE);
//        FormData label4LData = new FormData();
//        label4LData.left = new FormAttachment(0, 1000, 12);
//        label4LData.top = new FormAttachment(0, 1000, 140);
//        label4LData.width = 14;
//        label4LData.height = 26;
//        label4.setLayoutData(label4LData);
//        label4.setText("4");
//        label4.setFont(new org.eclipse.swt.graphics.Font(display,
//            "Courier New", 14, SWT.NORMAL));
//      }
//
//      int motorLettersTop = 210;
//      {
//        label5 = new Label(this, SWT.NONE);
//        FormData label5LData = new FormData();
//        label5LData.left = new FormAttachment(0, 1000, 12);
//        label5LData.top = new FormAttachment(0, 1000, motorLettersTop);
//        label5LData.width = 14;
//        label5LData.height = 26;
//        label5.setLayoutData(label5LData);
//        label5.setText("A");
//        label5.setFont(new org.eclipse.swt.graphics.Font(display,
//            "Courier New", 14, SWT.NORMAL));
//      }
//      {
//        label6 = new Label(this, SWT.NONE);
//        FormData label6LData = new FormData();
//        label6LData.left = new FormAttachment(0, 1000, 12);
//        label6LData.top = new FormAttachment(0, 1000, motorLettersTop + 46);
//        label6LData.width = 14;
//        label6LData.height = 26;
//        label6.setLayoutData(label6LData);
//        label6.setText("B");
//        label6.setFont(new org.eclipse.swt.graphics.Font(display,
//            "Courier New", 14, SWT.NORMAL));
//      }
//      {
//        label7 = new Label(this, SWT.NONE);
//        FormData label7LData = new FormData();
//        label7LData.left = new FormAttachment(0, 1000, 12);
//        label7LData.top = new FormAttachment(0, 1000, motorLettersTop
//            + (46 * 2));
//        label7LData.width = 14;
//        label7LData.height = 26;
//        label7.setLayoutData(label7LData);
//        label7.setText("C");
//        label7.setFont(new org.eclipse.swt.graphics.Font(display,
//            "Courier New", 14, SWT.NORMAL));
//      }
//      {
//        label8 = new Label(this, SWT.NONE);
//        FormData label8LData = new FormData();
//        label8LData.left = new FormAttachment(0, 1000, 32);
//        label8LData.top = new FormAttachment(0, 1000, 172);
//        label8LData.width = 90;
//        label8LData.height = 26;
//        label8.setLayoutData(label8LData);
//        label8.setText("Motors");
//        label8.setFont(new org.eclipse.swt.graphics.Font(display,
//            "Courier New", 14, SWT.NORMAL));
//      }
//      {
//        label9 = new Label(this, SWT.NONE);
//        FormData label9LData = new FormData();
//        label9LData.left = new FormAttachment(0, 1000, 32);
//        label9LData.top = new FormAttachment(0, 1000, 0);
//        label9LData.width = 90;
//        label9LData.height = 26;
//        label9.setLayoutData(label9LData);
//        label9.setText("Sensors");
//        label9.setFont(new org.eclipse.swt.graphics.Font(display,
//            "Courier New", 14, SWT.NORMAL));
//      }
//      {
//        lblValue1 = new Label(this, SWT.NONE);
//        FormData label10LData = new FormData();
//        label10LData.left = new FormAttachment(0, 1000, 275);
//        label10LData.top = new FormAttachment(0, 1000, 26);
//        label10LData.width = 83;
//        label10LData.height = 26;
//        lblValue1.setLayoutData(label10LData);
//        lblValue1.setText("");
//        lblValue1.setFont(new org.eclipse.swt.graphics.Font(display,
//            "Courier New", 14, SWT.NORMAL));
//      }
//      {
//        lblValue2 = new Label(this, SWT.NONE);
//        FormData label11LData = new FormData();
//        label11LData.left = new FormAttachment(0, 1000, 275);
//        label11LData.top = new FormAttachment(0, 1000, 64);
//        label11LData.width = 83;
//        label11LData.height = 26;
//        lblValue2.setLayoutData(label11LData);
//        lblValue2.setText("");
//        lblValue2.setFont(new org.eclipse.swt.graphics.Font(display,
//            "Courier New", 14, SWT.NORMAL));
//      }
//      {
//        lblValue3 = new Label(this, SWT.NONE);
//        FormData label12LData = new FormData();
//        label12LData.left = new FormAttachment(0, 1000, 275);
//        label12LData.top = new FormAttachment(0, 1000, 102);
//        label12LData.width = 83;
//        label12LData.height = 26;
//        lblValue3.setLayoutData(label12LData);
//        lblValue3.setText("");
//        lblValue3.setFont(new org.eclipse.swt.graphics.Font(display,
//            "Courier New", 14, SWT.NORMAL));
//      }
//      {
//        lblValue4 = new Label(this, SWT.NONE);
//        FormData label13LData = new FormData();
//        label13LData.left = new FormAttachment(0, 1000, 275);
//        label13LData.top = new FormAttachment(0, 1000, 140);
//        label13LData.width = 83;
//        label13LData.height = 26;
//        lblValue4.setLayoutData(label13LData);
//        lblValue4.setText("");
//        lblValue4.setFont(new org.eclipse.swt.graphics.Font(display,
//            "Courier New", 14, SWT.NORMAL));
//      }
//
//      allButtons.add(btnARight);
//      allButtons.add(btnALeft);
//      allButtons.add(btnARed);
//      allButtons.add(btnAYellow);
//      allButtons.add(btnBRight);
//      allButtons.add(btnBLeft);
//      allButtons.add(btnBRed);
//      allButtons.add(btnBYellow);
//      allButtons.add(btnCRight);
//      allButtons.add(btnCLeft);
//      allButtons.add(btnCRed);
//      allButtons.add(btnCYellow);
//      allScale.add(scaleA);
//      allScale.add(scaleB);
//      allScale.add(scaleC);
//
//      this.layout();
//      pack();
//    } catch (Exception e) {
//      e.printStackTrace();
//    }
  }

  // Takes in 0 to 100 and adjusts to -100 to 100
  protected int fixScale(int selection) {
    return (selection - 50) * 2;
  }

  // private void button1WidgetSelected(SelectionEvent evt) {
  // Button btn = (Button)evt.getSource() ;
  // int idx = allButtons.indexOf(btn) ;
  // int rowNum = idx/4 ;
  // String Row[] = {"A", "B", "C"} ;
  // // System.out.println("button.widgetSelected, event="+evt);
  // String motor = Row[rowNum];
  // int option = idx%4;
  // // System.out.println("button Number : "
  // // + motor + option + " Scale" + Row[rowNum] + ":" +
  // Integer.toString(allScale.get(rowNum).getSelection()));
  //
  // System.out.println(motor);
  //
  // Motor m;
  // int speed;
  // switch(rowNum){
  // case 0:
  // m = Motor.MOTOR_A;
  // speed = map(scaleA.getSelection(),0,100,65,100);
  // break;
  // case 1:
  // m = Motor.MOTOR_B;
  // speed = map(scaleB.getSelection(),0,100,65,100);
  // break;
  // case 2:
  // m = Motor.MOTOR_C;
  // speed = map(scaleC.getSelection(),0,100,50,100);
  // break;
  // default:
  // m=Motor.MOTOR_A;
  // speed = 100;
  // }
  // switch(option){
  // case 0:
  // nxt.motorOn(m, speed);
  // break;
  // case 1:
  // nxt.motorOn(m, speed*-1);
  // break;
  // case 2:
  // nxt.motorOff(m);
  // break;
  // default:
  // break;
  // }
  // }
  /**
   * Button GET was clicked, get all sensor values
   * 
   * @param evt
   */
//  private static void btnGetWidgetSelected(SelectionEvent evt) {
//
//    if (!cmbSensor1.getText().equals("None")) {
//      String sensor = Sensor.SENSOR_1.getName();
//      SensorMode mode = SensorMode.getTypeByName(cmbSensorType1.getText());
//      SensorType type = SensorType.getTypeByName(cmbSensor1.getText());
//      // System.out.println(sensor+" "+mode+ " "+type);
//      nxt.setSensorMode(sensor, mode);
//      nxt.setSensorType(sensor, type);
//
//      int val = nxt.getConvertedSensorData(Sensor.SENSOR_1);
//      // System.out.println(val);
//      lblValue1.setText(Integer.toString(val));
//    } else {
//      lblValue1.setText("");
//    }
//    if (!cmbSensor2.getText().equals("None")) {
//      String sensor = Sensor.SENSOR_2.getName();
//      SensorMode mode = SensorMode.getTypeByName(cmbSensorType2.getText());
//      SensorType type = SensorType.getTypeByName(cmbSensor2.getText());
//      // System.out.println(sensor+" "+mode+ " "+type);
//      nxt.setSensorMode(sensor, mode);
//      nxt.setSensorType(sensor, type);
//
//      int val = nxt.getConvertedSensorData(Sensor.SENSOR_2);
//      // System.out.println(val);
//      lblValue2.setText(Integer.toString(val));
//    } else {
//      lblValue2.setText("");
//    }
//    if (!cmbSensor3.getText().equals("None")) {
//      String sensor = Sensor.SENSOR_3.getName();
//      SensorMode mode = SensorMode.getTypeByName(cmbSensorType3.getText());
//      SensorType type = SensorType.getTypeByName(cmbSensor3.getText());
//      // System.out.println(sensor+" "+mode+ " "+type);
//      nxt.setSensorMode(sensor, mode);
//      nxt.setSensorType(sensor, type);
//
//      int val = nxt.getConvertedSensorData(Sensor.SENSOR_3);
//      // System.out.println(val);
//      lblValue3.setText(Integer.toString(val));
//    } else {
//      lblValue3.setText("");
//    }
//    if (!cmbSensor4.getText().equals("None")) {
//      String sensor = Sensor.SENSOR_4.getName();
//      SensorMode mode = SensorMode.getTypeByName(cmbSensorType4.getText());
//      SensorType type = SensorType.getTypeByName(cmbSensor4.getText());
//      // System.out.println(sensor+" "+mode+ " "+type);
//      nxt.setSensorMode(sensor, mode);
//      nxt.setSensorType(sensor, type);
//
//      int val = nxt.getConvertedSensorData(Sensor.SENSOR_4);
//      // System.out.println(val);
//      lblValue4.setText(Integer.toString(val));
//    } else {
//      lblValue4.setText("");
//    }
//
//  }

  private void updateSpeed(Motor motor, int speed) {
    // TODO

    if (speed > 0) {
      nxt.motorOn(motor, map(speed, 0, 100, 60, 100));
    } else if (speed < 0) {
      nxt.motorOn(motor, map(Math.abs(speed), 0, 100, 60, 100) * -1);
    }
  }

//  public void updateMode(int port, String sensor) {
//    Combo control;
//    switch (port) {
//      case 1:
//        control = cmbSensorType1;
//        break;
//      case 2:
//        control = cmbSensorType2;
//        break;
//      case 3:
//        control = cmbSensorType3;
//        break;
//      case 4:
//        control = cmbSensorType4;
//        break;
//      default:
//        control = cmbSensorType1;
//        break;
//    }
//
//    SensorType s = SensorType.getTypeByName(sensor);
//    for (int j = 0; j < control.getItems().length; j++) {
//
//      System.out.println(s.getName() + " ");
//      if (control.getItem(j).equals(s.getMode().getName())) {
//        System.out.println("YES " + control.getItem(j));
//        control.setText(control.getItem(j));
//        break;
//      }
//    }
//  }

//  private void pollSelected() {
//    if (poll.getSelection()) {
//      try {
//        Display.getDefault().timerExec(500, timer);
//      } catch (Exception e) {
//        e.printStackTrace();
//      }
//    } else {
//      Display.getDefault().timerExec(-1, timer);
//    }
//  }

  public static int map(long x, long in_min, long in_max, long out_min,
      long out_max) {

    int ret = (int) ((x - in_min) * (out_max - out_min) / (in_max - in_min) + out_min);
    // System.out.println("Setting Speed: " + ret);
    return ret;

  }
}
