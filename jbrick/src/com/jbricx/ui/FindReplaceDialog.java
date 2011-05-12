package com.jbricx.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.PatternSyntaxException;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.TrayDialog;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.FindReplaceDocumentAdapter;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.ScrollBar;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.jbricx.model.PersistentDocument;

/**
 * This class displays a find/replace dialog
 */
public class FindReplaceDialog extends TrayDialog {

  // The adapter that does the finding/replacing
  private FindReplaceDocumentAdapter frda;
  private static List<Control> changableComponentList = new ArrayList<Control>();
  // The associated viewer
  private ITextViewer viewer;
  private ScrollBar scroll; 
  // The find and replace buttons
  private Button doFind;
  private Button doReplace;
  private Button doReplaceFind;
  Display display;
  private Shell parentShell;
  private PersistentDocument document;
  public static int f = -1;
  List<Integer> listValue = new ArrayList<Integer>();
  /**
   * FindReplaceDialog constructor
   * 
   * @param shell
   *            the parent shell
   * @param document
   *            the associated document
   * @param viewer
   *            the associated viewer
   */
  public FindReplaceDialog(Shell shell, IDocument document, ITextViewer viewer) {
    super(shell);
    parentShell = shell;
    frda = new FindReplaceDocumentAdapter(document);
    this.document = (PersistentDocument)document;
    this.viewer = viewer;
  }

  /**
   * Opens the dialog box
   */
  public void openUp() {
    System.out.println("Find/Replace");
    Shell shell = new Shell(parentShell);
    shell.setText("Find/Replace");
    createContents(shell);
    shell.pack();
    shell.open();
    shell.setDefaultButton(doFind);
    
    display = shell.getDisplay();
    while (!shell.isDisposed()) {
      if (!display.readAndDispatch()) {
        display.sleep();
      }
    }
    open();
  }

  /**
   * Performs a find
   * 
   * @param find
   *            the find string
   * @param forward
   *            whether to search forward
   * @param matchCase
   *            whether to match case
   * @param wholeWord
   *            whether to search on whole word
   * @param regexp
   *            whether find string is a regular expression
   */
  protected void doFind(String find, boolean forward, boolean matchCase,
    boolean wholeWord, boolean regexp, boolean wrap) {
    int x = 0;
    int y = 0;
    int z = 0;
    
    
    System.out.println("FindReplaceDialog:doFind()" + find);
    // You can't mix whole word and regexp
    if (wholeWord && regexp){
        showError("You can't search on both Whole Words and Regular Expressions");
    }else{
      IRegion region = null;
      try {
        // Get the current offset
        System.out.println("try");
        int offset = viewer.getTextWidget().getCaretOffset();
          // If something is currently selected, and they're searching
          // backwards,
          // move offset to beginning of selection. Otherwise, repeated
          // backwards
          // finds will only find the same text
        if (!forward) {
          System.out.println("different from forward");
            Point pt = viewer.getSelectedRange();
            if (pt.x != pt.y) {
              offset = pt.x - 1;
            }
        }
        // Make sure we're in the document
        if (offset >= frda.length())
          offset = frda.length() - 1;
        if (offset < 0)
          offset = 0;
        // Perform the find
        region = frda.find(offset, find, forward, matchCase, wholeWord, regexp);
        // Update the viewer with found selection
        int valorMedio = 0;
        
        if (region != null) {
          f = f+1;
          System.out.println("f es  ====> " + f);
          viewer.setSelectedRange(region.getOffset(), region.getLength());
          x = region.getOffset();
          y = region.getLength();
          z = frda.length();
          
          System.out.println("amount:" + frda.length());
            valorMedio = frda.length()/2;
            System.out.println("valorMedio" + valorMedio);
            //it is more than the half
            System.out.println("6");          
            System.out.println("region.Offset = "+region.getOffset());
            System.out.println("region.Length = "+region.getLength());
            
            if(region.getOffset() <= valorMedio){
              //viewer.setVisibleRegion(0, valorMedio);
              int ln = document.getLineOfOffset(region.getOffset());
              viewer.setTopIndex(ln);
              System.out.println("Ln1a ====>"+ln);
              System.out.println("7");
            }
            if(region.getOffset() > valorMedio){
              //viewer.setVisibleRegion(valorMedio, valorMedio);
              int ln = document.getLineOfOffset(region.getOffset());
              viewer.setTopIndex(ln);
              System.out.println("Ln1b ====>"+ln);
              System.out.println("8");
            }
            int g = region.getOffset();
            System.out.println("lista cantidad"+listValue.size());
            listValue.add(f, new Integer(g));
            System.out.println("Lo encontro");
        }else{
          if(wrap){
                x = ((Integer)listValue.get(0)).intValue();
                System.out.println("Valor de X ===> " + x);
                System.out.println("**********Wrap**********");
                
                viewer.setSelectedRange(x,y);
                valorMedio = z/2;
                if(x <= valorMedio){
                    System.out.println("x<=valorMedio");
                    int ln = document.getLineOfOffset(x);
                    System.out.println("Ln1 ====>"+ln);
                    viewer.setTopIndex(ln);
                }
                if(x > valorMedio){
                    System.out.println("x>valorMedio");
                    int ln = document.getLineOfOffset(x);
                    System.out.println("Ln2 ====>"+ln);
                    viewer.setTopIndex(ln);
                }
                System.out.println("Lo encontro de nuevo");
              }
          }
          System.out.println("9");
          //else{
            //mostrara todo el scope
          //System.out.println("whole scope");    
          //viewer.setVisibleRegion(0, frda.length());
          //}       
      // If find succeeded, enable Replace buttons.
      // Otherwise, disable Replace buttons.
      // We know find succeeded if region is not null
        enableReplaceButtons(region != null);
        System.out.println("10");
        
      } catch (BadLocationException e){
        // Ignore
      } catch (PatternSyntaxException e) {
        // Show the error to the user
        showError(e.getMessage());
      }
    }
  }

