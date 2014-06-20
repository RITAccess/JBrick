package com.jbricx.swing.main;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
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
		mainWindow.setTitle("JBrick");
		mainWindow.run();
	}

}
