package com.jbricx.piano;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

@SuppressWarnings("serial")

/**
 * 
 * Holds all the information and action events to do key presses
 * Generates a keyboard that can be inserted into a panel
 * @author Ethan Jurmna
 *
 */
public class PianoKeyboard extends JPanel{
	
	public PianoKeyboard() {
		this.setLayout(new BorderLayout());
		// Building all the keys 
		
		// white and black
		JLayeredPane pianoKeys = new JLayeredPane();
		char[] whiteKeys = {'A','S','D','F','G','H','J'};
		char[] blackKeys = {'W','E',' ','T','Y','U'};
		int width = 0, height = 0;
		for (int k = 0; k < whiteKeys.length + blackKeys.length; k++){
			PianoButton key;
			if (k < whiteKeys.length) {
				key = new PianoButton(whiteKeys[k], true, k);
				width = width + 8 + key.getWidth(); // add to total width 
				height = height + key.getHeight(); // add to total height
			} else {
				int index = k % whiteKeys.length;
				if (blackKeys[index] == ' '){ continue;}
				key = new PianoButton(blackKeys[index], false, index);
			}
			pianoKeys.add(key, key.isWhite() ? 0 : 1, -1);
		}
		pianoKeys.setSize(width, height);
		
		// rest
		ActionButton restButton = new ActionButton("REST", '/');
		this.setSize(pianoKeys.getWidth() + restButton.getWidth(), 200);
		
		// octave changers
		ActionButton upOctave = new ActionButton("+", '=');
		ActionButton dnOctave = new ActionButton("-", '-');
		JPanel octavePanel = new JPanel();

		JLabel octaveLabel = new JLabel("3");
		upOctave.setAction(new OctaveChangeAction(octaveLabel, 1));
		dnOctave.setAction(new OctaveChangeAction(octaveLabel, -1));
		octavePanel.add(dnOctave);
		octavePanel.add(octaveLabel);
		octavePanel.add(upOctave);
		
		this.add(BorderLayout.CENTER, pianoKeys);
		this.add(BorderLayout.EAST, restButton);
		this.add(BorderLayout.SOUTH, octavePanel);
	}
	
	public static void main(String[] arg0){
		JFrame.setDefaultLookAndFeelDecorated(false);
		JFrame frame = new JFrame();
		PianoKeyboard pk = new PianoKeyboard();
		frame.add(pk);
		frame.setSize(pk.getSize());
		frame.setVisible(true);
	}
}

/**
 * Generates one piano button.
 * Should create a correct looking piano key
 * @author Ethan Jurman
 *
 */
@SuppressWarnings("serial")
class PianoButton extends ActionButton{
	
	PianoButton(String text, char key, boolean whiteKey, int index) {
		super(key);
		this.setLayout(new BorderLayout());
		this.add(BorderLayout.SOUTH, new JLabel(""+text));
		this.setBackground(whiteKey ? Color.WHITE : Color.BLACK);
		this.setOpaque(true);
		this.setSize(whiteKey ? 32 : 28, whiteKey ? 130 : 70);
		this.setLocation(index * 32 + (whiteKey ? 0 : 14), 0);
	}
	
	PianoButton(char text, boolean whiteKey, int index) {
		this("" + text, text, whiteKey, index);
	}
	
	public boolean isWhite(){
		return this.getBackground() == Color.WHITE;
	}
}

/**
 * Allows easy building of buttons that hold actions
 * 
 * @author Ethan Jurman
 *
 */
@SuppressWarnings("serial")
class ActionButton extends JButton{
	
	final char key;
	ActionButton(String label, final char key){
		this.add(new JLabel(label));
		this.key = key;
		AbstractAction buttonPressed = new AbstractAction(){
			public void actionPerformed(ActionEvent e) {
				System.out.println("key press: "+ key);
			}
		};
		
		this.setAction(buttonPressed);
		
	}
	ActionButton(final char key){
		this("", key);
	}
	
	public void setAction(AbstractAction newAction){
		this.addActionListener(newAction);
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
				KeyStroke.getKeyStroke(key, 0), key + "_pressed");
		this.getActionMap().put(key + "_pressed", newAction);
	}
}

/**
 * Specific implementation of Abstract Action to generate buttons that do octave changes
 * @author Ethan Jurman
 *
 */
@SuppressWarnings("serial")
class OctaveChangeAction extends AbstractAction{
	
	JLabel ol;
	int direction;
	
	public OctaveChangeAction(JLabel ol, int d){
		this.ol = ol;
		this.direction = d;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		int currentOctave = Integer.parseInt(ol.getText());
		if (!((currentOctave >= 8 && direction > 0) ||
				(currentOctave <= 0 && direction < 0))){
			ol.setText( "" + (currentOctave + direction) );
		}
	}
	
}
