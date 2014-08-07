package com.jbricx.swing.actions;

import java.awt.event.ActionEvent;
import java.io.File;

import javafx.application.Platform;
import javafx.stage.FileChooser;

import javax.swing.ImageIcon;

import com.jbricx.swing.ui.JBricxManager;
import com.jbricx.swing.ui.preferences.PreferenceStore;

/**
 * This action class responds to requests open a file
 */
@SuppressWarnings("serial") 
public class OpenAction extends JBricxAbstractAction {

	private FileChooser.ExtensionFilter extFilterNXC;
	private FileChooser.ExtensionFilter extFilterAll;
	
/**
   * OpenAction constructor
   */
  public OpenAction(final JBricxManager manager) {
	    super("", new ImageIcon(OpenAction.class.getResource("/icons/openDocument.png")), manager);

		extFilterNXC = new FileChooser.ExtensionFilter("NXC Files (*.nxc)","*.nxc","*.NXC");
		extFilterAll = new FileChooser.ExtensionFilter("All Files","*.*");
	  }

	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		Platform.runLater(new Runnable(){
			@Override
			public void run() {
				FileChooser fChooser = new FileChooser();
				fChooser.getExtensionFilters().add(extFilterNXC);
				fChooser.getExtensionFilters().add(extFilterAll);
				if(fChooser.getInitialDirectory() == null){
					fChooser.setInitialDirectory(new File(PreferenceStore.getString(PreferenceStore.Preference.WORKSPACE)));
				}
				fChooser.setTitle("Open");
				File file = fChooser.showOpenDialog(null);
				if (file != null) {
					getManager().getTabFolder().open(file.getAbsolutePath());
				}
			}
			
		});
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
