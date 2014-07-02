package com.jbricx.piano;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Hashtable;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;

public class OctaveChange{
	
	private JPanel transPanel;
	private JLabel transpose;
	private JSlider transposer;
	
	static final int OCTAVE_ZEROTH = 0;
	static final int OCTAVE_FIRST = 1;
	static final int OCTAVE_SECOND = 2;
	static final int OCTAVE_FIFTH = 5;
	static final int OCTAVE_FOURTH = 4;
	static final int OCTAVE_THIRD = 3;
	static final int OCTAVE_SIXTH = 6;
	static final int OCTAVE_SEVENTH = 7;
	static final int OCTAVE_EIGHTH = 8;
	
	/**
	 * Constructor for transposer 
	 */
	
	public OctaveChange() {
		
		transPanel = new JPanel(new GridBagLayout());
		transpose = new JLabel("Transposer/Octave");
		transposer = new JSlider(JSlider.HORIZONTAL,OCTAVE_ZEROTH,OCTAVE_EIGHTH,OCTAVE_FOURTH);
		
		Hashtable<Integer,JLabel> transLabels =
				new Hashtable<Integer,JLabel>();
		transLabels.put(new Integer(OCTAVE_ZEROTH), new JLabel("0th"));
		transLabels.put(new Integer(OCTAVE_FIRST), new JLabel("1st"));
		transLabels.put(new Integer(OCTAVE_SECOND), new JLabel("2nd"));
		transLabels.put(new Integer(OCTAVE_THIRD), new JLabel("3rd"));
		transLabels.put(new Integer(OCTAVE_FOURTH), new JLabel("4th"));
		transLabels.put(new Integer(OCTAVE_FIFTH), new JLabel("5th"));
		transLabels.put(new Integer(OCTAVE_SIXTH), new JLabel("6th"));
		transLabels.put(new Integer(OCTAVE_SEVENTH), new JLabel("7th"));
		transLabels.put(new Integer(OCTAVE_EIGHTH), new JLabel("8th"));
		
		transposer.setLabelTable(transLabels);
		transposer.setPaintLabels(true);
		transposer.setSnapToTicks(true);

		
	}
	
	/**
	 * gets transposer slider
	 * @return
	 */
	public JSlider getSlider(){
		return transposer;
	}
	
	/**
	 * Add the title and the transposer slider to a panel
	 * 
	 * @return transposer panel
	 */
	public JPanel setUpTransposer() {
		
		GridBagConstraints gbCon = new GridBagConstraints();
		gbCon.ipadx = 100;
		gbCon.weightx = 0.75;
		gbCon.weighty = 0.75;
		gbCon.insets = new Insets(0,20,10,0);
		
		gbCon.gridx = 0;
		gbCon.gridy = 1;
		gbCon.fill = GridBagConstraints.HORIZONTAL;
		gbCon.anchor = GridBagConstraints.CENTER;
		transPanel.add(transposer,gbCon);
		
		gbCon.gridx = 0;
		gbCon.gridy = 0;
		transpose.setLabelFor(transposer);
		transPanel.add(transpose,gbCon);
		return transPanel;
		
	}
	
}