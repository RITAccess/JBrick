package jbrick.tests;

import java.util.HashMap;
import javax.swing.AbstractButton;
import jbrick.library.*;
import com.jbricx.swing.ui.MainWindow;

public class Examples {

	MainWindow mainWindow = new MainWindow();
	

	public Examples(){
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
	
	public boolean allButtons(){

		// hashmap to hold all filemenu -> button combos
		HashMap<String, String[]> fileMenuHash = new HashMap<String, String[]>();
		
		// fill hashmap
		String[] fileMenu = {"New","Open","Save","Save As", "Print Preview", "Print", "Close", "Quit"};
		fileMenuHash.put("File", fileMenu);
		String[] editMenu = {"Undo","Redo","Cut","Copy","Paste","Select All", "Find and Replace", "Preferences"};
		fileMenuHash.put("Edit", editMenu);
		String[] compileMenu = {"Compile","Find Brick","Download"};
		fileMenuHash.put("Compile", compileMenu);
		String[] toolsMenu = {"GoTo"};
		fileMenuHash.put("Tools", toolsMenu);
		String[] viewMenu = {"Show/Hide File Viewer", "Maximize File Viewer", "Maximize File Editor", "Maximize Status", "Reset View"};
		fileMenuHash.put("View", viewMenu);
		String[] helpMenu = {"About", "Help Content"};
		fileMenuHash.put("Help", helpMenu);
		String[] toolBar = {"New File", "Open File", "Save", "Save As", "Undo", "Redo",
				"Cut", "Copy", "Paste", "Find and Replace", "GoTo", "Compile",
				"Find Brick", "Download", "Preferences", "Help Content"};
		fileMenuHash.put("ToolBar", toolBar);

		mainWindow.setTitle("Automation Test - JBrick");
		mainWindow.run();
		
		for (String key: fileMenuHash.keySet()){
			for (String item: fileMenuHash.get(key)){
				System.out.println(String.format("%s -> %s",key,item));
				AbstractButton button = TestUtils.getButton(mainWindow, item, TestUtils.getButton(mainWindow, key));
			
				if (button == null)
					System.out.println("NO ITEM : " + item);
				else
					button.doClick();
			}
		}
		return true;
	}
	
	public static void main(String[] args) {
		
		Examples test = new Examples();

		test.newFile();	
		test.compile();	
		//assertEquals(true, test.print());
	}
}
