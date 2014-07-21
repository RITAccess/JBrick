package com.jbricx.swing.ui.tabs;

/*
 * FindReplaceDialog.java
 *
 */


// import all the necessary classes and interfaces

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.WindowConstants;

import com.jbricx.tools.AccessibleWidget;


/**
 * Modified for JBricx
 * @author Pradeep Yadav
 */
@SuppressWarnings("serial")
public class FindReplaceDialog extends JDialog {

	private JTextArea textArea;
	private int currentPos;
	private int pos;
	private JButton findButton;
	private JButton replaceButton;
	private JButton replaceAllButton;
	private JTextField findTextField;
	private JTextField replaceTextField;
	private JLabel findLabel;
	private JLabel replaceLabel;
	private AccessibleWidget accessibleStatus;

	/**
	* Constructor for find and replace dialog box
	*/
	public FindReplaceDialog(JFrame parent, boolean modal, JTextArea textArea) {
		super(parent,modal);
		this.textArea = textArea;
		setUpFindReplace();
	}

	private void setUpFindReplace() {
		findButton = new JButton("Find Next");
		replaceButton = new JButton("Replace");
		replaceAllButton = new JButton("Replace All");
		findTextField = new JTextField();
		replaceTextField = new JTextField();
		findLabel = new JLabel("Find : ");
		replaceLabel = new JLabel("Replace : ");
		accessibleStatus = new AccessibleWidget(" ");

		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("Find/Replace");
		setModal(true);

		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(
				layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
						.addGap(14, 14, 14)
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
								.addComponent(replaceLabel)
								.addComponent(findLabel, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
										.addComponent(findTextField)
										.addComponent(accessibleStatus)
										.addComponent(replaceTextField, GroupLayout.DEFAULT_SIZE, 196, Short.MAX_VALUE))
										.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
												.addGroup(layout.createSequentialGroup()
														.addGap(8, 8, 8)
														.addComponent(findButton, GroupLayout.DEFAULT_SIZE, 96, Short.MAX_VALUE))
														.addGroup(layout.createSequentialGroup()
																.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
																.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
																		.addComponent(replaceAllButton, GroupLayout.DEFAULT_SIZE, 98, Short.MAX_VALUE)
																		.addComponent(replaceButton, GroupLayout.PREFERRED_SIZE, 98, Short.MAX_VALUE))))
																		.addContainerGap())
		);
		layout.setVerticalGroup(
				layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
						.addContainerGap()
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(findTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(findButton)
								.addComponent(findLabel))
								
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
										.addComponent(replaceLabel)
										.addComponent(replaceTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(replaceButton))
										
										.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
												.addComponent(accessibleStatus)
												.addComponent(replaceAllButton))
												.addContainerGap())
		);

		findButton.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent evt) {
				findButtonAction(evt);
			}
		});

		replaceButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				replaceButtonAction(evt);
			}
		});

		replaceAllButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				replaceAllAction(evt);
			}
		});

		findTextField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent evt) {
				findTextKeyAction(evt);
			}
		});

		pack();
	}

	private void findTextKeyAction(KeyEvent evt) {
		currentPos = 0;
	}

	/**
	 * Take text from find field, find all occurances in the main area, and replace them all with the text from the replace field
	 * @param evt
	 */
	private void replaceAllAction(ActionEvent evt) {
		String oldContent = textArea.getText();
		
		// Check if the find text field is empty
		// Replace should be able to remain empty in case you want to delete all occurances of that word
		if (findTextField.getText().isEmpty()) {
			accessibleStatus.readLabel("One or more fields is missing", FindReplaceDialog.this.replaceAllButton);
		}
		
		// Check for if the word doesn't exists
		else if (pos == -1) {
			accessibleStatus.readLabel("Cannot Find to Replace",FindReplaceDialog.this.replaceAllButton);
		}
		
		else {
			accessibleStatus.readLabel(" ", FindReplaceDialog.this.replaceAllButton);
			String newContent = oldContent.replaceAll( findTextField.getText() , replaceTextField.getText() );
			textArea.setText( newContent );
			
			int count = 0;
			int replaced = textArea.getText().indexOf(replaceTextField.getText());
			
			while (replaced != -1) {
				replaced = textArea.getText().indexOf(replaceTextField.getText(),replaced + 1);
				count ++;
			}
			if (count > 0) {
				String instanceOf = count + " instances of " + findTextField.getText() + " have been replaced with " +  replaceTextField.getText();
				accessibleStatus.readLabel(instanceOf, FindReplaceDialog.this.replaceAllButton);
			}
		}
	}

	/**
	 * Replace the currently selected occurance of the text from the find field with text from the replace field
	 * @param evt
	 */
	private void replaceButtonAction(ActionEvent evt) {
		if ( !findTextField.getText().isEmpty() &&
				!replaceTextField.getText().isEmpty() &&
					!(textArea.getSelectedText()== null))
		{

			String replaceWord = replaceTextField.getText();

			if ( pos != -1 )
			{
				
				textArea.replaceSelection( replaceWord );
			}
		} else {
			accessibleStatus.readLabel("Cannot Find to Replace", FindReplaceDialog.this.replaceButton);
		}
	}

	/** 
	 * Search for an occurance of the text entered into the find field
	 * @param evt
	 */
	private void findButtonAction(ActionEvent evt) {
		String wordToFind = findTextField.getText();
		String context = textArea.getText();

		// Check if anything to search was entered
		if (wordToFind.isEmpty()) {
			accessibleStatus.readLabel("Field is missing", FindReplaceDialog.this.findButton);
		} else {
			// Find the word entered into the field and highlight it
			if (currentPos < context.length()) {
				accessibleStatus.readLabel(" ", FindReplaceDialog.this.findButton);
				textArea.requestFocusInWindow();
				pos = context.indexOf( wordToFind, currentPos);
			
				// Check for if the position of the word is valid
				if (pos != -1) {
					textArea.select(pos, pos + wordToFind.length());
					textArea.getSelectedText();
					currentPos = pos + 1;
				} else {
					accessibleStatus.readLabel("Cannot Find",FindReplaceDialog.this.findButton);
				}
			} 
			else if (currentPos >= context.length()) {
				currentPos = 0;
			}
		}
	}
}

