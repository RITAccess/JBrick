package com.jbricx.piano;

import java.awt.BorderLayout;
import java.awt.Image;

import javax.swing.ActionMap;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JPanel;
import javax.swing.JLabel;


public class PianoKeys {
	
	private JPanel pianoBoard;
	private JLabel picLabel;
	private Image pianoPic;
	private ImageIcon piano;
	InputMap im;
	ActionMap am;
	
	/**
	 * Constructor for keyboard image
	 * 
	 */
	public PianoKeys() {
		
		pianoBoard = new JPanel(new BorderLayout());
		piano = new ImageIcon("images/PianoKeysSS.jpg");
		pianoPic = piano.getImage();
		pianoPic = pianoPic.getScaledInstance(1000, 200, java.awt.Image.SCALE_SMOOTH);
		piano = new ImageIcon(pianoPic);
		picLabel = new JLabel("",piano,JLabel.CENTER);
				
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
		
		pianoBoard.add(picLabel, BorderLayout.CENTER);
		return pianoBoard;
	}
	
	public void assignKeys(){
		

	}
}
