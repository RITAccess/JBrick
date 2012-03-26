package com.jbricx.swing.actions;

import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;

import com.jbricx.communications.ExitStatus;
import com.jbricx.communications.NXTManager;
import com.jbricx.swing.ui.JBricxManager;
import com.jbricx.ui.JBrickManager;

/**
 * Compiles the current file.
 */
@SuppressWarnings("serial")
public class CompileAction extends AbstractCompilerAction {

	/**
	 * Constructor
	 */
	public CompileAction(final JBricxManager manager) {
		super("&Compile@Ctrl+Shift+B", new ImageIcon(
				"./resources/images/compile.png"), manager);
	}

	// TODO Add in when AbstractCompilerAction is done
//	@Override
//	public ExitStatus doRun(final String filename) {
//		return NXTManager.getInstance().compile(filename);
//	}

	@Override
	public void onSuccess() {
		MessageDialog.openInformation(getManager().getShell(), "Compile",
				"Compile was a success!");
	}

	@Override
	public void onFailure() {
		MessageDialog.openError(getManager().getShell(), "Compile",
				"Errors found during compilation.");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}
}
