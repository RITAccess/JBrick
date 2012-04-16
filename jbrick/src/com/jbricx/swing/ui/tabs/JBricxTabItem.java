package com.jbricx.swing.ui.tabs;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.File;


import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.KeyStroke;

import javax.swing.text.BadLocationException;

import javax.swing.text.Utilities;

import org.fife.ui.rsyntaxtextarea.Style;
import org.fife.ui.rsyntaxtextarea.TextEditorPane;

import com.jbricx.swing.ui.preferences.PreferenceStore;

/**
 * An individual tab item to be used in the editor pane.
 * 
 * @author Daniel
 * 
 */
public class JBricxTabItem extends TextEditorPane {
	private boolean isNewFile;

	public JBricxTabItem(JBricxEditorTabFolder parent, int newFileNumber){
		super(newFileNumber);
		isNewFile = true;
		setShortcuts();

	}
	
	public JBricxTabItem(JBricxEditorTabFolder parent, String fileName) {
		super(fileName);
		isNewFile = false;
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
		
		Color comment			= new Color(PreferenceStore.getPrefs().getInt(PreferenceStore.ColorFor.COMMENT.toString(), PreferenceStore.COMMENT_DEFAULT));
		Color docComment		= new Color(PreferenceStore.getPrefs().getInt(PreferenceStore.ColorFor.COMMENT.toString(), PreferenceStore.COMMENT_DEFAULT));
		Color keyword			= new Color(PreferenceStore.getPrefs().getInt(PreferenceStore.ColorFor.KEYWORD.toString(), PreferenceStore.KEYWORD_DEFAULT));
		Color function			= new Color(0,0,0);
		Color preprocessor		= new Color(128,64,64);
		Color regex				= new Color(PreferenceStore.getPrefs().getInt(PreferenceStore.ColorFor.OPERATOR.toString(), PreferenceStore.OPERATOR_DEFAULT));;
		Color variable			= new Color(255,153,0);
		Color literalNumber		= new Color(100,0,200);
		Color literalString		= new Color(PreferenceStore.getPrefs().getInt(PreferenceStore.ColorFor.STRING.toString(), PreferenceStore.STRING_DEFAULT));
		Color error			= new Color(202,25,25);
		
		/*
		styles[COMMENT_EOL]				= new Style(comment, null, commentFont);
		styles[COMMENT_MULTILINE]			= new Style(comment, null, commentFont);
		styles[COMMENT_DOCUMENTATION]		= new Style(docComment, null, commentFont);
		styles[COMMENT_KEYWORD]			= new Style(new Color(255,152,0), null, commentFont);
		styles[COMMENT_MARKUP]			= new Style(Color.gray, null, commentFont);
		styles[RESERVED_WORD]				= new Style(keyword, null, keywordFont);
		styles[RESERVED_WORD_2]			= new Style(keyword, null, keywordFont);
		styles[FUNCTION]					= new Style(function);
		styles[LITERAL_BOOLEAN]			= new Style(literalNumber);
		styles[LITERAL_NUMBER_DECIMAL_INT]	= new Style(literalNumber);
		styles[LITERAL_NUMBER_FLOAT]		= new Style(literalNumber);
		styles[LITERAL_NUMBER_HEXADECIMAL]	= new Style(literalNumber);
		styles[LITERAL_STRING_DOUBLE_QUOTE]	= new Style(literalString);
		styles[LITERAL_CHAR]				= new Style(literalString);
		styles[LITERAL_BACKQUOTE]			= new Style(literalString);
		styles[DATA_TYPE]				= new Style(new Color(0,128,128));
		styles[VARIABLE]					= new Style(variable);
		styles[REGEX]						= new Style(regex);
		styles[ANNOTATION]				= new Style(Color.gray);
		styles[IDENTIFIER]				= new Style(null);
		styles[WHITESPACE]				= new Style(Color.gray);
		styles[SEPARATOR]				= new Style(Color.RED);
		styles[OPERATOR]					= new Style(preprocessor);
		styles[PREPROCESSOR]				= new Style(Color.gray);
		styles[MARKUP_TAG_DELIMITER]		= new Style(Color.RED);
		styles[MARKUP_TAG_NAME]			= new Style(Color.BLUE);
		styles[MARKUP_TAG_ATTRIBUTE]		= new Style(new Color(63,127,127));
		styles[MARKUP_TAG_ATTRIBUTE_VALUE]= new Style(literalString);
		styles[MARKUP_PROCESSING_INSTRUCTION] = new Style(preprocessor);
		styles[MARKUP_CDATA]				= new Style(variable);
		styles[ERROR_IDENTIFIER]			= new Style(error);
		styles[ERROR_NUMBER_FORMAT]		= new Style(error);
		styles[ERROR_STRING_DOUBLE]		= new Style(error);
		styles[ERROR_CHAR]				= new Style(error);

		
		
		this.setSyntaxScheme(getSyntaxScheme());
		*/
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
