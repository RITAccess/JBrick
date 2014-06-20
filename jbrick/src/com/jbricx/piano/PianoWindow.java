package com.jbricx.piano;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JFrame;

<<<<<<< HEAD
=======

//TODO: Start dividing the components up into separate classes 
>>>>>>> 888ad9eb85aa7a407b6b4657ca7cf2f4306d1ee3
public class PianoWindow extends JFrame {

	private static NotesView textView = new NotesView();
	private static PianoKeys pianoKeyboard = new PianoKeys();
	private static PianoControls controls = new PianoControls();
	
	/**
	 * Set up the orientation of the panels in the JFrame
	 * 
	 * Set up the settings of the JFrame
	 */
	private static void setUpPiano() {
		
		PianoWindow pianoControls = new PianoWindow();

		pianoControls.setLayout(new GridBagLayout());
		GridBagConstraints gbCon = new GridBagConstraints();
		gbCon.ipadx = 100;
		gbCon.ipady = 100;
		gbCon.gridx = 0;
		gbCon.gridy = 0;
		pianoControls.add(pianoKeyboard.setUpPianoImage(),gbCon);
		
		gbCon.insets = new Insets(10,10,10,10);
		gbCon.ipady = 100;
		gbCon.gridx = 0;
		gbCon.gridy = 1;
		gbCon.fill = GridBagConstraints.HORIZONTAL;
		pianoControls.add(textView.setUpNoteView(),gbCon);
		
		
		gbCon.gridx = 0;
		gbCon.gridy = 2;
		pianoControls.add(controls.setUpControls(),gbCon);
		
		pianoControls.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pianoControls.pack();
		pianoControls.setLocationRelativeTo(null);
		pianoControls.setVisible(true);
		pianoControls.setTitle("Piano Composer");

	}
	
	/**
	 * Create the UI of the piano composer 
	 * 
	 * @param args
	 */
	public static void main(String args[]) {
		setUpPiano();
	}
}