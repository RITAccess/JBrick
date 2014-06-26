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
	
	ButtonActions buttonPanel = new ButtonActions();
	NoteLengths noteRadioPanel = new NoteLengths();
	OctaveChange transPanel = new OctaveChange();
	
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
		rest = new JButton("Rest");
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
		
		gbCon.insets = new Insets(0,10,0,50);
		gbCon.ipadx = 100;
		gbCon.ipady = 200;
		gbCon.weightx = .75;
		gbCon.weighty = .75;
		gbCon.gridx = 2;
		gbCon.gridy = 0;
		gbCon.anchor = GridBagConstraints.CENTER;
		centerPanel.add(buttonPanel.setUpBPanel(),gbCon);
		
		gbCon.weightx = 0.5;
		gbCon.weighty = 0.5;
		gbCon.gridx = 1;
		gbCon.anchor = GridBagConstraints.WEST;
		centerPanel.add(noteRadioPanel.noteLengthPanel(),gbCon);
		centerPanel.add(rest,restSetUp());
		return centerPanel;
	}
	public JButton test() {
		return rest;
	}
	
	/**
	 * Add the other panels to the main control panel with orientation
	 * 
	 * @return the main control panel in it's entirety 
	 */
	public JPanel setUpControls(){
		
		controlPanel.add(transPanel.setUpTransposer(),BorderLayout.NORTH);

		controlPanel.add(setUpMidControls(),BorderLayout.CENTER);
		noteRadioPanel.customPanel().setAlignmentY(noteRadioPanel.noteLengthPanel().getAlignmentY());
		return controlPanel;
	}
}