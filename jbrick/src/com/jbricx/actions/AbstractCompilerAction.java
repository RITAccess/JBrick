/**
 * 
 */
package com.jbricx.actions;

import java.io.IOException;

import com.jbricx.communications.CompilerError;
import com.jbricx.communications.ExitStatus;
import com.jbricx.model.PersistentDocument;
import com.jbricx.ui.JBrickManager;
import com.jbricx.ui.tabs.JBrickTabItem;

/**
 * Contains common methods for the Actions that are involved with the compiler
 * tool.
 * 
 * @author byktol
 */
public abstract class AbstractCompilerAction extends AbstractAction {

  public AbstractCompilerAction(String text, ImageDescriptor image,
          JBrickManager manager) {
    super(text, image, manager);
  }

  protected JBrickTabItem getCurrentTab() {
    return getManager().getTabFolder().getSelection();
  }

  /**
   * @return the debugging table from the main window
   */
  protected Table getTable() {
    return (Table) getManager().getTable();
  }

  /**
   * Run the common operations that require the compiling tool.
   */
  public void run() {
    if (getManager().getTabFolder().getSelection() == null)
      return;

    // Save the current tab contents
    if (save()) {
      // Clear the status messages
      getTable().removeAll();
      // Clear the annotations on the border
      getCurrentTab().clearAnnotations();
      // Execute the operation
      final ExitStatus run = doRun(getCurrentTab().getDocument().getFileName());
  
      if (run.isOk()) {
        onSuccess();
  
      } else {
        displayErrors(run);
        onFailure();
  
      }
    }
  }

  /**
   * Save the current file.
   */
  protected boolean save() {
    if (getCurrentTab().getViewer().getTextWidget().getCharCount() == 0) {
      MessageDialog.openWarning(getManager().getShell(), "Compile",
        "Cannot compile an empty file.");
      return false;
    }

    PersistentDocument document = getCurrentTab().getDocument();

    if (document.getFileName() == null) {
      document.setFileName(getManager().getWorkspacePath()
        + System.getProperty("file.separator") + getCurrentTab().getText()
        + ".bak.nxc");
    }

    if (document.isDirty()) {

      try {
        document.save();
        getManager().setStatus("Saving File . . .");

      } catch (IOException e) {
        getManager().setStatus("There was an error while saving the file");
        e.printStackTrace();
        return false;
      }
    }

    return true;
  }

  /**
   * Displays the error messages in the status panel.
   * @param status The item containing all the error messages
   */
  protected void displayErrors(final ExitStatus status) {
    // iterate throw the returned message from the compiler
    for (CompilerError ce : status.getCompilerErrors()) {
      TableItem line = new TableItem(getTable(), SWT.NONE);
      line.setText(ce.toString());

      // add a new row to the table for each error
      int intLineNumber = Integer.parseInt(ce.getLineNumber());

      if (getCurrentTab().getDocument().getNumberOfLines() < intLineNumber) {
        intLineNumber = getCurrentTab().getDocument().getNumberOfLines();
      }

      getCurrentTab().addAnnotation(intLineNumber, ce.getMessage());
    } // end of for
  }

  /**
   * Execute the particular compiler operation.
   * @param filename
   */
  protected abstract ExitStatus doRun(final String filename);
  /**
   * Execute after a successful operation.
   */
  protected abstract void onSuccess();
  /**
   * Execute after a failed operation.
   */
  protected abstract void onFailure();
}
