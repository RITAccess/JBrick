package com.jbricx.piano;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

public class NoteLengths extends NotesView {

	private JTextField noteField;
	private JRadioButton firsts;
	private JRadioButton seconds;
	private JRadioButton thirds;
	private JRadioButton fourths;
	private JRadioButton eighths;
	private JRadioButton sixteenths;
	private JRadioButton custom;
	private ButtonGroup noteLength;
	private JPanel radioPanel;
	private Border radioBorder;
	NotesView noteText = new NotesView();
	private String currentLength;
	GridBagConstraints gbCon = new GridBagConstraints();
	ButtonActions align = new ButtonActions();
	
	/**
	 * Constructor for the radio buttons and the custom text fields
	 */
	public NoteLengths() {
		
		radioPanel = new JPanel(new GridBagLayout());
		noteField = new JTextField(3);
		
		noteLength = new ButtonGroup();
		firsts = new JRadioButton("1/1");
		firsts.setSelected(true);
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
		custom = new JRadioButton("Custom Note/Rest Time: ");
		noteLength.add(custom);
		radioBorder = new EtchedBorder();
		
	}
	
	/**
	 * Tell noteReader to print out the name of the button to its display
	 * @return the string value of the radio button selected to the NotesView
	 *
	 */
	
	public void setButtonValue() {
		
		ArrayList<JRadioButton> rbList = new ArrayList<JRadioButton>();
		rbList.add(firsts);
		rbList.add(seconds);
		rbList.add(thirds);
		rbList.add(fourths);
		rbList.add(eighths);
		rbList.add(sixteenths);
		rbList.add(custom);
		for (JRadioButton button: rbList) {
			button.addActionListener(new RadioAction(this, button.getText()));
		}
	
	}
	
	public void setCurrent(String length){
		this.currentLength = length;
	}
	
	public String getCurrent(){
		return this.currentLength;
	}
	
	private void radioPanel(JComponent noteButton,int xPlace,int yPlace) {
		gbCon.insets = new Insets(10,0,10,0);
		gbCon.anchor = GridBagConstraints.WEST;
		gbCon.gridx = xPlace;
		gbCon.gridy = yPlace;
		radioPanel.add(noteButton,gbCon);

	}
	
	/** 
	 * Add the radio buttons and their title to a panel
	 * 
	 * @return radio note length panel
	 */
	public JPanel noteLengthPanel(){
		
		radioPanel(firsts,0,0);
		radioPanel(seconds,1,0);
		radioPanel(thirds,0,1);
		radioPanel(fourths,1,1);
		radioPanel(eighths,0,2);
		radioPanel(sixteenths,1,2);
		radioPanel(custom,0,3);
		radioPanel(noteField,1,3);
		radioPanel.setBorder(BorderFactory.createTitledBorder(radioBorder,"Length"));
		radioPanel.setSize(align.setUpBPanel().getSize());
		return radioPanel;
	}

}

class RadioAction implements ActionListener{	
	private NoteLengths nl;
	private String length;

	public RadioAction(NoteLengths nl, String length){
		this.nl = nl;
		this.length = length;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		nl.setCurrent(length);
	}
}