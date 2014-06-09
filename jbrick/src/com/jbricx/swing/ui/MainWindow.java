package com.jbricx.swing.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.prefs.Preferences;

import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JSplitPane;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.WindowConstants;

import com.apple.eawt.AppEvent.QuitEvent;
import com.apple.eawt.Application;
import com.apple.eawt.QuitHandler;
import com.apple.eawt.QuitResponse;
import com.jbricx.swing.communications.NXTManager;
import com.jbricx.swing.ui.preferences.PreferenceStore;
import com.jbricx.swing.ui.tabs.JBricxEditorTabFolder;
import com.jbricx.swing.ui.tabs.JBricxFilePane;
import com.jbricx.swing.ui.tabs.JBricxStatusPane;
import com.jbricx.swing.ui.findbrick.FindBrickFileIO;
import com.sun.jna.Platform;

@SuppressWarnings("serial")
public class MainWindow extends JFrame implements JBricxManager,WindowListener  {

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
		new PreferenceStore();
		prefs = PreferenceStore.getPrefs();		
		
		initMainWindow();
		
		
		if (NXTManager.isFantomDriverLoaded()) {
		      NXTManager.getInstance().connect(FindBrickFileIO.getCT());
		    } else {
		      // TODO: make the notification accessible!
		    	if(!System.getProperty("os.arch").equalsIgnoreCase("i386") && !System.getProperty("os.arch").equalsIgnoreCase("x86"))
		    	{
		    		JOptionPane.showMessageDialog(null, "Fantom driver missing!\n Try running with a 32-bit version of Java");
		    	}
		    	else
		    		JOptionPane.showMessageDialog(null, "Fantom driver missing!");
		    }
		
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		addWindowListener(this);
		
		// Set on close operation for mac client (command + q / Jbricks -> quit)
		if (Platform.isMac()){
			Application macApp = Application.getApplication();
			macApp.setQuitHandler(new QuitHandler(){
				
				MainWindow mw;
				
				/**
				 * set main window - allows main window to be defined
				 * @param mw
				 * @return quit handler with main window defined
				 */
				public QuitHandler setMainWindow(MainWindow mw){
					this.mw = mw;
					return this;
				}

				@Override
				public void handleQuitRequestWith(QuitEvent qe, QuitResponse qr) {
					mw.beforeCloseActions();
					qr.performQuit();
				}
			}.setMainWindow(this));
		}
	}
	
	/**
	 * Run the application. Called by initial class. 
	 * Does not check for Fantom driver
	 * 
	 * for testing only
	 */
	public void runNoFantom() {
		new PreferenceStore();
		prefs = PreferenceStore.getPrefs();
		
		initMainWindow();
		
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		addWindowListener(this);
	}
	
	
	/**
	 * Configures settings of the main window and builds components.
	 */
	public void initMainWindow(){
		buildMenuAndToolbar();
		buildMainWindow();
		setupEnterActionForAllButtons();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setSize((screenSize.width-screenSize.width/10),(screenSize.height-(screenSize.height/10)));
		this.setVisible(true);
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
	}
	  
	/**
	 * Builds main windows (File viewer, code window, and status window)
	 */
	private void buildMainWindow() {
		
		editorPane = new JBricxEditorTabFolder(this);
		statusPane = new JBricxStatusPane(this);
		filePane = new JBricxFilePane(this);				
		
		//Contains the main Editor component, and the status component
		upDownSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT,true,editorPane,statusPane);
		upDownSplit.setOneTouchExpandable(true);
		upDownSplit.setResizeWeight(.7);
		upDownSplit.setMinimumSize(new Dimension(0,0));
		upDownSplit.setDividerSize(10);
		upDownSplit.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke(KeyEvent.VK_F6, 0), "none");
		
		//Contains the file viewer and the other JSplitpane which contains the editor and status panes
		leftRightSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, filePane,upDownSplit);
		leftRightSplit.setOneTouchExpandable(true);
		leftRightSplit.setResizeWeight(.5);
		leftRightSplit.setDividerLocation(250);		
		this.add(leftRightSplit);
		//Removes file pane.
		leftRightSplit.remove(filePane);
		leftRightSplit.setDividerSize(0);
		leftRightSplit.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke(KeyEvent.VK_F6, 0), "none");
	}

	/**
	 * Kinda hackey to allow buttons to work with enter press. Overwrides lookandfeel.
	 */
	private void setupEnterActionForAllButtons() {
        InputMap im = (InputMap) UIManager.getDefaults().get("Button.focusInputMap");
        Object pressedAction = im.get(KeyStroke.getKeyStroke("pressed SPACE"));
        Object releasedAction = im.get(KeyStroke.getKeyStroke("released SPACE"));

        im.put(KeyStroke.getKeyStroke("pressed ENTER"), pressedAction);
        im.put(KeyStroke.getKeyStroke("released ENTER"), releasedAction);
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
	public void close() {
		beforeCloseActions();
		this.dispose();
		NXTManager.getInstance().stopPolling();
	}
	
	/**
	 * operations to be called before closed event
	 * 
	 * operation to be run on exit or close of application
	 */
	public void beforeCloseActions() {
		// Save recent files
		if (getTabFolder().checkOverwrite()) {
			StringBuilder recentFiles = getTabFolder().getFileList();
			prefs.put(PreferenceStore.RECENTFILES, recentFiles.toString());
		}
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
	
	public JBricxFilePane getFilePane(){
		return filePane;
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

	public void openTab(String FilePath) {
		editorPane.open(FilePath);
	}

	public void updatePreferences() {
		editorPane.refreshTabItems();
		statusPane.refresh();
	}
	
	/**
	 * Clears which tabs have been open in recent history
	 * Should only be used in debugging and testing
	 */
	public void resetTabPreferences(){
		new PreferenceStore();
		prefs = PreferenceStore.getPrefs();
		prefs.put(PreferenceStore.RECENTFILES, "");
	}

	public void refreshExplorerContent() {
		editorPane.refreshTabTitles();
	}

}
