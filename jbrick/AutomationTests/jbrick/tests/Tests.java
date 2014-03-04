package jbrick.tests;

import jbrick.library.*;

import javax.swing.*;

import static org.junit.Assert.*;

import com.jbricx.swing.ui.MainWindow;

public class Tests {

	MainWindow mainWindow = new MainWindow();
	

	public Tests(){
		mainWindow.setTitle("Automation Test - JBrick");
		mainWindow.run();
		//System.out.println("MAIN WINDOW: " + mainWindow);  // instantiated?
	}
	
	public boolean newFile(){
		AbstractButton fileMenu = (AbstractButton) TestUtils.getChildNamed(mainWindow, "File");
		AbstractButton newItem = (AbstractButton) TestUtils.getChildNamed(fileMenu, "New");
		
		if (newItem != null)
			newItem.doClick();
		return true;
	}
	
	public boolean saveFile(){
		AbstractButton fileMenu = (AbstractButton) TestUtils.getChildNamed(mainWindow, "File");
		AbstractButton item = (AbstractButton) TestUtils.getChildNamed(fileMenu, "Save");
		
		if (item != null)
			item.doClick();		
		return true;
	}
	
	public boolean openFile(){
		AbstractButton fileMenu = (AbstractButton) TestUtils.getChildNamed(mainWindow, "File");
		AbstractButton openItem = (AbstractButton) TestUtils.getChildNamed(fileMenu, "Open");
		
		if (openItem != null)
			openItem.doClick();
		return true;
	}
	
	public boolean compile(){
		AbstractButton compileMenu = (AbstractButton) TestUtils.getChildNamed(mainWindow, "Compile");
		AbstractButton compile = (AbstractButton) TestUtils.getChildNamed(compileMenu, "Compile", compileMenu);
		
		if (compile != null)
			compile.doClick();
		return true;
	}
	
	public boolean print(){
		AbstractButton print = (AbstractButton) TestUtils.getChildNamed(mainWindow, "Print");
		
		if (print != null)
			print.doClick();
		return true;
	}
	
	public static void main(String[] args) {
		
		Tests test = new Tests();

		//assertEquals(true, test.saveFile());		
		//assertEquals(true, test.compile());	
		//assertEquals(true, test.print());
	}
}
