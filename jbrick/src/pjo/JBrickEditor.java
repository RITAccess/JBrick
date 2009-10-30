package pjo;

import java.io.IOException;

import model.PersistentDocument;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.PreferenceStore;
import org.eclipse.jface.text.IDocumentPartitioner;
import org.eclipse.jface.text.rules.DefaultPartitioner;

import source.ColorManager;
import source.JBrickCodeScanner;
import source.JBrickPartitionScanner;
import ui.JBrickTabItem;
import ui.MainWindow;

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

/*<<<<<<< .mine
    mainWindow = new MainWindow();
    prefs = new PreferenceStore("JBrickEditor.properties");
    prefs.addPropertyChangeListener(mainWindow);

    try {
      prefs.load();
    } catch (IOException e) {
      // Ignore
    }
  }
=======
*/		// Set up the document
//		setUpDocument();
/*>>>>>>> .r43*/

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
	 * Sets up the document
	 */
/*	protected void setUpDocument(JBrickTabItem tabItem) {
		// Create the document
		document = new PersistentDocument();

		// Create the partition scanner
		scanner = new JBrickPartitionScanner();

		// Create the partitioner
		IDocumentPartitioner partitioner = new DefaultPartitioner(scanner,
				JBrickPartitionScanner.TYPES);

		// Connect the partitioner and document
		document.setDocumentPartitioner(JBRICK_PARTITIONING, partitioner);
		partitioner.connect(document);
	}
*/
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
	 * Creates a new file
	 */
/*	public void newFile() {
		if (checkOverwrite())
			document.clear();
	}*/

	/**
	 * Opens a file
	 * 
	 * @param fileName
	 *            the file name
	 */
/*	public void openFile(String fileName) {
		if (checkOverwrite()) {
			try {
				document.clear();
				document.setFileName(fileName);
				document.open();
			} catch (IOException e) {
				showError(e.getMessage());
			}
		}
	}
*/
	/**
	 * Saves the current file
	 */
/*	public void saveFile() {
		String fileName = document.getFileName();
		if (fileName == null) {
			SafeSaveDialog dlg = new SafeSaveDialog(mainWindow.getShell());
			dlg.setFilterNames(FileExtensionConstants.FILTER_NAMES);
			dlg.setFilterExtensions(FileExtensionConstants.FILTER_EXTENSIONS);
			fileName = dlg.open();
		}
		if (fileName != null)
			saveFileAs(fileName);
	}
*/
	/**
	 * Saves the current file using the specified file name
	 * 
	 * @param fileName
	 *            the file name
	 */
/*	public void saveFileAs(String fileName) {
		try {
			document.setFileName(fileName);
			document.save();
		} catch (IOException e) {
			showError("Can't save file " + fileName + "; " + e.getMessage());
		}
	}
*/
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

/*	*//**
	 * Checks the current file for unsaved changes. If it has unsaved changes,
	 * confirms that user wants to overwrite
	 * 
	 * @return boolean
	 *//*
	public boolean checkOverwrite() {
		boolean proceed = true;
		if (document.isDirty()) {
			proceed = MessageDialog
					.openConfirm(mainWindow.getShell(), "Are you sure?",
							"You have unsaved changes--are you sure you want to lose them?");
		}
		return proceed;
	}
*/
	/**
	 * Gets the main window
	 * 
	 * @return MainWindow
	 */
	public static MainWindow getMainWindow() {
		return mainWindow;
	}

/*	public static MainWindow getCurrentTabItem() {
		return CTabItem;
	}
*/	
	/**
	 * Gets the document
	 * 
	 * @return PersistentDocument
	 */
/*	public PersistentDocument getDocument() {
		return document;
	}*/

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
		new JBrickEditor().run();
	}
}
