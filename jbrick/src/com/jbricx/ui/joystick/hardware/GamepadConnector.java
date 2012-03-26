/**
 * 
 */
package com.jbricx.ui.joystick.hardware;

import java.awt.Component;
import java.util.ArrayList;
import java.util.List;

/**
 * Provides a rough implementation of a plugged in controller.
 * 
 * This work is derived from an example by Andrew Davison (2006),
 * ad@fivedots.coe.psu.ac.th
 * Available at {@link http://fivedots.coe.psu.ac.th/~ad/jg2/ch11/index.html }
 * 
 * @author byktol
 */
public class GamepadConnector {

  /**
   * 
   */
  private Controller controller;
  /**
   * holds the components
   */
  private Component[] components;
  /**
   * holds the controller rumblers
   */
  private Rumbler[] rumblers;
  /**
   * whether rumbler is on or off
   */
  private boolean rumblerOn = false;

  /* comps[] indices for specific components */
  /**
   * indices for the analog sticks axes
   */
  private int xAxisIdx, yAxisIdx, zAxisIdx, rzAxisIdx;
  /**
   * index for the POV hat
   */
  private int povIdx;
  /**
   * indices for the buttons
   */
  private List<Integer> buttonsIdx = new ArrayList<Integer>();
  /**
   * index for the rumbler being used
   */
  private int rumblerIdx;

  public GamepadConnector() throws NoControllerFoundException {
    // get the controllers
    ControllerEnvironment ce = ControllerEnvironment.getDefaultEnvironment();

    // If you receive java.lang.UnsatisfiedLinkError: no jinput-dx8 in
    // java.library.path at this line then
    // you need to add a run argument to your run configuration to do this:
    // Click the run or debug drop down, select run/debug configurations, click
    // the arguments tab
    // Add this line to VM arguments -Djava.library.path="lib"
    Controller[] cs = ce.getControllers();

    if (cs.length == 0) {
      throw new NoControllerFoundException();
    }

    // get the game pad controller
    controller = findGamepad(cs);
    if (controller == null) {
      throw new NoControllerFoundException();
    }

    // collect indices for the required game pad components
    components = controller.getComponents();
    findCompoundIndices(components);
    findButtons(components);
    findRumblers(controller);
  }

  /**
   * Search the array of controllers until a suitable game pad controller is
   * found (either of type GAMEPAD or STICK).
   * 
   * @param cs
   * @return
   */
  private Controller findGamepad(final Controller[] cs) {
    for (int i = 0; i < cs.length; i++) {
      Controller.Type type = cs[i].getType();
      if ((type == Controller.Type.GAMEPAD) || (type == Controller.Type.STICK)) {
        return cs[i];
      }
    }

    return null;
  }

  /**
   * Store the indices for the analog sticks axes (x,y) and (z,rz), POV hat, and
   * button components of the controller.
   */
  private void findCompoundIndices(final Component[] components) {
    // get the indices for the axes of the analog sticks: (x,y) and (z,rz)
    xAxisIdx = findCompoundIndex(components, Component.Identifier.Axis.X,
            "x-axis");
    yAxisIdx = findCompoundIndex(components, Component.Identifier.Axis.Y,
            "y-axis");
    zAxisIdx = findCompoundIndex(components, Component.Identifier.Axis.Z,
            "z-axis");
    rzAxisIdx = findCompoundIndex(components, Component.Identifier.Axis.RZ,
            "rz-axis");

    // get POV hat index
    povIdx = findCompoundIndex(components, Component.Identifier.Axis.POV,
            "POV hat");
  }

  /**
   * Search through comps[] for id, returning the corresponding array index, or
   * -1
   */
  private int findCompoundIndex(final Component[] comps,
          final Component.Identifier id, final String nm) {

    for (int i = 0; i < comps.length; i++) {
      Component c = comps[i];
      if ((c.getIdentifier() == id) && !c.isRelative()) {
        return i;
      }
    }

    return -1;
  }

