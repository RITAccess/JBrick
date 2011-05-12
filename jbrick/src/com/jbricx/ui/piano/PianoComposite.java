package com.jbricx.ui.piano;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
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
import org.eclipse.swt.widgets.Scale;
import org.eclipse.swt.widgets.Text;

import com.jbricx.actions.HelpContentAction;

/**
 * The GUI for Piano.
 * 
 * @author Abhishek Shrestha
 */
class PianoComposite extends Composite {

  private Label waitTimeLabel;
  private Label noteLengthLabel;
  private Text noteLength;
  private Button onebysixteen;
  private Button onebytwo;
  private Button onebyone;
  private Button onebyfour;
  private Button onebyeight;
  private Button help;
  private Text waitTime;
  private Button save;
  private Button play;
  private Button copy;
  private Button clear;
  private Group length;
  private Button rest;
  private Label transpose_label;
  private Scale transpose;
  private static Display display;

  private ArrayList<Label> keyLabelsArray = new ArrayList<Label>();
  private ArrayList<Label> keyArray = new ArrayList<Label>();

  private static final boolean USE_BRICK = true;
  public static boolean disabuttons = false;
  private PianoController pianoController;
  private HelpContentAction helpAction = new HelpContentAction();
  private KeyListener pianoKeyListener = new KeyListener() {

    int keyIndex;

    @Override
    public void keyPressed(KeyEvent key) {
      try {
        hideAllLabels();
        keyIndex = pianoController.play(key.character);
        keyLabelsArray.get(keyIndex).moveAbove(keyArray.get(keyIndex));
        showHideLabel(keyIndex, true);
      } catch (KeyNotMappedExeption k) {
        /* check if you intend to map this key */
      } catch (OctaveNotMappedException o) {
        /* check if you intend to map this octave */
      } catch (KeyIndexNotMappedException ki) {
        /* check if you intend to map this keyIndex */
      }
    }

    @Override
    public void keyReleased(KeyEvent k) {
      showHideLabel(keyIndex, false);
    }
  };

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
  public static void disableButtons() {
    System.out.println("====== disableButtons =======");
    disabuttons = true;
  }

  public static void enableButtons() {
    System.out.println("====== enableButtons =======");
    disabuttons = false;
  }

  public PianoComposite(Composite parent, int style,
      final PianoController pianoController) {
    super(parent, style);
    this.pianoController = pianoController;
    initGUI();
  }

