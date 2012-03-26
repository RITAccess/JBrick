/**
 * 
 */
package com.jbricx.swing.actions;

import java.io.IOException;

import javax.swing.Icon;

import com.jbricx.swing.ui.JBricxManager;

/**
 * Contains common methods for the Actions that are involved with the compiler
 * tool.
 * 
 * @author Emma Nelson
 */
@SuppressWarnings("serial")
public abstract class AbstractCompilerAction extends JBricxAbstractAction {

	/**
	 * Constructor
	 * 
	 * @param text - the text for the button
	 * @param icon - image for buttons
	 * @param manager - the JBricxManager
	 */
	public AbstractCompilerAction(String text, Icon icon,
			final JBricxManager manager) {
		super(text, icon, manager);
	}

	// TODO Requires Tabs
	// protected JBrickTabItem getCurrentTab() {
	// return getManager().getTabFolder().getSelection();
	// }

	/**
	 * @return the debugging table from the main window
	 */
//	protected Table getTable() {
//		return (Table) getManager().getTable();
//	}

	/**
	 * Run the common operations that require the compiling tool.
	 */
	public void run() {
		// TODO
//		if (getManager().getTabFolder().getSelection() == null)
//			return;
//
//		// Save the current tab contents
//		if (save()) {
//			// Clear the status messages
//			getTable().removeAll();
//			// Clear the annotations on the border
//			getCurrentTab().clearAnnotations();
//			// Execute the operation
//			final ExitStatus run = doRun(getCurrentTab().getDocument()
//					.getFileName());
//
//			if (run.isOk()) {
//				onSuccess();
//
//			} else {
//				displayErrors(run);
//				onFailure();
//
//			}
//		}
	}

	/**
	 * Save the current file.
	 */
	protected boolean save() {
		// TODO Implement save action
//		if (getCurrentTab().getViewer().getTextWidget().getCharCount() == 0) {
//			MessageDialog.openWarning(getManager().getShell(), "Compile",
//					"Cannot compile an empty file.");
//			return false;
//		}
//
//		PersistentDocument document = getCurrentTab().getDocument();
//
//		if (document.getFileName() == null) {
//			document.setFileName(getManager().getWorkspacePath()
//					+ System.getProperty("file.separator")
//					+ getCurrentTab().getText() + ".bak.nxc");
//		}
//
//		if (document.isDirty()) {
//
//			try {
//				document.save();
//				getManager().setStatus("Saving File . . .");
//
//			} catch (IOException e) {
//				getManager().setStatus(
//						"There was an error while saving the file");
//				e.printStackTrace();
//				return false;
//			}
//		}

		return true;
	}

	/**
	 * Displays the error messages in the status panel.
	 * 
	 * @param status - The item containing all the error messages
	 */
	// TODO
//	protected void displayErrors(final ExitStatus status) {
//		// iterate throw the returned message from the compiler
//		for (CompilerError ce : status.getCompilerErrors()) {
//			TableItem line = new TableItem(getTable(), SWT.NONE);
//			line.setText(ce.toString());
//
//			// add a new row to the table for each error
//			int intLineNumber = Integer.parseInt(ce.getLineNumber());
//
//			if (getCurrentTab().getDocument().getNumberOfLines() < intLineNumber) {
//				intLineNumber = getCurrentTab().getDocument()
//						.getNumberOfLines();
//			}
//
//			getCurrentTab().addAnnotation(intLineNumber, ce.getMessage());
//		} // end of for
//	}

	/**
	 * Execute the particular compiler operation.
	 * 
	 * @param filename
	 */
//	protected abstract ExitStatus doRun(final String filename);

	/**
	 * Execute after a successful operation.
	 */
	protected abstract void onSuccess();

	/**
	 * Execute after a failed operation.
	 */
	protected abstract void onFailure();
}
