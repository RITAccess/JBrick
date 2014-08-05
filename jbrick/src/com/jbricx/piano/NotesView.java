package com.jbricx.piano;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

/**
 * NotesView - A panel that contains many of the panels required for the piano window
 * 
 */
public class NotesView {
	
	private JPanel notesPanel;
	private JTextArea notesView;
	private JLabel typedNotes;
	private JScrollPane noteScroll;
	
	
	/**
	 * Constructor for the note view text field
	 */
	public NotesView() {
		
		notesPanel = new JPanel(new BorderLayout());
		notesView = new JTextArea(10,5);
		noteScroll = new JScrollPane(notesView);
		noteScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		notesView.setEditable(false);
		notesView.setLineWrap(true);
		notesView.setWrapStyleWord(true);
		typedNotes = new JLabel("Current notes: ");
		
	}
	
	/**
	 * appendText - adds the notes view with the text provided
	 * @param text - text to add to the notes view
	 */
	public void appendText(String text){
		notesView.append(text);
		notesView.repaint();
		notesPanel.repaint();
	}
	
	/**
	 * getStringNotes - get all the notes from the notes view as a string list
	 * @return notes view content (list split by newlines)
	 */
	public String[] getStringNotes(){
		return notesView.getText().split("\n");
	}

	/**
	 * getText - returns all the notes from the notes view as a single string
	 * @return notes view content
	 */
	public String getText(){
		return notesView.getText();
	}
	
	/**
	 * setText - sets the text for the notes view
	 * @param notes
	 */
	public void setText(String notes){
		notesView.setText(notes);
	}
	
	/**
	 * clearText - clears text for the notes view
	 */
	public void clearText(){
		notesView.setText("");
		notesView.repaint();
	}
		
	/**
	 * Add title and the note view text field to a panel
	 * 
	 * @return note view panel
	 */
	public JPanel setUpNoteView() {
		
		notesPanel.add(noteScroll);
		notesPanel.add(typedNotes, BorderLayout.PAGE_START);
		return notesPanel;
	}
}