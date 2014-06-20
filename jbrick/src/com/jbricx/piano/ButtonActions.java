package com.jbricx.piano;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * Class for creating the buttons in the piano frame and giving them functionality
 * 
 * @author Melissa Y
 *
 */
public class ButtonActions {
	
	GridBagConstraints gbCon = new GridBagConstraints();
	
	private JButton rest;
	private JButton clear;
	private JButton copy;
	private JButton play;
	private JButton save;
	private JButton help;
	
	private JPanel buttonPanel;
	
	/**
	 * Constructor of the buttons used
	 * 
	 */
	public ButtonActions() {
		
		buttonPanel = new JPanel(new GridBagLayout());
		

		rest = new JButton("Rest");
		play = new JButton("Play");
		copy = new JButton("Copy");
		save = new JButton("Save");
		help = new JButton("Help");
		clear = new JButton("Clear");
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
		
		gbCon.insets = new Insets(30,30,30,30);
		gbCon.ipadx = 50;
		gbCon.ipady = 25;
		gbCon.gridx = xPlace;
		gbCon.gridy = yPlace;
		buttonPanel.add(ctrlButton,gbCon);

	}

	/**
	 * Give buttons functionality 
	 * 
	 * Set button information 
	 * 
	 */
	public void buttonAction() {
		
		rest.setToolTipText("Adds a rest or note of chosen time interval");
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
		
		return buttonPanel;
		
	}
}