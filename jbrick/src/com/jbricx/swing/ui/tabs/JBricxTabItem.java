package com.jbricx.swing.ui.tabs;


import java.io.File;
import java.io.IOException;


//import javax.swing.JEditorPane;
import javax.swing.JEditorPane;

import com.jbricx.model.PersistentDocument;


/**
 * An individual tab item to be used in the editor pane.
 * @author Daniel
 * do you still want to
 */
public class JBricxTabItem extends JEditorPane {
	private File file;
	private JBricxEditorTabFolder parent;
	private PersistentDocument document;
	private String name;
	
	public JBricxTabItem(JBricxEditorTabFolder parent,File file,String fileName){
		setFile(file);
	    setUpDocument(file);
		this.parent = parent;
		this.name = fileName;
		update();
	}

	private void setUpDocument(File file) {
		if (file != null) {
			document = new PersistentDocument(file.getAbsolutePath());
			try {
				document.open();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.setDocument(document);

		}else{
			document = new PersistentDocument();
			this.setDocument(document);
		}
	}

	/**
	 * Sets the file path for later use
	 * @param file2 absolute path of the file
	 */
	public void setFile(File file2) {
		this.file = file2;
		
	}

	/**
	 * Overwridden method to help ensure scrolling works properly #swingproblems.
	 */
	public boolean getScrollableTracksViewportWidth() {
        return true;
    }
	

	/**
	 * Gets the absolute path(+filename)
	 * @return Absolute path of the file
	 */
	public String getFilename(){
		if (file != null){
			return file.getAbsolutePath();
		}
		return null;
		
	}
	
	/**
	 * Updates the text with preference changes, or applies them for the first time.
	 */
	public void update(){
		//TODO: read preferences and change items
	}
	
	
	/**
	 * Return the document of the tabbed pane (where all the data is)
	 */
	public PersistentDocument getPersistantDocument(){
		return document	;
	}

	/**
	 * Clear the error annotations on the current file, if any.
	 */
	public void clearAnnotations() {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * returns if the document is empty
	 * @return
	 */
	public boolean isEmpty(){
		return document.isEmpty();
	}
	
	/**
	 * returns the files name with extension (ex New File 03 or test.txt) 
	 * 
	 * @return
	 */
	public String getDocumentName(){
		return name;
	}

	/**
	 * Sets the file name (not path).
	 * @param filename name of file
	 */
	public void setFileName(String filename) {
		name = filename;
		
	}
	
	
}
