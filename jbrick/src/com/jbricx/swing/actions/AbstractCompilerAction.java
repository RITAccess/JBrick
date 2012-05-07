/**
 * 
 */
package com.jbricx.swing.actions;

import java.io.IOException;

import javax.swing.Icon;
import javax.swing.JOptionPane;

import com.jbricx.swing.communications.ExitStatus;
import com.jbricx.swing.ui.JBricxManager;
import com.jbricx.swing.ui.preferences.PreferenceStore;
import com.jbricx.swing.ui.tabs.JBricxStatusPane;
import com.jbricx.swing.ui.tabs.JBricxTabItem;

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

	
	protected JBricxTabItem getCurrentTab() {
	 return getManager().getTabFolder().getSelection();
	 }

	/**
	 * @return the debugging table from the main window
	 */
	protected JBricxStatusPane getStatusPane() {
		return getManager().getStatusPane();
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
			getStatusPane().clearOldMessages();
			// Clear the annotations on the border
			//getCurrentTab().clearAnnotations();
			// Execute the operation
			final ExitStatus run = doRun(getCurrentTab().getFileFullPath());
			
			if (run.isOk()) {
				onSuccess();

			} else {
				if(run.getCompilerErrors().size() > 0) {
					displayErrors(run);
					onFailure(run);
				}
			}
		}
	}

	/**
	 * Save the current file.
	 */
	protected boolean save() {
		// TODO Implement save action
		if (getCurrentTab().isEmpty()) {
			JOptionPane.showMessageDialog(getManager().getShell(),
				    "Cannot compile an empty file.",
				    "Compile",
				    JOptionPane.WARNING_MESSAGE);
			return false;
		}

		JBricxTabItem tabItem = getCurrentTab();
		String filename="";
		if (tabItem.isNewFile()&&(!tabItem.getFileAbsolutePath().endsWith(".bak.nxc"))) {
			filename = PreferenceStore.getPrefs().get(PreferenceStore.WRKSPC, PreferenceStore.WRKSPC_DEFAULT)
			+ (System.getProperty("os.name").contains("OS X") ? "" : System.getProperty("file.separator"))
			+ getCurrentTab().getFileName() + ".bak.nxc";
		}else{
			filename = tabItem.getFileAbsolutePath();
		}
		if (tabItem.isDirty()) {

			try {
				
				tabItem.saveAs(filename);
				getManager().getTabFolder().refreshTabItems();
			} catch (IOException e) {
				JOptionPane.showMessageDialog(getManager().getShell(),
					    "There was an error saving the current file"+
					    e.getMessage(),
					    "Error",
					    JOptionPane.WARNING_MESSAGE);
				
				return false;
			}
		}

		return true;
	}

	/**
	 * Displays the error messages in the status panel.
	 * 
	 * @param status - The item containing all the error messages
	 */

	protected void displayErrors(final ExitStatus status) {
		JBricxStatusPane statusPane = getStatusPane();
		// iterate throw the returned message from the compiler
//		for (CompilerError ce : status.getCompilerErrors()) {
//			statusPane.pushMessage(ce.toString());
			statusPane.pushMessage(status.getCompilerErrors());
			//TODO: Logic for adding error annotations.
			// add a new row to the table for each error
//			int intLineNumber = Integer.parseInt(ce.getLineNumber());
//			if (getCurrentTab().getPersistantDocument().getNumberOfLines() < intLineNumber) {
//				intLineNumber = getCurrentTab().getPersistantDocument()
//						.getNumberOfLines();
//			}
//			getCurrentTab().addAnnotation(intLineNumber, ce.getMessage());
//		} // end of for
	}

	/**
	 * Execute the particular compiler operation.
	 * 
	 * @param filename
	 */
	protected abstract ExitStatus doRun(final String filename);

	/**
	 * Execute after a successful operation.
	 */
	protected abstract void onSuccess();

	/**
	 * Execute after a failed operation.
	 * @param run 
	 */
	protected abstract void onFailure(ExitStatus run);
}
