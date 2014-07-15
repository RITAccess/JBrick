package com.jbricx.swing.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.prefs.Preferences;

import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JSplitPane;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.WindowConstants;

import com.apple.eawt.AppEvent.QuitEvent;
import com.apple.eawt.Application;
import com.apple.eawt.QuitHandler;
import com.apple.eawt.QuitResponse;
import com.jbricx.swing.ui.preferences.BreakpointsStore;
import com.jbricx.swing.ui.preferences.MiscProperties;
import com.jbricx.swing.ui.preferences.MiscProperties.misc;
import com.jbricx.swing.ui.preferences.PreferenceStore;
import com.jbricx.swing.ui.tabs.JBricxEditorTabFolder;
import com.jbricx.swing.ui.tabs.JBricxFilePane;
import com.jbricx.swing.ui.tabs.JBricxStatusPane;
import com.jbricx.tools.AccessibleWidget;
import com.sun.jna.Platform;

@SuppressWarnings("serial")
public class MainWindow extends JFrame implements JBricxManager,WindowListener,ComponentListener  {
	
	Preferences prefs;
	
	JBricxEditorTabFolder editorPane;
	JBricxStatusPane statusPane;
	JBricxFilePane filePane;
	
	JSplitPane leftRightSplit;
	JSplitPane upDownSplit;
	
	public AccessibleWidget accessPane;

	public static long lostFocusTime;
	
	/**
	 * Runs the application. Called by initial class
	 */
	public void run() {
		new PreferenceStore();
		new BreakpointsStore();
		new MiscProperties(this);
		prefs = PreferenceStore.getPrefs();		
		
		initMainWindow();
		
		addWindowListener(this);
		addComponentListener(this);
		
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
					if (mw.beforeCloseActions()){
						qr.performQuit();
					}
					qr.cancelQuit();
				}
			}.setMainWindow(this));
		}
		if(!prefs.getBoolean("ranPreviously", false))
		{
			FirstTimeSettingsWindow firstTimeWindow = new FirstTimeSettingsWindow(this);
			firstTimeWindow.setVisible(true);
			prefs.putBoolean("ranPreviously",true);
		}
		accessPane = new AccessibleWidget("JBricks");
		accessPane.setVisible(true);
		this.add(accessPane, BorderLayout.SOUTH);
		accessPane.revalidate();
		accessPane.requestFocus();
	}
	
	/**
	 * Configures settings of the main window and builds components.
	 */
	public void initMainWindow(){
		buildMenuAndToolbar();
		buildMainWindow();
		setupEnterActionForAllButtons();

		this.setSize(
				MiscProperties.getInt(misc.WIDTH),
				MiscProperties.getInt(misc.HEIGHT)
			);
		this.setLocation(
				MiscProperties.getInt(misc.X),
				MiscProperties.getInt(misc.Y)
			);
		
		this.setVisible(true);
		this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
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
		if (beforeCloseActions()){
			this.dispose();
			System.exit(0);
		}
	}
	
	/**
	 * operations to be called before closed event
	 * 
	 * operation to be run on exit or close of application
	 */
	public boolean beforeCloseActions() {
		// Save recent files
		boolean cleanup = getTabFolder().checkOverwrite();
		if (cleanup) {
			StringBuilder recentFiles = getTabFolder().getFileList();
			prefs.put(PreferenceStore.RECENTFILES, recentFiles.toString());
		}
		return cleanup;
	}

	@Override
	public boolean isAutoCompile() {
		// TODO Auto-generated method stub
		return false;
	}

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
		editorPane.checkUpdates(lostFocusTime);
	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {
		lostFocusTime = System.currentTimeMillis();
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

	@Override
	public void componentHidden(ComponentEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentMoved(ComponentEvent arg0) {
		MiscProperties.setInt(misc.X);
		MiscProperties.setInt(misc.Y);
	}

	@Override
	public void componentResized(ComponentEvent arg0) {
		MiscProperties.setInt(misc.WIDTH);
		MiscProperties.setInt(misc.HEIGHT);
	}

	@Override
	public void componentShown(ComponentEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
