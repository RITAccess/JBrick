package com.jbricx.swing.ui.tabs;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.jbricx.swing.actions.GotoAction;


public class GoToDialog extends JDialog implements ActionListener{

	private JLabel goToInstruction;
	private JTextField goToLineInputBox;
	private JButton goToGoButton;
	private JButton goToCancelButton;
	private int maxLineNumber;
	private GotoAction action;
	
	public GoToDialog(int maxLineNumber,GotoAction action,JFrame shell ){
		super(shell,"Go To",true);
		this.setSize(new Dimension(400,150));
		this.action = action;
		this.maxLineNumber = maxLineNumber;
		JPanel panel = new JPanel(new BorderLayout(5,5));
		this.setLayout(new BorderLayout());
		panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); 
		
		goToInstruction = new JLabel("Enter a number between 1 and " + getLineNumbers());
		panel.add(goToInstruction,BorderLayout.NORTH);
		
		goToLineInputBox = new IntegerTextField();
		panel.add(goToLineInputBox,BorderLayout.CENTER);
		
		JPanel buttonBox = new JPanel();
		goToGoButton = new JButton("Go");
		goToGoButton.addActionListener(this);
		goToGoButton.setActionCommand("go");
		goToCancelButton = new JButton("Cancel");
		goToCancelButton.setActionCommand("cancel");
		goToCancelButton.addActionListener(this);
		buttonBox.add(goToGoButton);
		buttonBox.add(goToCancelButton);
		panel.add(buttonBox,BorderLayout.SOUTH);
		this.add(panel);
	}

	public class IntegerTextField extends JTextField {
		final static String badchars = "`~!-@#$%^&*()_+=\\|\"':;?/>.<, ";
		int numberEntered=0;
		
		public void processKeyEvent(KeyEvent ev) {
			char c = ev.getKeyChar();
			System.out.println("Why am I here");
			//System.out.println(this.getText()+ " + " + c + " = "+(Integer.parseInt(this.getText()+c) ));
			if ((Character.isLetter(c) && !ev.isAltDown())
					|| badchars.indexOf(c) > -1) {
				// 
				ev.consume();
				return;
			}
//			if (getDocument().getLength() > 0) {
//				ev.consume();
//			} else {
				addToNumberCount(ev.getKeyChar());
				super.processKeyEvent(ev);
//			}
		}

		private void addToNumberCount(char keyChar) {
			if(Character.isDigit(keyChar)){
				numberEntered= Integer.parseInt(numberEntered+Character.toString(keyChar));
			}
			
		}
	}

	/**
	 * 
	 * @return the number of current line numbers.
	 */
	public int getLineNumbers() {
		return maxLineNumber;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getActionCommand().equals("go")){
			action.goTo(Integer.parseInt(goToLineInputBox.getText()));
		}else{
			this.dispose();
		}
		
	}
	
	
}
