package com.jbricx.swing.ui.tabs;

import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.InputMap;
import javax.swing.KeyStroke;
import javax.swing.text.BadLocationException;

import org.fife.ui.rsyntaxtextarea.TextEditorPane;

/**
 * An individual tab item to be used in the editor pane.
 * 
 * @author Daniel
 * 
 */
@SuppressWarnings("serial")
public class JBricxTabItem extends TextEditorPane {
	private boolean isNewFile;
	private JBricxEditorTabFolder parent;

	
	/** 
	 * Constructor to open a new file.
	 * 
	 * @param parent
	 * @param newFileNumber
	 */
	public JBricxTabItem(JBricxEditorTabFolder parent, int newFileNumber){
		
		super(newFileNumber);
		isNewFile = true;
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
		setTabItem();
	}
	
	// Tab settings that are the same whether it is a new tab or old one. 
	
	private void setTabItem() {
		
		this.setLineWrap(true);
		this.setWrapStyleWord(true);
		InputMap im = this.getInputMap();
		im.put(KeyStroke.getKeyStroke((char) KeyEvent.VK_ENTER), "Enter");
//		this.getActionMap().put("Enter", setShortcuts());
//		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP,0), "Up");
//		this.getActionMap().put("Up",setShortcuts());
//		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN,0), "Down");
//		this.getActionMap().put("Down",setShortcuts());
	}
	private Action setShortcuts(){
		
		Action SetLine = new AbstractAction() {
		    public void actionPerformed(ActionEvent e) {
		    	
		       int dot = JBricxTabItem.this.getCaretPosition();
		       int rn = 0;
		        try {
		            int offs=dot;
		            rn = JBricxTabItem.this.getLineOfOffset(offs);
		            rn += 1;
		            
		        } catch (BadLocationException e1) {
		            e1.printStackTrace();
		        }
		        JBricxTabItem.this.getAccessibleContext().setAccessibleName("Line Number " + rn);
		        System.out.println(rn);
		        JBricxTabItem.this.getCaret().moveDot(dot-1);
		        JBricxTabItem.this.getCaret().moveDot(dot);
		        JBricxTabItem.this.repaint();
		        
		       
		    }
		};
		return SetLine;
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
