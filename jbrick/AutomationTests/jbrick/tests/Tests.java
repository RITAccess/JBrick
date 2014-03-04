package jbrick.tests;

import jbrick.library.*;
import com.jbricx.swing.ui.MainWindow;
import org.junit.Assert.*;

public class Tests {

	MainWindow mainWindow = new MainWindow();
	

	public Tests(){
		mainWindow.setTitle("Automation Test - JBrick");
		mainWindow.run();
		//System.out.println("MAIN WINDOW: " + mainWindow);  // instantiated?
	}
	
	public boolean newFile(){
		TestUtils.getButton(mainWindow, "New");
		return true;
	}
	
	public boolean saveFile(){
		TestUtils.getButton(mainWindow, "Save");
		return true;
	}
	
	public boolean openFile(){
		TestUtils.getButton(mainWindow, "Open");
		return true;
	}
	
	public boolean compile(){
		TestUtils.getButton(TestUtils.getButton(mainWindow, "Compile"), "Compile");
		return true;
	}
	
	public boolean print(){
		TestUtils.getButton(mainWindow, "Print");
		return true;
	}
	
	public static void main(String[] args) {
		
		Tests test = new Tests();

		//assertEquals(true, test.saveFile());		
		//assertEquals(true, test.compile());	
		//assertEquals(true, test.print());
	}
}
