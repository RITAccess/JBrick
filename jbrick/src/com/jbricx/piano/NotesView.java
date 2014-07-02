package com.jbricx.piano;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

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
	
	public void appendText(String text){
		notesView.append(text);
		notesView.repaint();
		notesPanel.repaint();
	}
	
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