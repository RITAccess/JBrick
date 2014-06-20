package com.jbricx.piano;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.Hashtable;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;

public class OctaveChange{
	
	private JPanel transPanel;
	private JLabel transpose;
	private JSlider transposer;
	
	static final int OCTAVE_LOW = 1;
	static final int OCTAVE_MIDLOW = 2;
	static final int OCTAVE_HIGH = 5;
	static final int OCTAVE_MIDHIGH = 4;
	static final int OCTAVE_DEFAULT = 3;
	
	/**
	 * Constructor for transposer 
	 */
	
	public OctaveChange() {
		
		transPanel = new JPanel(new GridBagLayout());
		transpose = new JLabel("Transposer");
		transposer = new JSlider(JSlider.HORIZONTAL,OCTAVE_LOW,OCTAVE_HIGH,OCTAVE_DEFAULT);
		
		Hashtable<Integer,JLabel> transLabels =
				new Hashtable<Integer,JLabel>();
		transLabels.put(new Integer(OCTAVE_LOW), new JLabel("1st Octave"));
		transLabels.put(new Integer(OCTAVE_MIDLOW), new JLabel("2nd Octave"));
		transLabels.put(new Integer(OCTAVE_DEFAULT), new JLabel("3nd Octave"));
		transLabels.put(new Integer(OCTAVE_MIDHIGH), new JLabel("4th Octave"));
		transLabels.put(new Integer(OCTAVE_HIGH), new JLabel("5th Octave"));
		
		transposer.setLabelTable(transLabels);
		transposer.setPaintLabels(true);
		transposer.setSnapToTicks(true);
		
	}
	/**
	 * Add the title and the transposer slider to a panel
	 * 
	 * @return transposer panel
	 */
	public JPanel setUpTransposer() {
		
		GridBagConstraints gbCon = new GridBagConstraints();
		gbCon.ipadx = 100;
		gbCon.ipady = 200;
		gbCon.weightx = 0.75;
		gbCon.weighty = 0.75;
		
		gbCon.gridx = 0;
		gbCon.gridy = 1;
		gbCon.ipady = 50;
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