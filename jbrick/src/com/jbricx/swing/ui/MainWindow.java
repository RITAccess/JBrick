package com.jbricx.swing.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.prefs.Preferences;

import javax.swing.JFrame;
import javax.swing.JSplitPane;

import com.jbricx.preferences.JBrickObserver;
import com.jbricx.swing.ui.preferences.PreferenceStore;
import com.jbricx.swing.ui.tabs.JBricxEditorPane;
import com.jbricx.swing.ui.tabs.JBricxFilePane;
import com.jbricx.swing.ui.tabs.JBricxStatusPane;
import com.jbricx.ui.tabs.TabFolder;

public class MainWindow extends JFrame implements JBricxManager  {

	Preferences prefs;
	
	JBricxEditorPane editorPane;
	JBricxStatusPane statusPane;
	JBricxFilePane filePane;
	
	JSplitPane leftRightSplit;
	JSplitPane upDownSplit;
	
	/**
	 * Runs the application. Called by initial class
	 */
	public void run() {
		initMainWindow();
		PreferenceStore prefClass = new PreferenceStore();
		prefs = prefClass.getPrefs();
		System.out.println(prefs.getBoolean(PreferenceStore.WRAP, false));
	}
	
	
	/**
	 * Configures settings of the main window and builds components.
	 */
	public void initMainWindow(){
		buildMenuAndToolbar();
		buildMainWindow();
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		//this.setLayout(new BorderLayout());
		this.setSize((screenSize.width-screenSize.width/10),(screenSize.height-(screenSize.height/10)));
		this.setVisible(true);
	}
	  
	/**
	 * Builds main windows (File viewer, code window, and status window)
	 */
	private void buildMainWindow() {
		
		editorPane = new JBricxEditorPane();
		statusPane = new JBricxStatusPane();
		filePane = new JBricxFilePane();
		
		//Contains the main Editor component, and the status component
		upDownSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT,true,editorPane,statusPane);
		upDownSplit.setOneTouchExpandable(true);
		upDownSplit.setResizeWeight(.7);
		upDownSplit.setMinimumSize(new Dimension(0,0));
		
		//Contains the file viewer and the other JSplitpane which contains the editor and status panes
		leftRightSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, filePane,upDownSplit);
		leftRightSplit.setOneTouchExpandable(true);
		leftRightSplit.setResizeWeight(.05);
		
		this.add(leftRightSplit);
	}



	/**
	 * builds menu and toolbar from the delegate
	 */
	public void buildMenuAndToolbar(){
		JBricxMenuAndToolBarDelegate menuAndToolBar = new JBricxMenuAndToolBarDelegate(this);
		this.setJMenuBar(menuAndToolBar.getMenuBar());
		this.add(menuAndToolBar.getToolBar(),BorderLayout.PAGE_START);
		
	}
	
	
	/**
	 * Closing logic
	 */
	public boolean close() {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * Returns the tab folder
	 */
	public TabFolder getTabFolder() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isAutoCompile() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void refreshExplorerContent() {
		// TODO Auto-generated method stub
		
	}


	/**
	 * Register observers
	 */
	public void registerObserver(final JBrickObserver observer) {
		//TODO
	  }

	/**
	 * Remove observers
	 * @param observer to remove
	 */
	public void removeObserver(final JBrickObserver observer) {
	    //TODO
	}

	/**
	 * Update any preference changes and resize objects if nessecary
	 */
	public void updatePreferences() {
		// TODO Auto-generated method stub
		
	}
	
	public JFrame getShell(){
		return this;
	}


}
