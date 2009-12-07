package com.jbricx.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;

import com.jbricx.communications.BrickCreator;
import com.jbricx.communications.ExitStatus;
import com.jbricx.pjo.JBrickEditor;



public class DownloadAction extends Action {
	  /**
	   * AboutAction constructor
	   */
	  public DownloadAction() {
	    super("&Download@Ctrl+D", ImageDescriptor.createFromFile(AboutAction.class,
	        "/images/media-playback-start.png"));
	    setToolTipText("Download");
	  }

	  /**
	   * Shows an about box
	   */
	  public void run() {
		
		System.out.println(JBrickEditor.getMainWindow().getCurrentTabItem().getDocument().getFileName());
		ExitStatus e = BrickCreator.createBrick().downloadFile(JBrickEditor.getMainWindow().getCurrentTabItem().getDocument().getFileName());
		
		if (e.isOk()){
			MessageDialog.openInformation(JBrickEditor.getApp().getMainWindow().getShell(), "Compile", "Compiling was a success!!");
		}
		else{
			MessageDialog.openInformation(JBrickEditor.getApp().getMainWindow().getShell(), "Compile", "Compiling failed: \n"+e.getMesage());
		}
	  }
	}
