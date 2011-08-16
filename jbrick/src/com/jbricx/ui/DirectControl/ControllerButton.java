package com.jbricx.ui.DirectControl;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;

public class ControllerButton extends Button{
	private char motor;
	
	public ControllerButton(Group parent, Image img, int locationX, int locationY, int width, int height, String toolTip) {
		super(parent, SWT.TOGGLE);
		this.setBounds(locationX, locationY, width, height);
		this.setImage(img);
		this.setToolTipText(toolTip);
		this.setMotor(motor);
	}
	
	  @Override
	  protected void checkSubclass() {
	    // Ignore class validator
	  }
	  
		public char getMotor() {
			return motor;
		}

		public void setMotor(char motor) {
			this.motor = motor;
		}

}
