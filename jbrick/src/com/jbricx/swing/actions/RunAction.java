package com.jbricx.swing.actions;

import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;

import com.jbricx.communication.NXTAccess;
import com.jbricx.swing.ui.JBricxManager;
import com.sun.jna.Platform;


/**
 * Downloads and runs the current file on the brick.
 */
@SuppressWarnings("serial")
public class RunAction extends JBricxAbstractAction{

	JBricxManager jBManager;
	
	/**
	 * Constructor
	 */
	public RunAction(final JBricxManager manager) {
		super("", new ImageIcon(DownloadAction.class.getResource("/icons/run.png")), manager);
		this.jBManager = manager;
		//Disable the run button on windows because the windows compiler does not support a run option.
		if(!Platform.isMac()){
			setEnabled(false);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
    	CompileAction action = new CompileAction(jBManager);
    	Boolean saved = action.compile("compiled", "be run", "compile", "Save & Compile", true);
    	if(saved){
    	    this.getManager().getStatusPane().pushMessage(
    				NXTAccess.downloadToBrick(jBManager.getTabFolder().getSelection().getFileFullPath(), false, true),
    				true
    	    );
    	}
	}
}