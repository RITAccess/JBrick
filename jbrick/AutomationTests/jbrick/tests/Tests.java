package jbrick.tests;

import javax.swing.AbstractButton;

import jbrick.library.*;

import com.jbricx.swing.ui.MainWindow;

import static org.junit.Assert.*;

public class Tests {

	MainWindow mainWindow = new MainWindow();
	

	public Tests(){
		mainWindow.setTitle("Automation Test - JBrick");
		mainWindow.run();
		//System.out.println("MAIN WINDOW: " + mainWindow);  // instantiated?
	}
	
	public boolean newFile(){
		TestUtils.getButton(mainWindow, "New").doClick();
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
		TestUtils.getButton(mainWindow, "Compile", TestUtils.getButton(mainWindow, "Compile")).doClick();
		return true;
	}
	
	public boolean print(){
		TestUtils.getButton(mainWindow, "Print");
		return true;
	}
	
	public static void main(String[] args) {
		
		Tests test = new Tests();

		test.newFile();	
		test.compile();	
		//assertEquals(true, test.print());
	}
}
