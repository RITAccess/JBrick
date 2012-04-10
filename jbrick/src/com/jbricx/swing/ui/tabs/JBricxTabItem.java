package com.jbricx.swing.ui.tabs;

import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

//import javax.swing.JEditorPane;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JTextPane;
import javax.swing.KeyStroke;

import javax.swing.text.BadLocationException;

import javax.swing.text.Utilities;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;


import com.jbricx.model.PersistentDocument;

/**
 * An individual tab item to be used in the editor pane.
 * 
 * @author Daniel
 * 
 */
public class JBricxTabItem extends RSyntaxTextArea {
	private File file;
	private JBricxEditorTabFolder parent;
	private PersistentDocument document;
	private String name;

	public JBricxTabItem(JBricxEditorTabFolder parent, File file,
			String fileName) {
		super();
		setFile(file);
		setUpDocument(file);
		this.parent = parent;
		this.name = fileName;
		
		//Logic for JAWS CTRL-L line read
		Action SetLine = new AbstractAction() {
		    public void actionPerformed(ActionEvent e) {
		       int dot = JBricxTabItem.this.getCaret().getDot();
		       int rn = (dot==0) ? 1 : 0;
		        try {
		            int offs=dot;
		            while( offs>0) {
		                offs=Utilities.getRowStart(JBricxTabItem.this, offs)-1;
		                rn++;
		            }
		        } catch (BadLocationException e1) {
		            e1.printStackTrace();
		        }
		        JBricxTabItem.this.getAccessibleContext().setAccessibleName(" ");
		        JBricxTabItem.this.getAccessibleContext().setAccessibleName("Line Number " + Integer.toString(rn));
		        JBricxTabItem.this.getCaret().moveDot(dot-1);
		        JBricxTabItem.this.getCaret().moveDot(dot);
		        JBricxTabItem.this.repaint();
		    }
		};
		
		this.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_L, InputEvent.CTRL_MASK), "SetLine");
		this.getActionMap().put("SetLine",SetLine);
        
		
		
       
    

	}

	public boolean getScrollableTracksViewportWidth() {
		return getUI().getPreferredSize(this).width <= getParent().getSize().width;
	}

	private void setUpDocument(File file) {
		/*if (file != null) {
			document = new PersistentDocument(file.getAbsolutePath());
			try {
				document.open();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.setDocument(document);

		} else {
			document = new PersistentDocument();
			this.setDocument(document);
		}*/
	}

	/**
	 * Sets the file path for later use
	 * 
	 * @param file2
	 *            absolute path of the file
	 */
	public void setFile(File file2) {
		this.file = file2;

	}

	/**
	 * Gets the absolute path(+filename)
	 * 
	 * @return Absolute path of the file
	 */
	public String getFilename() {
		if (file != null) {
			return file.getAbsolutePath();
		}
		return null;

	}

	/**
	 * Updates the text with preference changes, or applies them for the first
	 * time.
	 */
	public void updateText() {
		// TODO: read preferences and change items
	}

	/**
	 * Return the document of the tabbed pane (where all the data is)
	 */
	public PersistentDocument getPersistantDocument() {
		return document;
	}

	/**
	 * Clear the error annotations on the current file, if any.
	 */
	public void clearAnnotations() {
		// TODO Auto-generated method stub

	}

	/**
	 * returns if the document is empty
	 * 
	 * @return
	 */
	public boolean isEmpty() {
		return document.isEmpty();
	}

	/**
	 * returns the files name with extension (ex New File 03 or test.txt)
	 * 
	 * @return
	 */
	public String getDocumentName() {
		return name;

	}

	/**
	 * Sets the file name (not path).
	 * 
	 * @param filename
	 *            name of file
	 */
	public void setFileName(String filename) {
		name = filename;

	}

}
