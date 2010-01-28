package com.jbricx.ui.DirectControl;

import java.io.FileInputStream;
import java.util.ArrayList;

import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Scale;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

/**
* This code was edited or generated using CloudGarden's Jigloo
* SWT/Swing GUI Builder, which is free for non-commercial
* use. If Jigloo is being used commercially (ie, by a corporation,
* company or business for any purpose whatever) then you
* should purchase a license for each developer using Jigloo.
* Please visit www.cloudgarden.com for details.
* Use of Jigloo implies acceptance of these licensing terms.
* A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR
* THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED
* LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
*/
public class DirectControlWindow extends org.eclipse.swt.widgets.Composite {

	
	private Combo cmbSensor1;
	private Combo cmbSensor2;
	private Combo cmbSensor3;
	private Combo cmbSensor4;
	private Combo cmbSensorType1;
	private Combo cmbSensorType2;
	private Combo cmbSensorType3;
	private Combo cmbSensorType4;
	private Label label7;
	private Label label6;
	private Label label5;
	private Label label4;
	private Label label3;
	private Label label2;
	private Label label1;
	private Scale scaleC;
	private Button btnCYellow;
	private Button btnCRed;
	private Button btnCLeft;
	private Button btnCRight;
	private Scale scaleB;
	private Button btnBYellow;
	private Button btnBRed;
	private Button btnBLeft;
	private Button btnBRight;
	private Scale scaleA;
	private Button btnAYellow;
	private Button btnARed;
	private Label label9;
	private Label label8;
	private Button btnALeft;
	private Button btnARight;
	
	private ArrayList<Button> allButtons = new ArrayList<Button>() ;
	private ArrayList<Scale> allScale = new ArrayList<Scale>() ;

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
	static Display display = Display.getDefault();
	
