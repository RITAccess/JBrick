package com.jbricx.ui.controller;

import java.io.FileInputStream;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;


public class ControllerComposite extends Composite {
	
	private Group group1;
	private Label label1;
	private Button button8;
	private Group group4;
	private Group group3;
	private Group group2;
	private Button button16;
	private Button button15;
	private Button button14;
	private Button button13;
	private Button button12;
	private Button button11;
	private Button button10;
	private Button button9;
	private Button button7;
	private Button button6;
	private Button button5;
	private Button button4;
	private Button button3;
	private Button button2;
	private Button button1;
	private Button controller;
	private Button identify1;
	private Button disconnect1;
	private Label label4;
	private Label label3;
	private Label label2;
	private Button connect1;

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
		ControllerComposite inst = new ControllerComposite(shell, SWT.NULL);
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

	public ControllerComposite(org.eclipse.swt.widgets.Composite parent, int style) {
		super(parent, style);
		initGUI();
	}

	private void initGUI() {
		try {
			FormLayout thisLayout = new FormLayout();
			this.setLayout(thisLayout);
			{
				FormData button1LData = new FormData();
				button1LData.left =  new FormAttachment(0, 1000, 67);
				button1LData.top =  new FormAttachment(0, 1000, 334);
				button1LData.width = 13;
				button1LData.height = 16;
				button1 = new Button(this, SWT.RADIO | SWT.LEFT);
				button1.setLayoutData(button1LData);
			}
			{
				controller = new Button(this, SWT.PUSH | SWT.CENTER);
				FormData controllerLData = new FormData();
				controllerLData.left =  new FormAttachment(0, 1000, 35);
				controllerLData.top =  new FormAttachment(0, 1000, 289);
				controllerLData.width = 80;
				controllerLData.height = 23;
				controller.setLayoutData(controllerLData);
				controller.setText("Controller - 1");
			}
			{
				identify1 = new Button(this, SWT.PUSH | SWT.CENTER);
				FormData identify1LData = new FormData();
				identify1LData.left =  new FormAttachment(0, 1000, 35);
				identify1LData.top =  new FormAttachment(0, 1000, 245);
				identify1LData.width = 80;
				identify1LData.height = 23;
				identify1.setLayoutData(identify1LData);
				identify1.setText("Identify - 1");
			}
			{
				disconnect1 = new Button(this, SWT.PUSH | SWT.CENTER);
				FormData disconnect1LData = new FormData();
				disconnect1LData.left =  new FormAttachment(0, 1000, 35);
				disconnect1LData.top =  new FormAttachment(0, 1000, 199);
				disconnect1LData.width = 80;
				disconnect1LData.height = 23;
				disconnect1.setLayoutData(disconnect1LData);
				disconnect1.setText("Disconnect - 1");
			}
			{
				connect1 = new Button(this, SWT.PUSH | SWT.CENTER);
				FormData connect1LData = new FormData();
				connect1LData.left =  new FormAttachment(0, 1000, 35);
				connect1LData.top =  new FormAttachment(0, 1000, 157);
				connect1LData.width = 80;
				connect1LData.height = 23;
				connect1.setLayoutData(connect1LData);
				connect1.setText("Connect - 1");
			}
			{
				group1 = new Group(this, SWT.NONE);
				GridLayout group1Layout = new GridLayout();
				group1Layout.makeColumnsEqualWidth = true;
				group1.setLayout(group1Layout);
				FormData group1LData = new FormData();
				group1LData.left =  new FormAttachment(0, 1000, 18);
				group1LData.top =  new FormAttachment(0, 1000, 30);
				group1LData.width = 102;
				group1LData.height = 325;
				group1.setLayoutData(group1LData);
				group1.setText("group1");
				{
					label1 = new Label(group1, SWT.NONE);
					label1.setText("label1");
					GridData label1LData = new GridData();
					label1LData.horizontalIndent = 17;
					label1.setLayoutData(label1LData);
					label1.setImage(ImageDescriptor.createFromFile(getClass(),
					  "/images/1.PNG").createImage());
				}
			}
			{
				FormData button2LData = new FormData();
				button2LData.left =  new FormAttachment(0, 1000, 196);
				button2LData.top =  new FormAttachment(0, 1000, 334);
				button2LData.width = 13;
				button2LData.height = 16;
				button2 = new Button(this, SWT.RADIO | SWT.LEFT);
				button2.setLayoutData(button2LData);
			}
			{
				button3 = new Button(this, SWT.PUSH | SWT.CENTER);
				FormData button3LData = new FormData();
				button3LData.left =  new FormAttachment(0, 1000, 164);
				button3LData.top =  new FormAttachment(0, 1000, 289);
				button3LData.width = 80;
				button3LData.height = 23;
				button3.setLayoutData(button3LData);
				button3.setText("Controller - 2");
			}
			{
				button4 = new Button(this, SWT.PUSH | SWT.CENTER);
				FormData button4LData = new FormData();
				button4LData.left =  new FormAttachment(0, 1000, 164);
				button4LData.top =  new FormAttachment(0, 1000, 245);
				button4LData.width = 80;
				button4LData.height = 23;
				button4.setLayoutData(button4LData);
				button4.setText("Identify - 2");
			}
			{
				button5 = new Button(this, SWT.PUSH | SWT.CENTER);
				FormData button5LData = new FormData();
				button5LData.left =  new FormAttachment(0, 1000, 164);
				button5LData.top =  new FormAttachment(0, 1000, 199);
				button5LData.width = 80;
				button5LData.height = 23;
				button5.setLayoutData(button5LData);
				button5.setText("Disconnect - 2");
			}
			{
				button6 = new Button(this, SWT.PUSH | SWT.CENTER);
				FormData button6LData = new FormData();
				button6LData.left =  new FormAttachment(0, 1000, 164);
				button6LData.top =  new FormAttachment(0, 1000, 157);
				button6LData.width = 80;
				button6LData.height = 23;
				button6.setLayoutData(button6LData);
				button6.setText("Connect - 2");
			}
			{
				FormData button7LData = new FormData();
				button7LData.left =  new FormAttachment(0, 1000, 327);
				button7LData.top =  new FormAttachment(0, 1000, 334);
				button7LData.width = 13;
				button7LData.height = 16;
				button7 = new Button(this, SWT.RADIO | SWT.LEFT);
				button7.setLayoutData(button7LData);
			}
			{
				button8 = new Button(this, SWT.PUSH | SWT.CENTER);
				FormData button8LData = new FormData();
				button8LData.left =  new FormAttachment(0, 1000, 295);
				button8LData.top =  new FormAttachment(0, 1000, 289);
				button8LData.width = 80;
				button8LData.height = 23;
				button8.setLayoutData(button8LData);
				button8.setText("Controller - 3");
			}
			{
				button9 = new Button(this, SWT.PUSH | SWT.CENTER);
				FormData button9LData = new FormData();
				button9LData.left =  new FormAttachment(0, 1000, 295);
				button9LData.top =  new FormAttachment(0, 1000, 245);
				button9LData.width = 80;
				button9LData.height = 23;
				button9.setLayoutData(button9LData);
				button9.setText("Identify - 3");
			}
			{
				button10 = new Button(this, SWT.PUSH | SWT.CENTER);
				FormData button10LData = new FormData();
				button10LData.left =  new FormAttachment(0, 1000, 295);
				button10LData.top =  new FormAttachment(0, 1000, 199);
				button10LData.width = 80;
				button10LData.height = 23;
				button10.setLayoutData(button10LData);
				button10.setText("Disconnect - 3");
			}
			{
				button11 = new Button(this, SWT.PUSH | SWT.CENTER);
				FormData button11LData = new FormData();
				button11LData.left =  new FormAttachment(0, 1000, 295);
				button11LData.top =  new FormAttachment(0, 1000, 157);
				button11LData.width = 80;
				button11LData.height = 23;
				button11.setLayoutData(button11LData);
				button11.setText("Connect - 3");
			}
			{
				FormData button12LData = new FormData();
				button12LData.left =  new FormAttachment(0, 1000, 453);
				button12LData.top =  new FormAttachment(0, 1000, 334);
				button12LData.width = 13;
				button12LData.height = 16;
				button12 = new Button(this, SWT.RADIO | SWT.LEFT);
				button12.setLayoutData(button12LData);
			}
			{
				button13 = new Button(this, SWT.PUSH | SWT.CENTER);
				FormData button13LData = new FormData();
				button13LData.left =  new FormAttachment(0, 1000, 421);
				button13LData.top =  new FormAttachment(0, 1000, 289);
				button13LData.width = 80;
				button13LData.height = 23;
				button13.setLayoutData(button13LData);
				button13.setText("Controller - 4");
			}
			{
				button14 = new Button(this, SWT.PUSH | SWT.CENTER);
				FormData button14LData = new FormData();
				button14LData.left =  new FormAttachment(0, 1000, 421);
				button14LData.top =  new FormAttachment(0, 1000, 245);
				button14LData.width = 80;
				button14LData.height = 23;
				button14.setLayoutData(button14LData);
				button14.setText("Identify - 4");
			}
			{
				button15 = new Button(this, SWT.PUSH | SWT.CENTER);
				FormData button15LData = new FormData();
				button15LData.left =  new FormAttachment(0, 1000, 421);
				button15LData.top =  new FormAttachment(0, 1000, 199);
				button15LData.width = 80;
				button15LData.height = 23;
				button15.setLayoutData(button15LData);
				button15.setText("Disconnect - 4");
			}
			{
				button16 = new Button(this, SWT.PUSH | SWT.CENTER);
				FormData button16LData = new FormData();
				button16LData.left =  new FormAttachment(0, 1000, 421);
				button16LData.top =  new FormAttachment(0, 1000, 155);
				button16LData.width = 80;
				button16LData.height = 23;
				button16.setLayoutData(button16LData);
				button16.setText("Connect - 4");
			}
			{
				group2 = new Group(this, SWT.NONE);
				GridLayout group2Layout = new GridLayout();
				group2Layout.makeColumnsEqualWidth = true;
				group2.setLayout(group2Layout);
				FormData group2LData = new FormData();
				group2LData.left =  new FormAttachment(0, 1000, 146);
				group2LData.top =  new FormAttachment(0, 1000, 30);
				group2LData.width = 102;
				group2LData.height = 324;
				group2.setLayoutData(group2LData);
				group2.setText("group2");
				{
					label2 = new Label(group2, SWT.NONE);
					label2.setText("label2");
					GridData label2LData = new GridData();
					label2LData.horizontalIndent = 21;
					label2.setLayoutData(label2LData);
					label2.setImage(ImageDescriptor.createFromFile(getClass(),
          "/images/2.PNG").createImage());
				}
			}
			{
				group3 = new Group(this, SWT.NONE);
				GridLayout group3Layout = new GridLayout();
				group3Layout.makeColumnsEqualWidth = true;
				group3.setLayout(group3Layout);
				FormData group3LData = new FormData();
				group3LData.left =  new FormAttachment(0, 1000, 278);
				group3LData.top =  new FormAttachment(0, 1000, 30);
				group3LData.width = 102;
				group3LData.height = 321;
				group3.setLayoutData(group3LData);
				group3.setText("group3");
				{
					label3 = new Label(group3, SWT.NONE);
					label3.setText("label3");
					GridData label3LData = new GridData();
					label3LData.horizontalIndent = 21;
					label3.setLayoutData(label3LData);
					label3.setImage(ImageDescriptor.createFromFile(getClass(),
          "/images/3.PNG").createImage());
				}
			}
			{
				group4 = new Group(this, SWT.NONE);
				GridLayout group4Layout = new GridLayout();
				group4Layout.makeColumnsEqualWidth = true;
				group4.setLayout(group4Layout);
				FormData group4LData = new FormData();
				group4LData.left =  new FormAttachment(0, 1000, 402);
				group4LData.top =  new FormAttachment(0, 1000, 30);
				group4LData.width = 102;
				group4LData.height = 320;
				group4.setLayoutData(group4LData);
				group4.setText("group4");
				{
					label4 = new Label(group4, SWT.NONE);
					label4.setText("label4");
					GridData label4LData = new GridData();
					label4LData.horizontalIndent = 23;
					label4.setLayoutData(label4LData);
					label4.setImage(ImageDescriptor.createFromFile(getClass(),
            "/images/4.PNG").createImage());
				}
			}
			this.layout();
			pack();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
