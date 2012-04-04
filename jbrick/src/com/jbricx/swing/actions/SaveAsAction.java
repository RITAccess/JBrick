package com.jbricx.swing.actions;

import java.awt.event.ActionEvent;
import java.io.File;

import com.jbricx.model.PersistentDocument;
import com.jbricx.pjo.ActionControlClass;
import javax.swing.ImageIcon;

import com.jbricx.swing.ui.JBricxManager;
import com.jbricx.swing.ui.tabs.JBricxTabItem;

/**
 * This action class responds to requests to save a file as . . .
 */
public class SaveAsAction extends JBricxAbstractAction {

	/**
	 * SaveAsAction constructor
	 */
	public SaveAsAction(final JBricxManager manager) {
		super("", new ImageIcon("./resources/images/document-save-as.png"), manager);
	}

	/**
	 * Saves the file
	 */
	public void actionPerformed(ActionEvent e) {
		
		JBricxTabItem tabItem = getManager().getTabFolder().getSelection();
		PersistentDocument currDoc = getManager().getTabFolder().getSelection()
				.getPersistantDocument();

		// Check and see if it was previously saved as a backup
		if (currDoc.getFileName() != null
				&& currDoc.getFileName().endsWith(".bak.nxc")) {
			String fname = currDoc.getFileName();
			ActionControlClass.saveFile(getManager().getTabFolder()
					.getSelection(), true, getManager());
			if (!currDoc.getFileName().endsWith(".bak.nxc")) {
				// File was successfully saved, cleanup the temporary file
				File f = new File(fname);
				f.delete();
			}
		} else
			ActionControlClass.saveFile(tabItem, true, getManager());

		if (getManager().isAutoCompile()) {
			CompileAction compileAction = new CompileAction(getManager());
			compileAction.run();
		}
		//Refresh file names
	    getManager().getTabFolder().refreshTabItems();
	}
}
