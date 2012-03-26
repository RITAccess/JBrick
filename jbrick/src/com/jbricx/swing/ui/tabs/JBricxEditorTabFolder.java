package com.jbricx.swing.ui.tabs;

import javax.swing.JEditorPane;
import javax.swing.JTabbedPane;


public class JBricxEditorTabFolder extends JTabbedPane {

	boolean open(final String filename){
		return false;
		
	}

	void closeFile(String filename){
		
	}

	boolean openNewFile(){
		return false;
		
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
