package com.jbricx.swing.actions;

import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;

import com.jbricx.communication.NXTAccess;
import com.jbricx.swing.ui.JBricxManager;


/**
 * Downloads the current file into the brick.
 */
@SuppressWarnings("serial")
public class DownloadAction extends JBricxAbstractAction{

	JBricxManager jBManager;
	
	/**
	 * Constructor
	 */
	public DownloadAction(final JBricxManager manager) {
		super("", new ImageIcon(DownloadAction.class.getResource("/images/download.png")), manager);
		this.jBManager = manager;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
    	CompileAction action = new CompileAction(jBManager);
    	Boolean saved = action.compile("compiled", "be downloaded", "compile", "Save & Compile", true);
    	if(saved){
    	    this.getManager().getStatusPane().pushMessage(
    				NXTAccess.downloadToBrick(jBManager.getTabFolder().getSelection().getFileFullPath(), false),
    				true
    	    );
    	}
	}
}