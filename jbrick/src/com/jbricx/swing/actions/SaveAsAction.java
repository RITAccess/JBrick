package com.jbricx.swing.actions;

import java.awt.event.ActionEvent;
import java.io.File;

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
		super("", new ImageIcon(SaveAsAction.class.getResource("/images/document-save-as.png")), manager);
	}

	/**
	 * Saves the file
	 */
	public void actionPerformed(ActionEvent e) {
		
		JBricxTabItem currItem = getManager().getTabFolder().getSelection();

		// Check and see if it was previously saved as a backup
		if (!currItem.isNewFile()
				&& currItem.getFileName().endsWith(".bak.nxc")) {
			String fname = currItem.getFileName();
			ActionControlClass.saveFile(getManager().getTabFolder()
					.getSelection(), true, getManager());
			if (!fname.endsWith(".bak.nxc")) {
				// File was successfully saved, cleanup the temporary file
				File f = new File(fname);
				f.delete();
			}
		} else
			ActionControlClass.saveFile(currItem, true, getManager());

		if (getManager().isAutoCompile()) {
			CompileAction compileAction = new CompileAction(getManager());
			compileAction.run();
		}
		//Refresh file names
	    getManager().getTabFolder().refreshTabTitles();
	}
}
