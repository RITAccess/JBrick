package com.jbricx.swing.actions;

import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import com.jbricx.model.PersistentDocument;
import com.jbricx.pjo.ActionControlClass;
import com.jbricx.swing.communications.NXTManager;
import com.jbricx.swing.communications.ExitStatus;
import com.jbricx.swing.ui.JBricxManager;

/**
 * Compiles the current file.
 */
@SuppressWarnings("serial")
public class CompileAction extends AbstractCompilerAction {

	/**
	 * Constructor
	 */
	public CompileAction(final JBricxManager manager) {
		super("", new ImageIcon(
				"./resources/images/compile.png"), manager);
	}

	@Override
	public void onSuccess() {
		JOptionPane.showMessageDialog(null, "Compile was a success!");
	}

	@Override
	public void onFailure() {
		JOptionPane.showMessageDialog(null, "Errors found during compilation.");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Either add logic for compilation or call super to compile
		// save
		PersistentDocument currDoc = getManager().getTabFolder().getSelection()
        .getPersistantDocument();
	    // Check and see if it was previously saved as a backup
	    if (currDoc.getFileName() != null
	        && currDoc.getFileName().endsWith(".bak.nxc")) {
	      
	    	String fname = currDoc.getFileName();
	      ActionControlClass.saveFile(getManager().getTabFolder().getSelection(),
	          true, getManager());
	      if (!currDoc.getFileName().endsWith(".bak.nxc")) {
	        // File was successfully saved, cleanup the temporary file
	        File f = new File(fname);
	        f.delete();
	      }
	    } else {
	      ActionControlClass.saveFile(getManager().getTabFolder().getSelection(),
	          false, getManager());
	    }
	    
	    //Refresh file names
	    getManager().getTabFolder().refreshTabItems();
	    
	    // getting the file name
		String filename = getManager().getTabFolder().getCurrentFilename();
		System.out.println(filename);

	}

	@Override
	protected ExitStatus doRun(String filename) {
		// TODO Auto-generated method stub
		return NXTManager.getInstance().compile(filename);
	}
}
