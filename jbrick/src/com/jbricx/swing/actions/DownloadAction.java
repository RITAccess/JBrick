package com.jbricx.swing.actions;

import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import com.jbricx.swing.ui.JBricxManager;


/**
 * Downloads the current file into the brick.
 */
@SuppressWarnings("serial")
public class DownloadAction extends AbstractCompilerAction {

	/**
	 * Constructor
	 */
	public DownloadAction(final JBricxManager manager) {
		super("", new ImageIcon("./resources/images/download.png"), manager);
	}

	//TODO Implement after AbstractCompilerAction is done
//	@Override
//	protected ExitStatus doRun(final String filename) {
//		return NXTManager.getInstance().downloadFile(filename);
//	}

	@Override
	protected void onSuccess() {
		JOptionPane.showMessageDialog(null, "Download successful!");
	}

	@Override
	protected void onFailure() {
		JOptionPane.showMessageDialog(null, "Download failed!");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Either add logic for compilation or call super to download
	}
}
