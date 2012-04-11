package com.jbricx.swing.actions;

import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import com.jbricx.swing.communications.NXTManager;
import com.jbricx.swing.ui.JBricxManager;

/**
 * This class shows an About box
 */
@SuppressWarnings("serial")
public class FindBrickAction extends JBricxAbstractAction {

	/**
	 * AboutAction constructor
	 */
	public FindBrickAction(JBricxManager manager) {
		super("", new ImageIcon("./resources/images/find_brick.png"), manager);
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
	@Override
//	public void run() {
//		FindBrickUIWindow findBrick = new FindBrickUIWindow(getManager()
//				.getShell());
//		findBrick.setBlockOnOpen(true);
//		findBrick.open();
//
//	}

//	public void update(boolean isDriverAvailable) {
//		setEnabled(isDriverAvailable);
//	}
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		JOptionPane findBrick = new JOptionPane("Find Brick");

	}
}
