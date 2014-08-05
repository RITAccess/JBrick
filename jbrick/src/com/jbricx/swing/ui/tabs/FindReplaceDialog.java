package com.jbricx.swing.ui.tabs;

/*
 * FindReplaceDialog.java
 *
 */


// import all the necessary classes and interfaces

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.WindowConstants;

import com.jbricx.swing.ui.JBricxDialog;


/**
 * @author Pradeep Yadav
 * @author Melissa Young
 */
@SuppressWarnings("serial")
public class FindReplaceDialog extends JBricxDialog {

	// To help avoid multiple help windows
	static FindReplaceDialog findReplace = null;
	
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
	private JLabel accessibleStatus;

	/**
	* Constructor for find and replace dialog box
	*/
	private FindReplaceDialog(JFrame parent, boolean modal, JTextArea textArea) {
		super(parent, "Find and Replace",modal);
		this.textArea = textArea;
		setUpFindReplace();
	}
/**
 * Instantiate and organize all components for the pop up dialog window
 * Call the action listeners for the buttons 
 */
	private void setUpFindReplace() {
		
		findButton = new JButton("Find Next");
		replaceButton = new JButton("Replace");
		replaceAllButton = new JButton("Replace All");
		findTextField = new JTextField();
		replaceTextField = new JTextField();
		findLabel = new JLabel("Find : ");
		replaceLabel = new JLabel("Replace : ");
		accessibleStatus = new JLabel(" ");
		
		findTextField.getAccessibleContext().setAccessibleDescription("Find text field");
		replaceTextField.getAccessibleContext().setAccessibleDescription("Replace text field");
		
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

		// Basic set up for the dialog box
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		//setTitle("Find/Replace");
		setAlwaysOnTop(true);

		// Layout for the components in the dialog box
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
										.addComponent(replaceTextField, GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE))
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

		pack();
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
			accessibleStatus.setText("Find Field is Empty");
		}
		
		// Check for if the word doesn't exists
		else if (!(oldContent.contains(findTextField.getText()))) {
			accessibleStatus.setText("Cannot Find to Replace");
		}
		
		// If the word exist, it replaces all instances of the word with the user input
		else {
			accessibleStatus.setText(" ");
			textArea.setText(oldContent.replaceAll( findTextField.getText() , replaceTextField.getText()));
			
			int count = 0;
			int replaced = oldContent.indexOf(findTextField.getText());
			
			while (replaced != -1) {
				replaced = oldContent.indexOf(findTextField.getText(),replaced + 1);
				count ++;
			}
			
			// Send a message letting the user know how many instances there were to replace
			if (count > 0) {
				String instanceOf = count + " instances of " + findTextField.getText() + " have been replaced with " +  replaceTextField.getText();
				accessibleStatus.setText(instanceOf);
			}
		}
	}

	/**
	 * Replace the currently selected occurance of the text from the find field with text from the replace field
	 * @param evt
	 */
	private void replaceButtonAction(ActionEvent evt) {
		
		// Check for an empty find field
		if (findTextField.getText().isEmpty()) {
			accessibleStatus.setText("Find Field is Empty");
		} else {
			
			// Check for it the text exists in the main area
			if (textArea.getText().contains(findTextField.getText())) {
				String replaceWord = replaceTextField.getText();

				// If word is found, then it will be replaced and jump to the next instance of the word
				if (pos != -1) {
					textArea.replaceSelection(replaceWord);
					findButtonAction(evt);
				}
			} else {
				accessibleStatus.setText("Cannot Find to Replace");
			}
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
			accessibleStatus.setText("Find Field is Empty");
		} else {
			
			// Check if any instances of the text entered occurs in the main area
			if(context.contains(wordToFind)) {
				
					// Find the word entered into the field and highlight it
				if (currentPos < context.length()) {
					accessibleStatus.setText(" ");
					textArea.requestFocus();
					pos = context.indexOf( wordToFind, currentPos);
					
					// Check for if the position of the word is valid
						if (pos != -1) {
							textArea.select(pos, pos + wordToFind.length());
							currentPos = pos + 1;
						}
						// If the position is the last found text, it will return to the top found text
						else {
							currentPos = pos;
							pos = context.indexOf(wordToFind,currentPos);
							textArea.select(pos,pos + wordToFind.length());
							currentPos = pos + 1;
						}
					}
				} else {
					accessibleStatus.setText("Cannot Find");
			}
		}
	}
	
	/**
	 * Create one find and replace dialog and allow only one open while it is open
	 * @param parent
	 * @param modal
	 * @param textArea
	 */
	public static void openFindReplace(JFrame parent, boolean modal, JTextArea textArea) {
		if (findReplace == null) {
			findReplace = new FindReplaceDialog(parent,modal,textArea);
		}
		findReplace.requestFocus();
		findReplace.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent evt) {
				findReplace = null;
			}
		});
	}
}

