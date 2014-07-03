package com.jbricx.piano;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

import com.jbricx.tools.AudioPlayer;

/**
 * Class for creating the buttons in the piano frame and giving them functionality
 * 
 * @author Melissa Y
 *
 */
public class ButtonActions {
	
	GridBagConstraints gbCon = new GridBagConstraints();
	
	private JButton clear;
	private JButton copy;
	private JButton play;
	private JButton save;
	private JButton help;
	
	private JPanel buttonPanel;
	private Border buttonBorder;
	
	/**
	 * Constructor of the buttons used
	 * @param textViewPanel - where all the note information is ready to be grabbed 
	 * 
	 */
	public ButtonActions(final NotesView textViewPanel) {
		
		this.buttonPanel = new JPanel(new GridBagLayout());
		this.buttonBorder = new EtchedBorder();
		
		// THE PLAY BUTTON
		this.play = new JButton("Play");
		this.play.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				Note[] notes = ButtonActions.getNotesFromText(textViewPanel.getText());
				int[] lengths = new int[notes.length];
				String[] noteStrs = new String[notes.length];
				int count = 0;
				for (Note n: notes){
					lengths[count] = n.length;
					noteStrs[count++] = n.note;
				}
				AudioPlayer.play(lengths, noteStrs);
			}
			
		});
		this.copy = new JButton("Copy");
		this.save = new JButton("Save");
		this.help = new JButton("Help");
		
		// THE CLEAR BUTTON
		this.clear = new JButton("Clear");
		this.clear.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				textViewPanel.clearText();
				
			}
			
		});
	}
	
	/**
	 * Set up for the buttons orientations
	 * 
	 * Add a button to the panel 
	 * 
	 * Exception: The rest button
	 * 
	 * @param ctrlButton
	 * @param xplace
	 * @param yplace
	 */
	private void bPanel(JButton ctrlButton,int xPlace,int yPlace) {
		
		gbCon.anchor = GridBagConstraints.CENTER;
		gbCon.insets = new Insets(10,10,10,10);
		gbCon.ipadx = 50;
		gbCon.ipady = 20;
		gbCon.gridx = xPlace;
		gbCon.gridy = yPlace;
		buttonPanel.add(ctrlButton,gbCon);

	}
	
	/**
	 * Grabs the notes from the text and outputs a Note array
	 * (the Note array contains the String of the Note as well as it's duration in ms)
	 * @param text
	 * @return
	 */
	public static Note[] getNotesFromText(String[] text){
		Note[] notes = new Note[text.length];
		int count = 0;
		for (String txt: text){
			String[] len = txt.split(" ")[1].split("/");
			int ms = (2000 / Integer.parseInt(len[1])) * Integer.parseInt(len[0]);
			notes[count++] = new Note(txt.split(" ")[0], ms);
		}
		return notes;
	}

	/**
	 * Give buttons functionality 
	 * 
	 * Set button information 
	 * 
	 */
	public void buttonAction() {
		
		play.setToolTipText("Plays back notes and rests added");
		copy.setToolTipText("Copies NXC code that will play");
		save.setToolTipText("Saves NXC code that will play");
		help.setToolTipText("Opens help browser to piano composer");
		clear.setToolTipText("Clears all keys");
	}
	
	/**
	 * Add all of the buttons to the panel (except the rest)
	 * 
	 * @return the editing button panel
	 */
	public JPanel setUpBPanel() {
		
		bPanel(play,0,0);
		bPanel(copy,0,1);
		bPanel(save,1,0);
		bPanel(help,1,1);
		bPanel(clear,0,2);
		
		buttonPanel.setBorder(BorderFactory.createTitledBorder(buttonBorder, "Edit Controls"));
		return buttonPanel;
		
	}
}

class Note{
	String note;
	int length;
	Note(String note, int length){
		this.note = note;
		this.length = length;
	}
}