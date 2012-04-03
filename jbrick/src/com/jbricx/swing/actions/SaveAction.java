package com.jbricx.swing.actions;

import java.io.File;

import com.jbricx.model.PersistentDocument;
import com.jbricx.pjo.ActionControlClass;
import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;

import com.jbricx.swing.ui.JBricxManager;

/**
 * This action class responds to requests to save a file
 * 
 */
public class SaveAction extends JBricxAbstractAction {

  /**
   * SaveAction constructor
   */
  public SaveAction(final JBricxManager manager) {
	  super("", new ImageIcon("./resources/images/document-save.png"), manager);

    // setToolTipText("Save");
    // setAccelerator(SWT.CTRL + 's');
  }

  /**
   * Saves the file
   */
  @Override
  public void actionPerformed(ActionEvent e) {
    PersistentDocument currDoc = getManager().getTabFolder().getSelection()
        .getPersistantDocument();
    System.out.println("Saving!");
    // Check and see if it was previously saved as a backup
    if (currDoc.getFileName() != null
        && currDoc.getFileName().endsWith(".bak.nxc")) {
    	
    	System.out.println("CURR DOC IS NOT NULL OR BAK.NXC");
      
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

    if (getManager().isAutoCompile()) {
      CompileAction compileAction = new CompileAction(getManager());
      compileAction.run();
    }
    
  }
}
