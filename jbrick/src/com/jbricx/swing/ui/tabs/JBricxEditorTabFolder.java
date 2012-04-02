package com.jbricx.swing.ui.tabs;

import java.awt.Dimension;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import com.jbricx.swing.documentModel.DefaultSyntaxKit;


public class JBricxEditorTabFolder extends JTabbedPane {
	private int newFileCount = 0;
	private ArrayList<String> openFileList;
	
	public JBricxEditorTabFolder(){
		openFileList = new ArrayList<String>();
		openNewFile();
		openNewFile();

		openNewFile();

	}

	/**
	 * Opens a new file with the given filename. Makes a new tab item and hands off the file to it.
	 * @param filename Absolute path of the filename to open.
	 */
	public void open(final String filename){
		int tabIndex = getTabIndexByFilepath(filename);
		//Make a new file because it was not currently found in the list of open files
		if(tabIndex == -1){
			File file = new File(filename);
			if (file.exists()) {
				JBricxTabItem newItem = new JBricxTabItem(this, file,file.getName());
				JScrollPane scroller = new JScrollPane(newItem);				
				this.add(file.getName(),scroller);
				this.setTabComponentAt(this.getTabCount()-1,new ButtonTabComponent(this));
				this.setSelectedComponent(scroller);
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

	/**
	 * Have to do some black magic to get the Tab item out of the scrollpane.
	 * @param index of the file to close.
	 */
	void closeFile(int n){
		JBricxTabItem w = (JBricxTabItem)((JScrollPane)getComponentAt(n)).getViewport().getView();
		openFileList.remove(w.getFilename());
	}

	/**
	 * Opens a new file with a default name, and blank text. Changes focus to that file.
	 * Not sure atm why this returns true instead of being void but I'm leaving it for now.
	 * @return true when done
	 */
	boolean openNewFile(){
		newFileCount++;
		String fileName = "New File " + newFileCount;
		JBricxTabItem newTabItem = new JBricxTabItem(this,null,fileName);
		JScrollPane scroller = new JScrollPane(newTabItem);
		this.add(fileName,scroller);
		this.setTabComponentAt(this.getTabCount()-1,new ButtonTabComponent(this));
		this.setSelectedComponent(scroller);

		return true;
	}

	boolean checkOverwrite(){
		return false;
		
	}
	
	/**
	 * Uses the getSelection(index) method to get the currently focused tab item
	 * @return
	 */
	public JBricxTabItem getSelection(){
		return this.getSelection(getSelectedIndex());
		
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

	public void setSelection(int selectedIndex){
		this.setSelectedIndex(selectedIndex);
	}

	/**
	 * Pulls out the selected JBricxTabItem for whatever purpose.
	 * 
	 * @param index of the requested tab item
	 * @return the tab item requested
	 */
	public JBricxTabItem getSelection(int index) {
	    return (JBricxTabItem)(((JScrollPane)getComponentAt(index)).getViewport().getView());
	  }

	public int getSelectionIndex(){
		return tabPlacement;
		
	}

	public void saveFile(String filePath){
		
	}

	public void insertText(String text){
		
	}

	int contains(String fileName){
		return tabPlacement;
		
	}

	public void undo(){
		
	}

	public void redo(){
		
	}

	public void cut(){
		
	}

	public void copy(){
		
	}

	public void paste(){
		
	}

	public void selectAll(){
		
	}

	public String getCurrentFilename(){
		return null;
		
	}

	public int getCurrentIndex(){
		return tabPlacement;
		
	}

	void refreshTabItems(){
		
	}
	
	public int getTabIndexByFilepath(String filePath) {
	    int index = -1;
	    int count = getComponentCount();
	    for (int i = 0; i < count-1; i++) {
	      try {
	        if (getSelection(i).getFilename().equals(filePath)) {
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
