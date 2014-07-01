package com.jbricx.piano;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;

import javax.swing.ActionMap;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JLabel;


public class PianoKeys {
	
	private JPanel pianoBoard;
	private JLabel picLabel;
	private JButton rest;
	private Image pianoPic;
	private ImageIcon piano;
	private GridBagConstraints gbCon;
	InputMap im;
	ActionMap am;
	
	/**
	 * Constructor for keyboard image
	 * 
	 */
	public PianoKeys() {
		
		pianoBoard = new JPanel(new GridBagLayout());
		pianoBoard.setPreferredSize(pianoBoard.getPreferredSize());
		piano = new ImageIcon("images/PianoKeysSS.jpg");
		pianoPic = piano.getImage();
		pianoPic = pianoPic.getScaledInstance(500, 300,java.awt.Image.SCALE_SMOOTH);
		piano = new ImageIcon(pianoPic);
		rest = new JButton("Rest");
		picLabel = new JLabel(piano);
		gbCon = new GridBagConstraints();
				
	}
	
	//TODO: Add mouselisteners to the image for clicking options
	//TODO: Add keyboardlisters/bindings for the letters on the keyboard
	//TODO: Add the musical components and save notes 
	
	/**
	 * Add a label that displays the piano image to a panel
	 * 
	 * @return JPanel
	 */
	public JPanel setUpPianoImage() {
		
		pianoBoard.add(picLabel);
		return pianoBoard;
	}
	
	public void assignKeys(){
		

	}
}
