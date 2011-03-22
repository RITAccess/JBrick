package com.jbricx.ui.piano;

//import java.awt.Color;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.io.File;
import java.util.ArrayList;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
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
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Scale;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.jbricx.actions.HelpContentAction;
import com.jbricx.communications.NXTGadgetManager;
import com.jbricx.communications.NXTManager;
import com.jbricx.pjo.FileExtensionConstants;
import com.jbricx.ui.JBrickManager;

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
public class PianoComposite extends Composite {
	
  private Label label1;
  private Label label11;
  private Label waitTimeLabel;
  private Label noteLengthLabel;
  private Text noteLength;
  private Button onebysixteen;
  private Label dash2;
  private Label dash1;
  private Composite composite3;
  private Button onebytwo;
  private Button onebyone;
  private Button onebyfour;
  private Button onebyeight;
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
  private List list22;
  private Text waitTime;
  private List list1;
  private Label noteTime;
  private Button save;
  private Button play;
  private Button copy;
  private Button clear;
  private Group length;
  private Button rest;
  private Label transpose_label;
  private Scale transpose;
  private static Shell shell;
  private static Display display;
  private ArrayList<Label> whiteKeysArray = new ArrayList<Label>();
  private ArrayList<Label> blackKeysArray = new ArrayList<Label>();
  private ArrayList<Label> whiteKeyLabelArray = new ArrayList<Label>();
  private ArrayList<Label> blackKeyLabelsArray = new ArrayList<Label>();
  private static final boolean USE_BRICK = true;
  private static NXTGadgetManager nxt;
  private static PianoComposite myPianoComp;
  int noteLengthDiv = 4;
  int transposeMult = 3;
  int toneDuration = 1000;
  private JBrickManager manager;
  public static boolean disabuttons = false;

  
  private PianoRecording recording = new PianoRecording();
  
  private HelpContentAction helpAction = new HelpContentAction();
  
  private KeyListener pianoKeyListener = new KeyListener() {

    @Override
    public void keyReleased(KeyEvent arg0) {
      pianoKeyPressed(arg0.character, false);
    }

    @Override
    public void keyPressed(KeyEvent arg0) {
      pianoKeyPressed(arg0.character, true);
    }
  };

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
  
  public static void disableButtons(){
      System.out.println("====== disableButtons =======");
      disabuttons = true;
    }
  public static void enableButtons(){
      System.out.println("====== enableButtons =======");
      disabuttons = false;
  }
  
  public static void showGUI() {
    display = Display.getDefault();
    shell = new Shell(display);
    PianoComposite inst = new PianoComposite(shell, SWT.NULL);
    Point size = inst.getSize();
    shell.setLayout(new FillLayout());
    shell.layout();
    if (size.x == 0 && size.y == 0) {
      inst.pack();
      shell.pack();
    } else {
      Rectangle shellBounds = shell.computeTrim(0, 0, size.x, size.y);
      shell.setSize(shellBounds.width + 10, shellBounds.height + 10);
    }

    shell.open();
    while (!shell.isDisposed()) {
      if (!display.readAndDispatch()) {
        display.sleep();
      }
    }
  }

  public PianoComposite(org.eclipse.swt.widgets.Composite parent, int style) {
    super(parent, style);
    initGUI();

    nxt = NXTManager.getInstance();
  }

