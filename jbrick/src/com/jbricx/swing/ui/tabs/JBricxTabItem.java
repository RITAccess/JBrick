package com.jbricx.swing.ui.tabs;

import java.awt.event.KeyEvent;

import javax.swing.InputMap;
import javax.swing.KeyStroke;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

import org.fife.ui.rsyntaxtextarea.TextEditorPane;

import com.jbricx.swing.ui.MainWindow;

/**
 * An individual tab item to be used in the editor pane.
 * 
 * @author Daniel
 * 
 */
@SuppressWarnings("serial")
public class JBricxTabItem extends TextEditorPane {
	private boolean isNewFile;
	private int line = -1;
	JBricxEditorTabFolder parent;
	
	/** 
	 * Constructor to open a new file.
	 * 
	 * @param parent
	 * @param newFileNumber
	 */
	public JBricxTabItem(JBricxEditorTabFolder parent, int newFileNumber){
		super(newFileNumber);
		isNewFile = true;
		this.parent = parent;
		setTabItem();
	}
	
	/**
	 * Constructor to open an already existing file.
	 * @param parent
	 * @param fileName
	 */
	public JBricxTabItem(JBricxEditorTabFolder parent, String fileName) {
		super(fileName);
		isNewFile = false;
		this.parent = parent;
		setTabItem();
	}
	
	// Tab settings that are the same whether it is a new tab or old one. 
	
	private void setTabItem() {
		
		this.setLineWrap(true);
		this.setWrapStyleWord(true);
		InputMap im = this.getInputMap();
		im.put(KeyStroke.getKeyStroke((char) KeyEvent.VK_ENTER), "Enter");
		
		CaretListener caretListener = new CaretListener() {

			@Override
			public void caretUpdate(CaretEvent arg0) {
				JBricxTabItem tab = JBricxTabItem.this;
				if (tab != null){
					int newLine = tab.getCaretLineNumber() + 1;
					if (tab.line != newLine) {
						tab.line = newLine;
						MainWindow manager = (MainWindow) tab.parent.getManager();
						if (manager.accessPane != null) {
							manager.accessPane.setText("Line: " + newLine);
							manager.accessPane.readLabel("Line: " + newLine,tab);
						}
					}
				}
			}
			
		};
		this.addCaretListener(caretListener);
	}

	/**
	 * 
	 * @return true if the file in this tab item is new and has not been saved previously.
	 */
	public boolean isNewFile(){
		return isNewFile;
	}
	
	/**
	 * Changes the file status to whatever is desired
	 * @param fileStatus True if the file is new. False if the file has already existed or was just saved.
	 */
	public void setNewFile(boolean fileStatus){
		isNewFile = fileStatus;
	}
	
	/**
	 * Gets the absolute path(+filename) (Should return null if there is no actual file assoc with this.
	 * 
	 * @return Absolute path of the file
	 */
	public String getFileAbsolutePath() {
			return this.getFileFullPath();
	}

	/**
	 * Updates the text with preference changes, or applies them for the first
	 * time.
	 */
	public void updateText() {
		this.restoreDefaultSyntaxScheme();
	}

	/**
	 * Clear the error annotations on the current file, if any.
	 */
	public void clearAnnotations() {
		// TODO Auto-generated method stub

	}

	/**
	 * returns true if the document is empty
	 * 
	 * @return
	 */
	public boolean isEmpty() {
		return this.getDocument().getLength()==0;
	}
}
