package com.jbricx.swing.actions;

import java.awt.FileDialog;

import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;

import com.jbricx.swing.ui.JBricxManager;

import com.jbricx.pjo.FileExtensionConstants;
import com.jbricx.swing.ui.preferences.PreferenceStore;

/**
 * This action class responds to requests open a file
 */
@SuppressWarnings("serial")
public class OpenAction extends JBricxAbstractAction {

  /**
   * OpenAction constructor
   */
  public OpenAction(final JBricxManager manager) {
	  super("", new ImageIcon("./resources/images/document-open.png"), manager);
  }

  /**
   * Opens an existing file
   */
  public void actionPerformed(ActionEvent e){
    // lets set the path of the dialog to the workspace
    /*PreferenceStore store = getManager().getPreferences();
    String workspacePath = store.getString(FileExtensionConstants.WRKSPC);

    // Use the file dialog
    FileDialog dlg = new FileDialog(getManager().getShell(), SWT.OPEN);
    dlg.setFilterNames(FileExtensionConstants.FILTER_NAMES);
    dlg.setFilterExtensions(FileExtensionConstants.FILTER_EXTENSIONS);
    dlg.setFilterPath(workspacePath);

    String fileName = dlg.open();

    if (fileName != null) {
      getManager().getTabFolder().open(fileName);
    }
    */
  } 
}
