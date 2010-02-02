package com.jbricx.actions;

import java.io.IOException;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.source.AnnotationBarHoverManager;
import org.eclipse.jface.text.source.AnnotationModel;
import org.eclipse.jface.text.source.AnnotationPainter;
import org.eclipse.jface.text.source.AnnotationRulerColumn;
import org.eclipse.jface.text.source.CompositeRuler;
import org.eclipse.jface.text.source.IAnnotationAccess;
import org.eclipse.jface.text.source.OverviewRuler;
import org.eclipse.jface.text.source.SourceViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

import annotation.AnnotationConfiguration;
import annotation.AnnotationHover;
import annotation.AnnotationMarkerAccess;
import annotation.CodeViewerConfiguration;
import annotation.ColorCache;
import annotation.ErrorAnnotation;

import com.jbricx.communications.BrickCreator;
import com.jbricx.communications.ExitStatus;
import com.jbricx.pjo.ActionControlClass;
import com.jbricx.pjo.JBrickEditor;
import com.jbricx.ui.JBrickTabItem;

/**
 * This class shows an About box
 */
public class CompileAction extends Action {

	/**
	 * AboutAction constructor
	 */
	public CompileAction() {
		super("&Compile@Ctrl+Shift-b", ImageDescriptor.createFromFile(
				CompileAction.class, "/images/compile.png"));
		setToolTipText("Compile");
	}

	/**
	 * Shows an about box
	 */
	public void run() {
		
		if (JBrickEditor.getMainWindow().getCurrentTabItem().getDocument()
				.getFileName() == null) { /* Save before compiling */
			MessageBox box = new MessageBox(JBrickEditor.getApp()
					.getMainWindow().getShell(), SWT.OK);
			box.setText("Compile");
			box
					.setMessage("Before compiling, you need to save the code to file");
			int ret = box.open();
			if (ret == SWT.OK) {
				ActionControlClass.saveFile(JBrickEditor.getMainWindow()
						.getCurrentTabItem());
			}
		}

		if (JBrickEditor.getMainWindow().getCurrentTabItem().getDocument()
				.isDirty()) {
			try {
				JBrickEditor.getMainWindow().getCurrentTabItem().getDocument()
						.save();
				JBrickEditor.getMainWindow().setStatus("Saving File . . .");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

		//get the debugging table from the main window
		Table tbl = JBrickEditor.getMainWindow().table;
		tbl.removeAll();
		ExitStatus e = BrickCreator.createBrick().compile(
				JBrickEditor.getMainWindow().getCurrentTabItem().getDocument()
						.getFileName());
		
		JBrickTabItem tab = JBrickEditor.getMainWindow().getCurrentTabItem();	
		tab.fAnnotationModel.removeAllAnnotations();
		if (e.isOk()) {
			MessageDialog.openInformation(JBrickEditor.getApp().getMainWindow()
					.getShell(), "Compile", "Compile was a success!");
		} else {
			String msg = e.getMesage();
			//get the debugging table from the main window
//			tbl.clearAll();
			int errorMessageIndex;
			String errorTxt = "";
			String lineNumber;
				
			
			
			
			//iterate throw the returned message from the compiler 
			while ((errorMessageIndex = msg.indexOf("Error:")) > 0) {
				msg = msg.substring(errorMessageIndex);
				
				System.out.println(errorMessageIndex);
				System.out.println(msg.indexOf("File"));
				
				errorTxt = msg.substring(msg.indexOf("Error:"), msg.indexOf("File"));
				lineNumber = msg.substring(msg.indexOf("line ")+5, msg
						.indexOf("#"));
				msg = msg.substring(msg.indexOf("#"));

				//add a new row to the table for each error	
				TableItem line = new TableItem(tbl, SWT.NONE);
				line.setText("Line: " + lineNumber + " " + errorTxt);
				
				int intLineNumber = Integer.parseInt(lineNumber);
				
				// add an annotation
				ErrorAnnotation errorAnnotation = new ErrorAnnotation(intLineNumber,
						"Learn how to spell \"text!\"");

				try {
					int offset = tab.getDocument().getLineOffset(intLineNumber-1);
					
					//tab.fAnnotationModel.addAnnotation(errorAnnotation, new Position(offset, tab.getDocument().getLineLength(intLineNumber)));
					tab.fAnnotationModel.addAnnotation(errorAnnotation, new Position(offset,tab.getDocument().getLineLength(intLineNumber-1) ));
					
					
				} catch (BadLocationException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				// lets underline the word "texst"
				
				
			}

		}

	}
}
