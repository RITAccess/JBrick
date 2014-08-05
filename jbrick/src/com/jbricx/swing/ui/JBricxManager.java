package com.jbricx.swing.ui;

import javax.swing.JFrame;
import javax.swing.JSplitPane;

import com.jbricx.swing.ui.tabs.JBricxEditorTabFolder;
import com.jbricx.swing.ui.tabs.JBricxFilePane;
import com.jbricx.swing.ui.tabs.JBricxStatusPane;

public interface JBricxManager {
	
	JFrame getShell();
	
	void close();
		
	boolean isAutoCompile();

	JBricxEditorTabFolder getTabFolder();
	
	JBricxStatusPane getStatusPane();
	
	JSplitPane getSplitPane();
	
	JBricxFilePane getFilePane();

	void refreshExplorerContent();
	
	void openTab(String FilePath);

	void updatePreferences();
}
