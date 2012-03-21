package com.jbricx.swing.actions;

import java.io.File;

import org.eclipse.jface.resource.ImageDescriptor;

import com.jbricx.model.PersistentDocument;
import com.jbricx.pjo.ActionControlClass;
import com.jbricx.ui.JBrickManager;

/**
 * This action class responds to requests to save a file
 */
public class SaveAction extends AbstractAction {

  /**
   * SaveAction constructor
   */
  public SaveAction(final JBrickManager manager) {
    super("&Save@Ctrl+S", ImageDescriptor.createFromFile(SaveAction.class,
        "/images/document-save.png"), manager);

    setToolTipText("Save");
    // setAccelerator(SWT.CTRL + 's');
  }

  /**
   * Saves the file
   */
  @Override
  public void run() {
    getManager().setStatus("Saving File . . .");
    PersistentDocument currDoc = getManager().getTabFolder().getSelection()
        .getDocument();

    // Check and see if it was previously saved as a backup
    if (currDoc.getFileName() != null
        && currDoc.getFileName().endsWith(".bak.nxc")) {
      String fname = currDoc.getFileName();
      ActionControlClass.saveFile(getManager().getTabFolder().getSelection(),
          true, getManager(), getManager().getWorkspacePath());
      if (!currDoc.getFileName().endsWith(".bak.nxc")) {
        // File was successfully saved, cleanup the temporary file
        File f = new File(fname);
        f.delete();
      }
    } else {
      ActionControlClass.saveFile(getManager().getTabFolder().getSelection(),
          false, getManager(), getManager().getWorkspacePath());
    }

    if (getManager().isAutoCompile()) {
      CompileAction compileAction = new CompileAction(getManager());
      compileAction.run();
    }
  }
}
