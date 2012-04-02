package com.jbricx.swing.ui.tabs;

import javax.swing.JEditorPane;
import javax.swing.JTabbedPane;

public class JBricxStatusPane extends JTabbedPane {
	JEditorPane messagePane;
	
	public JBricxStatusPane(){
		messagePane = new JEditorPane();
		messagePane.setEnabled(false);
		
		this.addTab("Status", messagePane);
	}

	/**
	 * Proposed function for pushing a message to the console. 
	 * Not tested yet how to put links from compiler errors to jump to line. 
	 * @param message Message to **append** to the current text.
	 */
	public void pushMessage(String message){
		messagePane.setText(messagePane.getText()+"\n" + message);
	}
}
