package com.jbricx.piano;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Enumeration;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JFormattedTextField;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;


public class NoteLengths {

	private static JFormattedTextField noteField;
	private ButtonGroup buttonGroup;
	private JPanel radioPanel;
	private Border radioBorder;
	GridBagConstraints gbCon = new GridBagConstraints();
	private static String selectedValue;
	private static String customString = "Custom Note/Rest Time: ";
	
	/**
	 * Constructor for the radio buttons and the custom text fields
	 */
	public NoteLengths() {
		
		radioPanel = new JPanel(new GridBagLayout());
		noteField = new JFormattedTextField();
		noteField.setColumns(3);
		noteField.setEditable(false);
		noteField.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent ev) {
				char c = ev.getKeyChar();
				if(!("0123456789/".contains("" + c) || c == KeyEvent.VK_BACK_SPACE)) {
					ev.consume();
				}
			}
		});
		noteField.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void changedUpdate(DocumentEvent ev) {
				checkUpdate(ev);
				
			}

			@Override
			public void insertUpdate(DocumentEvent ev) {
				checkUpdate(ev);
				
			}

			@Override
			public void removeUpdate(DocumentEvent ev) {
				checkUpdate(ev);
			}
			
			private void checkUpdate(DocumentEvent docEv) {
				DocumentEvent.EventType type = docEv.getType();
				if (type.equals(DocumentEvent.EventType.INSERT)) {
					checkInput();
				}
				else if(type.equals(DocumentEvent.EventType.CHANGE)) {
					checkInput();
				}
				else if(type.equals(DocumentEvent.EventType.REMOVE)) {
					checkInput();
				}
			}
			
		});
		buttonGroup = new ButtonGroup();
		String[] lengthStrs = {"1/1", "1/2", "1/3", "1/4", "1/8", "1/16", customString};
		for (int i = 0; i < lengthStrs.length; i++){
			JRadioButton button = new JRadioButton(lengthStrs[i]);
			if (i == 3) { button.setSelected(true); NoteLengths.setValue(button.getText());}
			button.addActionListener(new ActionListener(){
				
				String text;
				
				public ActionListener setValues(String text){
					this.text = text;
					return this;
				}

				@Override
				public void actionPerformed(ActionEvent arg0) {
					NoteLengths.setValue(text);
				}
				
			}.setValues(button.getText()));
			buttonGroup.add(button);
		}
		radioBorder = new EtchedBorder();
	}
	
	private static void checkInput() {
		NoteLengths.selectedValue = noteField.getText();
		String checkInput = noteField.getText();
		//No change
		if(checkInput.matches("^[0-9]+(/[0-9]+)?$")) {
			if (!(checkInput.contains("/"))) {
				NoteLengths.selectedValue = noteField.getText() + "/1";
			}
		} else {
			NoteLengths.selectedValue = "1/4";
		}
		
		
	}
	
	public static void setValue(String text){
		if (text.equals(customString)){
			noteField.setEditable(true);
			checkInput();
			
		} else {
			NoteLengths.selectedValue = text;
			noteField.setEditable(false);
		}
	}
	
	public String getValue(){
		return NoteLengths.selectedValue;
	}
	
	public String getCurrent(){
		for (Enumeration<AbstractButton> buttons = buttonGroup.getElements(); buttons.hasMoreElements();){
			AbstractButton button = buttons.nextElement();
			if (button.isSelected()){
				return button.getText();
			}
		}
		return "";
	}
	
	private void insertRadioButton(JComponent noteButton,int xPlace,int yPlace) {
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
		int y = 0, x = 0;
		for (Enumeration<AbstractButton> buttons = buttonGroup.getElements(); buttons.hasMoreElements();){
			AbstractButton button = buttons.nextElement();
			insertRadioButton(button, x, y);
			if (x == 0) { x++; } else
			if (x == 1) { y++; x--; }
		}
		insertRadioButton(noteField, x, y);
		radioPanel.setBorder(BorderFactory.createTitledBorder(radioBorder,"Length"));
		return radioPanel;
	}

}
