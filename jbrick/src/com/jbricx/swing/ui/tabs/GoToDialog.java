package com.jbricx.swing.ui.tabs;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;

import com.jbricx.swing.actions.GotoAction;

/**
 * Dialog box for jumping to a line.
 * 
 * @author Daniel Larsen
 *
 */
public class GoToDialog extends JDialog implements ActionListener{

	private JLabel goToInstruction;
	private JTextField goToLineInputBox;
	private JButton goToGoButton;
	private JButton goToCancelButton;
	private int maxLineNumber;
	private GotoAction action;
	
	/**
	 * Make the goto box
	 * @param maxLineNumber the current number of lines in the text area
	 * @param action Goto action (used for callback)
	 * @param shell main JFrame - used to keep modal
	 */
	public GoToDialog(int maxLineNumber,GotoAction action,JFrame shell ){
		super(shell,"Go To",true);
		this.setSize(new Dimension(212,145));
		this.action = action;
		this.maxLineNumber = maxLineNumber;
		JPanel panel = new JPanel(new BorderLayout(5,5));
		this.setLayout(new BorderLayout());
		panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); 
		
		goToInstruction = new JLabel("Enter a number between 1 and " + getLineNumbers());
		panel.add(goToInstruction,BorderLayout.NORTH);
		
		goToLineInputBox = new IntTextField(10);
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

	/**
	 * Custom text field that filters out any input except for numbers that fall in a valid range
	 *
	 */
	class IntTextField extends JTextField {
		  public IntTextField(int size) {
		    super("" + null, size);
		  }
		 
		  @Override
		protected Document createDefaultModel() {
		    return new IntTextDocument();
		  }
		 
		  @Override
		public boolean isValid() {
		    try {
		    	Integer.parseInt(getText());
		    	  return true; 

		    } catch (NumberFormatException e) {
		      return false;
		    } catch( NullPointerException e){
		    	return false;
		    }
		  }
		  
		  private boolean isThisValid(int input)throws Exception{
			  if(!(input < 1) && !(input > getLineNumbers())){
				return true;
			}else{
				throw new Exception();
			}
			  
		  }
		 

		  class IntTextDocument extends PlainDocument {
		    @Override
			public void insertString(int offs, String str, AttributeSet a)
		        throws BadLocationException {
		      if (str == null)
		        return;
		      String oldString = getText(0, getLength());
		      String newString = oldString.substring(0, offs) + str
		          + oldString.substring(offs);
		      try {
		        int result = Integer.parseInt(newString);
		        isThisValid(result);
		        super.insertString(offs, str, a);
		      } catch (NumberFormatException e) {		
		      } catch(Exception e){
		      }
		    }
		  }
		}

	/**
	 * Helper function to get the max line number so we can tell if we should allow certain input.
	 */
	public int getLineNumbers() {
		return maxLineNumber;
	}

	/**
	 * When Go is pressed.
	 */
	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getActionCommand().equals("go")){
			action.goTo(Integer.parseInt(goToLineInputBox.getText()));
			this.dispose();
		}else{
			this.dispose();
		}
		
	}
	
	
}
