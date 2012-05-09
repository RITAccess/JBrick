package com.jbricx.swing.actions;

import java.io.File;
import com.jbricx.pjo.ActionControlClass;
import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;

import com.jbricx.swing.ui.JBricxManager;
import com.jbricx.swing.ui.tabs.JBricxTabItem;

/**
 * This action class responds to requests to save a file
 * 
 */
public class SaveAction extends JBricxAbstractAction {

  /**
   * SaveAction constructor
   */
  public SaveAction(final JBricxManager manager) {
	  super("", new ImageIcon(SaveAction.class.getResource("/images/document-save.png")), manager);

    // setToolTipText("Save");
    // setAccelerator(SWT.CTRL + 's');
  }

  /**
   * Saves the file.
   * Checks if it was saved during compile (.bak)
   * Calls actioncontrolclass which takes the filename and some other things, and saves the file
   * (Im not sure exactly why there are 3 classes (this, actioncontrolclass,safesavedialog) to save a file
   * but not messing with that currently.
   * 
   * When it is done, refreshes the tab items' title.
   */
  @Override
  public void actionPerformed(ActionEvent e) {
    JBricxTabItem currItem = getManager().getTabFolder().getSelection();
    // Check and see if it was previously saved as a backup
    if (!currItem.isNewFile()
        && currItem.getFileName().endsWith(".bak.nxc")) {
      
    	String fname = currItem.getFileAbsolutePath();
      ActionControlClass.saveFile(getManager().getTabFolder().getSelection(),
          true, getManager());
      //File was saved, check again if it has .bak
      if (currItem.getFileAbsolutePath().endsWith(".bak.nxc")) {
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
    
    //Refresh file names
    getManager().getTabFolder().refreshTabItems();
    
  }
}