  /**
   * Search through comps[] for NUM_BUTTONS buttons, storing their indices in
   * buttonsIdx[]. Ignore excessive buttons. If there aren't enough buttons,
   * then fill the empty spots in buttonsIdx[] with -1's.
   */
  private void findButtons(Component[] comps) {
    for (int i = 0; i < comps.length; i++) {
      Component c = comps[i];
      // deal with a button
      if (isButton(c)) {
        buttonsIdx.add(i);
      }
    }
  }

  /**
   * Return true if the component is a digital/absolute button, and its
   * identifier name ends with "Button" (i.e. the identifier class is
   * Component.Identifier.Button).
   */
  private boolean isButton(final Component component) {
    // digital and absolute
    return !component.isAnalog()
            && !component.isRelative()
            && component.getIdentifier().getClass().getName()
                    .endsWith("Button");
  }

  /**
   * Find the rumblers. Use the last rumbler for making vibrations, an arbitrary
   * decision.
   */
  private void findRumblers(Controller controller) {
    // get the game pad's rumblers
    rumblers = controller.getRumblers();
    // use last rumbler or set it to -1 if the rumblers array is empty.
    rumblerIdx = rumblers.length - 1;
  }

  /**
   * update the component values in the controller
   */
  public boolean poll() {
    return controller != null && controller.poll();
  }

  /**
   * @return the (x,y) analog stick compass direction
   */
  public GamepadDirection getXYStickDirection() {
    if ((xAxisIdx == -1) || (yAxisIdx == -1)) {
      System.out.println("(x,y) axis data unavailable");
      return GamepadDirection.NONE;
    }

    return getCompassDirection(xAxisIdx, yAxisIdx);
  }

  /**
   * @return the (z,rz) analog stick compass direction
   */
  public GamepadDirection getZRZStickDirection() {
    if ((zAxisIdx == -1) || (rzAxisIdx == -1)) {
      System.out.println("(z,rz) axis data unavailable");
      return GamepadDirection.NONE;
    }

    return getCompassDirection(zAxisIdx, rzAxisIdx);
  }

  /**
   * Return the axes as a single compass value
   * 
   * @param xA
   * @param yA
   * @return
   */
  private GamepadDirection getCompassDirection(int xA, int yA) {
    float xCoord = components[xA].getPollData();
    float yCoord = components[yA].getPollData();

    int xc = Math.round(xCoord);
    int yc = Math.round(yCoord);

    return GamepadDirection.getDirection(xc, yc);
  }

  /**
   * @return the POV hat's direction as a compass direction
   */
  public GamepadDirection getHatDir() {
    if (povIdx == -1) {
      System.out.println("POV hat data unavailable");
      return GamepadDirection.NONE;
    } else {
      float povDir = components[povIdx].getPollData();
      return GamepadDirection.getDirection(povDir);
    }
  }

  /**
   * Return all the buttons in a single array. Each button value is a boolean.
   */
  public boolean[] getButtons() {
    boolean[] buttons = new boolean[buttonsIdx.size()];

    for (int i = 0; i < buttonsIdx.size(); i++) {
      float value = components[buttonsIdx.get(i)].getPollData();
      buttons[i] = (value == 0.0f? false : true);
    }

    return buttons;
  }

  /**
   * Return the button value (a boolean) for button number 'pos'. pos is in the
   * range [0-buttonsIdx.size()] to match the game pad button labels.
   */
  public boolean isButtonPressed(int pos) {
    if ((pos < 0) || (pos >= buttonsIdx.size())) {
      System.out.println("Button position out of range [0-" + buttonsIdx.size()
              + "): " + pos);
      return false;
    }

    float value = components[buttonsIdx.get(pos)].getPollData();
    return (value == 0.0f? false : true);
  }

  /**
   * turn the rumbler on or off
   */
  public void setRumbler(boolean switchOn) {
    if (rumblerIdx != -1) {
      if (switchOn) {
        // almost full on for last rumbler
        rumblers[rumblerIdx].rumble(0.8f);
      } else {
        // switch off
        rumblers[rumblerIdx].rumble(0.0f);
      }
      // record rumbler's new status
      rumblerOn = switchOn;
    }
  }

  public boolean isRumblerOn() {
    return rumblerOn;
  }
}