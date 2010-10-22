package com.jbricx.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.text.source.SourceViewer;
import org.eclipse.swt.custom.StyledTextPrintOptions;
import org.eclipse.swt.widgets.Shell;

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
		Shell s = JBrickEditor.getInstance().getMainWindow().getShell();
		SourceViewer viewer = JBrickEditor.getInstance().getMainWindow().getCurrentTabItem()
				.getViewer();

		StyledTextPrintOptions options = new StyledTextPrintOptions();

		options.jobName = "Example";
		options.printLineBackground = true;

		viewer.print(options);

	}
}
