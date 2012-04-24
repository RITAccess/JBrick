package com.jbricx.swing.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.prefs.PreferenceChangeEvent;
import java.util.prefs.PreferenceChangeListener;
import java.util.prefs.Preferences;

import javax.swing.JFrame;
import javax.swing.JSplitPane;





import com.jbricx.swing.communications.NXTManager;
import com.jbricx.swing.ui.preferences.PreferenceStore;
import com.jbricx.swing.ui.tabs.JBricxEditorTabFolder;
import com.jbricx.swing.ui.tabs.JBricxFilePane;
import com.jbricx.swing.ui.tabs.JBricxStatusPane;
import com.jbricx.swing.ui.findbrick.FindBrickFileIO;
import com.jbricx.ui.tabs.JBrickTabItem;

@SuppressWarnings("serial")
public class MainWindow extends JFrame implements JBricxManager,PreferenceChangeListener,WindowListener  {

	Preferences prefs;
	
	JBricxEditorTabFolder editorPane;
	JBricxStatusPane statusPane;
	JBricxFilePane filePane;
	
	JSplitPane leftRightSplit;
	JSplitPane upDownSplit;
	
	/**
	 * Runs the application. Called by initial class
	 */
	public void run() {
		PreferenceStore prefClass = new PreferenceStore();
		prefs = prefClass.getPrefs();
		prefs.addPreferenceChangeListener(this);
		
		
		
		initMainWindow();
		
		
		if (NXTManager.isFantomDriverLoaded()) {
		      NXTManager.getInstance().connect(FindBrickFileIO.getCT());
		    } else {
		      // TODO: make the notification accessible!
		      System.out.println("MainWindow.MainWindow(): Fantom driver missing!");
		    }
		
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(this);
	}
	
	
	
	/**
	 * Configures settings of the main window and builds components.
	 */
	public void initMainWindow(){
		buildMenuAndToolbar();
		buildMainWindow();
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setSize((screenSize.width-screenSize.width/10),(screenSize.height-(screenSize.height/10)));
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		

	}
	  
	/**
	 * Builds main windows (File viewer, code window, and status window)
	 */
	private void buildMainWindow() {
		
		editorPane = new JBricxEditorTabFolder(this);
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
		
		if (getTabFolder().checkOverwrite()) {
				StringBuilder recentFiles = getTabFolder().getFileList();
		        prefs.put(PreferenceStore.RECENTFILES, recentFiles.toString());
		        this.dispose();
		  		NXTManager.getInstance().stopPolling();
		  		System.exit(0);
		  		return true;
		        }
		
		return false;
	}

	@Override
	public boolean isAutoCompile() {
		// TODO Auto-generated method stub
		return false;
	}

//
//	/**
//	 * Register observers
//	 */
//	public void registerObserver(final JBrickObserver observer) {
//		//TODO
//	  }
//
//	/**
//	 * Remove observers
//	 * @param observer to remove
//	 */
//	public void removeObserver(final JBrickObserver observer) {
//	    //TODO
//	}

	
	public JFrame getShell(){
		return this;
	}

	public JBricxStatusPane getStatusPane(){
		return statusPane;
	}

	@Override
	public JBricxEditorTabFolder getTabFolder() {
		return editorPane;
	}
	
	public JSplitPane getSplitPane(){
		return leftRightSplit;
	}


	/**
	 * Called by the listner whenever a property has changed.
	 */
	public void preferenceChange(PreferenceChangeEvent arg0) {
		editorPane.refreshTabItems();
		statusPane.refresh();	
	}
	
	
	@Override
	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void windowClosed(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void windowClosing(WindowEvent arg0) {
		close();
		
	}
	@Override
	public void windowDeactivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void windowDeiconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void windowIconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void windowOpened(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	public void refreshExplorerContent() {
		editorPane.refreshTabItems();
		statusPane.refresh();	
	}


}
