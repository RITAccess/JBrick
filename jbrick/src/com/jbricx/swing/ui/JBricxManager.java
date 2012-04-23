package com.jbricx.swing.ui;

import javax.swing.JFrame;
import javax.swing.JSplitPane;

import com.jbricx.swing.ui.tabs.JBricxEditorTabFolder;
import com.jbricx.swing.ui.tabs.JBricxStatusPane;

//import com.jbricx.preferences.JBrickObserver;
//import com.jbricx.ui.tabs.TabFolder;



public interface JBricxManager {
	
	JFrame getShell();
	
	boolean close();
	
//	void registerObserver(JBrickObserver o);
		
	boolean isAutoCompile();

	JBricxEditorTabFolder getTabFolder();
	
	JBricxStatusPane getStatusPane();
	
	JSplitPane getSplitPane();

	void refreshExplorerContent();
	
}
