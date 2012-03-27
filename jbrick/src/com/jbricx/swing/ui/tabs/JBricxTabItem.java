package com.jbricx.swing.ui.tabs;


import java.io.File;
import java.io.IOException;


import javax.swing.JEditorPane;

import com.jbricx.model.PersistentDocument;


/**
 * An individual tab item to be used in the editor pane.
 * @author Daniel
 *
 */
public class JBricxTabItem extends JEditorPane {
	File file;
	JBricxEditorTabFolder parent;
	private PersistentDocument document;
	
	public JBricxTabItem(JBricxEditorTabFolder parent,File file){
		setFile(file);
	    setUpDocument(file);
		this.parent = parent;
		update();
	}

	private void setUpDocument(File fileName) {
		if (file != null) {
			document = new PersistentDocument(fileName.getAbsolutePath());
			try {
				document.open();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.setDocument(document);

		}else{
			document = new PersistentDocument();
		}
	}

	private void setFile(File file2) {
		this.file = file2;
		
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
	
	public JBricxEditorTabFolder getParent(){
		return parent;
	}
	
	public PersistentDocument getDocument(){
		return null;
	}
}
