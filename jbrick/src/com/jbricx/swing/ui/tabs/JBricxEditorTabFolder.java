package com.jbricx.swing.ui.tabs;

import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.util.ArrayList;
import java.util.prefs.Preferences;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.Gutter;
import org.fife.ui.rtextarea.RTextScrollPane;




import com.jbricx.pjo.ActionControlClass;
import com.jbricx.pjo.FileExtensionConstants;
import com.jbricx.swing.ui.JBricxManager;
import com.jbricx.swing.ui.preferences.PreferenceStore;



public class JBricxEditorTabFolder extends JTabbedPane {
	private int newFileCount = 0;
	private JBricxManager manager;
	private ArrayList<String> listOfFiles;
	//Used so the folder knows whether to add files to the recent file list.
	private boolean closingTime;
	private Preferences prefs;
	
	public JBricxEditorTabFolder(JBricxManager  manager){
		this.manager = manager;
		refreshTabItems();
		listOfFiles = new ArrayList<String>();
		prefs = PreferenceStore.getPrefs();
		if(prefs.getBoolean(PreferenceStore.BOOLRECENTFILES, PreferenceStore.BOOLRECENTFILES_DEFAULT)){
			ArrayList<String>recentFiles = getRecentFiles();
				if (recentFiles.size() > 1) {
				      for (String file : recentFiles) {
				        if (new File(file).exists()) {
				          open(file);
				        }
				      }
				    } else {
				      this.openNewFile();
				    }
		}else{
			this.openNewFile();
		}

	}

	private ArrayList<String> getRecentFiles() {
	    ArrayList<String> recentfiles = new ArrayList<String>();
	    
	      File dir = new File(prefs.get(PreferenceStore.WRKSPC, PreferenceStore.WRKSPC_DEFAULT));
	      String[] fileNames = dir.list();

	      for (int i = 0; fileNames != null && i < fileNames.length; i++) {
	        if (fileNames[i].endsWith(".bak.nxc")) {
	          recentfiles.add(dir.getAbsolutePath() + "\\" + fileNames[i]);
	        }
	      }
	      for (String s : prefs.get(PreferenceStore.RECENTFILES,"").split(
	          ";")) {
	        recentfiles.add(s);
	      }
	    return recentfiles;
		
	}

