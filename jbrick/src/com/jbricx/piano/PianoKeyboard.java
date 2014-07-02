package com.jbricx.piano;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

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
 * @author Ethan Jurman
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
	public PianoKeyboard(PianoActionHandler handler, JPanel parentPanel) {
		this.setLayout(new BorderLayout());
		// Building all the keys 
		
		// white and black
		JLayeredPane pianoKeys = new JLayeredPane();
		if (parentPanel == null){
			PianoButton.setParentPanel(pianoKeys);
		} else {
			PianoButton.setParentPanel(parentPanel);
		}
		char[] whiteKeys = "ASDFGHJ".toCharArray();
		String[] whiteKeyStrings = {"A", "B", "C", "D", "E", "F", "G"};
		char[] blackKeys = "W RT UI".toCharArray();
		String[] blackKeyStrings = {"A#", "", "C#", "D#", "", "F#", "G#"};
		PianoButton.setKeyCount(whiteKeys.length);
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
		ActionButton restButton = new ActionButton("REST", '\\', handler);
		
		// octave changers
		ActionButton upOctave = new ActionButton("+", '=', null);
		ActionButton dnOctave = new ActionButton("-", '-', null);
		JPanel octavePanel = new JPanel();

		octaveLabel = new JLabel("4");
		upOctave.setAction(new OctaveChangeAction(octaveLabel, 1));
		dnOctave.setAction(new OctaveChangeAction(octaveLabel, -1));
		octavePanel.add(dnOctave);
		octavePanel.add(octaveLabel);
		octavePanel.add(upOctave);
		
		this.add(pianoKeys);
		this.add(BorderLayout.EAST, restButton);
		this.add(BorderLayout.SOUTH, octavePanel);
		this.setVisible(true);
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

		PianoKeyboard pk = new PianoKeyboard(pianoHandler, null);
		frame.add(pk);
		frame.setSize(pk.getSize());
		frame.pack();
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
	private static int width = 0;
	private static int height = 0;
	static int keyCount = 7;
	private static Component panel;
	
	PianoButton(String text, char key, final boolean whiteKey, final int index, PianoActionHandler actionHandler) {
		super(text, key, actionHandler);
		this.setLayout(new BorderLayout());
		this.add(BorderLayout.SOUTH, new JLabel(""+key));
		this.setBackground(whiteKey ? Color.WHITE : Color.BLACK);
		this.setOpaque(true);
		if (panel != null){
			panel.addComponentListener(new ComponentListener(){
	
				private ActionButton button;
				
				public ComponentListener setButton(ActionButton button){
					this.button = button;
					return this;
				}
				
				@Override
				public void componentHidden(ComponentEvent arg0) {
					button.setVisible(false);
				}
	
				@Override
				public void componentResized(ComponentEvent arg0) {
					int width = panel.getWidth() == 0 ? PianoButton.width : panel.getWidth();
					int height = panel.getHeight() == 0 ? PianoButton.height : panel.getHeight(); 
					button.setSize(width / PianoButton.keyCount, (int)(height * (whiteKey ? 1.0 : 0.65)));
					button.setLocation(PianoButton.xOffset + index * button.getWidth() + (whiteKey ? 0 : button.getWidth()/2), PianoButton.yOffset);
					
				}
	
				@Override
				public void componentShown(ComponentEvent arg0) {
					button.setVisible(true);
					button.repaint();
				}

				@Override
				public void componentMoved(ComponentEvent e) {
					// TODO Auto-generated method stub
					
				}
				
			}.setButton(this));
		}
	}
	
	PianoButton(char text, boolean whiteKey, int index, PianoActionHandler actionHandler) {
		this("" + text, text, whiteKey, index, actionHandler);
	}
	
	public boolean isWhite(){
		return this.getBackground() == Color.WHITE;
	}
	
	public static void setParentPanel(Component panel){
		PianoButton.panel = panel;
	}
	
	public static void setSize(int width, int height, int xOffset, int yOffset){
		PianoButton.xOffset = xOffset;
		PianoButton.yOffset = yOffset;
		PianoButton.width = width;
		PianoButton.height = height;
	}
	
	public static void setKeyCount(int count){
		PianoButton.keyCount = count;
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
		
		if (actionHandler != null){
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
