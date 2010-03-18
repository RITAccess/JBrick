package com.jbricx.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;

import com.jbricx.communications.AbstractNXTBrick;
import com.jbricx.communications.BrickCreator;
import com.jbricx.communications.ExitStatus;
import com.jbricx.communications.NXT;
import com.jbricx.communications.NXTNotFoundException;
import com.jbricx.communications.UnableToCreateNXTException;
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
		
		System.out.println(JBrickEditor.getMainWindow().getCurrentTabItem().getDocument().getFileName());
		AbstractNXTBrick nxt = BrickCreator.createBrick();
		try {
			nxt.NXTConnect(NXT.ConnectionType.USB);
			ExitStatus e = nxt.downloadFile(JBrickEditor.getMainWindow().getCurrentTabItem().getDocument().getFileName());
			
			if (e.isOk()){
				MessageDialog.openInformation(JBrickEditor.getApp().getMainWindow().getShell(), "Download", "Downloading was a success!!");
			}
			else{
				MessageDialog.openInformation(JBrickEditor.getApp().getMainWindow().getShell(), "Download", "Downloading failed: \n"+e.getMesage());
			}
		} catch (NXTNotFoundException e1) {
			// TODO Auto-generated catch block
			MessageDialog.openInformation(JBrickEditor.getApp().getMainWindow().getShell(), "Download","Connecting to NXT Brick failed!");
		} catch (UnableToCreateNXTException e1) {
			// TODO Auto-generated catch block
			MessageDialog.openInformation(JBrickEditor.getApp().getMainWindow().getShell(), "Download","Connecting to NXT Brick failed!");
		}
		
	  }
	}
