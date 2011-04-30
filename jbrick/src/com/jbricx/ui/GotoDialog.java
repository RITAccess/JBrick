package com.jbricx.ui;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.jbricx.model.PersistentDocument;

/**
 * This class displays a Goto line number dialog
 * 
 * @author Abhishek Shrestha
 */
public class GotoDialog extends Dialog {
  // The associated viewer
  private ITextViewer viewer;
  private PersistentDocument document;
  private Button doGoto;
  private Shell myShell;
  private int lineNumber;

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
    super(shell, SWT.DIALOG_TRIM | SWT.MODELESS);
    this.document = (PersistentDocument) document;
    this.viewer = viewer;
    lineNumber = 1;
  }

  /**
   * Opens the dialog box
   */
  public void open() {
    Shell shell = new Shell(getParent(), getStyle());
    shell.setText("Goto Line Number");
    createContents(shell);
    int WIDTH = 200;
    int HEIGHT = 140;

    // locate the window at the center of the parent screen
    Point parentWindowLocation = shell.getParent().getLocation();
    Point parentWindowSize = shell.getParent().getSize();

    int x = ((parentWindowSize.x - WIDTH) / 2) + parentWindowLocation.x;
    int y = ((parentWindowSize.y - WIDTH) / 2) + parentWindowLocation.y;

    shell.setBounds(x, y, WIDTH, HEIGHT);
    shell.open();
  }

  protected void doGoto() {
    viewer.setSelectedRange(lineNumber, 0);
    myShell.close();
  }

  /**
   * Creates the dialog's contents
   * 
   * @param shell
   */
  protected void createContents(final Shell shell) {
    myShell = shell;

    GridData layoutDataOneCol = new GridData(GridData.FILL_HORIZONTAL);
    GridData layoutDataTwoCol = new GridData();

    layoutDataOneCol.horizontalSpan = 2;
    layoutDataTwoCol.horizontalAlignment = GridData.FILL;
    layoutDataTwoCol.grabExcessHorizontalSpace = true;

    GridLayout layout = new GridLayout();
    layout.numColumns = 2;
    myShell.setLayout(layout);

    // goto label
    Label labelGoto = new Label(shell, SWT.LEFT);
    labelGoto.setText("Goto Line Number:");
    labelGoto.setLayoutData(layoutDataOneCol);

    // goto text
    final Text findText = new Text(myShell, SWT.BORDER);
    findText.setLayoutData(layoutDataTwoCol);

    // notification label
    final Label inputNotifier = new Label(shell, SWT.LEFT);
    inputNotifier.setLayoutData(layoutDataOneCol);

    // Create the Goto button
    doGoto = new Button(myShell, SWT.PUSH);
    doGoto.setText("Goto");
    doGoto.setLayoutData(layoutDataTwoCol);
    doGoto.setEnabled(false);

    // Create the Close button
    Button close = new Button(myShell, SWT.PUSH);
    close.setText("Close");
    close.setLayoutData(layoutDataTwoCol);
    close.addSelectionListener(new SelectionAdapter() {
      public void widgetSelected(SelectionEvent event) {
        shell.close();
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
            inputNotifier.setText("Invalid/ Not a number!");
            doGoto.setEnabled(false);
          } catch (BadLocationException e) {
            inputNotifier.setText("Line number out of range!");
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
    shell.setDefaultButton(doGoto);
  }

  /**
   * Shows an error
   * 
   * @param message
   *          the error message
   */
  protected void showError(String message) {
    MessageDialog.openError(getParent(), "Error", message);
  }
}
