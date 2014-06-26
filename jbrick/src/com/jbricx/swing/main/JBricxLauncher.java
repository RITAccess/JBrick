package com.jbricx.swing.main;

import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.apple.eawt.Application;
import com.jbricx.swing.ui.MainWindow;
import com.sun.jna.Platform;

public class JBricxLauncher {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// Look and feel code
		try {
			if (Platform.isMac()) {
				System.setProperty("apple.laf.useScreenMenuBar", "true");
				System.setProperty(
						"com.apple.mrj.application.apple.menu.about.name",
						"JBrick");
			}
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		MainWindow mainWindow = new MainWindow();
		try {
		     Image img = Toolkit.getDefaultToolkit().getImage("images/icon-small.png");
		     mainWindow.setIconImage(img);
		     if(Platform.isMac()){
			     Application app = Application.getApplication();
			     app.setDockIconImage(img); 
		     }
		  } catch (Exception e) {
			  System.err.println("Image not loaded");
		  }
		mainWindow.setTitle("JBrick");
		mainWindow.run();
	}

}
