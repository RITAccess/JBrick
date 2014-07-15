package com.jbricx.swing.actions;

import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;

import com.jbricx.swing.ui.JBricxManager;

/**
 * This action class prints the document
 */
@SuppressWarnings("serial") 
public class PrintAction extends JBricxAbstractAction {

	/**
	 * PrintAction constructor
	 */
	public PrintAction(final JBricxManager manager) {
		super("", new ImageIcon(PrintAction.class.getResource("/icons/printDocument.png")), manager);
	}

	/**
	 * Prints the document
	 */
	public void actionPerformed(ActionEvent e) {
		
		getManager().getTabFolder().print();
		/*SourceViewer viewer = getManager().getTabFolder().getSourceViewer();

		StyledTextPrintOptions options = new StyledTextPrintOptions();

		options.jobName = "Example";
		options.printLineBackground = true;

		viewer.print(options);
		*/
	}
}
