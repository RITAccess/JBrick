package com.jbricx.ui.piano;

import java.io.FileInputStream;
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
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Scale;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.SWT;


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
public class PianoComposite extends org.eclipse.swt.widgets.Composite {
	private Label label1;
	private Label label11;
	private Button onebysixteen;
	private Label dash2;
	private Label dash1;
	private Composite composite3;
	private Button onebytwo;
	private Button onebyone;
	private Button nxtMelody;
	private Button forth;
	private Button nxc;
	private Composite composite2;
	private Button nbc;
	private Button java;
	private Composite composite1;
	private Button pascal;
	private Button c;
	private Button lasm;
	private Button mindScript;
	private Button nqc;
	private Group language;
	private Button help;
	private List list2;
	private Label waitTime;
	private List list1;
	private Label noteTime;
	private Button save;
	private Button play;
	private Button copy;
	private Button clear;
	private Group length;
	private Button rest;
	private Label transpose_label;
	private Label label24;
	private Scale transpose;
	private Label label23;
	private Label label22;
	private Label label21;
	private Label label20;
	private Label label19;
	private Label label18;
	private Label label17;
	private Label label16;
	private Label label3;
	private Label label15;
	private Label label14;
	private Label label13;
	private Label label12;
	private Label label10;
	private Label label9;
	private Label label8;
	private Label label7;
	private Label label6;
	private Label label5;
	private Label label4;
	private Label label2;

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
		PianoComposite inst = new PianoComposite(shell, SWT.NULL);
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

	public PianoComposite(org.eclipse.swt.widgets.Composite parent, int style) {
		super(parent, style);
		initGUI();
	}

