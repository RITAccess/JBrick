package com.jbricx.ui.findbrick;

import java.io.FileInputStream;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.List;
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
public class FindBrickComposite extends org.eclipse.swt.widgets.Composite {

	private Group rightMotor;
	private Group leftMotor;
	private Button cancel;
	private Button leJOS;
	private Button other;
	private Button pbForth;
	private Button brickOS;
	private Button standard;
	private Button ok;
	private Button button1;
	private List brickType;
	private List portList;
	private StyledText searchBrick;
	private Group driveMode;

	/**
	* Auto-generated main method to display this 
	* org.eclipse.swt.widgets.Composite inside a new Shell.
	*/
	public static void main(String[] args) {
		showGUI();
	}
	
	/**
	* Overriding checkSubclass allows this class to extend org.eclipse.swt.widgets.Composite
	*/	
	protected void checkSubclass() {
	}
	
	/**
	* Auto-generated method to display this 
	* org.eclipse.swt.widgets.Composite inside a new Shell.
	*/
	public static void showGUI() {
		Display display = Display.getDefault();
		Shell shell = new Shell(display);
		FindBrickComposite inst = new FindBrickComposite(shell, SWT.NULL);
		Point size = inst.getSize();
		shell.setLayout(new FillLayout());
		shell.layout();
		if(size.x == 0 && size.y == 0) {
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

	public FindBrickComposite(org.eclipse.swt.widgets.Composite parent, int style) {
		super(parent, style);
		initGUI();
	}

	private void initGUI() {
		try {
			{
				driveMode = new Group(this, SWT.NONE);
				driveMode.setText("Brick Type");
				driveMode.setBounds(217, 51, 153, 73);
				{
					brickType = new List(driveMode, SWT.NONE);
					brickType.setBounds(17, 28, 117, 24);
				}
			}
			{
				leftMotor = new Group(this, SWT.NONE);
				leftMotor.setText("Port");
				leftMotor.setBounds(25, 51, 159, 74);
				{
					portList = new List(leftMotor, SWT.NONE);
					portList.setBounds(17, 28, 121, 24);
				}
			}
			{
				rightMotor = new Group(this, SWT.NONE);
				rightMotor.setText("Firmware");
				rightMotor.setBounds(19, 145, 356, 74);
				{
					standard = new Button(rightMotor, SWT.RADIO | SWT.LEFT);
					standard.setText("Standard");
					standard.setBounds(12, 26, 65, 30);
				}
				{
					brickOS = new Button(rightMotor, SWT.RADIO | SWT.LEFT);
					brickOS.setText("brickOS");
					brickOS.setSize(60, 30);
					brickOS.setBounds(89, 26, 60, 30);
				}
				{
					pbForth = new Button(rightMotor, SWT.RADIO | SWT.LEFT);
					pbForth.setText("pbForth");
					pbForth.setBounds(162, 26, 60, 30);
				}
				{
					leJOS = new Button(rightMotor, SWT.RADIO | SWT.LEFT);
					leJOS.setText("leJOS");
					leJOS.setBounds(234, 26, 60, 30);
				}
				{
					other = new Button(rightMotor, SWT.RADIO | SWT.LEFT);
					other.setText("Other");
					other.setBounds(294, 26, 54, 30);
				}
			}
			{
				
			}
			{
				cancel = new Button(this, SWT.PUSH | SWT.CENTER);
				cancel.setText("Cancel");
				cancel.setBounds(157, 231, 60, 30);
			}
			{
				button1 = new Button(this, SWT.PUSH | SWT.CENTER);
				button1.setText("Help");
				button1.setSize(60, 30);
				button1.setBounds(237, 231, 60, 30);
			}
			{
				ok = new Button(this, SWT.PUSH | SWT.CENTER);
				ok.setText("Ok");
				ok.setBounds(74, 231, 60, 30);
			}
			FormLayout thisLayout = new FormLayout();
			
			this.layout();
			pack();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
