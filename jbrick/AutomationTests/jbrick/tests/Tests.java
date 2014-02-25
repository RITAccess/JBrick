package jbrick.tests;

import java.awt.Component;

import jbrick.library.*;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTextField;

import com.jbricx.swing.ui.MainWindow;

public class Tests {

	public static void main(String[] args) {
		
		MainWindow mainWindow = new MainWindow();
		mainWindow.setTitle("Automation Test - JBrick");
		mainWindow.run();
		
		System.out.println(mainWindow);  // instantiated?

			
		JMenuBar jmb = mainWindow.getJMenuBar();
		System.out.println(jmb);
		Component fileMenu = MenuFunctions.getMenuOption(jmb, "File");
		System.out.println(fileMenu);
	}
}