  private void initGUI() {
    myPianoComp = this;
    display = Display.getDefault();
    shell = new Shell(display);
    
    {
    	waitTime = new Text(this, SWT.NONE);
    	FormData waitTimeLData = new FormData();
    	waitTimeLData.left =  new FormAttachment(0, 1000, 227);
    	waitTimeLData.top =  new FormAttachment(0, 1000, 404);
    	waitTimeLData.width = 24;
    	waitTimeLData.height = 15;
    	waitTime.setLayoutData(waitTimeLData);
    	waitTime.setText("40");
    }
    {
    	waitTimeLabel = new Label(this, SWT.NONE);
    	FormData waitTimeLabelLData = new FormData();
    	waitTimeLabelLData.left =  new FormAttachment(0, 1000, 164);
    	waitTimeLabelLData.top =  new FormAttachment(0, 1000, 404);
    	waitTimeLabelLData.width = 57;
    	waitTimeLabelLData.height = 15;
    	waitTimeLabel.setLayoutData(waitTimeLabelLData);
    	waitTimeLabel.setText("Wait Time:");
    }
    {
    	noteLengthLabel = new Label(this, SWT.NONE);
    	FormData noteLengthLabelLData = new FormData();
    	noteLengthLabelLData.left =  new FormAttachment(0, 1000, 62);
    	noteLengthLabelLData.top =  new FormAttachment(0, 1000, 404);
    	noteLengthLabelLData.width = 62;
    	noteLengthLabelLData.height = 15;
    	noteLengthLabel.setLayoutData(noteLengthLabelLData);
    	noteLengthLabel.setText("Note Time:");
    }
    {
    	noteLength = new Text(this, SWT.NONE);
    	FormData noteLengthLData = new FormData();
    	noteLengthLData.left =  new FormAttachment(0, 1000, 124);
    	noteLengthLData.top =  new FormAttachment(0, 1000, 404);
    	noteLengthLData.width = 28;
    	noteLengthLData.height = 15;
    	noteLength.setLayoutData(noteLengthLData);
    	noteLength.setText("80");
    }

      layoutComponents();

    this.addKeyListener(pianoKeyListener);
  }
  
