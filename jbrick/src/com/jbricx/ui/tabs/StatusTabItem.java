package com.jbricx.ui.tabs;

import org.eclipse.jface.action.StatusLineManager;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

import com.jbricx.ui.JBrickStatusUpdater;

/**
 * This {@link CTabItem} is used to present a status panel for compilation error
 * messages. These messages are 'clickable' and move the text cursor to the line
 * in the opened file containing the error.
 * 
 * @author byktol
 */
public class StatusTabItem extends CTabItem {
  private Table table;

  public StatusTabItem(CTabFolder parent, int style, final JBrickStatusUpdater statusUpdater) {
    super(parent, style);

    setText("Status");
    table = new Table(parent, SWT.BORDER);
    setControl(table);
    parent.setSelection(this);

    table.addListener(SWT.DefaultSelection, new Listener() {

      public void handleEvent(Event e) {
        IDocument document = getDocument();

        try {

          String errorMessageText = ((TableItem) e.item).getText();

          // FIXME: The location is based on a hard-coded string.
          String strLineNumber = errorMessageText.substring(
                  errorMessageText.indexOf("Error on line") + 13,
                  errorMessageText.indexOf(":"));

          int errorLineNumber = Integer.parseInt(strLineNumber.trim()) - 1;

          int offset = document.getLineOffset(errorLineNumber);
          int lineLength = document.getLineLength(errorLineNumber);
          setSelectedRange(offset, lineLength);
          statusUpdater.setStatus(" status bar line " + strLineNumber);

          /*
           * if(lnrc != null){ System.out.println("mouse thing -- "+lnrc
           * .getLineOfLastMouseButtonActivity()); } else{
           * System.out.println("it is null"); }
           */

          // TODO: What does this do?
          getStatusLineManager().getControl().setFocus();

        } catch (BadLocationException ble) {
          ble.printStackTrace();
        }

      }
    });
  }

  /*
   * The following methods are here for 'compatibility' issues with previous
   * version. We want to add features without breaking something, right? These
   * methods should be checked for refactoring purposes.
   */

  /**
   * @return The table used for presenting the status
   */
  public Table getTable() {
    return table;
  }

  /**
   * @return
   */
  protected IDocument getDocument() {
    return null;
  }

  /**
   * @param offset
   *          The number of lines to skip
   * @param lineLength
   *          The number of lines to select
   */
  protected void setSelectedRange(int offset, int lineLength) {
  }

  /**
   * @return
   */
  protected StatusLineManager getStatusLineManager() {
    return null;
  }
}
