package com.jbricx.swing.actions;

import java.io.File;

import com.jbricx.model.PersistentDocument;
import com.jbricx.pjo.ActionControlClass;
import com.jbricx.ui.JBrickManager;
import com.jbricx.ui.tabs.JBrickTabItem;

/**
 * This action class responds to requests to save a file as . . .
 */
public class SaveAsAction extends AbstractAction {

	/**
	 * SaveAsAction constructor
	 */
	public SaveAsAction(final JBrickManager manager) {
		super("Save As...", ImageDescriptor.createFromFile(SaveAsAction.class,
				"/images/document-save-as.png"), manager);
		setToolTipText("Save As");
	}

	/**
	 * Saves the file
	 */
	public void run() {
		JBrickTabItem tabItem = getManager().getTabFolder().getSelection();
		PersistentDocument currDoc = getManager().getTabFolder().getSelection()
				.getDocument();

		// Check and see if it was previously saved as a backup
		if (currDoc.getFileName() != null
				&& currDoc.getFileName().endsWith(".bak.nxc")) {
			String fname = currDoc.getFileName();
			ActionControlClass.saveFile(getManager().getTabFolder()
					.getSelection(), true, getManager(), getManager()
					.getWorkspacePath());
			if (!currDoc.getFileName().endsWith(".bak.nxc")) {
				// File was successfully saved, cleanup the temporary file
				File f = new File(fname);
				f.delete();
			}
		} else
			ActionControlClass.saveFile(tabItem, true, getManager(),
					getManager().getWorkspacePath());

		if (getManager().isAutoCompile()) {
			CompileAction compileAction = new CompileAction(getManager());
			compileAction.run();
		}
	}
}
