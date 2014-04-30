package com.jbricx.swing.actions;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.ImageIcon;

import com.jbricx.swing.ui.JBricxManager;

@SuppressWarnings("serial")
public class KeyboardShortcutReferencesAction extends JBricxAbstractAction 
{
	String fileName = "/KeyboardShortcutsHelp.html";

	/**
	 * KeyboardShortcutReferencesAction constructor
	 */
	public KeyboardShortcutReferencesAction(final JBricxManager manager) 
	{
		//Name set to "" so that "About" does not show in toolbar icon.
		super("",new ImageIcon(AboutAction.class.getResource("/images/help-browser.png")), manager);
	}

	/**
	 * Open the html reference page for keyboard shortcuts
	 */
	public void actionPerformed(ActionEvent arg0) 
	{
		File f = new File(fileName);
       
		//first check if Desktop is supported by Platform or not
        if(!Desktop.isDesktopSupported())
        {
            System.out.println("Desktop is not supported");
            return;
        }
         
        Desktop desktop = Desktop.getDesktop();
        
        if(f.exists())
        {
            System.out.println("File found");
        	
			try 
			{
				desktop.open(f);
			} catch (IOException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