	public static void showGUI() {
		Shell shell = new Shell(display);
		DirectControlWindow inst = new DirectControlWindow(shell, SWT.NULL);
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

	public DirectControlWindow(org.eclipse.swt.widgets.Composite parent, int style) {
		super(parent, style);
		initGUI();
	}

	private void initGUI() {
		try {
			FormLayout thisLayout = new FormLayout();
			this.setLayout(thisLayout);
			{
				FormData scale1LData = new FormData();
				scale1LData.left =  new FormAttachment(0, 1000, 228);
				scale1LData.top =  new FormAttachment(0, 1000, 199);
				scale1LData.width = 158;
				scale1LData.height = 42;
				scaleA = new Scale(this, SWT.NONE);
				scaleA.setLayoutData(scale1LData);
				scaleA.setSelection(50) ;
			}
			{
				cmbSensor1 = new Combo(this, SWT.NONE);
				FormData combo1LData = new FormData();
				combo1LData.left =  new FormAttachment(0, 1000, 32);
				combo1LData.top =  new FormAttachment(0, 1000, 26);
				combo1LData.width = 46;
				combo1LData.height = 26;
				cmbSensor1.setLayoutData(combo1LData);
				cmbSensor1.add("None");
				cmbSensor1.setText("None");
			}
			{
				cmbSensor2 = new Combo(this, SWT.NONE);
				FormData combo2LData = new FormData();
				combo2LData.left =  new FormAttachment(0, 1000, 32);
				combo2LData.top =  new FormAttachment(0, 1000, 64);
				combo2LData.width = 46;
				combo2LData.height = 26;
				cmbSensor2.setLayoutData(combo2LData);
				cmbSensor2.add("None");
				cmbSensor2.setText("None");
			}
			{
				cmbSensor3 = new Combo(this, SWT.NONE);
				FormData combo3LData = new FormData();
				combo3LData.left =  new FormAttachment(0, 1000, 32);
				combo3LData.top =  new FormAttachment(0, 1000, 102);
				combo3LData.width = 46;
				combo3LData.height = 26;
				cmbSensor3.setLayoutData(combo3LData);
				cmbSensor3.add("None");
				cmbSensor3.setText("None");
			}
			{
				cmbSensor4 = new Combo(this, SWT.NONE);
				FormData combo4LData = new FormData();
				combo4LData.left =  new FormAttachment(0, 1000, 32);
				combo4LData.top =  new FormAttachment(0, 1000, 140);
				combo4LData.width = 46;
				combo4LData.height = 26;
				cmbSensor4.setLayoutData(combo4LData);
				cmbSensor4.add("None");
				cmbSensor4.setText("None");
			}
			{
				cmbSensorType1 = new Combo(this, SWT.NONE);
				FormData combo5LData = new FormData();
				combo5LData.left =  new FormAttachment(0, 1000, 128);
				combo5LData.top =  new FormAttachment(0, 1000, 26);
				combo5LData.width = 46;
				combo5LData.height = 26;
				cmbSensorType1.setLayoutData(combo5LData);
				cmbSensorType1.add("Raw");
				cmbSensorType1.setText("Raw");
			}
			{
				cmbSensorType4 = new Combo(this, SWT.NONE);
				FormData combo6LData = new FormData();
				combo6LData.left =  new FormAttachment(0, 1000, 128);
				combo6LData.top =  new FormAttachment(0, 1000, 64);
				combo6LData.width = 46;
				combo6LData.height = 26;
				cmbSensorType4.setLayoutData(combo6LData);
				cmbSensorType4.add("Raw");
				cmbSensorType4.setText("Raw");
			}
			{
				cmbSensorType3 = new Combo(this, SWT.NONE);
				FormData combo7LData = new FormData();
				combo7LData.left =  new FormAttachment(0, 1000, 128);
				combo7LData.top =  new FormAttachment(0, 1000, 102);
				combo7LData.width = 46;
				combo7LData.height = 26;
				cmbSensorType3.setLayoutData(combo7LData);
				cmbSensorType3.add("Raw");
				cmbSensorType3.setText("Raw");
			}
			{
				cmbSensorType2 = new Combo(this, SWT.NONE);
				FormData combo8LData = new FormData();
				combo8LData.left =  new FormAttachment(0, 1000, 128);
				combo8LData.top =  new FormAttachment(0, 1000, 140);
				combo8LData.width = 46;
				combo8LData.height = 26;
				cmbSensorType2.setLayoutData(combo8LData);
				cmbSensorType2.add("Raw");
				cmbSensorType2.setText("Raw");
			}
			{
				btnARight = new Button(this, SWT.PUSH | SWT.CENTER);
				FormData button1LData = new FormData();
				button1LData.left =  new FormAttachment(0, 1000, 32);
				button1LData.top =  new FormAttachment(0, 1000, 204);
				button1LData.width = 40;
				button1LData.height = 31;
				btnARight.setLayoutData(button1LData);
				Image image = new Image(null, new FileInputStream("src/images/RightArrow.png")) ;
				Image scaledImage = new Image(display, image.getImageData().scaledTo(35, 25)) ;
				btnARight.setImage(scaledImage) ;
				btnARight.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent evt) {
						button1WidgetSelected(evt);
					}
				});
			}
			{
				btnALeft = new Button(this, SWT.PUSH | SWT.CENTER);
				FormData button2LData1 = new FormData();
				button2LData1.left =  new FormAttachment(0, 1000, 82);
				button2LData1.top =  new FormAttachment(0, 1000, 204);
				button2LData1.width = 40;
				button2LData1.height = 31;
				btnALeft.setLayoutData(button2LData1);
				Image image = new Image(null, new FileInputStream("src/images/LeftArrow.png")) ;
				Image scaledImage = new Image(display, image.getImageData().scaledTo(35, 25)) ;
				btnALeft.setImage(scaledImage);
				btnALeft.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent evt) {
						button1WidgetSelected(evt);
					}
				});
			}
			{
				btnARed = new Button(this, SWT.PUSH | SWT.CENTER);
				FormData button3LData = new FormData();
				button3LData.left =  new FormAttachment(0, 1000, 133);
				button3LData.top =  new FormAttachment(0, 1000, 204);
				button3LData.width = 40;
				button3LData.height = 31;
				btnARed.setLayoutData(button3LData);
				Image image = new Image(null, new FileInputStream("src/images/RedBox.png")) ;
				Image scaledImage = new Image(display, image.getImageData().scaledTo(35, 25)) ;
				btnARed.setImage(scaledImage);
				btnARed.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent evt) {
						button1WidgetSelected(evt);
					}
				});
			}
			{
				btnAYellow = new Button(this, SWT.PUSH | SWT.CENTER);
				FormData button4LData = new FormData();
				button4LData.left =  new FormAttachment(0, 1000, 184);
				button4LData.top =  new FormAttachment(0, 1000, 204);
				button4LData.width = 40;
				button4LData.height = 31;
				btnAYellow.setLayoutData(button4LData);
				Image image = new Image(null, new FileInputStream("src/images/YellowBox.png")) ;
				Image scaledImage = new Image(display, image.getImageData().scaledTo(35, 25)) ;
				btnAYellow.setImage(scaledImage);
				btnAYellow.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent evt) {
						button1WidgetSelected(evt);
					}
				});
			}
			{
				btnBRight = new Button(this, SWT.PUSH | SWT.CENTER);
				FormData button5LData = new FormData();
				button5LData.left =  new FormAttachment(0, 1000, 32);
				button5LData.top =  new FormAttachment(0, 1000, 250);
				button5LData.width = 40;
				button5LData.height = 31;
				btnBRight.setLayoutData(button5LData);
				Image image = new Image(null, new FileInputStream("src/images/RightArrow.png")) ;
				Image scaledImage = new Image(display, image.getImageData().scaledTo(35, 25)) ;
				btnBRight.setImage(scaledImage) ;
				btnBRight.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent evt) {
						button1WidgetSelected(evt);
					}
				});
			}
			{
				btnBLeft = new Button(this, SWT.PUSH | SWT.CENTER);
				FormData button6LData = new FormData();
				button6LData.left =  new FormAttachment(0, 1000, 82);
				button6LData.top =  new FormAttachment(0, 1000, 250);
				button6LData.width = 40;
				button6LData.height = 31;
				btnBLeft.setLayoutData(button6LData);
				Image image = new Image(null, new FileInputStream("src/images/LeftArrow.png")) ;
				Image scaledImage = new Image(display, image.getImageData().scaledTo(35, 25)) ;
				btnBLeft.setImage(scaledImage);
				btnBLeft.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent evt) {
						button1WidgetSelected(evt);
					}
				});
			}
			{
				btnBRed = new Button(this, SWT.PUSH | SWT.CENTER);
				FormData button7LData = new FormData();
				button7LData.left =  new FormAttachment(0, 1000, 133);
				button7LData.top =  new FormAttachment(0, 1000, 250);
				button7LData.width = 40;
				button7LData.height = 31;
				btnBRed.setLayoutData(button7LData);
				Image image = new Image(null, new FileInputStream("src/images/RedBox.png")) ;
				Image scaledImage = new Image(display, image.getImageData().scaledTo(35, 25)) ;
				btnBRed.setImage(scaledImage);
				btnBRed.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent evt) {
						button1WidgetSelected(evt);
					}
				});
			}
			{
				btnBYellow = new Button(this, SWT.PUSH | SWT.CENTER);
				FormData button8LData = new FormData();
				button8LData.left =  new FormAttachment(0, 1000, 184);
				button8LData.top =  new FormAttachment(0, 1000, 250);
				button8LData.width = 40;
				button8LData.height = 31;
				btnBYellow.setLayoutData(button8LData);
				Image image = new Image(null, new FileInputStream("src/images/YellowBox.png")) ;
				Image scaledImage = new Image(display, image.getImageData().scaledTo(35, 25)) ;
				btnBYellow.setImage(scaledImage);
				btnBYellow.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent evt) {
						button1WidgetSelected(evt);
					}
				});
			}
			{
				FormData scale2LData = new FormData();
				scale2LData.left =  new FormAttachment(0, 1000, 228);
				scale2LData.top =  new FormAttachment(0, 1000, 245);
				scale2LData.width = 158;
				scale2LData.height = 42;
				scaleB = new Scale(this, SWT.NONE);
				scaleB.setLayoutData(scale2LData);
				scaleB.setSelection(50) ;
			}
			{
				FormData scale3LData = new FormData();
				scale3LData.left =  new FormAttachment(0, 1000, 228);
				scale3LData.top =  new FormAttachment(0, 1000, 293);
				scale3LData.width = 158;
				scale3LData.height = 42;
				scaleC = new Scale(this, SWT.NONE);
				scaleC.setLayoutData(scale3LData);
				scaleC.setSelection(50) ;
			}
			{
				btnCRight = new Button(this, SWT.PUSH | SWT.CENTER);
				FormData button9LData = new FormData();
				button9LData.left =  new FormAttachment(0, 1000, 32);
				button9LData.top =  new FormAttachment(0, 1000, 298);
				button9LData.width = 40;
				button9LData.height = 31;
				btnCRight.setLayoutData(button9LData);
				Image image = new Image(null, new FileInputStream("src/images/RightArrow.png")) ;
				Image scaledImage = new Image(display, image.getImageData().scaledTo(35, 25)) ;
				btnCRight.setImage(scaledImage) ;
				btnCRight.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent evt) {
						button1WidgetSelected(evt);
					}
				});
			}
			{
				btnCLeft = new Button(this, SWT.PUSH | SWT.CENTER);
				FormData button10LData = new FormData();
				button10LData.left =  new FormAttachment(0, 1000, 82);
				button10LData.top =  new FormAttachment(0, 1000, 298);
				button10LData.width = 40;
				button10LData.height = 31;
				btnCLeft.setLayoutData(button10LData);
				Image image = new Image(null, new FileInputStream("src/images/LeftArrow.png")) ;
				Image scaledImage = new Image(display, image.getImageData().scaledTo(35, 25)) ;
				btnCLeft.setImage(scaledImage);
				btnCLeft.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent evt) {
						button1WidgetSelected(evt);
					}
				});
			}
			{
				btnCRed = new Button(this, SWT.PUSH | SWT.CENTER);
				FormData button11LData = new FormData();
				button11LData.left =  new FormAttachment(0, 1000, 133);
				button11LData.top =  new FormAttachment(0, 1000, 298);
				button11LData.width = 40;
				button11LData.height = 31;
				btnCRed.setLayoutData(button11LData);
				Image image = new Image(null, new FileInputStream("src/images/RedBox.png")) ;
				Image scaledImage = new Image(display, image.getImageData().scaledTo(35, 25)) ;
				btnCRed.setImage(scaledImage);
				btnCRed.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent evt) {
						button1WidgetSelected(evt);
					}
				});
			}
			{
				btnCYellow = new Button(this, SWT.PUSH | SWT.CENTER);
				FormData button12LData = new FormData();
				button12LData.left =  new FormAttachment(0, 1000, 184);
				button12LData.top =  new FormAttachment(0, 1000, 298);
				button12LData.width = 40;
				button12LData.height = 31;
				btnCYellow.setLayoutData(button12LData);
				Image image = new Image(null, new FileInputStream("src/images/YellowBox.png")) ;
				Image scaledImage = new Image(display, image.getImageData().scaledTo(35, 25)) ;
				btnCYellow.setImage(scaledImage);
				btnCYellow.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent evt) {
						button1WidgetSelected(evt);
					}
				});
			}
			{
				label1 = new Label(this, SWT.NONE);
				FormData label1LData = new FormData();
				label1LData.left =  new FormAttachment(0, 1000, 12);
				label1LData.top =  new FormAttachment(0, 1000, 26);
				label1LData.width = 14;
				label1LData.height = 26;
				label1.setLayoutData(label1LData);
				label1.setText("1");
				label1.setFont(new org.eclipse.swt.graphics.Font(display,"Courier New",14, SWT.NORMAL ));
			}
			{
				label2 = new Label(this, SWT.NONE);
				FormData label2LData = new FormData();
				label2LData.left =  new FormAttachment(0, 1000, 12);
				label2LData.top =  new FormAttachment(0, 1000, 64);
				label2LData.width = 14;
				label2LData.height = 26;
				label2.setLayoutData(label2LData);
				label2.setText("2");
				label2.setFont(new org.eclipse.swt.graphics.Font(display,"Courier New",14, SWT.NORMAL ));
			}
			{
				label3 = new Label(this, SWT.NONE);
				FormData label3LData = new FormData();
				label3LData.left =  new FormAttachment(0, 1000, 12);
				label3LData.top =  new FormAttachment(0, 1000, 102);
				label3LData.width = 14;
				label3LData.height = 26;
				label3.setLayoutData(label3LData);
				label3.setText("3");
				label3.setFont(new org.eclipse.swt.graphics.Font(display,"Courier New",14, SWT.NORMAL ));
			}
			{
				label4 = new Label(this, SWT.NONE);
				FormData label4LData = new FormData();
				label4LData.left =  new FormAttachment(0, 1000, 12);
				label4LData.top =  new FormAttachment(0, 1000, 140);
				label4LData.width = 14;
				label4LData.height = 26;
				label4.setLayoutData(label4LData);
				label4.setText("4");
				label4.setFont(new org.eclipse.swt.graphics.Font(display,"Courier New",14, SWT.NORMAL ));
			}
			{
				label5 = new Label(this, SWT.NONE);
				FormData label5LData = new FormData();
				label5LData.left =  new FormAttachment(0, 1000, 12);
				label5LData.top =  new FormAttachment(0, 1000, 204);
				label5LData.width = 14;
				label5LData.height = 26;
				label5.setLayoutData(label5LData);
				label5.setText("A");
				label5.setFont(new org.eclipse.swt.graphics.Font(display,"Courier New",14, SWT.NORMAL ));
			}
			{
				label6 = new Label(this, SWT.NONE);
				FormData label6LData = new FormData();
				label6LData.left =  new FormAttachment(0, 1000, 12);
				label6LData.top =  new FormAttachment(0, 1000, 250);
				label6LData.width = 14;
				label6LData.height = 26;
				label6.setLayoutData(label6LData);
				label6.setText("B");
				label6.setFont(new org.eclipse.swt.graphics.Font(display,"Courier New",14, SWT.NORMAL ));
			}
			{
				label7 = new Label(this, SWT.NONE);
				FormData label7LData = new FormData();
				label7LData.left =  new FormAttachment(0, 1000, 12);
				label7LData.top =  new FormAttachment(0, 1000, 298);
				label7LData.width = 14;
				label7LData.height = 26;
				label7.setLayoutData(label7LData);
				label7.setText("C");
				label7.setFont(new org.eclipse.swt.graphics.Font(display,"Courier New",14, SWT.NORMAL ));
			}
			{
				label8 = new Label(this, SWT.NONE);
				FormData label8LData = new FormData();
				label8LData.left =  new FormAttachment(0, 1000, 32);
				label8LData.top =  new FormAttachment(0, 1000, 172);
				label8LData.width = 90;
				label8LData.height = 26;
				label8.setLayoutData(label8LData);
				label8.setText("Motors");
				label8.setFont(new org.eclipse.swt.graphics.Font(display,"Courier New",14, SWT.NORMAL ));
			}
			{
				label9 = new Label(this, SWT.NONE);
				FormData label9LData = new FormData();
				label9LData.left =  new FormAttachment(0, 1000, 32);
				label9LData.top =  new FormAttachment(0, 1000, -3);
				label9LData.width = 90;
				label9LData.height = 26;
				label9.setLayoutData(label9LData);
				label9.setText("Sensors");
				label9.setFont(new org.eclipse.swt.graphics.Font(display,"Courier New",14, SWT.NORMAL ));
			}

			allButtons.add(btnARight) ;
			allButtons.add(btnALeft) ;
			allButtons.add(btnARed) ;
			allButtons.add(btnAYellow) ;
			allButtons.add(btnBRight) ;
			allButtons.add(btnBLeft) ;
			allButtons.add(btnBRed) ;
			allButtons.add(btnBYellow) ;
			allButtons.add(btnCRight) ;
			allButtons.add(btnCLeft) ;
			allButtons.add(btnCRed) ;
			allButtons.add(btnCYellow) ;
			allScale.add(scaleA) ;
			allScale.add(scaleB) ;
			allScale.add(scaleC) ;
			
			this.layout();
			pack();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void button1WidgetSelected(SelectionEvent evt) {
		Button btn = (Button)evt.getSource() ;
		int idx = allButtons.indexOf(btn) ;
		int rowNum = idx/4 ;
		String Row[] = {"A", "B", "C"} ;
		System.out.println("button.widgetSelected, event="+evt);
		System.out.println("button Number : " 
				+ Row[rowNum] + Integer.toString(idx%4) + " Scale" + Row[rowNum] + ":" + Integer.toString(allScale.get(rowNum).getSelection()));
	}
}


