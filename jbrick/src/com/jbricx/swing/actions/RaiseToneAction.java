package com.jbricx.swing.actions;

import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;

import org.fife.ui.rtextarea.RTextAreaUI;
import org.fife.ui.rtextarea.RTextScrollPane;

import com.jbricx.swing.ui.JBricxManager;
import com.jbricx.swing.ui.tabs.JBricxTabItem;

@SuppressWarnings("serial")
public class RaiseToneAction extends JBricxAbstractAction {

	/**
	 * AboutAction constructor
	 */
	public RaiseToneAction(final JBricxManager manager) {
		//Name set to "" so that "AudioBreakAction" does not show in toolbar icon.
		super("", new ImageIcon(AudioBreakAction.class.getResource("/icons/toneUp.png")), manager);
	}

	/**
	 * Shows an about box
	 */
	public void actionPerformed(ActionEvent arg0) {
	    JBricxTabItem tab =(JBricxTabItem)((RTextScrollPane)getManager().getTabFolder().getSelectedComponent()).getViewport().getView();
	    ((RTextAreaUI) tab.getUI()).RaiseLowerTone(true);
	    tab.setDirty(true);
	    tab.repaint();
	}
}