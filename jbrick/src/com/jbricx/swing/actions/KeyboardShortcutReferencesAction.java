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

@SuppressWarnings("serial")
public class KeyboardShortcutReferencesAction extends JBricxAbstractAction 
{
	String filePath = "KeyboardShortcutsHelp.html";

	/**
	 * KeyboardShortcutReferencesAction constructor
	 */
	public KeyboardShortcutReferencesAction(final JBricxManager manager) 
	{
		//Name set to "" so that "About" does not show in toolbar icon.
		super("",new ImageIcon(AboutAction.class.getResource("/icons/keyboardShortcuts.png")), manager);
	}
	
    private void copyInputstreamToFile (InputStream in, OutputStream out) {
        try {
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            in.close();
            out.close();
        } catch (IOException ex) {
           
        }
    }

	/**
	 * Open the html reference page for keyboard shortcuts
	 */
	public void actionPerformed(ActionEvent arg0) 
	{		
		InputStream is = getClass().getResourceAsStream(filePath);  
		File f;
		try {
			f = File.createTempFile("yourappname", ".html");
			f.deleteOnExit(); 
			FileOutputStream os = new FileOutputStream(f);
			copyInputstreamToFile(is,os);
			
			//first check if Desktop is supported by Platform or not
	        if(!Desktop.isDesktopSupported())
	        {
	            System.out.println("Desktop is not supported");
	            return;
	        }
			
	        Desktop desktop = Desktop.getDesktop();
			desktop.open(f);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}  

	}
}
