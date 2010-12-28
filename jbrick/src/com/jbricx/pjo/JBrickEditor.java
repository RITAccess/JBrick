package com.jbricx.pjo;

import java.io.IOException;
import java.util.ArrayList;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.PreferenceStore;

import com.jbricx.preferences.JBrickObservable;
import com.jbricx.source.ColorManager;
import com.jbricx.source.JBrickCodeScanner;
import com.jbricx.source.JBrickPartitionScanner;
import com.jbricx.ui.MainWindow;

/**
 * This class demonstrates TextViewer and Document.
 */
public class JBrickEditor {
	// Set up the name of the partitioner

	public static final String JBRICK_PARTITIONING = "jbrick_partitioning";
	// A reference to the current app
	private static JBrickEditor APP;
	/*	// The current document
	private PersistentDocument document;
	 */
	// The main window
	private static MainWindow mainWindow;
	// The stored preferences
	private PreferenceStore prefs;

	public void setPrefs(PreferenceStore prefs) {
		this.prefs = prefs;
//		mainWindow.notifyViewers();
	}

	// The partition scanner
	private JBrickPartitionScanner scanner;
	// The code scanner
	private JBrickCodeScanner codeScanner;
	// The color manager
	private ColorManager colorManager;

	/**
	 * Returns the application instance
	 * 
	 * @return JBrickEditor
	 */
	public static JBrickEditor getInstance() {
		return APP;
	}


	/**
	 * JBrickEditor constructor
	 */
	private JBrickEditor() {
	  APP = this;
	  
	  prefs = new PreferenceStore("JBrickEditor.properties");
	  try {
	    prefs.load();
	  } catch (IOException e) {
	    // Ignore
	  }

	  colorManager = new ColorManager();
	  codeScanner = new JBrickCodeScanner(colorManager);
	  mainWindow = new MainWindow(prefs);
	  mainWindow.registerObserver(colorManager);
	  mainWindow.registerObserver(codeScanner);
	  prefs.addPropertyChangeListener(mainWindow);

	  //TODO: verify this?
//    mainWindow.notifyViewers();
	}
	/**
	 * Runs the application
	 */
	public void run() {
		System.out.println("JbrickEditor:Run");
		mainWindow.run();

		// Dispose the colors
		if (colorManager != null) {
			colorManager.dispose();
		}
	}

	/**
	 * Gets the main window
	 * 
	 * @return MainWindow
	 */
	public MainWindow getMainWindow() {
		return mainWindow;
	}

	/**
	 * Gets the preferences
	 * 
	 * @return PreferenceStore
	 */
	public PreferenceStore getPreferences() {
		return prefs;
	}

	/**
	 * Gets the color manager
	 * 
	 * @return ColorManager
	 */
	public ColorManager getColorManager() {
		return colorManager;
	}

	/**
	 * Gets the code scanner
	 * 
	 * @return JBrickCodeScanner
	 */
	public JBrickCodeScanner getCodeScanner() {
		return codeScanner;
	}

	/**
	 * Shows an error
	 * 
	 * @param msg
	 *            the error
	 */
	public void showError(String msg) {
		MessageDialog.openError(mainWindow.getShell(), "Error", msg);
	}

	/**
	 * The application entry point
	 * 
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String[] args) {
		//ExitStatus e = BrickCreator.createBrick().getBatteryLevel();
		new JBrickEditor().run();
	}
}