  /**
   * Performs a replace
   * 
   * @param replaceText
   *            the replacement text
   */
  protected void doReplace(String replaceText) {
    try {
      System.out.println("replace::");
      frda.replace(replaceText, false);
    } catch (BadLocationException e) {
      // Ignore
    }
  }

  /**
   * Creates the dialog's contents
   * 
   * @param shell
   */
  protected Control createContents(Composite parent) {
    Composite composite = new Composite(parent, SWT.NONE);
    
    composite.setLayout(new GridLayout(2, false));
    // Add the text input fields
    Composite text = new Composite(composite, SWT.NONE);
    text.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
    text.setLayout(new GridLayout(3, true));
    changableComponentList.add(text);

    new Label(text, SWT.LEFT).setText("&Find:");
    final Text findText = new Text(text, SWT.BORDER);
    GridData data = new GridData(GridData.FILL_HORIZONTAL);
    data.horizontalSpan = 2;
    findText.setLayoutData(data);

    new Label(text, SWT.LEFT).setText("R&eplace With:");
    final Text replaceText = new Text(text, SWT.BORDER);
    data = new GridData(GridData.FILL_HORIZONTAL);
    data.horizontalSpan = 2;
    replaceText.setLayoutData(data);
    
    // Add the match case checkbox
    final Button match = new Button(text, SWT.CHECK);
    match.setText("&Match Case");

    // Add the whole word checkbox
    final Button wholeWord = new Button(text, SWT.CHECK);
    wholeWord.setText("&Whole Word");

    // Add the regular expression checkbox
    final Button regexp = new Button(text, SWT.CHECK);
    regexp.setText("RegE&xp");

    // Add the direction radio buttons
    final Button down = new Button(text, SWT.RADIO);
    down.setText("D&own");

    final Button up = new Button(text, SWT.RADIO);
    up.setText("&Up");
    //wrap....
    final Button wrap = new Button(text, SWT.CHECK);
    wrap.setText("Wrap the &Search");

    // Add the buttons
    Composite buttons = new Composite(composite, SWT.NONE);
    buttons.setLayout(new GridLayout());

    // Create the Find button
    doFind = new Button(buttons, SWT.PUSH);
    doFind.setText("Fi&nd");
    doFind.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
    // Set the initial find operation to FIND_FIRST
    // doFind.setData(FindReplaceOperationCode.FIND_FIRST);
    // Create the Replace button
    doReplace = new Button(buttons, SWT.PUSH);
    doReplace.setText("&Replace");
    doReplace.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
    
    // Create the Replace/Find button
    doReplaceFind = new Button(buttons, SWT.PUSH);
    doReplaceFind.setText("Replace/Fin&d");
    doReplaceFind.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
    doReplaceFind.addSelectionListener(new SelectionAdapter() {
      public void widgetSelected(SelectionEvent event) {
        System.out.println("it found selection");
        doReplace(replaceText.getText());
        doFind(findText.getText(), down.getSelection(), match
            .getSelection(), wholeWord.getSelection(), regexp
            .getSelection(),wrap.getSelection());
      }
    });

    // Create the Close button
    Button close = new Button(buttons, SWT.PUSH);
    close.setText("Close");
    close.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
    close.addSelectionListener(new SelectionAdapter() {
      public void widgetSelected(SelectionEvent event) {
        close();
      }
    });

    // Disable the replace button when find text is modified
    findText.addModifyListener(new ModifyListener() {
      public void modifyText(ModifyEvent event) {
        enableReplaceButtons(false);
      }
    });

    // Do a find
    doFind.addSelectionListener(new SelectionAdapter() {
      public void widgetSelected(SelectionEvent event) {
        doFind(findText.getText(), down.getSelection(), match
            .getSelection(), wholeWord.getSelection(), regexp
            .getSelection(), wrap.getSelection());
      }
    });

    // Replace loses "find" state, so disable buttons
    doReplace.addSelectionListener(new SelectionAdapter() {
      public void widgetSelected(SelectionEvent event) {
        doReplace(replaceText.getText());
        enableReplaceButtons(false);
      }
    });

    // adding components to be changed
    changableComponentList.add(text);
    changableComponentList.add(doFind);
    changableComponentList.add(doReplace);
    changableComponentList.add(doReplaceFind);
    changableComponentList.add(findText);

    // Set defaults
    down.setSelection(true);
    findText.setFocus();
    enableReplaceButtons(false);
    //setDefaultButton(doFind);
    return composite;
  }

  /**
   * Enables/disables the Replace and Replace/Find buttons
   * 
   * @param enable
   *            whether to enable or disable
   */
  protected void enableReplaceButtons(boolean enable) {
      doReplace.setEnabled(enable);
      doReplaceFind.setEnabled(enable);
  }

  /**
   * Shows an error
   * 
   * @param message
   *            the error message
   */
  protected void showError(String message) {
      MessageDialog.openError(parentShell, "Error", message);
  }
}
