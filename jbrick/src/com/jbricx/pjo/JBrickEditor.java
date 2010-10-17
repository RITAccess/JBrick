package com.jbricx.pjo;

import java.io.IOException;
import java.util.ArrayList;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.PreferenceConverter;
import org.eclipse.jface.preference.PreferenceStore;
import org.eclipse.swt.graphics.RGB;

import com.jbricx.preferences.JBrickObservable;
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
	public static ArrayList<JBrickObservable> observerList = new ArrayList<JBrickObservable>();
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
		notifyViewers() ;
	}
	public static void notifyViewers(){
		for(JBrickObservable observer: observerList){
			observer.update();
		}
		if (mainWindow != null)	mainWindow.refreshCurrentTabItem() ;
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

		colorManager = new ColorManager();
		registerObserver(colorManager);

		prefs = new PreferenceStore("JBrickEditor.properties");
		try {
			prefs.load();
		} catch (IOException e) {
			// Ignore
		}
		notifyViewers() ; 

		codeScanner = new JBrickCodeScanner();
		registerObserver(codeScanner);
		mainWindow = new MainWindow();
		prefs.addPropertyChangeListener(mainWindow);
	}

	/**
	 * Runs the application
	 */
	public void run() {
		System.out.println("JbrickEditor:Run");
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
	 * Select all of the text in the editor
	 */
	public void selectAll() {
		mainWindow.getCurrentTabItem().getViewer().getTextWidget().selectAll();
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
		
		//System.out.println(e);
		
		new JBrickEditor().run();
		
		
	}
	public static void registerObserver(JBrickObservable observer){
		observerList.add(observer);
	}
	public static void removeObserver(JBrickObservable observer){
		if(observerList.contains(observer)){
			observerList.remove(observer);
		}
		
	}
}
 