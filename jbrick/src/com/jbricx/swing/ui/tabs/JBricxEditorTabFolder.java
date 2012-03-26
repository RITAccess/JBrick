package com.jbricx.swing.ui.tabs;

import javax.swing.JEditorPane;
import javax.swing.JTabbedPane;


public class JBricxEditorTabFolder extends JTabbedPane {
	private int newFileCount = 0;
	private int currentTabIndexCount = 0;
	
	
	public JBricxEditorTabFolder(){
		openNewFile();
		openNewFile();
		openNewFile();
		openNewFile();
	}

	boolean open(final String filename){

		return false;
	}

	void closeFile(String filename){
		
	}

	/**
	 * Opens a new file with a default name, and blank text. Changes focus to that file.
	 * Not sure atm why this returns true instead of being void
	 * @return true when done
	 */
	boolean openNewFile(){
		newFileCount++;
		String fileName = "New File " + newFileCount;
		JBricxTabItem newTabItem = new JBricxTabItem(this);
		this.addTab(fileName,newTabItem);
		this.setTabComponentAt(currentTabIndexCount,new ButtonTabComponent(this));
		currentTabIndexCount++;
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

	JBricxTabItem[] getItems(){
		return null;
		
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
}
