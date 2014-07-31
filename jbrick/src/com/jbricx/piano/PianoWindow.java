package com.jbricx.piano;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.sound.sampled.SourceDataLine;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import javax.swing.WindowConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.jbricx.communication.USBConnection;
import com.jbricx.swing.ui.JBricxDialog;
import com.jbricx.tools.AccessibleWidget;
import com.jbricx.tools.AudioPlayer;
/**
 * 
 * @author Melissa Young, Ethan Jurman
 *
 */

@SuppressWarnings("serial")
public class PianoWindow extends JBricxDialog implements WindowListener {

	private NotesView textView;
	private PianoControls controls;
	private NoteLengths notePrint;
	private OctaveChange transposer;
	private PianoActionHandler pianoHandler;
	private PianoKeyboard pianoKeyboard;
	private SourceDataLine line;
	public AccessibleWidget accessibleStatus = new AccessibleWidget(" ");
	
	/**
	 * sets manager for other tools (like piano help)
	 * @param manager
	 */
	public PianoWindow(JFrame shell) {
		super(shell,"Piano Composer",false);
		line = AudioPlayer.openLine();
		controls = new PianoControls(this,shell);
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
				if (textView.getText().isEmpty()){
					if(noteInformation == "REST") {
						textView.appendText(noteInformation + " " + notePrint.getValue());
					} else {
						int octave = transposer.getSlider().getValue(); 
						// if 1, go up an octave
						if (noteInformation.contains("1")){
							octave++;
							noteInformation = noteInformation.substring(0, noteInformation.length() - 1);
						}
						textView.appendText(noteInformation + octave +" "+ notePrint.getValue());
						
						if(controls.buttonPanel.nxtOutput && USBConnection.isConnected()){
							AudioPlayer.playNXT(
									AudioPlayer.getLength(notePrint.getValue()), 
									noteInformation + octave);
						}
						if(controls.buttonPanel.javaOutput){
							AudioPlayer.play(
									line,
									noteInformation + octave,
									AudioPlayer.getLength(notePrint.getValue()));
						}
					}
				}
				else {
				if(noteInformation == "REST") {
					textView.appendText("\n" + noteInformation + " " + notePrint.getValue());
				} else {
					int octave = transposer.getSlider().getValue(); 
					// if 1, go up an octave
					if (noteInformation.contains("1")){
						octave++;
						noteInformation = noteInformation.substring(0, noteInformation.length() - 1);
					}
					textView.appendText("\n" + noteInformation + octave +" "+ notePrint.getValue());
					
					if(controls.buttonPanel.nxtOutput && USBConnection.isConnected()){
						AudioPlayer.playNXT(
								AudioPlayer.getLength(notePrint.getValue()), 
								noteInformation + octave);
					}
					if(controls.buttonPanel.javaOutput){
						AudioPlayer.play(
								line,
								noteInformation + octave,
								AudioPlayer.getLength(notePrint.getValue()));
					}
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
		final JPanel mainPanel = new JPanel();
		JScrollPane mainScroll = new JScrollPane(mainPanel);
		this.setSize((int) screenSize.getHeight(), (int)screenSize.getWidth());
		
		mainPanel.setLayout(new GridBagLayout());
		GridBagConstraints gbCon = new GridBagConstraints();
		gbCon.anchor = GridBagConstraints.CENTER;
		gbCon.fill = GridBagConstraints.HORIZONTAL;
		
		gbCon.ipadx = 50;
		gbCon.ipady = 100;
		gbCon.gridx = 0;
		gbCon.gridy = 0;
		gbCon.insets = new Insets(20,20,0,20);
		mainPanel.add(pianoKeyboard,gbCon);
		
		gbCon.insets = new Insets(0,0,0,20);
		gbCon.ipady = 50;
		gbCon.gridx = 0;
		gbCon.gridy = 3;
		gbCon.gridheight = 1;
		mainPanel.add(transposer.setUpTransposer(),gbCon);
		
		gbCon.gridx = 0;
		gbCon.gridy = 4;
		mainPanel.add(controls.setUpControls(),gbCon);
	
		gbCon.gridx = 0;
		gbCon.gridwidth = 2;
		gbCon.fill = GridBagConstraints.NONE;
		gbCon.gridy = 5;
		gbCon.ipady = 0;
		gbCon.insets = new Insets(0,0,20,0);
		
		mainPanel.add(accessibleStatus, gbCon);
		mainScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		this.add(mainScroll);
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
		this.addWindowListener(this);
		this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
	}

	@Override
	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void windowClosing(WindowEvent arg0) {
		AudioPlayer.closeLine(line);
		this.dispose();
		
	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowOpened(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}