package com.jbricx.piano;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JButton;

public class PianoControls {
	
	protected ButtonActions buttonPanel;
	protected NoteLengths noteRadioPanel;
	protected NotesView textViewPanel;
	protected PianoKeys keysPanel;
	protected OctaveChange transPanel;
	
	GridBagConstraints gbCon = new GridBagConstraints();

	private JPanel centerPanel;
	private JPanel controlPanel;
	private JButton rest;
	
	/**
	 * Constructor for the different panels and components 
	 */
	public PianoControls(){
		
		centerPanel = new JPanel(new GridBagLayout());
		controlPanel = new JPanel(new BorderLayout());
		textViewPanel = new NotesView();
		buttonPanel = new ButtonActions();
		noteRadioPanel = new NoteLengths();
		transPanel = new OctaveChange();
		rest = new JButton("Rest (C)");
		rest.addActionListener(setNoteButton(noteRadioPanel));
	}
	
	/**
	 * Set up for the rest button's orientation in the panel
	 */
	private GridBagConstraints restSetUp(){
		
		gbCon.fill = GridBagConstraints.VERTICAL;
		gbCon.insets = new Insets(100,50,100,50);
		gbCon.weightx = 0.25;
		gbCon.weighty = 0.5;
		gbCon.ipadx = 100;
		gbCon.ipady = 200;
		gbCon.gridx = 0;
		gbCon.gridy = 0;
		gbCon.anchor = GridBagConstraints.CENTER;
		return gbCon;
	}
	
	private ActionListener setNoteButton(NoteLengths noteRadioPanel) {
		ActionListener noteListener = new ActionListener() {
			private NoteLengths noteLengths;
			public ActionListener setLength(NoteLengths noteLengths) {
				this.noteLengths = noteLengths;
				return this;
			}
			public void actionPerformed(ActionEvent av) {
				System.out.println(noteLengths.getCurrent());
			}
		}.setLength(noteRadioPanel);
		
		return noteListener;
	}
	
	/**
	 * Set up for the other panels orientation within the central panel
	 * 
	 * @return the central control panel
	 */
	private JPanel setUpMidControls(){
		
		gbCon.gridx = 0;
		gbCon.gridy = 0;
		gbCon.gridheight = 2;
		gbCon.ipadx = 150;
		gbCon.insets = new Insets(0,20,20,10);
		gbCon.fill = GridBagConstraints.BOTH;
		centerPanel.add(textViewPanel.setUpNoteView(),gbCon);
		
		gbCon.insets = new Insets(10,10,0,0);
		gbCon.ipadx = 0;
		gbCon.weightx = .75;
		gbCon.weighty = .75;
		gbCon.gridheight = 1;
		gbCon.gridx = 1;
		gbCon.gridy = 0;
		gbCon.fill = GridBagConstraints.HORIZONTAL;
		gbCon.anchor = GridBagConstraints.BASELINE_LEADING;
		centerPanel.add(buttonPanel.setUpBPanel(),gbCon);
		
    	gbCon.weightx = 0.5;
		gbCon.weighty = 0.5;
		gbCon.gridy = 1;
		gbCon.anchor = GridBagConstraints.BASELINE_LEADING;
		centerPanel.add(noteRadioPanel.noteLengthPanel(),gbCon);
		
		return centerPanel;
	}
	
	/**
	 * Add the other panels to the main control panel with orientation
	 * 
	 * @return the main control panel in it's entirety 
	 */
	public JPanel setUpControls(){
		
		
		controlPanel.add(setUpMidControls());
		return controlPanel;
	}
}