package com.jbricx.swing.ui.tabs;

import java.awt.Font;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.RTextScrollPane;



import com.jbricx.model.PersistentDocument;
import com.jbricx.pjo.ActionControlClass;

import com.jbricx.swing.ui.JBricxManager;



public class JBricxEditorTabFolder extends JTabbedPane {
	private int newFileCount = 0;
	private ArrayList<String> openFileList;
	private JBricxManager manager;
	
	public JBricxEditorTabFolder(JBricxManager  manager){
		this.manager = manager;
		openFileList = new ArrayList<String>();
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
	 * Closes file. If the file is dirty, asks if it should be saved first. 
	 * If true, user has chosen to close the file, or the file was not dirty.
	 * If false, user wants to cancel the close.
	 * Have to do some black magic to get the Tab item out of the scrollpane.
	 * 
	 * @param index
	 *            of the file to close.
	 */
	public boolean closeFile(int n) {
		JBricxTabItem tabItem = (JBricxTabItem) ((JScrollPane) getComponentAt(n))
				.getViewport().getView();
		String fileName = tabItem.getPersistantDocument().getFileName();
		//User needs to be prompted to save file before closing
		if (tabItem.getPersistantDocument().isDirty() || fileName != null
				&& fileName.endsWith(".bak.nxc")) {

			Object[] options = { "Save", "Don't save", "Cancel" };
			int overwrite = JOptionPane
					.showOptionDialog(
							this,
							"Changes to \""
									+ tabItem.getDocumentName()
									+ "\" have not been saved. Do you wish to save your changes?",
							"Unsaved Changes", JOptionPane.YES_NO_CANCEL_OPTION,
							JOptionPane.QUESTION_MESSAGE, null, options,
							options[0]);
			//User wishes to save the file before closing.
			if(overwrite == JOptionPane.YES_OPTION){
				//TODO: Implement saving here...
				boolean saveSuccess;
				
				PersistentDocument currDoc = getSelection().getPersistantDocument();
				if (currDoc.getFileName() != null
				        && currDoc.getFileName().endsWith(".bak.nxc")) {
				      
				    	String fname = currDoc.getFileName();
				      saveSuccess = ActionControlClass.saveFile(tabItem,
				          true, manager);
				      if (!currDoc.getFileName().endsWith(".bak.nxc")) {
				        // File was successfully saved, cleanup the temporary file
				        File f = new File(fname);
				        f.delete();
				      }
				    } else {
				      saveSuccess = ActionControlClass.saveFile(tabItem,
				          false, manager);
				    }
				
				if(saveSuccess){
					openFileList.remove(tabItem.getFilename());
					return true;
				}else{
					return false;
				}
				
				
			//User said they do not wish to save (close without saving)	
			}else if(overwrite == JOptionPane.NO_OPTION){
				openFileList.remove(tabItem.getFilename());
				return true;
			//user chose to cancel or hit x. Do nothing
			}else{
				return false;
			}
		//File was already saved, do not need to prompt
		}else{
			
			openFileList.remove(tabItem.getFilename());
			return true;	
		}
	}

	/**
	 * Opens a new file with a default name, and blank text. Changes focus to that file.
	 * Not sure atm why this returns true instead of being void but I'm leaving it for now.
	 * @return true when done
	 */
	public boolean openNewFile(){
		newFileCount++;
		String fileName = "New File " + newFileCount;
		
		JBricxTabItem newTabItem = new JBricxTabItem(this,null,fileName);
		newTabItem.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);
		newTabItem.setCodeFoldingEnabled(true);
	    newTabItem.setAntiAliasingEnabled(true);
	    newTabItem.setFont(new Font(null, Font.BOLD,50));
	      
	     
		RTextScrollPane scroller = new RTextScrollPane(newTabItem);
		scroller.setFoldIndicatorEnabled(true);		
		this.add(fileName,scroller);
		//this.setTabComponentAt(this.getTabCount()-1,new ButtonTabComponent(this));
		//this.setSelectedComponent(scroller);

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
		return getSelection().getName();
		
	}


	/**
	 * Refreshes tab items (file names), eventually text and color etc.
	 */
	public void refreshTabItems() {
		int paneCount = this.getTabCount();
		for (int i = 0; i < paneCount; i++) {
			JBricxTabItem tab = (JBricxTabItem) (((JScrollPane) getComponentAt(i))
					.getViewport().getView());
			if (tab.getDocumentName() != null) {
				this.setTitleAt(i, tab.getDocumentName());
				this.setTabComponentAt(i, new ButtonTabComponent(this));
			}
		}
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
