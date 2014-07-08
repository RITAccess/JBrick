package com.jbricx.swing.actions;

import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;

import org.fife.ui.rtextarea.RTextAreaUI;
import org.fife.ui.rtextarea.RTextScrollPane;

import com.jbricx.swing.ui.JBricxManager;
import com.jbricx.swing.ui.tabs.JBricxTabItem;

/**
 * This class shows an About box
 */
@SuppressWarnings("serial")
public class AudioBreakAction extends JBricxAbstractAction {

	/**
	 * AboutAction constructor
	 */
	public AudioBreakAction(final JBricxManager manager) {
		//Name set to "" so that "AudioBreakAction" does not show in toolbar icon.
		super("", new ImageIcon(AudioBreakAction.class.getResource("/images/help-browser.png")), manager);
	}

	/**
	 * Shows an about box
	 */
	public void actionPerformed(ActionEvent arg0) {
	    JBricxTabItem tab =(JBricxTabItem)((RTextScrollPane)getManager().getTabFolder().getSelectedComponent()).getViewport().getView();
	    ((RTextAreaUI) tab.getUI()).toggleAudioBreak();
	    tab.setDirty(true);
	    tab.repaint();
	}
}