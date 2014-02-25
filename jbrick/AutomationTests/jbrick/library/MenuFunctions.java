package jbrick.library;

import java.awt.Component;

import jbrick.library.*;

import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import com.jbricx.swing.ui.MainWindow;

/**
 * Framework for interacting with the top level menu
 */


public class MenuFunctions {
	
	/**
	 * Function which selects one of the menu options, returns nothing.
	 * 
	 * @param jmb - MainWindow instance
	 * @param optionPath - path, separated by dashes (-), to the option
	 * 					e.g. optionPath = "File-Save As"
	 */
	public static Component getMenuOption(JMenuBar jmb, String optionPath) {
		return (Component) TestUtils.getChildNamed(jmb, "File");
	}
}
