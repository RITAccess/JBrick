package com.jbricx.swing.ui;


import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.KeyStroke;

@SuppressWarnings("serial")
public class JBricxDialog extends JDialog {
	/**
	 * Create a base for all dialogs used
	 * Close all dialogs on escape
	 * @param shell - frame in reference
	 * @param title - title for dialog
	 * @param modal - if the dialog should disable parent (if true, you must set your own visibility)
	 */
	public JBricxDialog(JFrame shell,String title,Boolean modal) {
		
		super(shell,title,modal);
		this.setVisible(!modal);
		this.requestFocus();
		this.setLocation(shell.getX() + shell.getWidth()/2,shell.getY()+shell.getY()/3);
	
		KeyStroke closeKeys = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE,0,false);
		
		Action closeAction = new AbstractAction() {
			public void actionPerformed(ActionEvent evt) {
				JBricxDialog.this.dispose();
			}
		};
		
		this.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(closeKeys,"Close");
		this.getRootPane().getActionMap().put("Close", closeAction);
	}	
}

