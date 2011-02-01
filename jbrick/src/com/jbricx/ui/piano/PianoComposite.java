package com.jbricx.ui.piano;

//import java.awt.Color;
import java.io.FileInputStream;
import java.util.ArrayList;

import javax.swing.JOptionPane;

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
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Scale;
import org.eclipse.swt.widgets.Shell;

import com.jbricx.communications.AbstractNXTBrick;
import com.jbricx.communications.NXTManager;
import com.jbricx.communications.exceptions.AlreadyConnectedException;
import com.jbricx.communications.exceptions.NXTNotFoundException;
import com.jbricx.ui.findbrick.FindBrickFileIO;

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
  private Scale transpose;
  private static Shell shell;
  private static Display display;
  private ArrayList<Label> whiteKeysArray = new ArrayList<Label>();
  private ArrayList<Label> blackKeysArray = new ArrayList<Label>();
  private ArrayList<Label> whiteKeyLabelArray = new ArrayList<Label>();
  private ArrayList<Label> blackKeyLabelsArray = new ArrayList<Label>();
  private static final boolean USE_BRICK = true;
  private static boolean SAVE_NOTES = false;
  private static AbstractNXTBrick nxt;
  private static PianoComposite myPianoComp;
  int noteLengthDiv = 4;
  int transposeMult = 3;
  int toneDuration = 1000;
  
  
  private PianoRecording recording = new PianoRecording();
  
  
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
   * Overriding checkSubclass allows this class to extend org.eclipse.swt.widgets.Composite
   */
  protected void checkSubclass() {
  }

  /**
   * Auto-generated method to display this
   * org.eclipse.swt.widgets.Composite inside a new Shell.
   */
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
  }

  private void initGUI() {
    myPianoComp = this;

    if (USE_BRICK) {
      try {
        nxt = NXTManager.connect("brick12", FindBrickFileIO.getCT());
        if (nxt.isConnected()) {
          nxt.playTone(2000, 300);
          System.out.println("Piano: Brick Connected!");
          nxt.playTone(3000, 300);
        }
      } catch (AlreadyConnectedException e) {
        // TODO Auto-generated catch block
        //e.printStackTrace();
        System.out.println("Piano already Connected");
        try {
          nxt = NXTManager.getBrick("brick1");
          nxt.playTone(2000, 300);
          System.out.println("Piano: Brick Connected!");
        } catch (NXTNotFoundException e1) {
          // TODO Auto-generated catch block          
          System.err.println("Could not find brick");
        }        
      }
      layoutComponents();
    }

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
			public void mouseUp(MouseEvent arg0) {
				pianoClickedHelp(true, keyId, false);
				// TODO Auto-generated method stub
				
				//java.net.URI uri = new java.net.URI( arg );
                //desktop.browse( uri );

				
				//HelpBrowser br = new HelpBrowser();
				//br.setUrl("www.google.com.pe");
			}
			
			@Override
			public void mouseDown(MouseEvent arg0) {
				pianoClickedHelp(true, keyId, false);
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseDoubleClick(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
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
        //save.addKeyListener(pianoKeyListener);
        save.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseUp(MouseEvent arg0) {
				// TODO Auto-generated method stub
				System.out.println("saving_mouseUp");
				savingPianoNotesFile spnf = new savingPianoNotesFile();
				spnf.receivingNotes(recording.getNotes());
				
				//save.setEnabled(false);
				SAVE_NOTES = true;
			}
			
			@Override
			public void mouseDown(MouseEvent arg0) {
				// TODO Auto-generated method stub
				JOptionPane.showMessageDialog(null, "JBrick will start saving the notes...");
				//save.setEnabled(false);
				SAVE_NOTES = true;
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
            int duration1 = (int) Math.round(toneDuration / noteLengthDiv);
            
			@Override
			public void mouseUp(MouseEvent arg0) {
				// TODO Auto-generated method stub
				 recording.AddKey(toneToPlay1, duration1);
			}
			
			@Override
			public void mouseDown(MouseEvent arg0) {
				// TODO Auto-generated method stub
				 recording.AddKey(toneToPlay1, duration1);
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
          myLabel.setImage(new Image(null, new FileInputStream("src/images/black_key.PNG")));
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
        myLabel.setImage(new Image(null, new FileInputStream("src/images/white_key.PNG")));
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

      PianoNote note = new PianoNote(toneToPlay, noteLengthDiv);
      recording.AddKey(note);

      if (USE_BRICK) {
        //nxt.playTone(toneToPlay, duration);
    	 nxt.playTone(note.getTone(), note.getNoteTime());
      }
 
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

  }
  
  protected void playButtonPressed(){
	  ArrayList<PianoNote> notes = recording.getNotes();
	  for(PianoNote note : notes){
		  nxt.playTone(note.getTone(), note.getNoteTime());
		  try {
			Thread.sleep(note.getWaitTime());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	  }
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
  
  protected void pianoClickedHelp(boolean whiteKeys, int keyId, boolean isDown) {
	  System.out.println("it is calling");
	   
	  }
  
  
}
