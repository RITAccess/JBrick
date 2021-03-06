package com.jbricx.piano;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
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
		char[] whiteKeys = "ASDFGHJKL;".toCharArray();
		String[] whiteKeyStrings = {"C", "D", "E", "F", "G", "A1", "B1", "C1", "D1", "E1"};
		char[] blackKeys = "WE TYU OP".toCharArray();
		String[] blackKeyStrings = {"C#", "D#", "", "F#", "G#", "A#1", "", "C#1", "D#1"};
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
		restButton.getAccessibleContext().setAccessibleName("Rest");
		
		// octave changers
		ActionButton upOctave = new ActionButton("+", '=', null);
		ActionButton dnOctave = new ActionButton("-", '-', null);
		JPanel octavePanel = new JPanel();

		octaveLabel = new JLabel("5");
		upOctave.getAccessibleContext().setAccessibleName("Increase Octave");
		dnOctave.getAccessibleContext().setAccessibleName("Decrease Octave");
		upOctave.setAction(new OctaveChangeAction(octaveLabel, 1,upOctave));
		dnOctave.setAction(new OctaveChangeAction(octaveLabel,-1,dnOctave));
		
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
	
	private Color backgroundColor;
	private Color hoverColor;
	private Color pressedColor;
	
	/**
	 * Constructor for a piano button
	 * @param text - text for button to show
	 * @param key - key required to trigger buttor
	 * @param whiteKey - if the key is white (else it's black)
	 * @param index - location via an index
	 * @param actionHandler - action for piano key
	 */
	PianoButton(String text, char key, final boolean whiteKey, final int index, PianoActionHandler actionHandler) {
		super(text, key, actionHandler);
		
		backgroundColor = whiteKey ? Color.WHITE : Color.BLACK;
		hoverColor = whiteKey ? Color.WHITE.darker() : Color.GRAY.darker().darker();
		pressedColor = whiteKey ? Color.GRAY.brighter() : Color.GRAY.darker();
		
		this.setLayout(new BorderLayout());
		this.add(BorderLayout.SOUTH, new JLabel("<html><div style= \"font-size:26pt; color: #" + (whiteKey ? "000000" : "FFFFFF") + "\">"+key + "</div></html>"));
		this.setBackground(backgroundColor);
		this.setBorder(BorderFactory.createLineBorder(whiteKey ? Color.BLACK : Color.WHITE));
		this.setContentAreaFilled(false);
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

		if(text.contains("#")) {
			text = text.replaceFirst("#", " sharp ");
			this.getAccessibleContext().setAccessibleName(text);
		} else {
			this.getAccessibleContext().setAccessibleName(text);
		}
	}
	
    @Override
    protected void paintComponent(Graphics g) {
        if (getModel().isPressed()) {
            g.setColor(pressedColor);
            this.setBackground(pressedColor);
        } else if (getModel().isRollover()) {
            g.setColor(hoverColor);
            this.setBackground(hoverColor);
        } else {
            g.setColor(backgroundColor);
            this.setBackground(backgroundColor);
        }
        g.fillRect(0, 0, getWidth(), getHeight());
        super.paintComponent(g);
    }
	
	PianoButton(char text, boolean whiteKey, int index, PianoActionHandler actionHandler) {
		this("" + text, text, whiteKey, index, actionHandler);
	}
	
	/**
	 * get if the button is white (else it's assumed black)
	 * @return boolean - true if the color is white
	 */
	protected boolean isWhite(){
		return this.getBackground() == Color.WHITE;
	}
	
	/**
	 * setParentPanel - sets the parent panel for the button
	 * @param panel
	 */
	protected static void setParentPanel(Component panel){
		PianoButton.panel = panel;
	}
	
	/**
	 * setKeyCount - sets the key count to parameter count
	 * @param count
	 */
	protected static void setKeyCount(int count){
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
	boolean keyHit = false;
	/**
	 * Constructor for ActionButton
	 * @param label - text for the button
	 * @param key - key that triggers button
	 * @param actionHandler - action associated with button
	 */
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
	/**
	 * ActionButton - alternate constructor for no text in button
	 * @param key 
	 * @param actionHandler
	 */
	ActionButton(final char key, PianoActionHandler actionHandler){
		this("", key, actionHandler);
	}
	
	/**
	 * setAction - adds an action to this ActionButton
	 * @param newAction
	 */
	public void setAction(AbstractAction newAction){
		this.addActionListener(newAction);
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
				KeyStroke.getKeyStroke(key, 0), key + "_pressed");
		this.getActionMap().put(key + "_pressed",
		new AbstractAction(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				ActionButton.this.doClick();
				ActionButton.this.requestFocus();
			}
		});
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
	ActionButton button;
	
	/**
	 * Constructor - way to control the octaves of the piano keyboard
	 * @param ol - Octave Label
	 * @param d - Direction ("+" goes up, "-" goes down)
	 * @param button - ActionButton
	 */
	protected OctaveChangeAction(JLabel ol, int d,ActionButton button){
		this.ol = ol;
		this.direction = d;
		this.button = button;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		int currentOctave = Integer.parseInt(ol.getText());
		String label = "";
		if (!((currentOctave >= 7 && direction > 0) ||
				(currentOctave <= 3 && direction < 0))){
			 ol.setText( "" + (currentOctave + direction) );
			 label = Integer.toString(currentOctave + direction);
			 button.getAccessibleContext().setAccessibleName("Octave " + Integer.toString(currentOctave) + " went to " + label);
		}
	}
}
