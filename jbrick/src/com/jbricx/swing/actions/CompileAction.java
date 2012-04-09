package com.jbricx.swing.actions;

import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import com.jbricx.communications.ExitStatus;
import com.jbricx.swing.ui.JBricxManager;

/**
 * Compiles the current file.
 */
@SuppressWarnings("serial")
public class CompileAction extends AbstractCompilerAction {

	/**
	 * Constructor
	 */
	public CompileAction(final JBricxManager manager) {
		super("", new ImageIcon(
				"./resources/images/compile.png"), manager);
	}

	@Override
	public void onSuccess() {
		JOptionPane.showMessageDialog(null, "Compile was a success!");
	}

	@Override
	public void onFailure() {
		JOptionPane.showMessageDialog(null, "Errors found during compilation.");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Either add logic for compilation or call super to compile

	}

	@Override
	protected ExitStatus doRun(String filename) {
		// TODO Auto-generated method stub
		return null;
	}
}
