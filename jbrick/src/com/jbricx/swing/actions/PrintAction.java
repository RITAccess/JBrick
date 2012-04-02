package com.jbricx.swing.actions;

import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;

import com.jbricx.swing.ui.JBricxManager;

/**
 * This action class prints the document
 */
public class PrintAction extends JBricxAbstractAction {

	/**
	 * PrintAction constructor
	 */
	public PrintAction(final JBricxManager manager) {
		super("", new ImageIcon("./resources/images/document-print.png"), manager);
	}

	/**
	 * Prints the document
	 */
	public void actionPerformed(ActionEvent e) {
		/*SourceViewer viewer = getManager().getTabFolder().getSourceViewer();

		StyledTextPrintOptions options = new StyledTextPrintOptions();

		options.jobName = "Example";
		options.printLineBackground = true;

		viewer.print(options);
		*/
	}
}
