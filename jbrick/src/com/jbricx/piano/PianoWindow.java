package com.jbricx.piano;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JFrame;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.jbricx.communication.USBConnection;
import com.jbricx.tools.AudioPlayer;
/**
 * 
 * @author Melissa Young
 *
 */

@SuppressWarnings("serial")
public class PianoWindow extends JFrame {

	private NotesView textView;
	private PianoControls controls;
	private NoteLengths notePrint;
	private OctaveChange transposer;
	private PianoActionHandler pianoHandler;
	private PianoKeyboard pianoKeyboard;
	
	/**
	 * sets manager for other tools (like piano help)
	 * @param manager
	 */
	public PianoWindow() {
		
		controls = new PianoControls(this);
		transposer = controls.transPanel;
		notePrint = controls.noteRadioPanel;
		textView = controls.textViewPanel;
		
		pianoHandler = new PianoActionHandler(){

			private NotesView textView;

			public PianoActionHandler setTextArea(NotesView textView){
				this.textView = textView;
				return this;
			}
			
			@Override
			public void pianoActionHit(String noteInformation) {
				
				if(noteInformation == "REST") {
					textView.appendText(noteInformation + " " + notePrint.getValue() + "\n");
				} else {
					int octave = transposer.getSlider().getValue(); 
					// if 1, go up an octave
					if (noteInformation.contains("1")){
						octave++;
						noteInformation = noteInformation.substring(0, noteInformation.length() - 1);
					}
					textView.appendText(noteInformation + octave +" "+ notePrint.getValue() + "\n");
					
					if(controls.buttonPanel.nxtOutput && USBConnection.isConnected()){
						AudioPlayer.playNXT(
								AudioPlayer.getLength(notePrint.getValue()), 
								noteInformation + octave);
					}
					if(controls.buttonPanel.javaOutput){
						AudioPlayer.play(
								AudioPlayer.getLength(notePrint.getValue()),
								noteInformation + octave);
					}
					
				}
			}
			
		}.setTextArea(textView);
		pianoKeyboard = new PianoKeyboard(pianoHandler,null);
	}

	/**
	 * Set up the orientation of the panels in the JFrame
	 * 
	 * Set up the settings of the JFrame
	 */
	public void setUpPiano() {
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setSize((int) screenSize.getHeight(), (int)screenSize.getWidth());
		
		this.setLayout(new GridBagLayout());
		GridBagConstraints gbCon = new GridBagConstraints();
		gbCon.anchor = GridBagConstraints.CENTER;
		gbCon.fill = GridBagConstraints.HORIZONTAL;
		
		gbCon.ipadx = 50;
		gbCon.ipady = 100;
		gbCon.gridx = 0;
		gbCon.gridy = 0;
		gbCon.insets = new Insets(20,20,0,20);
		this.add(pianoKeyboard,gbCon);
		
		gbCon.insets = new Insets(0,0,0,20);
		gbCon.ipady = 50;
		gbCon.gridx = 0;
		gbCon.gridy = 3;
		gbCon.gridheight = 1;
		this.add(transposer.setUpTransposer(),gbCon);
		
		gbCon.gridx = 0;
		gbCon.gridy = 4;
		this.add(controls.setUpControls(),gbCon);
	
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.pack();
		this.setTitle("Piano Composer");
		
		transposer.getSlider().addChangeListener(new ChangeListener(){

			@Override
			public void stateChanged(ChangeEvent arg0) {
				pianoKeyboard.getOctaveLabel().setText("" + transposer.getSlider().getValue());
			}
			
		});
		pianoKeyboard.getOctaveLabel().addPropertyChangeListener("text", new PropertyChangeListener(){

			@Override
			public void propertyChange(PropertyChangeEvent arg0) {
				transposer.getSlider().setValue(Integer.parseInt(pianoKeyboard.getOctaveLabel().getText()));
			}
			
		});

	}
	
	/**
	 * Create the UI of the piano composer 
	 * 
	 * @param args
	 */
	public static void main(String args[]) {
		PianoWindow pw = new PianoWindow();
		pw.setUpPiano();
		}

}