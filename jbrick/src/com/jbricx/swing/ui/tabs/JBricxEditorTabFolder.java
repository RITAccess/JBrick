package com.jbricx.swing.ui.tabs;

import java.io.File;
import java.util.ArrayList;

import javax.swing.JEditorPane;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;

import com.jbricx.ui.tabs.JBrickTabItem;


public class JBricxEditorTabFolder extends JTabbedPane {
	private int newFileCount = 0;
	private ArrayList<String> openFileList;
	
	public JBricxEditorTabFolder(){
		openFileList = new ArrayList<String>();
		openNewFile();
		openNewFile();
		openNewFile();
		openNewFile();
	}

	public void open(final String filename){
		int tabIndex = getTabIndexByFilepath(filename);
		System.out.println("Index is: " + tabIndex);
		//Make a new file because it was not currently found in the list of open files
		if(tabIndex == -1){
			File file = new File(filename);
			if (file.exists()) {
				System.out.println("File exists, with a file name of " + file.getAbsolutePath());
				JBricxTabItem newItem = new JBricxTabItem(this, file);
				this.add(file.getName(),newItem);
				this.setTabComponentAt(this.getTabCount()-1,new ButtonTabComponent(this));
				this.setSelectedComponent(newItem);
				openFileList.add(file.getAbsolutePath());
				
			} else { //File doesn't exist, throw an error.
				JOptionPane.showMessageDialog(null,
						"The file you have specified does not exits!", "File Not Found!",
			            JOptionPane.WARNING_MESSAGE);
			}
		}else{
			this.setSelectedIndex(tabIndex);
		}
	}

	void closeFile(int n){
		JBricxTabItem w = ((JBricxTabItem)getComponentAt(n));
		openFileList.remove(w.getFilename());
	}

	/**
	 * Opens a new file with a default name, and blank text. Changes focus to that file.
	 * Not sure atm why this returns true instead of being void
	 * @return true when done
	 */
	boolean openNewFile(){
		newFileCount++;
		String fileName = "New File " + newFileCount;
		JBricxTabItem newTabItem = new JBricxTabItem(this,null);
		this.addTab(fileName,newTabItem);
		this.setTabComponentAt(this.getTabCount()-1,new ButtonTabComponent(this));
		this.setSelectedComponent(newTabItem);
		return true;
	}

	boolean checkOverwrite(){
		return false;
		
	}
	
	JBricxTabItem getSelection(){
		return null;
		
	}
/*
	SourceViewer getSourceViewer(){
		
	}

	void addCTabFolder2Listener(CTabFolder2Adapter cTabFolder2Adapter){
		
	}
*/
	void setMaximized(boolean b){
		
	}

	void setMinimized(boolean b){
		
	}

	void setSelection(int selectedIndex){
		
	}

	public JBricxTabItem getItem(int index) {
	    return (JBricxTabItem)getComponentAt(index);
	  }

	int getSelectionIndex(){
		return tabPlacement;
		
	}

	void saveFile(String filePath){
		
	}

	void insertText(String text){
		
	}

	int contains(String fileName){
		return tabPlacement;
		
	}

	void undo(){
		
	}

	void redo(){
		
	}

	void cut(){
		
	}

	void copy(){
		
	}

	void paste(){
		
	}

	void selectAll(){
		
	}

	String getCurrentFilename(){
		return null;
		
	}

	int getCurrentIndex(){
		return tabPlacement;
		
	}

	void refreshTabItems(){
		
	}
	
	public int getTabIndexByFilepath(String filePath) {
	    int index = -1;
	    int count = getComponentCount();
	    for (int i = 0; i < count-1; i++) {
	      try {
	        if (getItem(i).getFilename().equals(filePath)) {
	          index = i;
	          break;
	        }
	      } catch (NullPointerException ne) {
	        // in case a new tab is opened no filename is open so.. let the
	        // loop conitnue
	      }
	    }
	    return index;
	  }

}
