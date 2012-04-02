package com.jbricx.swing.ui;

import javax.swing.JFrame;

import com.jbricx.swing.ui.tabs.JBricxEditorTabFolder;
import com.jbricx.swing.ui.tabs.JBricxStatusPane;

//import com.jbricx.preferences.JBrickObserver;
//import com.jbricx.ui.tabs.TabFolder;



public interface JBricxManager {
	
	JFrame getShell();
	
	boolean close();
	
	void refreshExplorerContent();
	
//	void registerObserver(JBrickObserver o);
	
	void updatePreferences();
	
	boolean isAutoCompile();

	JBricxEditorTabFolder getTabFolder();
	
	JBricxStatusPane getStatusPane();
	
}