	/**
	 * Opens a new file with the given filename. Makes a new tab item and hands off the filepath to it.
	 * @param absoluteFilePath Absolute path of the filename to open.
	 */
	public void open(final String absoluteFilePath){
		int tabIndex = getTabIndexByFilepath(absoluteFilePath);
		//Make a new file because it was not currently found in the list of open files
		if(tabIndex == -1){
				JBricxTabItem newItem = new JBricxTabItem(this, absoluteFilePath);
				newItem.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);
				newItem.setCodeFoldingEnabled(true);
			    newItem.setAntiAliasingEnabled(true);
				RTextScrollPane scroller = new RTextScrollPane(newItem);	
				scroller.setFoldIndicatorEnabled(true);
				String fileName = newItem.getFileName();
				if(fileName != null){
					 
				
				this.add(fileName,scroller);
				this.setTabComponentAt(this.getTabCount()-1,new ButtonTabComponent(this));
				this.setSelectedComponent(scroller);
				
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
		String fileName = tabItem.getFileAbsolutePath();
		//User needs to be prompted to save file before closing
		if (tabItem.isDirty() || !tabItem.isNewFile()
				&& fileName.endsWith(".bak.nxc")) {

			Object[] options = { "Save", "Don't save", "Cancel" };
			int overwrite = JOptionPane
					.showOptionDialog(
							this,
							"Changes to \""
									+ tabItem.getFileName()
									+ "\" have not been saved. Do you wish to save your changes?",
							"Unsaved Changes", JOptionPane.YES_NO_CANCEL_OPTION,
							JOptionPane.QUESTION_MESSAGE, null, options,
							options[0]);
			//User wishes to save the file before closing.
			if(overwrite == JOptionPane.YES_OPTION){
				boolean saveSuccess;
				
				if (tabItem.getFileAbsolutePath() != null
				        && tabItem.getFileAbsolutePath().endsWith(".bak.nxc")) {
				      
				    	String fpathname = tabItem.getFileAbsolutePath();
				      saveSuccess = ActionControlClass.saveFile(tabItem,
				          true, manager);
				      if (!tabItem.getFileAbsolutePath().endsWith(".bak.nxc")) {
				        // File was successfully saved, cleanup the temporary file
				        File f = new File(fpathname);
				        f.delete();
				      }
				    } else {
				      saveSuccess = ActionControlClass.saveFile(tabItem,
				          false, manager);
				    }
				
				if(saveSuccess){
					if(closingTime){
						listOfFiles.add(tabItem.getFileAbsolutePath());
					}
					return true;
				}else{
					return false;
				}
				
				
			//User said they do not wish to save (close without saving)	
			}else if(overwrite == JOptionPane.NO_OPTION){
				//User chose not to save .bak.nxc file. Delete it.
				if(tabItem.getFileAbsolutePath().endsWith(".bak.nxc")){
					String fpathname = tabItem.getFileAbsolutePath();
					File f = new File(fpathname);
			        f.delete();
				}else{
					if(closingTime){
						listOfFiles.add(tabItem.getFileAbsolutePath());
					}
				}
				return true;
			//user chose to cancel or hit x. Do nothing
			}else{
				return false;
			}
		//File was already saved, do not need to prompt
		}else{
			if(closingTime){
				if(!tabItem.isNewFile()){
					listOfFiles.add(tabItem.getFileAbsolutePath());
				}
			}
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
		
		JBricxTabItem newTabItem = new JBricxTabItem(this,newFileCount);
		newTabItem.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);
		newTabItem.setCodeFoldingEnabled(true);
	    newTabItem.setAntiAliasingEnabled(true);
	    //newTabItem.setFont(new Font(null, Font.BOLD,50));
	      
	    
		RTextScrollPane scroller = new RTextScrollPane(newTabItem);
		scroller.setFoldIndicatorEnabled(true);
		
		this.add(newTabItem.getFileName(),scroller);
		this.setTabComponentAt(this.getTabCount()-1,new ButtonTabComponent(this));
		this.setSelectedComponent(scroller);

		return true;
	}

	/**
	 * Called to see if anything needs to be saved before closing. If it does, saves files.
	 * @return true if everything is cleaned up, false if they weren't done.
	 */
	public boolean checkOverwrite(){
		closingTime = true;
		 boolean proceed = true;
		 
		 int paneCount = this.getTabCount();
			
			for (int i = 0; i < paneCount; i++) {
				
		      if (closeFile(i)) {
		    	  //file save succeeded or they chose not to save the file, or file doesnt need to be saved.
		      }else{
		    	  proceed = false;
		    	  closingTime = false;
		    	  listOfFiles.clear();
		    	  break;
		      }

		  }
			return proceed;
		
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


	public String getCurrentFilename(){
		return getSelection().getFileName();
	}


	/**
	 * Refreshes tab items (file names), eventually text and color etc.
	 */
	public void refreshTabItems() {
		int paneCount = this.getTabCount();
		
		for (int i = 0; i < paneCount; i++) {
			RTextScrollPane scroller = (RTextScrollPane)getComponentAt(i);
			Gutter theGutter = scroller.getGutter();
			theGutter.setBackground(new Color(PreferenceStore.getPrefs().getInt(PreferenceStore.ColorFor.LINENUMBERBG.toString(), PreferenceStore.LINENUMBERBG_DEFAULT)));
			theGutter.setLineNumberColor(new Color(PreferenceStore.getPrefs().getInt(PreferenceStore.ColorFor.LINENUMBERFG.toString(), PreferenceStore.LINENUMBERFG_DEFAULT)));
			theGutter.setLineNumberFont(Font.decode(PreferenceStore.getPrefs().get(PreferenceStore.FONT,PreferenceStore.FONT_DEFAULT)));
			
			JBricxTabItem tab = (JBricxTabItem) (scroller.getViewport().getView());
			tab.updateText();
			if (tab.getFileName() != null) {
				this.setTitleAt(i, tab.getFileName());
				this.setTabComponentAt(i, new ButtonTabComponent(this));
			}
		}
	}
	
	/**
	 * Returns the index of the tab item based on the file path given.
	 * @param filePath absolute file path of the desired tab item
	 * @return the index of the tab. If it does not exist will return -1
	 */
	public int getTabIndexByFilepath(String filePath) {
	    int index = -1;
	    int count = getComponentCount();
	    for (int i = 0; i < count-1; i++) {
	      try {
	        if (getSelection(i).getFileAbsolutePath()	.equals(filePath)) {
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

	/**
	 * Gets the list of files currently open in order to open them next time.
	 * @return
	 */
	public StringBuilder getFileList() {
		StringBuilder recentFiles = new StringBuilder();
		for (String eachName : listOfFiles) {
				recentFiles.append(eachName);
				recentFiles.append(";");
		}
		return recentFiles;
	}

}