  private void initGUI() {
    display = Display.getDefault();

    {
      waitTime = new Text(this, SWT.NONE);
      FormData waitTimeLData = new FormData();
      waitTimeLData.left = new FormAttachment(0, 1000, 227);
      waitTimeLData.top = new FormAttachment(0, 1000, 404);
      waitTimeLData.width = 24;
      waitTimeLData.height = 15;
      waitTime.setLayoutData(waitTimeLData);
      waitTime.setText("40");
    }
    {
      waitTimeLabel = new Label(this, SWT.NONE);
      FormData waitTimeLabelLData = new FormData();
      waitTimeLabelLData.left = new FormAttachment(0, 1000, 164);
      waitTimeLabelLData.top = new FormAttachment(0, 1000, 404);
      waitTimeLabelLData.width = 57;
      waitTimeLabelLData.height = 15;
      waitTimeLabel.setLayoutData(waitTimeLabelLData);
      waitTimeLabel.setText("Wait Time:");
    }
    {
      noteLengthLabel = new Label(this, SWT.NONE);
      FormData noteLengthLabelLData = new FormData();
      noteLengthLabelLData.left = new FormAttachment(0, 1000, 62);
      noteLengthLabelLData.top = new FormAttachment(0, 1000, 404);
      noteLengthLabelLData.width = 62;
      noteLengthLabelLData.height = 15;
      noteLengthLabel.setLayoutData(noteLengthLabelLData);
      noteLengthLabel.setText("Note Time:");
    }
    {
      noteLength = new Text(this, SWT.NONE);
      FormData noteLengthLData = new FormData();
      noteLengthLData.left = new FormAttachment(0, 1000, 124);
      noteLengthLData.top = new FormAttachment(0, 1000, 404);
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
        help.addSelectionListener(new SelectionAdapter() {
          public void widgetSelected(SelectionEvent e) {
            helpAction.runPianoLink();
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
        save.addSelectionListener(new SelectionAdapter() {
          public void widgetSelected(SelectionEvent e) {
            pianoController.saveNotes();
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
        play.addSelectionListener(new SelectionAdapter() {
          public void widgetSelected(SelectionEvent e) {
            pianoController.playRecords();
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
        copy.addSelectionListener(new SelectionAdapter() {
          public void widgetSelected(SelectionEvent e) {
            pianoController.copyRecords();
          }
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
        clear.addSelectionListener(new SelectionAdapter() {
          public void widgetSelected(SelectionEvent e) {
            pianoController.clearRecords();
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
              pianoController.setToneDuration(1000);
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
              pianoController.setToneDuration(500);
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
              pianoController.setToneDuration(250);
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
              pianoController.setToneDuration(125);
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
              pianoController.setToneDuration(62.5f);
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
        rest.addMouseListener(new MouseListener() {

          @Override
          public void mouseUp(MouseEvent arg0) {
            pianoController.playRest();
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
        transpose_label = new Label(this, SWT.NONE);
        FormData transpose_labelLData = new FormData(98, 22);
        transpose_labelLData.left = new FormAttachment(0, 1000, 32);
        transpose_labelLData.top = new FormAttachment(0, 1000, 200);
        transpose_label.setLayoutData(transpose_labelLData);
        transpose_label.setText("Transpose");
        transpose_label.addKeyListener(pianoKeyListener);
      }

      {
        FormData transposeLData = new FormData();
        transposeLData.left = new FormAttachment(0, 1000, 10);
        transposeLData.top = new FormAttachment(0, 1000, 235);
        transposeLData.width = 652;
        transposeLData.height = 50;
        transpose = new Scale(this, SWT.NONE);
        transpose.setMinimum(pianoController.getOctaveSlideStart());
        transpose.setMaximum(pianoController.getOctaveSlideLimit());
        transpose.setIncrement(1);
        transpose.setSelection(pianoController.getStartOctave());
        transpose.setLayoutData(transposeLData);
        transpose.addSelectionListener(new SelectionAdapter() {

          @Override
          public void widgetSelected(SelectionEvent s) {
            try {
              pianoController.setStartOctave(transpose.getSelection());
            } catch (OctaveScaleOutofBoundsException e) {
              e.printStackTrace();
            }
          }
        });
        transpose.addKeyListener(pianoKeyListener);
      }
      int leftValue = 14;

      List<PianoKey> pianoKeys = pianoController.getKeys();
      int startOctave = pianoController.getStartOctave();
      int cnt = 0;
      for (int o = startOctave; o <= pianoController.getEndOctave(); o++) {
        final int octaveIndex = o % startOctave;
        for (int i = 0; i < pianoKeys.size(); i++) {
          final PianoKey pianoKey = pianoKeys.get(i);
          final Label myLabel = new Label(this, SWT.NONE);

          FormData label1LData = new FormData();
          label1LData.height = 94;

          final String keyLabel = pianoKey.getName();
          final int keyIndex = cnt;

          myLabel.setLayoutData(label1LData);
          label1LData.left = new FormAttachment(0, 1000, leftValue);
          label1LData.top = new FormAttachment(0, 1000, 41);

          /* these are the labels that display the name of the key when pressed */
          FormData label2LData = new FormData();
          label2LData.top = new FormAttachment(0, 1000, 100);
          Label myLabel2 = new Label(this, SWT.None);
          myLabel2.setText(keyLabel);
          myLabel2.setLayoutData(label2LData);
          FontData[] fD = myLabel.getFont().getFontData();
          fD[0].setHeight(16);
          myLabel2.setFont(new Font(Display.getCurrent(), fD[0]));

          if (pianoKey.isBlack()) {
            label2LData.left = new FormAttachment(0, 1000, leftValue + 1);
            myLabel2.setBackground(new Color(display, 0, 0, 0));
            myLabel2.setForeground(new Color(display, 255, 255, 255));

            label1LData.width = 28;
            label1LData.height = 94;
            leftValue += 14;
            myLabel.setBackground(new Color(display, 0, 0, 0));
          } else {
            label2LData.left = new FormAttachment(0, 1000, leftValue + 15);
            myLabel2.setBackground(new Color(display, 255, 255, 255));

            leftValue += 46;
            label1LData.width = 47;
            label1LData.height = 138;
            myLabel.setForeground(new Color(display, 0, 0, 0));
            myLabel.setBackground(new Color(display, 255, 255, 255));
            myLabel.setImage(ImageDescriptor.createFromFile(getClass(),
                "/images/white_key.PNG").createImage());
          }
          keyArray.add(keyIndex, myLabel);
          keyLabelsArray.add(keyIndex, myLabel2);

          /* also move the black key above the previous white key */
          if (pianoKey.isBlack()) {
            keyArray.get(keyIndex).moveAbove(keyArray.get(keyIndex - 1));
          }

          /* decrease the position if the next key is black */
          if (i < pianoKeys.size() - 1) {
            if (pianoKeys.get(i + 1).isBlack()) {
              leftValue -= 14;
            }
          }

          myLabel.addMouseListener(new MouseListener() {

            @Override
            public void mouseDoubleClick(MouseEvent arg0) {
            }

            @Override
            public void mouseUp(MouseEvent arg0) {
              /* as soon as the mouse is up move the label below */
              showHideLabel(keyIndex, false);
            }

            @Override
            public void mouseDown(MouseEvent arg0) {
              if (USE_BRICK) {
                pianoController.play(pianoKey, octaveIndex);
              }
              keyLabelsArray.get(keyIndex).moveAbove(keyArray.get(keyIndex));
              showHideLabel(keyIndex, true);
            }
          });
          cnt++;
        }
      }
      this.layout();
      pack();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void showHideLabel(int labelIndex, boolean show) {
    if (show) {
      keyLabelsArray.get(labelIndex).moveAbove(keyArray.get(labelIndex));
    } else {
      keyLabelsArray.get(labelIndex).moveBelow(keyArray.get(labelIndex));
    }
  }

  private void hideAllLabels() {
    for (int i = 0; i < keyLabelsArray.size(); i++) {
      keyLabelsArray.get(i).moveBelow(keyArray.get(i));
    }
  }
}
