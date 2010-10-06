package com.jbricx.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;

import com.jbricx.communications.AbstractNXTBrick;
import com.jbricx.communications.BrickCreator;
import com.jbricx.communications.ExitStatus;
import com.jbricx.communications.NXT;

import com.jbricx.communications.exceptions.NXTNotFoundException;
import com.jbricx.communications.exceptions.UnableToCreateNXTException;


import com.jbricx.pjo.JBrickEditor;



public class DownloadAction extends Action {
	  /**
	   * AboutAction constructor
	   */
	  public DownloadAction() {
	    super("&Download@Ctrl+Shift+D", ImageDescriptor.createFromFile(AboutAction.class,
	        "/images/media-playback-start.png"));
	    setToolTipText("Download");
	  }

	  /**
	   * Shows an about box
	   */
	  public void run() {
		
//		System.out.println(JBrickEditor.getMainWindow().getCurrentTabItem().getDocument().getFileName());
		AbstractNXTBrick nxt = BrickCreator.createBrick();
		
//		nxt.NXTConnect(NXT.ConnectionType.USB);
		ExitStatus e = nxt.downloadFile(JBrickEditor.getInstance().getMainWindow().getCurrentTabItem().getDocument().getFileName());
		
		if (e.isOk()){
			MessageDialog.openInformation(JBrickEditor.getInstance().getMainWindow().getShell(), "Download", "Downloading was a success!!");
		}
		else{
			MessageDialog.openInformation(JBrickEditor.getInstance().getMainWindow().getShell(), "Download", "Downloading failed: \n"+e.getMesage());
		}
		
		
	  }
	}