  private void layoutComponents() {
    try {
      FormLayout thisLayout = new FormLayout();
      this.setLayout(thisLayout);

      {
        help = new Button(this, SWT.PUSH | SWT.CENTER);
        FormData helpLData = new FormData();
        helpLData.left = new FormAttachment(0, 1000, 580);
        helpLData.top = new FormAttachment(0, 1000, 384);
        helpLData.width = 100;
        helpLData.height = 27;
        help.setLayoutData(helpLData);
        help.setText("Help");
        help.addMouseListener(new MouseListener() {
            final int keyId = 1;
            //java.awt.Desktop desktop = java.awt.Desktop.getDesktop();

			@Override
			public void mouseUp(MouseEvent arg0) { }
			
			@Override
			public void mouseDown(MouseEvent arg0) {
				help();
			}
			
			@Override
			public void mouseDoubleClick(MouseEvent arg0) { }
		});
        
      }

      {
        save = new Button(this, SWT.PUSH | SWT.CENTER);
        FormData saveLData = new FormData();
        saveLData.left = new FormAttachment(0, 1000, 580);
        saveLData.top = new FormAttachment(0, 1000, 332);
        saveLData.width = 100;
        saveLData.height = 30;
        save.setLayoutData(saveLData);
        save.setText("Save");
        save.addMouseListener(new MouseListener() {
			@Override
			public void mouseUp(MouseEvent arg0) {
				// TODO Auto-generated method stub
				//savingPianoNotesFile spnf = new savingPianoNotesFile();
				//spnf.receivingNotes(recording.getNotes());
				creatingSavingInterface();
			}
			@Override
			public void mouseDown(MouseEvent arg0) {
				// TODO Auto-generated method stub
				//JOptionPane.showMessageDialog(null, "it is saving the file on C:");
				//save.setEnabled(false);
			}
			@Override
			public void mouseDoubleClick(MouseEvent arg0) {
				// TODO Auto-generated method stub
			}
        });
        
        }
	
      {
        play = new Button(this, SWT.PUSH | SWT.CENTER);
        FormData playLData = new FormData();
        playLData.left = new FormAttachment(0, 1000, 450);
        playLData.top = new FormAttachment(0, 1000, 332);
        playLData.width = 107;
        playLData.height = 30;
        play.setLayoutData(playLData);
        play.setText("Play");
        play.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseUp(MouseEvent arg0) {
				playButtonPressed();
			}
			
			@Override
			public void mouseDown(MouseEvent arg0) {
			}
			
			@Override
			public void mouseDoubleClick(MouseEvent arg0) {
			}
		});
      }
      {
        copy = new Button(this, SWT.PUSH | SWT.CENTER);
        FormData copyLData = new FormData();
        copyLData.left = new FormAttachment(0, 1000, 580);
        copyLData.top = new FormAttachment(0, 1000, 280);
        copyLData.width = 100;
        copyLData.height = 31;
        copy.setLayoutData(copyLData);
        copy.setText("Copy");
        copy.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseUp(MouseEvent arg0) {
				copyButtonPressed();
			}		
			@Override
			public void mouseDown(MouseEvent arg0) {}		
			@Override
			public void mouseDoubleClick(MouseEvent arg0) {}
		});
      }
      {
        clear = new Button(this, SWT.PUSH | SWT.CENTER);
        FormData clearLData = new FormData();
        clearLData.left = new FormAttachment(0, 1000, 450);
        clearLData.top = new FormAttachment(0, 1000, 280);
        clearLData.width = 107;
        clearLData.height = 31;
        clear.setLayoutData(clearLData);
        clear.setText("Clear");
        clear.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseUp(MouseEvent arg0) {
				clearButtonPressed();
			}
			
			@Override
			public void mouseDown(MouseEvent arg0) {
			}
			
			@Override
			public void mouseDoubleClick(MouseEvent arg0) {
			}
		});
      }
      {
        length = new Group(this, SWT.NONE);
        GridLayout lengthLayout = new GridLayout();
        lengthLayout.makeColumnsEqualWidth = true;
        length.setLayout(lengthLayout);
        FormData lengthLData = new FormData();
        lengthLData.left = new FormAttachment(0, 1000, 124);
        lengthLData.top = new FormAttachment(0, 1000, 270);
        lengthLData.width = 80;
        lengthLData.height = 110;
        length.setLayoutData(lengthLData);
        length.setText("Length");
        {
          onebyone = new Button(length, SWT.RADIO | SWT.LEFT);
          GridData onebyoneLData = new GridData();
          onebyone.setLayoutData(onebyoneLData);
          onebyone.setText("1/1");
          onebyone.addSelectionListener(new SelectionAdapter() {

            public void widgetSelected(SelectionEvent evt) {
              noteLengthDiv = 1;
            }
          });
          onebyone.addKeyListener(pianoKeyListener);

        }
        {
          onebytwo = new Button(length, SWT.RADIO | SWT.LEFT);
          GridData onebytwoLData = new GridData();
          onebytwo.setLayoutData(onebytwoLData);
          onebytwo.setText("1/2");
          onebytwo.addSelectionListener(new SelectionAdapter() {

            public void widgetSelected(SelectionEvent evt) {
              noteLengthDiv = 2;
            }
          });
          onebytwo.addKeyListener(pianoKeyListener);
        }
        {
          onebyfour = new Button(length, SWT.RADIO | SWT.LEFT);
          GridData onebyoneLData = new GridData();
          onebyfour.setLayoutData(onebyoneLData);
          onebyfour.setText("1/4");
          onebyfour.setSelection(true);
          onebyfour.addSelectionListener(new SelectionAdapter() {

            public void widgetSelected(SelectionEvent evt) {
              noteLengthDiv = 4;
            }
          });
          onebyfour.addKeyListener(pianoKeyListener);
        }
        {
          onebyeight = new Button(length, SWT.RADIO | SWT.LEFT);
          GridData onebytwoLData = new GridData();
          onebyeight.setLayoutData(onebytwoLData);
          onebyeight.setText("1/8");
          onebyeight.addSelectionListener(new SelectionAdapter() {

            public void widgetSelected(SelectionEvent evt) {
              noteLengthDiv = 8;
            }
          });
          onebyeight.addKeyListener(pianoKeyListener);
        }
        {
          onebysixteen = new Button(length, SWT.RADIO | SWT.LEFT);
          GridData onebysixteenLData = new GridData();
          onebysixteen.setLayoutData(onebysixteenLData);
          onebysixteen.setText("1/16");
          onebysixteen.addSelectionListener(new SelectionAdapter() {

            public void widgetSelected(SelectionEvent evt) {
              noteLengthDiv = 16;
            }
          });
          onebysixteen.addKeyListener(pianoKeyListener);
        }
      }
      {
        rest = new Button(this, SWT.PUSH | SWT.CENTER);
        FormData restLData = new FormData();
        restLData.left = new FormAttachment(0, 1000, 32);
        restLData.top = new FormAttachment(0, 1000, 276);
        restLData.width = 50;
        restLData.height = 80;
        rest.setLayoutData(restLData);
        rest.setText("Rest");
        //rest.addKeyListener(pianoKeyListener);
        rest.addMouseListener(new MouseListener() {
        	double toneFreq = 0;
        	int toneToPlay1 = (int) Math.round(toneFreq);
            //int duration1 = (int) Math.round(toneDuration / noteLengthDiv);
            
			@Override
			public void mouseUp(MouseEvent arg0) {
				// TODO Auto-generated method stub
			   recording.AddKey(toneToPlay1, noteLengthDiv, Integer.parseInt(noteLength.getText()), Integer.parseInt(waitTime.getText()));
			}
			
			@Override
			public void mouseDown(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseDoubleClick(MouseEvent arg0) {
				// TODO Auto-generated method stub
			}
		});
      }
      {
        transpose_label = new Label(this, SWT.NONE);
        FormData transpose_labelLData = new FormData();
        transpose_labelLData.width = 98;
        transpose_labelLData.height = 22;
        //transpose_labelLData.right =  new FormAttachment(1000, 1000, -580);
        transpose_labelLData.left = new FormAttachment(0, 1000, 32);
        transpose_labelLData.top = new FormAttachment(0, 1000, 200);
        transpose_label.setLayoutData(transpose_labelLData);
        transpose_label.setText("Transpose");
        transpose_label.addKeyListener(pianoKeyListener);
      }



      {
        FormData transposeLData = new FormData();
        transposeLData.left = new FormAttachment(0, 1000, 32);
        transposeLData.top = new FormAttachment(0, 1000, 225);
        transposeLData.width = 652;
        transposeLData.height = 34;
        transpose = new Scale(this, SWT.NONE);
        transpose.setMinimum(1);
        transpose.setMaximum(6);
        transpose.setIncrement(1);
        transpose.setSelection(3);
        transpose.setLayoutData(transposeLData);
        transpose.addSelectionListener(new SelectionListener() {

          @Override
          public void widgetSelected(SelectionEvent arg0) {
            transposeMult = transpose.getSelection();
          }

          @Override
          public void widgetDefaultSelected(SelectionEvent arg0) {
          }
        });
        transpose.addKeyListener(pianoKeyListener);
      }
      Label myLabel;
      int leftValue = 64;

      for (int x = 0; x < 13; x++) {
        if (x == 3 || x == 6 || x == 10) {
          blackKeysArray.add(x, null);
        } else {
          myLabel = new Label(this, SWT.NONE);
          myLabel.setText("label1");
          final int keyId = x;
          FormData label1LData = new FormData();
          label1LData.left = new FormAttachment(0, 1000, leftValue);
          label1LData.top = new FormAttachment(0, 1000, 41);
          label1LData.width = 28;
          label1LData.height = 94;
          myLabel.setLayoutData(label1LData);
          myLabel.addMouseListener(new MouseListener() {

            @Override
            public void mouseDoubleClick(MouseEvent arg0) {
            }

            @Override
            public void mouseUp(MouseEvent arg0) {
              pianoClicked(false, keyId, false);
            }

            @Override
            public void mouseDown(MouseEvent arg0) {
              pianoClicked(false, keyId, true);
            }
          });
          myLabel.setImage(ImageDescriptor.createFromFile(getClass(),
            "/images/black_key.PNG").createImage());
          blackKeysArray.add(x, myLabel);
        }
        leftValue += 46;
      }

      leftValue = 32;
      for (int x = 0; x < 14; x++) {

        myLabel = new Label(this, SWT.NONE);
        myLabel.setText("label1");
        final int keyId = x;
        FormData label1LData = new FormData();
        label1LData.left = new FormAttachment(0, 1000, leftValue);
        label1LData.top = new FormAttachment(0, 1000, 41);
        label1LData.width = 47;
        label1LData.height = 138;
        myLabel.setLayoutData(label1LData);
        myLabel.addMouseListener(new MouseListener() {

          @Override
          public void mouseDoubleClick(MouseEvent arg0) {
          }

          @Override
          public void mouseUp(MouseEvent arg0) {
            pianoClicked(true, keyId, false);
          }

          @Override
          public void mouseDown(MouseEvent arg0) {
            pianoClicked(true, keyId, true);
          }
        });
        myLabel.setImage(ImageDescriptor.createFromFile(getClass(),
          "/images/white_key.PNG").createImage());
        whiteKeysArray.add(x, myLabel);
        leftValue += 46;
      }


      this.layout();
      pack();

      buildKeyLabels();

    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  /**
   * Invokes the help contents Window.
   */
  protected void help() {
    helpAction.runPianoLink();
  }

  protected void pianoClicked(boolean whiteKeys, int keyId, boolean isDown) {
    if (isDown) {

      double toneFreq = 0;
      boolean toneDouble = false;
      int toneId = keyId;
      
      if (whiteKeys) {
        System.out.println("White Key: " + keyId);
        if (toneId > 6) {
          toneId -= 7;
          toneDouble = true;
        }
        switch (toneId) {
          //F
          case 0:
            toneFreq = 43.65;
            break;
          //G
          case 1:
            toneFreq = 48.99;
            break;
          //A
          case 2:
            toneFreq = 55;
            break;
          //B
          case 3:
            toneFreq = 61.74;
            break;
          //C
          case 4:
            toneFreq = 65.41;
            break;
          //D
          case 5:
            toneFreq = 73.42;
            break;
          //E
          case 6:
            toneFreq = 82.41;
            break;
        }
      } else {
        System.out.println("Black Key: " + keyId);
        if (toneId > 6) {
          toneId -= 7;
          toneDouble = true;
        }
        switch (toneId) {
          //F#
          case 0:
            toneFreq = 46.25;
            break;
          //G#
          case 1:
            toneFreq = 51.91;
            break;
          //A#
          case 2:
            toneFreq = 58.27;
            break;
          //C#
          case 4:
            toneFreq = 69.30;
            break;
          //D#
          case 5:
            toneFreq = 77.78;
            break;

          default:
            toneFreq = 0;
        }
      }

      if (toneDouble) {
        toneFreq = toneFreq * 2;
      }
      toneFreq *= transposeMult * 2; 
      int toneToPlay = (int) Math.round(toneFreq);
      
      //int duration = (int) Math.round(toneDuration / noteLengthDiv); 

      System.out.println("Playing Tone: " + toneToPlay);
      Integer noteDuration;
      try{
      noteDuration = Integer.parseInt(noteLength.getText());
      }
      catch(Exception e){
    	  noteDuration = 80;
    	  noteLength.setText("80");
      }
      Integer waitDuration;
      try{
      	waitDuration = Integer.parseInt(waitTime.getText());    	
      }
      catch(Exception e){
    	  waitDuration = 40;
    	  waitTime.setText("40");
      }

      PianoNote note = new PianoNote(toneToPlay, noteLengthDiv, noteDuration, waitDuration);
      recording.AddKey(note);

      if (USE_BRICK) {
        //nxt.playTone(toneToPlay, duration);
    	 nxt.playTone(note.getTone(), note.getNoteTime());
      }
      if(noteLength.isFocusControl() || waitTime.isFocusControl())
    	  this.length.setFocus();
      highlightKey(whiteKeys, keyId);
    } else {
      unHighlightKey(whiteKeys, keyId);
    }
  }

  protected void pianoKeyPressed(char charIn, boolean isDown) {
    charIn = Character.toLowerCase(charIn);
    System.out.println("Key pressed: " + charIn);

    int keyId = -1;
    boolean whiteKeys = true;



    switch (charIn) {
      case 'q':
        keyId = 0;
        break;
      case 'w':
        keyId = 1;
        break;
      case 'e':
        keyId = 2;
        break;
      case 'r':
        keyId = 3;
        break;
      case 't':
        keyId = 4;
        break;
      case 'y':
        keyId = 5;
        break;
      case 'u':
        keyId = 6;
        break;
      case 'i':
        keyId = 7;
        break;
      case 'o':
        keyId = 8;
        break;
      case 'p':
        keyId = 9;
        break;
      case '[':
        keyId = 10;
        break;
      case ']':
        keyId = 11;
        break;
      //two new black key  
      case 'z':
        keyId = 12;
        break;
      case 'x':
        keyId = 13;
        break;  

        
        
      case '2':
        keyId = 0;
        whiteKeys = false;
        break;
      case '3':
        keyId = 1;
        whiteKeys = false;
        break;
      case '4':
        keyId = 2;
        whiteKeys = false;
        break;
      case '6':
        keyId = 4;
        whiteKeys = false;
        break;
      case '7':
        keyId = 5;
        whiteKeys = false;
        break;
      case '9':
        keyId = 7;
        whiteKeys = false;
        break;
      case '0':
        keyId = 8;
        whiteKeys = false;
        break;
      case '-':
        keyId = 9;
        whiteKeys = false;
        break;
      
      //two new black key
      case 's':
        keyId = 11;
        whiteKeys = false;
        break;
      case 'd':
        keyId = 12;
        whiteKeys = false;
        break;
    }

    if (keyId > -1) {
      pianoClicked(whiteKeys, keyId, isDown);
    }

  }

  protected void clearButtonPressed(){
	  recording.ClearKeys();
  }
  
  protected void copyButtonPressed(){
	  String recStr = recording.getRecordingStr();
	  
	  Clipboard systemClipboard =
			Toolkit
				.getDefaultToolkit()
				.getSystemClipboard();
		Transferable transferableText =
			new StringSelection(recStr);
		systemClipboard.setContents(
			transferableText,
			null);
  }
  
  protected void playButtonPressed(){
	  PlayThread thread = new PlayThread();
	  thread.start();
  }
  
  protected void unHighlightKey(boolean whiteKeys, int keyId) {
    if (whiteKeys) {
      whiteKeyLabelArray.get(keyId).moveBelow(whiteKeysArray.get(keyId));
    } else {
      blackKeyLabelsArray.get(keyId).moveBelow(blackKeysArray.get(keyId));
    }

    this.layout();
    pack();
  }

  protected void highlightKey(boolean whiteKeys, int keyId) {

    cleanKeys();

    if (whiteKeys) {
      whiteKeyLabelArray.get(keyId).moveAbove(whiteKeysArray.get(keyId));
    } else {
      blackKeyLabelsArray.get(keyId).moveAbove(blackKeysArray.get(keyId));
    }
    this.layout();
    pack();
    this.setSize(1307, 453);
  }

  private void buildKeyLabels() {

    for (int x = 0; x < whiteKeysArray.size(); x++) {
      FormData label1LData = new FormData();

      label1LData.left = new FormAttachment(0, 1000, whiteKeysArray.get(x).getLocation().x + 15);
      label1LData.top = new FormAttachment(0, 1000, 140);

      Label myLabel = new Label(this, SWT.None);
      myLabel.setLayoutData(label1LData);

      myLabel.setBackground(new Color(Display.getCurrent(), 255, 255, 255));
      FontData[] fD = myLabel.getFont().getFontData();
      fD[0].setHeight(18);
      myLabel.setFont(new Font(Display.getCurrent(), fD[0]));
      myLabel.setText(getKeyName(true, x));
      whiteKeyLabelArray.add(x, myLabel);
    }

    for (int x = 0; x < blackKeysArray.size(); x++) {
      if (x == 3 || x == 6 || x == 10) {
        blackKeyLabelsArray.add(x, null);
      } else {
        FormData label1LData = new FormData();

        label1LData.left = new FormAttachment(0, 1000, blackKeysArray.get(x).getLocation().x + 1);
        label1LData.top = new FormAttachment(0, 1000, 100);

        Label myLabel = new Label(this, SWT.None);
        myLabel.setLayoutData(label1LData);

        myLabel.setBackground(new Color(Display.getCurrent(), 0, 0, 0));
        myLabel.setForeground(new Color(Display.getCurrent(), 255, 255, 255));

        FontData[] fD = myLabel.getFont().getFontData();
        fD[0].setHeight(16);
        myLabel.setFont(new Font(Display.getCurrent(), fD[0]));
        myLabel.setText(getKeyName(false, x));
        blackKeyLabelsArray.add(x, myLabel);
      }
    }


  }

  private String getKeyName(boolean whiteKeys, int keyId) {
    String ret = "";
    int toneId = keyId;
    if (whiteKeys) {
      if (toneId > 6) {
        toneId -= 7;
      }
      switch (toneId) {
        //F
        case 0:
          ret = "F";
          break;
        //G
        case 1:
          ret = "G";
          break;
        //A
        case 2:
          ret = "A";
          break;
        //B
        case 3:
          ret = "B";
          break;
        //C
        case 4:
          ret = "C";
          break;
        //D
        case 5:
          ret = "D";
          break;
        //E
        case 6:
          ret = "E";
          break;
      }
    } else {
      if (toneId > 6) {
        toneId -= 7;
      }
      switch (toneId) {
        //F#
        case 0:
          ret = "F#";
          break;
        //G#
        case 1:
          ret = "G#";
          break;
        //A#
        case 2:
          ret = "A#";
          break;
        //C#
        case 4:
          ret = "C#";
          break;
        //D#
        case 5:
          ret = "D#";
          break;
      }

    }
    return ret;
  }

  private void cleanKeys() {
    for (int x = 0; x < whiteKeysArray.size(); x++) {
      whiteKeyLabelArray.get(x).moveBelow(whiteKeysArray.get(x));
    }
    for (int x = 0; x < blackKeysArray.size(); x++) {
      if (x == 3 || x == 6 || x == 10) {
      } else {
        blackKeyLabelsArray.get(x).moveBelow(blackKeysArray.get(x));
      }
    }

  }
  
  public void creatingSavingInterface() {
      String fileName = null;
      boolean done = false;
      try{
          FileDialog dlg = new FileDialog(shell, SWT.SAVE);
          dlg.setFilterNames(FileExtensionConstants.FILTER_NAMES);
          dlg.setFilterExtensions(FileExtensionConstants.FILTER_EXTENSIONS);
          fileName = dlg.open();
          savingPianoNotesFile spnf = new savingPianoNotesFile();
          
          if(fileName == null){
              done = true;
          }else{
              File file = new File(fileName);
              if(file.exists()){
                    boolean overwrite = MessageDialog.openQuestion(shell, "Confirm over write", fileName + " already exists. Do you want to replace it?");
                    if(!overwrite) {
                            fileName = null;
                    }else{
                            spnf.receivingNotes(recording.getNotes(), fileName);        
                    }
              }else{
                     spnf.receivingNotes(recording.getNotes(), fileName);
                   }
          }
          
       }catch(Exception e){
            e.printStackTrace();
       }
    }
   
  //Thread for playing back the recording, needs to be a thread since we sleep the thread for the 
  //wait times between notes and this would cause the window to stop responding
  class PlayThread extends Thread {
      PlayThread() {
      }
      public void run() {
    	  ArrayList<PianoNote> notes = recording.getNotes();
    	  try{
    	  for(PianoNote note : notes){
    		  nxt.playTone(note.getTone(), note.getNoteTime());
    		  Thread.sleep(note.getWaitTime());
    	  }
    	  }
    	  catch(Exception e){}
      }
  }
  
}
