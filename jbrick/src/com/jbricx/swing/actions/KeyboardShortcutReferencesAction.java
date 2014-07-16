package com.jbricx.swing.actions;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.swing.ImageIcon;

import com.jbricx.swing.ui.JBricxManager;
import com.jbricx.swing.ui.browser.Browser;

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
    	Browser.openBrowser(getManager().getShell(), "Shortcuts");
    }
}