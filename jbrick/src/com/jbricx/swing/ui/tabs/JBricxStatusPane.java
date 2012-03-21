package com.jbricx.swing.ui.tabs;

import javax.swing.JEditorPane;
import javax.swing.JTabbedPane;

public class JBricxStatusPane extends JTabbedPane {
	
	public JBricxStatusPane(){
		JEditorPane test = new JEditorPane();
		test.setEnabled(false);
		
		this.addTab("Status", test);
	}

}
