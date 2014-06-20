package com.jbricx.swing.actions;

import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;

import com.jbricx.swing.communications.NXTManager;
import com.jbricx.swing.ui.JBricxManager;
import com.jbricx.swing.ui.findbrick.FindBrickUIWindow;

/**
 * This class shows an About box
 */
@SuppressWarnings("serial") 
public class FindBrickAction extends JBricxAbstractAction {

	/**
	 * AboutAction constructor
	 */
	public FindBrickAction(JBricxManager manager) {
		super("", new ImageIcon(FindBrickAction.class.getResource("/images/find_brick.png")), manager);
		setEnabled(NXTManager.isFantomDriverLoaded());
		// try {
		// NXTManager.getInstance().getFantom();
		// } catch (FantomDriverNotFoundException e) {
		// // TODO: notify the user that the fantom driver is missing
		// setEnabled(false);
		// }
	}

	/**
	 * Shows an about box
	 */
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		new FindBrickUIWindow(getManager());
	}
}
