package com.jbricx.piano;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JLabel;

public class NotesView {
	
	private JPanel notesPanel;
	private JTextArea notesView;
	private JLabel typedNotes;
	
	/**
	 * Constructor for the note view text field
	 */
	public NotesView() {
		
		notesPanel = new JPanel(new BorderLayout());
		notesView = new JTextArea(20,30);
		notesView.setEditable(false);
		typedNotes = new JLabel("Current notes: ");
		
	}
	
	/**
	 * TODO: Have the text area display the note readouts
	 */
	public void printNotes(String noteInfo) {
		notesView.setText(noteInfo + " note");
	}
	
	/**
	 * Add title and the note view text field to a panel
	 * 
	 * @return note view panel
	 */
	public JPanel setUpNoteView() {
		
		notesPanel.add(notesView, BorderLayout.CENTER);
		notesPanel.add(typedNotes, BorderLayout.PAGE_START);
		return notesPanel;
	}
}