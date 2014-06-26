package com.jbricx.piano;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
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

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

public class NoteLengths extends NotesView {

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
	private Border radioBorder;
	NotesView noteText = new NotesView();
	private String currentLength;
	
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

		radioBorder = new EtchedBorder();
		
	}
	
	/**
	 * Checks for if a textarea has been selected 
	 * If yes, any radio buttons are deselected 
	 */
	private void selectNote(JTextField textNote) {
		textNote.addFocusListener(new FocusListener() {
			
			public void focusGained(FocusEvent checkCursor) {
				
				firsts.setEnabled(false);
				firsts.setSelected(false);
				seconds.setEnabled(false);
				seconds.setSelected(false);
				thirds.setEnabled(false);
				thirds.setSelected(false);
				fourths.setEnabled(false);
				fourths.setSelected(false);
				eighths.setEnabled(false);
				eighths.setSelected(false);
				sixteenths.setEnabled(false);
				sixteenths.setSelected(false);
				
				noteField.setEditable(true);
				waitField.setEditable(true);
				
			}
			
			public void focusLost(FocusEvent noCursor) {
				
				radioPanel.addMouseListener(new MouseAdapter() {

					public void mouseClicked(MouseEvent e) {
						// TODO Auto-generated method stub
						radioPanel = (JPanel) e.getSource();
				
					}
					
				});
				noteField.setEditable(false);
				waitField.setEditable(false);
			}
		});
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
		for (JRadioButton button: rbList) {
			button.addActionListener(new RadioAction(this, button.getText()));
		}
	
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
		
		gbCon.insets = new Insets(0,0,30,5);
		gbCon.weighty = 0.25;
		gbCon.gridx = xPlace;
		gbCon.gridy = yPlace;
		gbCon.anchor = GridBagConstraints.WEST;
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
	
	public void setCurrent(String length){
		this.currentLength = length;
	}
	
	public String getCurrent(){
		return this.currentLength;
	}
	
	/**
	 * Add the radio buttons and their title to a panel
	 * 
	 * @return radio note length panel
	 */
	public JPanel noteLengthPanel(){
		
		radioPanel.add(firsts);
		radioPanel.add(seconds);
		radioPanel.add(thirds);
		radioPanel.add(fourths);
		radioPanel.add(eighths);
		radioPanel.add(sixteenths);
		radioPanel.add(customLengthPanel);
		radioPanel.setBorder(BorderFactory.createTitledBorder(radioBorder,"Length"));
		selectNote(noteField);
		selectNote(waitField);
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