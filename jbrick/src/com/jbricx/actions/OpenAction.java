package com.jbricx.actions;

import org.eclipse.jface.preference.PreferenceStore;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;

import com.jbricx.pjo.FileExtensionConstants;
import com.jbricx.ui.JBrickManager;

/**
 * This action class responds to requests open a file
 */
public class OpenAction extends AbstractAction {

  /**
   * OpenAction constructor
   */
  public OpenAction(final JBrickManager manager) {
    super("&Open...@Ctrl+O", ImageDescriptor.createFromFile(OpenAction.class, "/images/document-open.png"), manager);
    setToolTipText("Open");
  }

  /**
   * Opens an existing file
   */
  public void run() {
    // lets set the path of the dialog to the workspace
    PreferenceStore store = getManager().getPreferences();
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
  }
}
