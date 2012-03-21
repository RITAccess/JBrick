package com.jbricx.swing.actions;


import com.jbricx.swing.ui.JBricxManager;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 * This class shows an About box
 */
public class AboutAction extends JBricxAbstractAction {

	/**
	 * AboutAction constructor
	 */
	public AboutAction(final JBricxManager manager) {
		//Name set to "" so that "About" does not show in toolbar icon.
		super("", new ImageIcon("resources/images/help-browser.png"), manager);
	}


	/**
	 * Shows an about box
	 */
	public void actionPerformed(ActionEvent arg0) {
	JOptionPane.showMessageDialog(getManager().getShell(),"JBrick Editor--a NXC source code editor");
  }

}
