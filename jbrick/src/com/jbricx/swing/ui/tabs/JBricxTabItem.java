package com.jbricx.swing.ui.tabs;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.JEditorPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

/**
 * An individual tab item to be used in the editor pane.
 * @author Daniel
 *
 */
public class JBricxTabItem extends JEditorPane {
	File file;
	JBricxEditorTabFolder parent;
	
	public JBricxTabItem(JBricxEditorTabFolder parent,File file){
		this.file = file;
		this.parent = parent;
		if (file != null){
			populateText();
		}
		update();
	}

	
	/**
	 * Gets the absolute path(+filename)
	 * @return Absolute path of the file
	 */
	public String getFilename(){
		return file.getAbsolutePath();
	}
	
	private void populateText(){
		if (file.exists()){
			Document doc = (Document) this.getDocument();
			String strLine;
			FileInputStream in;
			try {
				in = new FileInputStream(file);
				BufferedReader br = new BufferedReader(new InputStreamReader(in));
				try {
					while ((strLine = br.readLine()) != null) {
					   try {
						doc.insertString(doc.getLength(), strLine + "\n", null);
					} catch (BadLocationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        
	        
		}
	}
	
	/**
	 * Updates the text with preference changes, or applies them for the first time.
	 */
	public void update(){
		//TODO: read preferences and change items
	}
}
