package com.jbricx.piano;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JRadioButton;

public class PianoWindow extends JFrame {

	private static PianoControls controls = new PianoControls();
	private static NoteLengths notePrint = new NoteLengths();
	private static OctaveChange transposer = new OctaveChange();
	static PianoActionHandler pianoHandler = new PianoActionHandler(){

		@Override
		public void pianoActionHit(String noteInformation) {
			// TODO Auto-generated method stub
			// Add note information + octave + note length to the textview 
		}
		
	};
	private static PianoKeyboard pianoKeyboard = new PianoKeyboard(pianoHandler);
	

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
		gbCon.anchor = GridBagConstraints.CENTER;
		gbCon.fill = GridBagConstraints.HORIZONTAL;
		
		gbCon.ipadx = 50;
		gbCon.ipady = 100;
		gbCon.gridx = 0;
		gbCon.gridy = 0;
		pianoControls.add(pianoKeyboard,gbCon);
		
		gbCon.insets = new Insets(0,0,0,20);
		gbCon.ipady = 50;
		gbCon.gridx = 0;
		gbCon.gridy = 3;
		gbCon.gridheight = 1;
		pianoControls.add(transposer.setUpTransposer(),gbCon);
		
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