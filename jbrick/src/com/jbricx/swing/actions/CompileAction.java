package com.jbricx.swing.actions;

import java.awt.event.ActionEvent;

import javax.accessibility.AccessibleContext;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import com.jbricx.swing.communications.NXTManager;
import com.jbricx.swing.communications.ExitStatus;
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
		super("", new ImageIcon(CompileAction.class.getResource(
				"/images/compile.png")), manager);
	}

	@Override
	public void onSuccess() {
		JOptionPane.showMessageDialog(null, "Compile was a success!");
	}

	@Override
	public void onFailure(ExitStatus run) {
		run.getCompilerErrors().toString();
		JOptionPane newMessage = new JOptionPane();
		newMessage.getAccessibleContext().setAccessibleName("Hello there");
		newMessage.showMessageDialog(null, "Errors found during compilation.");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		this.run();
	}

	@Override
	protected ExitStatus doRun(String filename) {
		return NXTManager.getInstance().compile(filename);	
	}
}
