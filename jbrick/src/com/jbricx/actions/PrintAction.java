package com.jbricx.actions;

import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

import javax.swing.text.Document;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.source.SourceViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledTextPrintOptions;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.printing.PrintDialog;
import org.eclipse.swt.printing.Printer;
import org.eclipse.swt.printing.PrinterData;
import org.eclipse.swt.widgets.Shell;

import com.jbricx.model.PersistentDocument;
import com.jbricx.pjo.JBrickEditor;

/**
 * This action class prints the document
 */
public class PrintAction extends Action {
	/**
	 * PrintAction constructor
	 */
	public PrintAction() {
		super("&Print...@Ctrl+P", ImageDescriptor.createFromFile(
				PrintAction.class, "/images/document-print.png"));
		setToolTipText("Print");
	}

	/**
	 * Prints the document
	 */
	public void run() {
		Shell s = JBrickEditor.getMainWindow().getShell();
		SourceViewer viewer = JBrickEditor.getMainWindow().getCurrentTabItem()
				.getViewer();

		/*PrintDialog printDialog = new PrintDialog(s, SWT.NONE);
		printDialog.setText("Print");
		PrinterData printerData = printDialog.open();
		Printer printer = new Printer(printerData);*/

		StyledTextPrintOptions options = new StyledTextPrintOptions();
		options.footer = "\t\t<page>";
		options.jobName = "Example";
		options.printLineBackground = true;

		// viewer.getTextWidget().print(printer);
		viewer.print(options);
		// JBrickEditor.getApp().print();
	}
}
