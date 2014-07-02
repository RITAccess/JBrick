package com.jbricx.piano;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
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
	
	// items with access
	JLabel octaveLabel;
	private JPanel keyPanel;
	
	/**
	 * 
	 * @param handler
	 */
	public PianoKeyboard(PianoActionHandler handler, int xOffset, int yOffset, float xScale, float yScale) {
		// setting pianokey sizes
		PianoButton.setSizes(xOffset, yOffset, xScale, yScale);

		this.setLayout(new BorderLayout());
		// Building all the keys 
		
		// white and black
		JLayeredPane pianoKeys = new JLayeredPane();
		char[] whiteKeys = {'A','S','D','F','G','H','J'};
		String[] whiteKeyStrings = {"C", "D", "E", "F", "G", "A", "B"};
		char[] blackKeys = {'W','E',' ','T','Y','U'};
		String[] blackKeyStrings = {"C#", "D#", "", "F#", "G#", "A#"};
		for (int k = 0; k < whiteKeys.length + blackKeys.length; k++){
			PianoButton key;
			if (k < whiteKeys.length) {
				key = new PianoButton(whiteKeyStrings[k], whiteKeys[k], true, k, handler);
			} else {
				int index = k % whiteKeys.length;
				if (blackKeys[index] == ' '){ continue;}
				key = new PianoButton(blackKeyStrings[index], blackKeys[index], false, index, handler);
			}
			pianoKeys.add(key, key.isWhite() ? 0 : 1, -1);
		}
		
		// rest
		ActionButton restButton = new ActionButton("REST", '/', handler);
		this.setSize(pianoKeys.getWidth() + restButton.getWidth(), 200);
		
		// octave changers
		ActionButton upOctave = new ActionButton("+", '=', handler);
		ActionButton dnOctave = new ActionButton("-", '-', handler);
		JPanel octavePanel = new JPanel();

		octaveLabel = new JLabel("4");
		upOctave.setAction(new OctaveChangeAction(octaveLabel, 1));
		dnOctave.setAction(new OctaveChangeAction(octaveLabel, -1));
		octavePanel.add(dnOctave);
		octavePanel.add(octaveLabel);
		octavePanel.add(upOctave);
		
		JPanel keyPanel = new JPanel(new GridLayout(1,1));
		keyPanel.add(pianoKeys);
		
		this.add(keyPanel);
		this.add(BorderLayout.EAST, restButton);
		this.add(BorderLayout.SOUTH, octavePanel);
	}
	
	PianoKeyboard(PianoActionHandler handler){
		this(handler, 0, 0, 1, 1);
	}
	
	public JPanel test(){
		return keyPanel;
	}
	
	public JLabel getOctaveLabel(){
		return octaveLabel;
	}
	
	public static void main(String[] arg0){
	
		JFrame.setDefaultLookAndFeelDecorated(false);
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		PianoActionHandler pianoHandler = new PianoActionHandler(){

			@Override
			public void pianoActionHit(String noteInformation) {
				System.out.println(noteInformation);
			}
			
		};
		
		
		PianoKeyboard pk = new PianoKeyboard(pianoHandler, 100, 50, 3.0f, 3.0f);

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
	
	private static int xOffset = 0;
	private static int yOffset = 0;
	private static float xScale = 1;
	private static float yScale = 1;
	
	PianoButton(String text, char key, boolean whiteKey, int index, PianoActionHandler actionHandler) {
		super(text, key, actionHandler);
		this.setLayout(new BorderLayout());
		this.add(BorderLayout.SOUTH, new JLabel(""+key));
		this.setBackground(whiteKey ? Color.WHITE : Color.BLACK);
		this.setOpaque(true);
		this.setSize(
				(int)((whiteKey ? 32 : 28) * xScale),
				(int)((whiteKey ? 130 : 70) * yScale)
			);
		this.setLocation(
				(int)(xOffset + index * (32 * xScale) + (whiteKey ? 0 : (14 * xScale))),
				yOffset
			);
	}
	
	PianoButton(char text, boolean whiteKey, int index, PianoActionHandler actionHandler) {
		this("" + text, text, whiteKey, index, actionHandler);
	}
	
	public boolean isWhite(){
		return this.getBackground() == Color.WHITE;
	}
	
	public static void setSizes(int xOffset, int yOffset, float xScale, float yScale){
		PianoButton.xOffset = xOffset;
		PianoButton.yOffset = yOffset;
		PianoButton.xScale = xScale;
		PianoButton.yScale = yScale;
		
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
	ActionButton(String label, final char key, PianoActionHandler actionHandler){
		this.add(new JLabel(label));
		this.key = key;
		
		AbstractAction buttonPressed = new AbstractAction(){

			PianoActionHandler actionHandler;
			String text;
			
			public AbstractAction setActionInformation(PianoActionHandler actionHandler, String text){
				this.actionHandler = actionHandler;
				this.text = text;
				return this;
			}
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				actionHandler.pianoActionHit(text);
			}
			
		}.setActionInformation(actionHandler, label);
		
		this.setAction(buttonPressed);
		
	}
	ActionButton(final char key, PianoActionHandler actionHandler){
		this("", key, actionHandler);
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
