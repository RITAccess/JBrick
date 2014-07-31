package com.jbricx.swing.ui;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.sound.sampled.SourceDataLine;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.KeyStroke;

import com.jbricx.piano.PianoWindow;
import com.jbricx.tools.AudioPlayer;

@SuppressWarnings("serial")
public class JBricxDialog extends JDialog {
	/**
	 * Create a base for all dialogs used
	 * Close all dialogs on cmd/ctrl W
	 * @param shell
	 * @param title
	 * @param modal
	 */
	public JBricxDialog(JFrame shell,String title,Boolean modal) {
		super(shell,title,modal);	
		final SourceDataLine line = AudioPlayer.openLine();
		Integer modifier = Toolkit.getDefaultToolkit().getMenuShortcutKeyMask();
		KeyStroke closeKeys = KeyStroke.getKeyStroke(KeyEvent.VK_W,modifier,false);
		Action closeAction = new AbstractAction() {
			public void actionPerformed(ActionEvent evt) {
				JBricxDialog.this.dispose();
				
				if (JBricxDialog.this instanceof PianoWindow){
					AudioPlayer.closeLine(line);
				}
			}
		};
		this.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(closeKeys,"Close");
		this.getRootPane().getActionMap().put("Close", closeAction);
		this.setVisible(true);
		this.setFocusable(true);
		this.setLocation(shell.getX(),shell.getY());
	}	
}

