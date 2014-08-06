package com.jbricx.swing.actions;

import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;

import com.jbricx.swing.ui.JBricxManager;

/**
 * This action class exits the application
 */
@SuppressWarnings("serial")
public class ExitAction extends JBricxAbstractAction {
	
	/**
	 * ExitAction constructor
	 */
	public ExitAction(final JBricxManager manager) {
		super("", new ImageIcon(ExitAction.class.getResource("/icons/quit.png")), manager);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO close() is not implemented
		getManager().close();
	}
}
