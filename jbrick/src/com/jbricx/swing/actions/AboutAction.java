package com.jbricx.swing.actions;

import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import com.jbricx.swing.ui.JBricxManager;

/**
 * This class shows an About box
 */
@SuppressWarnings("serial")
public class AboutAction extends JBricxAbstractAction {

	/**
	 * AboutAction constructor
	 */
	public AboutAction(final JBricxManager manager) {
		//Name set to "" so that "About" does not show in toolbar icon.
		super("", new ImageIcon(AboutAction.class.getResource("/icons/about.png")), manager);
	}

	/**
	 * Shows an about box
	 */
	public void actionPerformed(ActionEvent arg0) {
		JOptionPane.showMessageDialog(getManager().getShell(),"JBrick Editor--a NXC source code editor");
	}

}
