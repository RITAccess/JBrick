package com.jbricx.swing.ui.tabs;

/*
 * FindReplaceDialog.java
 *
 */


// import all the necessary classes and interfaces
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

/**
 * Modified for JBricx
 * @author Pradeep Yadav
 */
public class FindReplaceDialog extends javax.swing.JDialog
{
	// the variable declaration begins here.
	private JTextArea textArea;
	private int currentPos = 0;    
	private int pos = -1;
	private javax.swing.JButton findButton;
	private javax.swing.JLabel findLabel;
	private javax.swing.JTextField findTextField;
	private javax.swing.JButton replaceAllButton;
	private javax.swing.JButton replaceButton;
	private javax.swing.JLabel replaceLabel;
	private javax.swing.JTextField replaceTextField;
	// End of variable declaration.





	/** This constructor creates new form FindReplaceDialog */
	public FindReplaceDialog(java.awt.Frame parent, boolean modal , JTextArea textArea )
	{
		super(parent, modal);
		this.textArea = textArea;
		initComponents();
	}

	//The initComponents() method is called from within the constructor  for initialization of the form.
	//The content of this method is always regenerated by the Form Editor.
	// The method initComponents() begins here.
	private void initComponents()
	{
		findButton = new javax.swing.JButton();
		replaceButton = new javax.swing.JButton();
		replaceAllButton = new javax.swing.JButton();
		replaceTextField = new javax.swing.JTextField();
		findTextField = new javax.swing.JTextField();
		replaceLabel = new javax.swing.JLabel();
		findLabel = new javax.swing.JLabel();

		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("Find/Replace");
		setModal(true);
		findButton.setText("Find Next");
		findButton.addActionListener(new java.awt.event.ActionListener()
		{
			public void actionPerformed(java.awt.event.ActionEvent evt)
			{
				findButtonActionPerformed(evt);
			}
		});

		replaceButton.setText("Replace");
		replaceButton.addActionListener(new java.awt.event.ActionListener()
		{
			public void actionPerformed(java.awt.event.ActionEvent evt)
			{
				replaceButtonActionPerformed(evt);
			}
		});

		replaceAllButton.setText("Replace All");
		replaceAllButton.addActionListener(new java.awt.event.ActionListener()
		{
			public void actionPerformed(java.awt.event.ActionEvent evt)
			{
				replaceAllButtonActionPerformed(evt);
			}
		});

		findTextField.addKeyListener(new java.awt.event.KeyAdapter()
		{
			@Override
			public void keyReleased(java.awt.event.KeyEvent evt)
			{
				findTextFieldKeyReleased(evt);
			}
		});

		replaceLabel.setText("Replace With :");

		findLabel.setText("Find :");

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
						.addGap(14, 14, 14)
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addComponent(replaceLabel)
								.addComponent(findLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
										.addComponent(findTextField)
										.addComponent(replaceTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 196, Short.MAX_VALUE))
										.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
												.addGroup(layout.createSequentialGroup()
														.addGap(8, 8, 8)
														.addComponent(findButton, javax.swing.GroupLayout.DEFAULT_SIZE, 96, Short.MAX_VALUE))
														.addGroup(layout.createSequentialGroup()
																.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
																		.addComponent(replaceAllButton, javax.swing.GroupLayout.DEFAULT_SIZE, 98, Short.MAX_VALUE)
																		.addComponent(replaceButton, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))))
																		.addContainerGap())
		);
		layout.setVerticalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
						.addContainerGap()
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(findTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(findButton)
								.addComponent(findLabel))
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
										.addComponent(replaceLabel)
										.addComponent(replaceTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(replaceButton))
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(replaceAllButton)
										.addContainerGap())
		);
		pack();
	}// The method initComponents() ends here.

	private void findTextFieldKeyReleased(java.awt.event.KeyEvent evt)
	{
		currentPos = 0;
	}


	//This function replaces all the occurence of the particular string(word) by the new required string(word)
	//that is passed.
	private void replaceAllButtonActionPerformed(java.awt.event.ActionEvent evt)
	{
		String oldContent = textArea.getText();
		String newContent = oldContent.replaceAll( findTextField.getText() , replaceTextField.getText() );
		textArea.setText( newContent );
	}



	//This function replaces a word that has been currently selected by a new required word.
	private void replaceButtonActionPerformed(java.awt.event.ActionEvent evt)
	{
		if ( !findTextField.getText().isEmpty() &&
				!replaceTextField.getText().isEmpty() &&
					!(textArea.getSelectedText()== null))
		{
			System.out.println(!(textArea.getSelectedText()== null));

			String replaceWord = replaceTextField.getText();

			if ( pos != -1 )
			{
				
				textArea.replaceSelection( replaceWord );
			}
		}
	}




	private void findButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_findButtonActionPerformed
	{
		//If the word to be searched is not there then simply return
		if ( findTextField.getText().isEmpty() )
			return;

		// Get the text from the text area.
		String context = textArea.getText();

		//If the end of the string is reached then simply return
		if ( currentPos > context.length() )
			return;

		//The word entered by the user is the word that needs to be found and this word is stored in the string wordToFind. 
		String wordToFind = findTextField.getText();

		// find the  word entered by the user in context from the current position
		pos = context.indexOf( wordToFind , currentPos );

		// This block below checks whether the word is found or not.
		//If the word is found then the value of pos is a nonnegative value as shown below .
		if ( pos != -1 )
		{
			// The word that has been found is highlighted in the text area.
			textArea.setSelectionStart( pos );
			textArea.setSelectionEnd( pos + wordToFind.length() );
			textArea.requestFocusInWindow();
			//Now after the word has been found in order that the user should find the next occurrence of 
			//the given word increment the value of currentPos by 1 so that the search continues from next position
			//and not from beginning when the user clicks the Next Button.
			currentPos = pos + 1;
		}
		else
		{
			//If the word to be found is not found then display the message given below.
			JOptionPane.showMessageDialog( this , "Can Not Find \"" + wordToFind + "\"" );
		}
	}



}