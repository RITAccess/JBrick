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

	/**
	 * Constructor
	 */
	public DownloadAction(final JBricxManager manager) {
		super("", new ImageIcon(DownloadAction.class.getResource("/images/download.png")), manager);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		NXTAccess.downloadToBrick(
				this.getManager().getTabFolder().getSelection().getFileFullPath()
		);
	}
}
