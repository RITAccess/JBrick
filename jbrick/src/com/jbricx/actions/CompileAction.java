package com.jbricx.actions;

import java.io.File;
import java.io.IOException;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.Position;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;

import annotation.ErrorAnnotation;

import com.jbricx.communications.BrickCreator;
import com.jbricx.communications.ExitStatus;
import com.jbricx.model.PersistentDocument;
import com.jbricx.pjo.ActionControlClass;
import com.jbricx.pjo.JBrickEditor;
import com.jbricx.ui.JBrickTabItem;
import com.jbricx.ui.MainWindow;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

/**
 * This class shows an About box
 */
public class CompileAction extends Action {

    /**
     * AboutAction constructor
     */
    public CompileAction() {
        super("&Compile@Ctrl+Shift+B", ImageDescriptor.createFromFile(
                CompileAction.class, "/images/compile.png"));
        setToolTipText("Compile");
    }

    /**
     * Shows an about box
     */
    public void run() {
        MainWindow mainWindow = JBrickEditor.getInstance().getMainWindow();
        JBrickTabItem curTabItem = mainWindow.getCurrentTabItem();
        PersistentDocument currDoc = curTabItem.getDocument();
        
        if (currDoc.getFileName() == null) { /* A new file so save before compiling */
            MessageBox box = new MessageBox(mainWindow.getShell(), SWT.OK);
            box.setText("Compile");
            box.setMessage("Before compiling, you need to save the code to file");

            if (box.open() == SWT.OK) {
                ActionControlClass.saveFile(curTabItem, false);                
            }
        } else if (currDoc.isDirty()) {
            try {
                currDoc.save();
                mainWindow.setStatus("Saving File . . .");
            } catch (IOException e) {
                mainWindow.setStatus("There was error while saving File . . .");
                // TODO Auto-generated catch block
                //e1.printStackTrace();
            }
        }

        //get the debugging table from the main window
        Table tbl = (Table) JBrickEditor.getInstance().getMainWindow().getTable();
        tbl.removeAll();
        ExitStatus e = BrickCreator.createBrick().compile(currDoc.getFileName());

        JBrickTabItem tab = JBrickEditor.getInstance().getMainWindow().getCurrentTabItem();
        tab.fAnnotationModel.removeAllAnnotations();
        if (e.isOk()) {
            MessageDialog.openInformation(JBrickEditor.getInstance().getMainWindow().getShell(), "Compile", "Compile was a success!");
        } else {
            String msg = e.getMesage();
            //get the debugging table from the main window
            tbl.clearAll();
            int errorMessageIndex;
            String errorTxt = "";
            String lineNumber;

            //iterate throw the returned message from the compiler
            while ((errorMessageIndex = msg.indexOf("Error:")) > 0) {
                msg = msg.substring(errorMessageIndex);

                System.out.println(errorMessageIndex);
                System.out.println(msg.indexOf("File"));

                errorTxt = msg.substring(msg.indexOf("Error:"), msg.indexOf("File"));
                lineNumber = msg.substring(msg.indexOf("line ") + 5, msg.indexOf("#"));
                msg = msg.substring(msg.indexOf("#"));

                //add a new row to the table for each error
                TableItem line = new TableItem(tbl, SWT.NONE);

                int intLineNumber = Integer.parseInt(lineNumber);
                if (tab.getDocument().getNumberOfLines() < intLineNumber) {
                    intLineNumber = tab.getDocument().getNumberOfLines();
                    lineNumber = String.valueOf(intLineNumber);
                }

                // add an annotation
                ErrorAnnotation errorAnnotation = new ErrorAnnotation(intLineNumber,
                        "Learn how to spell \"text!\"");

                try {
                    int offset = tab.getDocument().getLineOffset(intLineNumber - 1);

                    //tab.fAnnotationModel.addAnnotation(errorAnnotation, new Position(offset, tab.getDocument().getLineLength(intLineNumber)));
                    tab.fAnnotationModel.addAnnotation(errorAnnotation, new Position(offset, tab.getDocument().getLineLength(intLineNumber - 1)));
                } catch (BadLocationException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                // lets underline the word "texst"
            }
        }
    }
}
