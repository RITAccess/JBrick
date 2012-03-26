package com.jbricx.swing.actions;

import java.awt.FileDialog;

import java.awt.event.ActionEvent;
import java.io.File;
import java.util.prefs.Preferences;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

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
	    super("", new ImageIcon("./resources//images/document-open.png"), manager);
	  }



	@Override
	public void actionPerformed(ActionEvent arg0) {
		Preferences prefs = PreferenceStore.getPrefs();
		
		final JFileChooser fileOpener = new JFileChooser(prefs.get(PreferenceStore.WRKSPC , PreferenceStore.WRKSPC_DEFAULT));
		
		fileOpener.setFileFilter(new FileNameExtensionFilter("Accepted",PreferenceStore.FILTER_EXTENSIONS));
		fileOpener.showOpenDialog(getManager().getShell());
		File selectedFile = fileOpener.getSelectedFile();
		
		if(selectedFile.exists()){
			getManager().getTabFolder().open(selectedFile.getAbsolutePath());
		}
	}
	}