	private void initGUI() {
		try {
			FormLayout thisLayout = new FormLayout();
			this.setLayout(thisLayout);
			{
				composite3 = new Composite(this, SWT.NONE);
				GridLayout composite3Layout = new GridLayout();
				composite3Layout.makeColumnsEqualWidth = true;
				FormData composite3LData = new FormData();
				composite3LData.left =  new FormAttachment(0, 1000, 223);
				composite3LData.top =  new FormAttachment(0, 1000, 286);
				composite3LData.width = 92;
				composite3LData.height = 81;
				composite3.setLayoutData(composite3LData);
				composite3.setLayout(composite3Layout);
				{
					dash1 = new Label(composite3, SWT.NONE);
					GridData dash1LData = new GridData();
					dash1LData.widthHint = 69;
					dash1LData.heightHint = 21;
					dash1.setLayoutData(dash1LData);
					dash1.setText("__");
				}
				{
					dash2 = new Label(composite3, SWT.NONE);
					GridData dash2LData = new GridData();
					dash2LData.widthHint = 50;
					dash2LData.heightHint = 23;
					dash2.setLayoutData(dash2LData);
					dash2.setText("__");
				}
				{
					onebysixteen = new Button(composite3, SWT.RADIO | SWT.LEFT);
					GridData onebysixteenLData = new GridData();
					onebysixteenLData.widthHint = 79;
					onebysixteenLData.heightHint = 20;
					onebysixteen.setLayoutData(onebysixteenLData);
					onebysixteen.setText("1/16");
				}
			}
			{
				composite2 = new Composite(this, SWT.NONE);
				GridLayout composite2Layout = new GridLayout();
				composite2Layout.makeColumnsEqualWidth = true;
				FormData composite2LData = new FormData();
				composite2LData.left =  new FormAttachment(0, 1000, 442);
				composite2LData.top =  new FormAttachment(0, 1000, 443);
				composite2LData.width = 122;
				composite2LData.height = 112;
				composite2.setLayoutData(composite2LData);
				composite2.setLayout(composite2Layout);
				{
					nxc = new Button(composite2, SWT.RADIO | SWT.LEFT);
					GridData nxcLData = new GridData();
					nxcLData.widthHint = 91;
					nxcLData.heightHint = 27;
					nxc.setLayoutData(nxcLData);
					nxc.setText("NXC");
				}
				{
					nxtMelody = new Button(composite2, SWT.RADIO | SWT.LEFT);
					GridData nxtMelodyLData = new GridData();
					nxtMelodyLData.widthHint = 95;
					nxtMelodyLData.heightHint = 21;
					nxtMelody.setLayoutData(nxtMelodyLData);
					nxtMelody.setText("NXT Melody");
				}
			}
			{
				composite1 = new Composite(this, SWT.NONE);
				GridLayout composite1Layout = new GridLayout();
				composite1Layout.makeColumnsEqualWidth = true;
				FormData composite1LData = new FormData();
				composite1LData.left =  new FormAttachment(0, 1000, 262);
				composite1LData.top =  new FormAttachment(0, 1000, 443);
				composite1LData.width = 134;
				composite1LData.height = 118;
				composite1.setLayoutData(composite1LData);
				composite1.setLayout(composite1Layout);
				{
					pascal = new Button(composite1, SWT.RADIO | SWT.LEFT);
					GridData pascalLData = new GridData();
					pascalLData.widthHint = 97;
					pascalLData.heightHint = 21;
					pascal.setLayoutData(pascalLData);
					pascal.setText("Pascal");
				}
				{
					forth = new Button(composite1, SWT.RADIO | SWT.LEFT);
					GridData forthLData = new GridData();
					forthLData.widthHint = 88;
					forthLData.heightHint = 26;
					forth.setLayoutData(forthLData);
					forth.setText("Forth");
				}
				{
					java = new Button(composite1, SWT.RADIO | SWT.LEFT);
					GridData javaLData = new GridData();
					javaLData.widthHint = 73;
					javaLData.heightHint = 24;
					java.setLayoutData(javaLData);
					java.setText("Java");
				}
				{
					nbc = new Button(composite1, SWT.RADIO | SWT.LEFT);
					GridData nbcLData = new GridData();
					nbcLData.widthHint = 93;
					nbcLData.heightHint = 25;
					nbc.setLayoutData(nbcLData);
					nbc.setText("NBC");
				}
			}
			{
				language = new Group(this, SWT.NONE);
				GridLayout languageLayout = new GridLayout();
				languageLayout.makeColumnsEqualWidth = true;
				language.setLayout(languageLayout);
				FormData languageLData = new FormData();
				languageLData.left =  new FormAttachment(0, 1000, 32);
				languageLData.top =  new FormAttachment(0, 1000, 430);
				languageLData.width = 637;
				languageLData.height = 124;
				language.setLayoutData(languageLData);
				language.setText("Language");
				{
					nqc = new Button(language, SWT.RADIO | SWT.LEFT);
					GridData nqcLData = new GridData();
					nqcLData.widthHint = 107;
					nqcLData.heightHint = 22;
					nqc.setLayoutData(nqcLData);
					nqc.setText("NQC");
				}
				{
					mindScript = new Button(language, SWT.RADIO | SWT.LEFT);
					GridData mindScriptLData = new GridData();
					mindScriptLData.widthHint = 111;
					mindScriptLData.heightHint = 25;
					mindScript.setLayoutData(mindScriptLData);
					mindScript.setText("MindScript");
				}
				{
					lasm = new Button(language, SWT.RADIO | SWT.LEFT);
					GridData lasmLData = new GridData();
					lasmLData.widthHint = 102;
					lasmLData.heightHint = 22;
					lasm.setLayoutData(lasmLData);
					lasm.setText("LASM");
				}
				{
					c = new Button(language, SWT.RADIO | SWT.LEFT);
					GridData cLData = new GridData();
					cLData.widthHint = 83;
					cLData.heightHint = 24;
					c.setLayoutData(cLData);
					c.setText("C");
				}
				
				
			}
			{
				help = new Button(this, SWT.PUSH | SWT.CENTER);
				FormData helpLData = new FormData();
				helpLData.left =  new FormAttachment(0, 1000, 577);
				helpLData.top =  new FormAttachment(0, 1000, 384);
				helpLData.width = 100;
				helpLData.height = 27;
				help.setLayoutData(helpLData);
				help.setText("Help");
			}
			{
				waitTime = new Label(this, SWT.NONE);
				FormData waitTimeLData = new FormData();
				waitTimeLData.left =  new FormAttachment(0, 1000, 315);
				waitTimeLData.top =  new FormAttachment(0, 1000, 389);
				waitTimeLData.width = 64;
				waitTimeLData.height = 17;
				waitTime.setLayoutData(waitTimeLData);
				waitTime.setText("Wait Time");
			}
			{
				FormData list1LData = new FormData();
				list1LData.left =  new FormAttachment(0, 1000, 156);
				list1LData.top =  new FormAttachment(0, 1000, 384);
				list1LData.width = 96;
				list1LData.height = 22;
				list1 = new List(this, SWT.NONE);
				list1.setLayoutData(list1LData);
			}
			{
				noteTime = new Label(this, SWT.NONE);
				FormData noteTimeLData = new FormData();
				noteTimeLData.left =  new FormAttachment(0, 1000, 38);
				noteTimeLData.top =  new FormAttachment(0, 1000, 389);
				noteTimeLData.width = 65;
				noteTimeLData.height = 17;
				noteTime.setLayoutData(noteTimeLData);
				noteTime.setText("Note time");
			}
			{
				save = new Button(this, SWT.PUSH | SWT.CENTER);
				FormData saveLData = new FormData();
				saveLData.left =  new FormAttachment(0, 1000, 577);
				saveLData.top =  new FormAttachment(0, 1000, 332);
				saveLData.width = 100;
				saveLData.height = 30;
				save.setLayoutData(saveLData);
				save.setText("Save");
			}
			{
				play = new Button(this, SWT.PUSH | SWT.CENTER);
				FormData playLData = new FormData();
				playLData.left =  new FormAttachment(0, 1000, 403);
				playLData.top =  new FormAttachment(0, 1000, 323);
				playLData.width = 107;
				playLData.height = 30;
				play.setLayoutData(playLData);
				play.setText("Play");
			}
			{
				copy = new Button(this, SWT.PUSH | SWT.CENTER);
				FormData copyLData = new FormData();
				copyLData.left =  new FormAttachment(0, 1000, 577);
				copyLData.top =  new FormAttachment(0, 1000, 280);
				copyLData.width = 100;
				copyLData.height = 31;
				copy.setLayoutData(copyLData);
				copy.setText("Copy");
			}
			{
				clear = new Button(this, SWT.PUSH | SWT.CENTER);
				FormData clearLData = new FormData();
				clearLData.left =  new FormAttachment(0, 1000, 403);
				clearLData.top =  new FormAttachment(0, 1000, 280);
				clearLData.width = 107;
				clearLData.height = 31;
				clear.setLayoutData(clearLData);
				clear.setText("Clear");
			}
			{
				length = new Group(this, SWT.NONE);
				GridLayout lengthLayout = new GridLayout();
				lengthLayout.makeColumnsEqualWidth = true;
				length.setLayout(lengthLayout);
				FormData lengthLData = new FormData();
				lengthLData.left =  new FormAttachment(0, 1000, 124);
				lengthLData.top =  new FormAttachment(0, 1000, 276);
				lengthLData.width = 200;
				lengthLData.height = 80;
				length.setLayoutData(lengthLData);
				length.setText("Length");
				{
					onebyone = new Button(length, SWT.RADIO | SWT.LEFT);
					GridData onebyoneLData = new GridData();
					onebyoneLData.widthHint = 98;
					onebyoneLData.heightHint = 19;
					onebyone.setLayoutData(onebyoneLData);
					onebyone.setText("1/1");
				}
				{
					onebytwo = new Button(length, SWT.RADIO | SWT.LEFT);
					GridData onebytwoLData = new GridData();
					onebytwoLData.widthHint = 62;
					onebytwoLData.heightHint = 22;
					onebytwo.setLayoutData(onebytwoLData);
					onebytwo.setText("1/2");
				}
			}
			{
				rest = new Button(this, SWT.PUSH | SWT.CENTER);
				FormData restLData = new FormData();
				restLData.left =  new FormAttachment(0, 1000, 32);
				restLData.top =  new FormAttachment(0, 1000, 276);
				restLData.width = 36;
				restLData.height = 80;
				rest.setLayoutData(restLData);
				rest.setText("Rest");
			}
			{
				transpose_label = new Label(this, SWT.NONE);
				FormData transpose_labelLData = new FormData();
				transpose_labelLData.width = 98;
				transpose_labelLData.height = 22;
				transpose_labelLData.right =  new FormAttachment(1000, 1000, -580);
				transpose_labelLData.left =  new FormAttachment(0, 1000, 32);
				transpose_labelLData.bottom =  new FormAttachment(1000, 1000, -375);
				transpose_label.setLayoutData(transpose_labelLData);
				transpose_label.setText("Transpose");
			}
			{
				FormData transposeLData = new FormData();
				transposeLData.left =  new FormAttachment(0, 1000, 32);
				transposeLData.top =  new FormAttachment(0, 1000, 225);
				transposeLData.width = 652;
				transposeLData.height = 34;
				transpose = new Scale(this, SWT.NONE);
				transpose.setLayoutData(transposeLData);
			}
			{
				label24 = new Label(this, SWT.NONE);
				label24.setText("label24");
				FormData label24LData = new FormData();
				label24LData.left =  new FormAttachment(0, 1000, 623);
				label24LData.top =  new FormAttachment(0, 1000, 41);
				label24LData.width = 28;
				label24LData.height = 94;
				label24.setLayoutData(label24LData);
				label24.setImage(new Image(null, new FileInputStream("src/images/black_key.PNG")));
			}
			{
				label23 = new Label(this, SWT.NONE);
				label23.setText("label23");
				FormData label23LData = new FormData();
				label23LData.left =  new FormAttachment(0, 1000, 577);
				label23LData.top =  new FormAttachment(0, 1000, 41);
				label23LData.width = 28;
				label23LData.height = 94;
				label23.setLayoutData(label23LData);
				label23.setImage(new Image(null, new FileInputStream("src/images/black_key.PNG")));
			}
			{
				label22 = new Label(this, SWT.NONE);
				label22.setText("label22");
				FormData label22LData = new FormData();
				label22LData.left =  new FormAttachment(0, 1000, 483);
				label22LData.top =  new FormAttachment(0, 1000, 41);
				label22LData.width = 28;
				label22LData.height = 94;
				label22.setLayoutData(label22LData);
				label22.setImage(new Image(null, new FileInputStream("src/images/black_key.PNG")));
			}
			{
				label21 = new Label(this, SWT.NONE);
				label21.setText("label21");
				FormData label21LData = new FormData();
				label21LData.left =  new FormAttachment(0, 1000, 436);
				label21LData.top =  new FormAttachment(0, 1000, 41);
				label21LData.width = 28;
				label21LData.height = 94;
				label21.setLayoutData(label21LData);
				label21.setImage(new Image(null, new FileInputStream("src/images/black_key.PNG")));
			}
			{
				label20 = new Label(this, SWT.NONE);
				label20.setText("label20");
				FormData label20LData = new FormData();
				label20LData.left =  new FormAttachment(0, 1000, 389);
				label20LData.top =  new FormAttachment(0, 1000, 41);
				label20LData.width = 28;
				label20LData.height = 94;
				label20.setLayoutData(label20LData);
				label20.setImage(new Image(null, new FileInputStream("src/images/black_key.PNG")));
			}
			{
				label19 = new Label(this, SWT.NONE);
				label19.setText("label19");
				FormData label19LData = new FormData();
				label19LData.left =  new FormAttachment(0, 1000, 295);
				label19LData.top =  new FormAttachment(0, 1000, 41);
				label19LData.width = 28;
				label19LData.height = 94;
				label19.setLayoutData(label19LData);
				label19.setImage(new Image(null, new FileInputStream("src/images/black_key.PNG")));
			}
			{
				label18 = new Label(this, SWT.NONE);
				label18.setText("label18");
				FormData label18LData = new FormData();
				label18LData.left =  new FormAttachment(0, 1000, 248);
				label18LData.top =  new FormAttachment(0, 1000, 41);
				label18LData.width = 28;
				label18LData.height = 94;
				label18.setLayoutData(label18LData);
				label18.setImage(new Image(null, new FileInputStream("src/images/black_key.PNG")));
			}
			{
				label17 = new Label(this, SWT.NONE);
				label17.setText("label17");
				FormData label17LData = new FormData();
				label17LData.left =  new FormAttachment(0, 1000, 156);
				label17LData.top =  new FormAttachment(0, 1000, 41);
				label17LData.width = 28;
				label17LData.height = 94;
				label17.setLayoutData(label17LData);
				label17.setImage(new Image(null, new FileInputStream("src/images/black_key.PNG")));
			}
			{
				label16 = new Label(this, SWT.NONE);
				label16.setText("label16");
				FormData label16LData = new FormData();
				label16LData.left =  new FormAttachment(0, 1000, 111);
				label16LData.top =  new FormAttachment(0, 1000, 41);
				label16LData.width = 28;
				label16LData.height = 94;
				label16.setLayoutData(label16LData);
				label16.setImage(new Image(null, new FileInputStream("src/images/black_key.PNG")));
			}
			{
				label3 = new Label(this, SWT.NONE);
				label3.setText("label3");
				FormData label3LData = new FormData();
				label3LData.left =  new FormAttachment(0, 1000, 64);
				label3LData.top =  new FormAttachment(0, 1000, 41);
				label3LData.width = 28;
				label3LData.height = 94;
				label3.setLayoutData(label3LData);
				label3.setImage(new Image(null, new FileInputStream("src/images/black_key.PNG")));
			}
			{
				label2 = new Label(this, SWT.NONE);
				label2.setText("label2");
				FormData label2LData = new FormData();
				label2LData.left =  new FormAttachment(0, 1000, 78);
				label2LData.top =  new FormAttachment(0, 1000, 41);
				label2LData.width = 47;
				label2LData.height = 138;
				label2.setLayoutData(label2LData);
				label2.setImage(new Image(null, new FileInputStream("src/images/white_key.PNG")));
			}
			{
				label1 = new Label(this, SWT.NONE);
				label1.setText("label1");
				FormData label1LData = new FormData();
				label1LData.left =  new FormAttachment(0, 1000, 32);
				label1LData.top =  new FormAttachment(0, 1000, 41);
				label1LData.width = 47;
				label1LData.height = 138;
				label1.setLayoutData(label1LData);
				label1.setImage(new Image(null, new FileInputStream("src/images/white_key.PNG")));
			}
			{
				label4 = new Label(this, SWT.NONE);
				label4.setText("label2");
				FormData label4LData = new FormData();
				label4LData.left =  new FormAttachment(0, 1000, 124);
				label4LData.top =  new FormAttachment(0, 1000, 41);
				label4LData.width = 47;
				label4LData.height = 138;
				label4.setLayoutData(label4LData);
				label4.setImage(new Image(null, new FileInputStream("src/images/white_key.PNG")));
			}
			{
				label5 = new Label(this, SWT.NONE);
				label5.setText("label2");
				FormData label5LData = new FormData();
				label5LData.left =  new FormAttachment(0, 1000, 169);
				label5LData.top =  new FormAttachment(0, 1000, 41);
				label5LData.width = 47;
				label5LData.height = 138;
				label5.setLayoutData(label5LData);
				label5.setImage(new Image(null, new FileInputStream("src/images/white_key.PNG")));
			}
			{
				label6 = new Label(this, SWT.NONE);
				label6.setText("label2");
				FormData label6LData = new FormData();
				label6LData.left =  new FormAttachment(0, 1000, 216);
				label6LData.top =  new FormAttachment(0, 1000, 41);
				label6LData.width = 47;
				label6LData.height = 138;
				label6.setLayoutData(label6LData);
				label6.setImage(new Image(null, new FileInputStream("src/images/white_key.PNG")));
			}
			{
				label7 = new Label(this, SWT.NONE);
				label7.setText("label2");
				FormData label7LData = new FormData();
				label7LData.left =  new FormAttachment(0, 1000, 262);
				label7LData.top =  new FormAttachment(0, 1000, 41);
				label7LData.width = 47;
				label7LData.height = 138;
				label7.setLayoutData(label7LData);
				label7.setImage(new Image(null, new FileInputStream("src/images/white_key.PNG")));
			}
			{
				label8 = new Label(this, SWT.NONE);
				label8.setText("label2");
				FormData label8LData = new FormData();
				label8LData.left =  new FormAttachment(0, 1000, 309);
				label8LData.top =  new FormAttachment(0, 1000, 41);
				label8LData.width = 47;
				label8LData.height = 138;
				label8.setLayoutData(label8LData);
				label8.setImage(new Image(null, new FileInputStream("src/images/white_key.PNG")));
			}
			{
				label9 = new Label(this, SWT.NONE);
				label9.setText("label2");
				FormData label9LData = new FormData();
				label9LData.left =  new FormAttachment(0, 1000, 356);
				label9LData.top =  new FormAttachment(0, 1000, 41);
				label9LData.width = 47;
				label9LData.height = 138;
				label9.setLayoutData(label9LData);
				label9.setImage(new Image(null, new FileInputStream("src/images/white_key.PNG")));
			}
			{
				label10 = new Label(this, SWT.NONE);
				label10.setText("label2");
				FormData label10LData = new FormData();
				label10LData.left =  new FormAttachment(0, 1000, 403);
				label10LData.top =  new FormAttachment(0, 1000, 41);
				label10LData.width = 47;
				label10LData.height = 138;
				label10.setLayoutData(label10LData);
				label10.setImage(new Image(null, new FileInputStream("src/images/white_key.PNG")));
			}
			{
				label11 = new Label(this, SWT.NONE);
				label11.setText("label2");
				FormData label11LData = new FormData();
				label11LData.left =  new FormAttachment(0, 1000, 449);
				label11LData.top =  new FormAttachment(0, 1000, 41);
				label11LData.width = 47;
				label11LData.height = 138;
				label11.setLayoutData(label11LData);
				label11.setImage(new Image(null, new FileInputStream("src/images/white_key.PNG")));
			}
			{
				label12 = new Label(this, SWT.NONE);
				label12.setText("label2");
				FormData label12LData = new FormData();
				label12LData.left =  new FormAttachment(0, 1000, 496);
				label12LData.top =  new FormAttachment(0, 1000, 41);
				label12LData.width = 47;
				label12LData.height = 138;
				label12.setLayoutData(label12LData);
				label12.setImage(new Image(null, new FileInputStream("src/images/white_key.PNG")));
			}
			{
				label13 = new Label(this, SWT.NONE);
				label13.setText("label2");
				FormData label13LData = new FormData();
				label13LData.left =  new FormAttachment(0, 1000, 543);
				label13LData.top =  new FormAttachment(0, 1000, 41);
				label13LData.width = 47;
				label13LData.height = 138;
				label13.setLayoutData(label13LData);
				label13.setImage(new Image(null, new FileInputStream("src/images/white_key.PNG")));
			}
			{
				label14 = new Label(this, SWT.NONE);
				label14.setText("label2");
				FormData label14LData = new FormData();
				label14LData.left =  new FormAttachment(0, 1000, 590);
				label14LData.top =  new FormAttachment(0, 1000, 41);
				label14LData.width = 47;
				label14LData.height = 138;
				label14.setLayoutData(label14LData);
				label14.setImage(new Image(null, new FileInputStream("src/images/white_key.PNG")));
			}
			{
				label15 = new Label(this, SWT.NONE);
				label15.setText("label2");
				FormData label15LData = new FormData();
				label15LData.left =  new FormAttachment(0, 1000, 637);
				label15LData.top =  new FormAttachment(0, 1000, 41);
				label15LData.width = 47;
				label15LData.height = 138;
				label15.setLayoutData(label15LData);
				label15.setImage(new Image(null, new FileInputStream("src/images/white_key.PNG")));
			}
			{
				FormData list2LData = new FormData();
				list2LData.left =  new FormAttachment(0, 1000, 403);
				list2LData.top =  new FormAttachment(0, 1000, 384);
				list2LData.width = 96;
				list2LData.height = 22;
				list2 = new List(this, SWT.NONE);
				list2.setLayoutData(list2LData);
			}
			this.layout();
			pack();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
}
