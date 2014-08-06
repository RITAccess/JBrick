package com.jbricx.swing.actions;

import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FilenameFilter;

import javax.swing.ImageIcon;

import com.jbricx.swing.ui.JBricxManager;
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
	    super("", new ImageIcon(OpenAction.class.getResource("/icons/openDocument.png")), manager);
	  }

	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		FileDialog fDialog = new FileDialog(getManager().getShell(), "Open", FileDialog.LOAD);
		fDialog.setFilenameFilter(new FilenameFilter(){
			@Override
			public boolean accept(File dir, String name) {
				return name.toLowerCase().endsWith(".nxc");
			}
		});
		fDialog.setDirectory(PreferenceStore.getString(PreferenceStore.Preference.WORKSPACE));
		fDialog.setVisible(true);
		String filepath = fDialog.getFile();
		if (filepath != null) {
			filepath = fDialog.getDirectory() + filepath;
			getManager().getTabFolder().open(filepath);
		}
		
	}
	
	/**
	 * Gets the extension of a file
	 * @param file
	 * @return Returns extension or and empty string if it has none.
	 */
	public String getExtension(File file){
		String name = file.getName();
		if(name.lastIndexOf(".") > 0){
			return "." + name.substring(name.lastIndexOf(".")+1);
		} else {
			return "";
		}
	}
}
