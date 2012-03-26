package com.jbricx.ui.joystick;

import java.awt.Button;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.security.acl.Group;
import java.util.HashMap;
import java.util.Map;

import com.jbricx.actions.HelpContentAction;
import com.jbricx.communications.enums.Motor;
import com.jbricx.ui.joystick.hardware.GamepadController;
import com.jbricx.ui.joystick.hardware.GamepadControllerObserver;
import com.jbricx.ui.joystick.hardware.GamepadType;

/**
 * Graphical interface for manipulating an NXT Brick. It is supposed to contain
 * only the objects for display without any Brick manipulation logic.
 * 
 * @author byktol
 */
public class JoystickComposite extends Composite implements GamepadControllerObserver {
  private final GamepadController gamepadController;
  private Map<Motor, Button> leftMotors = new HashMap<Motor, Button>(3);
  private Map<Motor, Button> rightMotors = new HashMap<Motor, Button>(3);
  private Map<GamepadType, Button> gamepads = new HashMap<GamepadType, Button>(3);
  private Button reversedLeftMotorCheck;
  private Button reversedRightMotorCheck;

  private Scale speedBar;
  public JoystickComposite(final Composite parent, final int style, final GamepadController gc) {
    super(parent, style);
    gamepadController = gc;
    gamepadController.registerObserver(this);

    /* Virtual Pad */
    final Group movementGrp = new Group(this, SWT.NONE); 
    movementGrp.setText("Movement");
    movementGrp.setBounds(14, 10, 115, 154);

    new DirectionButton(movementGrp, "/images/UpLeft.png",
            new int[] {18, 36, 26, 26}, "Q") {
      @Override
      protected void onClick(MouseEvent e) {
        gamepadController.turnForwardCCW();
      }
      @Override
      protected void onClickUp(MouseEvent e) {
        gamepadController.stop();
      }
    };
    new DirectionButton(movementGrp, "/images/Up.png",
            new int[] {42, 36, 27, 26}, "W") {
      @Override
      protected void onClick(MouseEvent e) {
        gamepadController.forward();
      }
      @Override
      protected void onClickUp(MouseEvent e) {
        gamepadController.stop();
      }
    };
    new DirectionButton(movementGrp, "/images/UpRight.png",
            new int[] {67, 37, 27, 26}, "E") {
      @Override
      protected void onClick(MouseEvent e) {
        gamepadController.turnForwardCW();
      }
      @Override
      protected void onClickUp(MouseEvent e) {
        gamepadController.stop();
      }
    };
    new DirectionButton(movementGrp, "/images/left.png",
            new int[] {18, 59, 24, 30}, "A") {
      @Override
      protected void onClick(MouseEvent e) {
        gamepadController.rotateCCW();
      }
      @Override
      protected void onClickUp(MouseEvent e) {
        gamepadController.stop();
      }
    };
    new DirectionButton(movementGrp, "/images/centreStop.png",
            new int[] {41, 62, 27, 26}, "S") {
      @Override
      protected void onClick(MouseEvent e) {
        gamepadController.stop();
      }
    };
    new DirectionButton(movementGrp, "/images/right.png",
            new int[] {66, 59, 28, 30}, "D") {
      @Override
      protected void onClick(MouseEvent e) {
        gamepadController.rotateCW();
      }
      @Override
      protected void onClickUp(MouseEvent e) {
        gamepadController.stop();
      }
    };
    new DirectionButton(movementGrp, "/images/downLeft.png",
            new int[] {18, 84, 24, 28}, "Z") {
      @Override
      protected void onClick(MouseEvent e) {
        gamepadController.turnBackwardsCCW();
      }
      @Override
      protected void onClickUp(MouseEvent e) {
        gamepadController.stop();
      }
    };
    new DirectionButton(movementGrp, "/images/down.png",
            new int[] {43, 87, 25, 24}, "X") {
      @Override
      protected void onClick(MouseEvent e) {
        gamepadController.backwards();
      }
      @Override
      protected void onClickUp(MouseEvent e) {
        gamepadController.stop();
      }
    };
    new DirectionButton(movementGrp, "/images/downRight.png",
            new int[] {68, 86, 25, 26}, "C") {
      @Override
      protected void onClick(MouseEvent e) {
        gamepadController.turnBackwardsCW();
      }
      @Override
      protected void onClickUp(MouseEvent e) {
        gamepadController.stop();
      }
    };

    /* Drive Mode */
    final Group driveModeGrp = new Group(this, SWT.NONE);
    driveModeGrp.setText("Drive Mode");
    driveModeGrp.setBounds(142, 266, 115, 84);

    Button leftRight = new Button(driveModeGrp, SWT.RADIO | SWT.LEFT);
    leftRight.setText("Left-Right");
    leftRight.setBounds(12, 21, 73, 30);

    Button driveSteer = new Button(driveModeGrp, SWT.RADIO | SWT.LEFT);
    driveSteer.setText("Drive-Steer");
    driveSteer.setBounds(12, 47, 80, 30);

    driveSteer.setEnabled(false);
    leftRight.setSelection(true);

    /* Choose the controller */
    final Group controllerGrp = new Group(this, SWT.NONE);
    controllerGrp.setText("Controller");
    controllerGrp.setBounds(141, 15, 115, 110);

    Button gpRadio;
    gpRadio = new GamepadRadioButton(gamepadController, controllerGrp, "None",
            new int[] {12, 21, 73, 30});
    gamepads.put(GamepadType.NONE, gpRadio);
    gpRadio = new GamepadRadioButton(gamepadController, controllerGrp, "Xbox",
            new int[] {12, 44, 73, 30});
    gamepads.put(GamepadType.XBOX, gpRadio);
    gpRadio = new GamepadRadioButton(gamepadController, controllerGrp, "Wii",
            new int[] {12, 70, 80, 30});
    gamepads.put(GamepadType.WII, gpRadio);

    Button motorRadio;
    /* Left Motor */
    final Group leftMotorGrp = new Group(this, SWT.NONE);
    leftMotorGrp.setText("Left Motor");
    leftMotorGrp.setBounds(12, 176, 115, 74);

    motorRadio = new MotorRadioButton(leftMotorGrp, "A",
            new int[] {12, 22, 30, 24}) {
      @Override
      protected void onSelect(final Motor m) {
        gamepadController.setMotorLeft(m);
      }
    };
    leftMotors.put(Motor.MOTOR_A, motorRadio);
    motorRadio = new MotorRadioButton(leftMotorGrp, "B",
            new int[] {47, 22, 29, 24}) {
      @Override
      protected void onSelect(final Motor m) {
        gamepadController.setMotorLeft(m);
      }
    };
    leftMotors.put(Motor.MOTOR_B, motorRadio);
    motorRadio = new MotorRadioButton(leftMotorGrp, "C",
            new int[] {81, 22, 26, 24}) {
      @Override
      protected void onSelect(final Motor m) {
        gamepadController.setMotorLeft(m);
      }
    };
    leftMotors.put(Motor.MOTOR_C, motorRadio);
    reversedLeftMotorCheck = new MotorCheckBox(leftMotorGrp, "Reversed",
            new int[] {12, 48, 80, 20}) {
      @Override
      protected void onCheck(boolean checked) {
        gamepadController.setReversedMotorLeft(checked);
      }
    };

    /* Right Motor */
    final Group rightMotorGrp = new Group(this, SWT.NONE);
    rightMotorGrp.setText("Right Motor");
    rightMotorGrp.setBounds(141, 176, 115, 74);

    motorRadio = new MotorRadioButton(rightMotorGrp, "A",
            new int[] {12, 20, 27, 23}) {
      @Override
      protected void onSelect(final Motor m) {
        gamepadController.setMotorRight(m);
      }
    };
    rightMotors.put(Motor.MOTOR_A, motorRadio);
    motorRadio = new MotorRadioButton(rightMotorGrp, "B",
            new int[] {48, 20, 26, 23}) {
      @Override
      protected void onSelect(final Motor m) {
        gamepadController.setMotorRight(m);
      }
    };
    rightMotors.put(Motor.MOTOR_B, motorRadio);
    motorRadio = new MotorRadioButton(rightMotorGrp, "C",
            new int[] {82, 19, 24, 23}) {
      @Override
      protected void onSelect(final Motor m) {
        gamepadController.setMotorRight(m);
      }
    };
    rightMotors.put(Motor.MOTOR_C, motorRadio);
    reversedRightMotorCheck = new MotorCheckBox(rightMotorGrp, "Reversed",
            new int[] {12, 45, 70, 24}) {
      @Override
      protected void onCheck(boolean checked) {
        gamepadController.setReversedMotorRight(checked);
      }
    };

    /* Speed slider */
    final Group speedGrp = new Group(this, SWT.NONE);
    speedGrp.setText("Speed");
    speedGrp.setBounds(12, 266, 115, 59);
    speedBar = new Scale(speedGrp, SWT.NONE);
    speedBar.setMinimum(60);
    speedBar.setMaximum(100);
    speedBar.setBounds(12, 15, 85, 33);
    speedBar.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(final SelectionEvent e) {
        gamepadController.setSpeed(speedBar.getSelection());
      }
    });

    /* Help Button */
    final HelpContentAction helpAction = new HelpContentAction();
    Button help = new Button(this, SWT.PUSH | SWT.CENTER);
    help.setText("Help");
    help.setBounds(142, 357, 60, 25);
    help.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseDown(MouseEvent e) {
        helpAction.runJoistickLink();
      }
    });

    /* Copy NXC Code Button */
    Button copyNXC= new Button(this, SWT.PUSH | SWT.CENTER);
    copyNXC.setText("Copy NXC");
    copyNXC.setBounds(10, 337, 70, 25);
    copyNXC.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseDown(MouseEvent e) {
        gamepadController.getRecordJoystick().codeToClipboard();
      }
    });

    /* Update the interface from the default values. */
    updateInterface();

    /* Make it keyboard friendly. */
    addKeyListener(new KeyListener() {
      @Override
      public void keyPressed(KeyEvent e) {
        switch (Character.toLowerCase(e.character)) {
          case 'q':
            gamepadController.turnForwardCCW();
            break;
          case 'w':
            gamepadController.forward();
            break;
          case 'e':
            gamepadController.turnForwardCW();
            break;
          case 'a':
            gamepadController.rotateCCW();
            break;
          case 'd':
            gamepadController.rotateCW();
            break;
          case 's':
            gamepadController.stop();
            break;
          case 'z':
            gamepadController.turnBackwardsCW();
            break;
          case 'x':
            gamepadController.backwards();
            break;
          case 'c':
            gamepadController.turnBackwardsCCW();
            break;
          case '+':
            gamepadController.increaseSpeed();
            break;
          case '-':
            gamepadController.decreaseSpeed();
            break;
        } // end of switch

      }

      @Override
      public void keyReleased(KeyEvent e) {
        gamepadController.stop();
      }
    });
  }

  @Override
  public void updateInterface() {
    Display.getDefault().syncExec(new Runnable() {
      @Override
      public void run() {
        speedBar.setSelection(gamepadController.getSpeed());
        for (Motor m : leftMotors.keySet()) {
          leftMotors.get(m).setSelection(false);
        }
        leftMotors.get(gamepadController.getMotorLeft()).setSelection(true);
        for (Motor m : rightMotors.keySet()) {
          rightMotors.get(m).setSelection(false);
        }
        rightMotors.get(gamepadController.getMotorRight()).setSelection(true);
        for (GamepadType t : gamepads.keySet()) {
          gamepads.get(t).setSelection(false);
        }
        gamepads.get(gamepadController.getGamepadType()).setSelection(true);
        reversedLeftMotorCheck.setSelection(gamepadController.getReversedMotorLeft());
        reversedRightMotorCheck.setSelection(gamepadController.getReversedMotorRight());
      }
    });
  }
}
