package com.jbricx.swing.ui.tabs;

import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.KeyStroke;
import javax.swing.text.BadLocationException;
import javax.swing.text.Utilities;

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

	public JBricxTabItem(JBricxEditorTabFolder parent, int newFileNumber){
		
		super(newFileNumber);
		isNewFile = true;
		this.parent = parent;
		setShortcuts();

	}
	
	public JBricxTabItem(JBricxEditorTabFolder parent, String fileName) {
		super(fileName);
		isNewFile = false;
		this.parent = parent;
		setShortcuts();
	}
	
	private void setShortcuts(){
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
