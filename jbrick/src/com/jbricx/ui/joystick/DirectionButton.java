/**
 * 
 */
package com.jbricx.ui.joystick;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Group;

/**
 * Button used to move in a specific direction.
 * 
 * @author byktol
 */
public class DirectionButton extends Button {
  public DirectionButton(final Group parent, final String imagePath,
          final int[] bounds, final String toolTipText) {
    super(parent, SWT.PUSH | SWT.CENTER);

    setBounds(bounds[0], bounds[1], bounds[2], bounds[3]);
    setImage(ImageDescriptor.createFromFile(getClass(), imagePath)
            .createImage());
    setToolTipText(toolTipText);

    addMouseListener(new MouseAdapter() {
      @Override
      public void mouseDown(MouseEvent e) {
        onClick(e);
      }

      @Override
      public void mouseUp(MouseEvent e) {
        // only the normal click catches this event
        if (e.button == 1) {
          onClickUp(e);
        }
      }
    });
  }

  /** The class button is not supposed to be subclassed. */
  @Override
  protected void checkSubclass() {
    /* Ignore class validator */
  }

  protected void onClick(MouseEvent e) {
  };

  protected void onClickUp(MouseEvent e) {
  };
}
