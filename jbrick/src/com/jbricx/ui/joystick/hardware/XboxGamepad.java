/**
 * 
 */
package com.jbricx.ui.joystick.hardware;

/**
 * Handles the interaction with an Xbox Controller. There might be the case that
 * some other controllers work with this implementation.
 * 
 * @see GamepadController
 * @see GamepadConnector
 * 
 * @author byktol
 */
public class XboxGamepad implements Gamepad {
  private volatile boolean run = true;

  public XboxGamepad(final GamepadController gc) throws NoControllerFoundException {
    final GamepadConnector gamepad = new GamepadConnector();
    new Thread() {
      @Override
      public void run() {
        while (run) {
          run = gamepad.poll();
          // Somehow, no data from the plugged device
          if (!run) {
            XboxGamepad.this.stop();
            break;
          }

          GamepadDirection xyDirection = gamepad.getXYStickDirection();
          switch (xyDirection) {
            case NW:
              gc.turnForwardCCW();
              break;
            case NORTH:
              gc.forward();
              break;
            case NE:
              gc.turnForwardCW();
              break;
            case EAST:
              gc.rotateCW();
              break;
            case WEST:
              gc.rotateCCW();
              break;
            case SW:
              gc.turnBackwardsCCW();
              break;
            case SOUTH:
              gc.backwards();
              break;
            case SE:
              gc.turnBackwardsCW();
              break;
            case NONE:
            default:
              gc.stop();
          }

          boolean[] buttonsPressed = gamepad.getButtons();
          for(int i = 0; i < buttonsPressed.length; i++) {
            if (!buttonsPressed[i]) {
              continue;
            }

            switch (i) {
              case 0: // A
                gc.honk();
                break;
              case 1: // B
                gc.honk2();
                break;
              case 2: // X
                gc.honk3();
                break;
              case 3: // Y
                gc.honk4();
                break;
              case 4: // LB
                gc.decreaseSpeed();
                break;
              case 5: // RB
                gc.increaseSpeed();
                break;
              case 6: // Back
              case 7: // Start
              case 8: // LStickDown
              case 9: // RStickDown
                gc.honk();
                break;
            }
            // We only want to have a delay when pressing buttons
            try {
              sleep(200);
            } catch (InterruptedException e) {
              /* Ignore */
            }

          } // end of for

        } // end of while
      }
    }.start();
  }

  @Override
  public void stop() {
    run = false;
  }

  @Override
  public GamepadType getGamepadType() {
    return GamepadType.XBOX;
  }
}
