package com.jbricx.piano;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Hashtable;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;

/**
 * Octave transposer for altering what octave
 * the Piano Keyboard is at. 
 * 
 */
public class OctaveChange{
	
	private JPanel transPanel;
	private JLabel transpose;
	private JSlider transposer;
	
	static final int OCTAVE_THIRD = 3;
	static final int OCTAVE_FOURTH = 4;
	static final int OCTAVE_FIFTH = 5;
	static final int OCTAVE_SIXTH = 6;
	static final int OCTAVE_SEVENTH = 7;
	
	/**
	 * Constructor for transposer 
	 */
	public OctaveChange() {
		
		transPanel = new JPanel(new GridBagLayout());
		transpose = new JLabel("Transposer/Octave");
		transposer = new JSlider(JSlider.HORIZONTAL,OCTAVE_THIRD,OCTAVE_SEVENTH,OCTAVE_FIFTH);
		
		Hashtable<Integer,JLabel> transLabels =
				new Hashtable<Integer,JLabel>();
		transLabels.put(new Integer(OCTAVE_THIRD), new JLabel("3rd"));
		transLabels.put(new Integer(OCTAVE_FOURTH), new JLabel("4th"));
		transLabels.put(new Integer(OCTAVE_FIFTH), new JLabel("5th"));
		transLabels.put(new Integer(OCTAVE_SIXTH), new JLabel("6th"));
		transLabels.put(new Integer(OCTAVE_SEVENTH), new JLabel("7th"));
		
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
		
		gbCon.weightx = 0.75;
		gbCon.insets = new Insets(0,10,10,0);
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