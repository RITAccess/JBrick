package com.jbricx.piano;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class NoteLengths {
	
	private JLabel length;
	private JLabel noteTime;
	private JTextField noteField;
	private JLabel waitTime;
	private JTextField waitField;
	private JRadioButton firsts;
	private JRadioButton seconds;
	private JRadioButton thirds;
	private JRadioButton fourths;
	private JRadioButton eighths;
	private JRadioButton sixteenths;
	private ButtonGroup noteLength;
	private JPanel radioPanel;
	private JPanel customLengthPanel;
	
	/**
	 * Constructor for the radio buttons and the custom text fields
	 */
	public NoteLengths() {
		
		radioPanel = new JPanel(new GridLayout(7,1));
		customLengthPanel = new JPanel(new GridBagLayout());
		
		noteTime = new JLabel("Note Time: ");
		noteField = new JTextField(3);
		waitTime = new JLabel("Wait Time: ");
		waitField = new JTextField(3);
		
		length = new JLabel("Length");
		noteLength = new ButtonGroup();
		firsts = new JRadioButton("1/1");
		noteLength.add(firsts);
		seconds = new JRadioButton("1/2");
		noteLength.add(seconds);
		thirds = new JRadioButton("1/3");
		noteLength.add(thirds);
		fourths = new JRadioButton("1/4");
		noteLength.add(fourths);
		eighths = new JRadioButton("1/8");
		noteLength.add(eighths);
		sixteenths =new JRadioButton("1/16");
		noteLength.add(sixteenths);
		
	}
	
	/**
	 * Set up for the text field components orientations
	 * 
	 * @param noteCustoms
	 * @param xPlace
	 * @param yPlace
	 */
	private void setUpCustom(JComponent noteCustoms,int xPlace, int yPlace) {
		
		GridBagConstraints gbCon = new GridBagConstraints();
		
		gbCon.insets = new Insets(0,10,30,10);
		gbCon.gridx = xPlace;
		gbCon.gridy = yPlace;
		customLengthPanel.add(noteCustoms,gbCon);
	}
	
	/**
	 * Add components for the custom length panel
	 * 
	 * @return the custom notes length panel
	 */
	public JPanel customPanel() {
		
		setUpCustom(noteTime,0,0);
		setUpCustom(noteField,1,0);
		setUpCustom(waitTime,2,0);
		setUpCustom(waitField,3,0);
		return customLengthPanel;
	}
	
	/**
	 * Add the radio buttons and their title to a panel
	 * 
	 * @return radio note length panel
	 */
	public JPanel noteLengthPanel(){
		
		radioPanel.add(length);
		radioPanel.add(firsts);
		radioPanel.add(seconds);
		radioPanel.add(thirds);
		radioPanel.add(fourths);
		radioPanel.add(eighths);
		radioPanel.add(sixteenths);
		
		radioPanel.setBorder(BorderFactory.createEtchedBorder());
		return radioPanel;
	}
}