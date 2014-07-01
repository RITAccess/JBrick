package com.jbricx.piano;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JRadioButton;

public class PianoWindow extends JFrame {

	private static NotesView textView = new NotesView();
	private static PianoControls controls = new PianoControls();
	private static NoteLengths notePrint = new NoteLengths();
	private static OctaveChange transposer = new OctaveChange();
	private static PianoKeyboard pianoKeyboard = new PianoKeyboard();
	

	/**
	 * Set up the orientation of the panels in the JFrame
	 * 
	 * Set up the settings of the JFrame
	 */
	private static void setUpPiano() {
		
		PianoWindow pianoControls = new PianoWindow();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		pianoControls.setSize((int) screenSize.getHeight(), (int)screenSize.getWidth());
		
		pianoControls.setLayout(new GridBagLayout());
		GridBagConstraints gbCon = new GridBagConstraints();
		gbCon.ipadx = 100;
		gbCon.ipady = 100;
		gbCon.gridx = 0;
		gbCon.gridy = 0;
		gbCon.gridwidth = 3;
		gbCon.gridheight = 2;
		gbCon.fill = GridBagConstraints.BOTH;
		pianoControls.add(pianoKeyboard,gbCon);
		
		gbCon.insets = new Insets(0,0,0,20);
		gbCon.ipady = 50;
		gbCon.gridx = 0;
		gbCon.gridy = 3;
		gbCon.gridheight = 1;
		gbCon.fill = GridBagConstraints.HORIZONTAL;
		pianoControls.add(transposer.setUpTransposer(),gbCon);
		
		gbCon.fill = GridBagConstraints.NONE;
		gbCon.gridx = 0;
		gbCon.gridy = 4;
		pianoControls.add(controls.setUpControls(),gbCon);
		
		pianoControls.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		pianoControls.setLocationRelativeTo(null);
		pianoControls.pack();
		pianoControls.setVisible(true);
		pianoControls.setTitle("Piano Composer");

	}
	
	public static void setUpActions() {
		notePrint.setButtonValue();
		textView.printNotes(notePrint.getCurrent());
	}
	/**
	 * Create the UI of the piano composer 
	 * 
	 * @param args
	 */
	public static void main(String args[]) {
		setUpPiano();
		setUpActions();
	}

}