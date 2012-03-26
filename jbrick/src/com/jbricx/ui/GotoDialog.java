package com.jbricx.ui;

import java.awt.Button;
import java.awt.Composite;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.text.BadLocationException;

import com.jbricx.model.PersistentDocument;

/**
 * This class displays a Goto line number dialog
 * 
 * @author Abhishek Shrestha
 */
public class GotoDialog extends TrayDialog{
  // The associated viewer
  private ITextViewer viewer;
  private PersistentDocument document;
  private Button doGoto;
  private Shell parentShell;
  private int lineNumber;
  private static GotoDialog instance = null;
  private  boolean isOpen = false;

  /**
   * GotoDialog constructor
   * 
   * @param shell
   *          the parent shell
   * @param document
   *          the associated document
   * @param viewer
   *          the associated viewer
   */
  public GotoDialog(Shell shell, IDocument document, ITextViewer viewer) {
    super(shell);
    parentShell = shell;
    this.document = (PersistentDocument) document;
    this.viewer = viewer;
    lineNumber = 1;
  }


  //private void addToolBar(int i) {
    // TODO Auto-generated method stub  
  //}

  /**
   * Opens the dialog box
   */
  public void openUp() {
    
    int WIDTH = 300;
    int HEIGHT = 140;
      // locate the window at the center of the parent screen
      Point parentWindowLocation = parentShell.getLocation();
      Point parentWindowSize = parentShell.getSize();
      int x = ((parentWindowSize.x - WIDTH) / 2) + parentWindowLocation.x;
      int y = ((parentWindowSize.y - WIDTH) / 2) + parentWindowLocation.y;
      open();
  }

  protected void doGoto() {
    viewer.setSelectedRange(lineNumber, 0);
    close();
  }
  
  @Override
  protected Control createContents(Composite parent) {
    Composite composite = new Composite(parent, SWT.NONE);
    //new Text(composite, SWT.ABORT);

    GridData layoutDataOneCol = new GridData(GridData.FILL_HORIZONTAL);
    GridData layoutDataTwoCol = new GridData();

    layoutDataOneCol.horizontalSpan = 2;
    layoutDataTwoCol.horizontalAlignment = GridData.FILL;
    layoutDataTwoCol.grabExcessHorizontalSpace = true;

    GridLayout layout = new GridLayout();
    layout.numColumns = 2;
    composite.setLayout(layout);
    
    // goto label
    Label labelGoto = new Label(composite, SWT.LEFT);
    labelGoto.setText("Goto Line:");
    labelGoto.setLayoutData(layoutDataOneCol);

    // goto text
    final Text findText = new Text(composite, SWT.BORDER);
    findText.setLayoutData(layoutDataTwoCol);

    // notification label
    final Label inputNotifier = new Label(composite, SWT.LEFT);
    inputNotifier.setLayoutData(layoutDataOneCol);

    // Create the Goto button
    doGoto = new Button(composite, SWT.PUSH);
    doGoto.setText("Goto");
    doGoto.setLayoutData(layoutDataTwoCol);
    doGoto.setEnabled(false);
    
    // Create the Close button
    Button close = new Button(composite, SWT.PUSH);
    close.setText("Close");
    close.setLayoutData(layoutDataTwoCol);
    close.addSelectionListener(new SelectionAdapter() {
      public void widgetSelected(SelectionEvent event) {
        close();
      }
    });
    // Do a goto
    doGoto.addSelectionListener(new SelectionAdapter() {
      public void widgetSelected(SelectionEvent event) {
        doGoto();
      }
    });

    findText.addKeyListener(new KeyListener() {
      @Override
      public void keyReleased(KeyEvent ke) {
        String inputText = findText.getText();

        if (!inputText.equals("")) {
          try {
            lineNumber = document.getLineOffset(Integer.parseInt(inputText) - 1);
            inputNotifier.setText("");
            doGoto.setEnabled(true);
          } catch (NumberFormatException e) {
            //inputNotifier.setText("Invalid/ Not a number!");
            inputNotifier.setText("Not a number!");
            doGoto.setEnabled(false);
          } catch (BadLocationException e) {
            //inputNotifier.setText("Line number out of range!");
            inputNotifier.setText("Out of range!");
            doGoto.setEnabled(false);
          }
        } else {
          inputNotifier.setText("");
          doGoto.setEnabled(false);
        }
      }

      @Override
      public void keyPressed(KeyEvent ke) {
      }
    });

    findText.setFocus();
    //composite.setDefaultButton(doGoto);
    return composite;
  }

  /**
   * Shows an error
   * 
   * @param message
   *          the error message
   */
  protected void showError(String message) {
    MessageDialog.openError(parentShell, "Error", message);
  }
  

  public static GotoDialog getInstance(Shell shell, IDocument document, ITextViewer viewer) {
    if (instance == null) {
      instance = new GotoDialog(shell, document, viewer);
    }
    return instance;
  }
}
