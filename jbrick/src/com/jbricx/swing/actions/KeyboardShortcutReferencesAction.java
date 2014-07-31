package com.jbricx.swing.actions;
import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;

import com.jbricx.swing.ui.JBricxManager;
import com.jbricx.swing.ui.browser.HelpWindow;

@SuppressWarnings("serial")
public class KeyboardShortcutReferencesAction extends JBricxAbstractAction 
{
	/**
	 * KeyboardShortcutReferencesAction constructor
	 */
	public KeyboardShortcutReferencesAction(final JBricxManager manager) 
	{
		//Name set to "" so that "About" does not show in toolbar icon.
		super("",new ImageIcon(AboutAction.class.getResource("/icons/keyboardShortcuts.png")), manager);
	}

    /**
     * Opens help browser with keyboard information
     */
    public void actionPerformed(ActionEvent e){
    	HelpWindow.openHelpWindow("Shortcuts",this.getManager().getShell());
    }
}