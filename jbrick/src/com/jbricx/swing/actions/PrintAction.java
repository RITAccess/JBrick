package com.jbricx.swing.actions;

import com.jbricx.ui.JBrickManager;

/**
 * This action class prints the document
 */
public class PrintAction extends AbstractAction {

	/**
	 * PrintAction constructor
	 */
	public PrintAction(final JBrickManager manager) {
		super("&Print...@Ctrl+P", ImageDescriptor.createFromFile(
				PrintAction.class, "/images/document-print.png"), manager);
		setToolTipText("Print");
	}

	/**
	 * Prints the document
	 */
	public void run() {
		SourceViewer viewer = getManager().getTabFolder().getSourceViewer();

		StyledTextPrintOptions options = new StyledTextPrintOptions();

		options.jobName = "Example";
		options.printLineBackground = true;

		viewer.print(options);
	}
}
