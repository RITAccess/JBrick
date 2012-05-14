package com.jbricx.swing.actions;

import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import com.jbricx.swing.communications.NXTManager;
import com.jbricx.swing.communications.ExitStatus;
import com.jbricx.swing.communications.NXTObserver;
import com.jbricx.swing.ui.JBricxManager;


/**
 * Downloads the current file into the brick.
 */
@SuppressWarnings("serial")
public class DownloadAction extends AbstractCompilerAction implements NXTObserver {

	/**
	 * Constructor
	 */
	public DownloadAction(final JBricxManager manager) {
		super("", new ImageIcon(DownloadAction.class.getResource("/images/download.png")), manager);
		//System.out.println(NXTManager.isConnected());
		if (!System.getProperty("os.name").contains("OS X")) {
			setEnabled(NXTManager.isFantomDriverLoaded());
		} else {
			setEnabled(true);
		}
	}


	@Override
	protected void onSuccess() {
		JOptionPane.showMessageDialog(null, "Download successful!");
	}

	@Override
	protected void onFailure(ExitStatus run) {
		JOptionPane.showMessageDialog(null, "Download failed!");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Either add logic for compilation or call super to download
		this.run();
	}

	@Override
	protected ExitStatus doRun(String filename) {
		// TODO Auto-generated method stub
		return NXTManager.getInstance().downloadFile(filename);

	}

	// Disables when not connected to brick.
	public void update(boolean isConnected) {
		setEnabled(isConnected);
	}
}
