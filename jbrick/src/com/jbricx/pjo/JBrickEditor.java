package com.jbricx.pjo;

import java.io.IOException;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.PreferenceStore;

import com.jbricx.communications.BrickCreator;
import com.jbricx.communications.ExitStatus;
import com.jbricx.source.ColorManager;
import com.jbricx.source.JBrickCodeScanner;
import com.jbricx.source.JBrickPartitionScanner;
import com.jbricx.ui.JBrickTabItem;
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
	public static JBrickEditor getApp() {
		return APP;
	}

	/**
	 * JBrickEditor constructor
	 */
	public JBrickEditor() {
		APP = this;

		colorManager = new ColorManager();
		codeScanner = new JBrickCodeScanner();

		mainWindow = new MainWindow();
		prefs = new PreferenceStore("JBrickEditor.properties");
		prefs.addPropertyChangeListener(mainWindow);
		try {
			prefs.load();
		} catch (IOException e) {
			// Ignore
		}
	}

	/**
	 * Runs the application
	 */
	public void run() {
		mainWindow.run();

		// Dispose the colors
		if (colorManager != null)
			colorManager.dispose();
	}
	/**
	 * Prints the document
	 */
	public void print() {
		mainWindow.getCurrentTabItem().getViewer().getTextWidget().print();
	}

	/**
	 * Cuts the selection to the clipboard
	 */
	public void cut() {
		mainWindow.getCurrentTabItem().getViewer().getTextWidget().cut();
	}

	/**
	 * Copies the selection to the clipboard
	 */
	public void copy() {
		mainWindow.getCurrentTabItem().getViewer().getTextWidget().copy();
	}

	/**
	 * Pastes the clipboard
	 */
	public void paste() {
		mainWindow.getCurrentTabItem().getViewer().getTextWidget().paste();
	}

	/**
	 * Undoes the last operation
	 */
	public void undo(JBrickTabItem tabItem) {
		tabItem.getUndoManager().undo();
	}

	/**
	 * Redoes the last operation
	 */
	public void redo(JBrickTabItem tabItem) {
		tabItem.getUndoManager().redo();
	}
	/**
	 * Gets the main window
	 * 
	 * @return MainWindow
	 */
	public static MainWindow getMainWindow() {
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
		ExitStatus e = BrickCreator.createBrick().getBatteryLevel();
		
		System.out.println(e);
		
		new JBrickEditor().run();
		
		
	}
}
 