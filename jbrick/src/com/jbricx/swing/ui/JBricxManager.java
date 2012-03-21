package com.jbricx.swing.ui;

import javax.swing.JFrame;

import com.jbricx.preferences.JBrickObserver;
import com.jbricx.ui.tabs.TabFolder;



public interface JBricxManager {
	
	JFrame getShell();
	
	boolean close();
	 
	TabFolder getTabFolder();
	
	void refreshExplorerContent();
	
	void registerObserver(JBrickObserver o);
	
	void updatePreferences();
	
	boolean isAutoCompile();
	
}
