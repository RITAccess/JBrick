package com.jbricx.swing.ui.tabs;

import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.util.ArrayList;
import java.util.prefs.Preferences;

import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.KeyStroke;

import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rsyntaxtextarea.SyntaxScheme;
import org.fife.ui.rtextarea.Gutter;
import org.fife.ui.rtextarea.RTextScrollPane;

import com.jbricx.pjo.ActionControlClass;
import com.jbricx.swing.ui.JBricxManager;
import com.jbricx.swing.ui.preferences.PreferenceStore;

public class JBricxEditorTabFolder extends JTabbedPane {
	private JBricxManager manager;
	// Used to name new files when opened
	private int newFileCount = 0;
	// List of currently open files.
	private ArrayList<String> listOfFiles;

	// Used so the folder knows whether to add files to the recent file list.
	private boolean closingTime;

	private Preferences prefs;

	/**
	 * Constructor for the tab folder. Loads recent files if any exist.
	 * 
	 * @param manager
	 *            MainWindow class goes here (JBricxManager)
	 */
	public JBricxEditorTabFolder(JBricxManager manager) {
		this.manager = manager;
		listOfFiles = new ArrayList<String>();
		prefs = PreferenceStore.getPrefs();

		if (prefs.getBoolean(PreferenceStore.BOOLRECENTFILES, 
				PreferenceStore.BOOLRECENTFILES_DEFAULT)) {
			
			ArrayList<String> recentFiles = getRecentFiles();
			
			if (recentFiles.size() > 0 && recentFiles.get(0).length() > 1) {
				boolean fileOpened = false;
				
				// try to open all the recent files
				for (String file : recentFiles) {
					if (new File(file).exists()) {
						open(file);
						fileOpened = true;
					}
				}
				
				// if NONE of the files exist any more, open a new file
				if(!fileOpened){
					this.openNewFile();
				}
				
			} else {
				this.openNewFile();
			}
		} else {
			this.openNewFile();
		}
		
		setupTabTraversalKeys();
	}
	/*
	 * Adapted from http://www.davidc.net/programming/java/how-make-ctrl-tab-switch-tabs-jtabbedpane
	 * Sets up the JTabbedPane to allow a shortcut for tabbing between files
	 */
	private void setupTabTraversalKeys()
	{
	   /* KeyStroke ctrlTab = KeyStroke.getKeyStroke("ctrl TAB");
	    KeyStroke ctrlShiftTab = KeyStroke.getKeyStroke("ctrl shift TAB");
	 
	    // Remove ctrl-tab from normal focus traversal
	    Set<AWTKeyStroke> forwardKeys = new HashSet<AWTKeyStroke>(getFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS));
	    forwardKeys.remove(ctrlTab);
	    setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, forwardKeys);
	 
	    // Remove ctrl-shift-tab from normal focus traversal
	    Set<AWTKeyStroke> backwardKeys = new HashSet<AWTKeyStroke>(getFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS));
	    backwardKeys.remove(ctrlShiftTab);
	    setFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, backwardKeys);
	 
	    // Add keys to the tab's input map
	    InputMap inputMap = getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
	    inputMap.put(ctrlTab, "navigateNext");
	    inputMap.put(ctrlShiftTab, "navigatePrevious");*/
	    
	   /* InputMap inputMap = getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
	    inputMap.put(KeyStroke.getKeyStroke("ctrl 1"), "navigatePrevious");
	    inputMap.put(KeyStroke.getKeyStroke("ctrl 2"), "navigateNext");*/
	    
		int ctrl_or_command_mask = Toolkit.getDefaultToolkit().getMenuShortcutKeyMask();
		
	    InputMap inputMap = getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
	    inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, ctrl_or_command_mask), "navigatePrevious");
	    inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, ctrl_or_command_mask), "navigateNext");
	}

	/**
	 * Returns an ArrayList of Strings, each representing the absolute file path
	 * of any files that were left open for the next time. Also includes any
	 * .bak.nxc files in the working directory.
	 * 
	 * @return
	 * 
	 */
	private ArrayList<String> getRecentFiles() {
		ArrayList<String> recentfiles = new ArrayList<String>();

		File dir = new File(prefs.get(PreferenceStore.WRKSPC,
				PreferenceStore.WRKSPC_DEFAULT));
		String[] fileNames = dir.list();

		for (int i = 0; fileNames != null && i < fileNames.length; i++) {
			if (fileNames[i].endsWith(".bak.nxc")) {
				recentfiles.add(dir.getAbsolutePath() + "\\" + fileNames[i]);
			}
		}
		for (String s : prefs.get(PreferenceStore.RECENTFILES, "").split(";")) {
			recentfiles.add(s);
		}
		return recentfiles;

	}

	/**
	 * Opens a new file with the given filename. Makes a new tab item and hands
	 * off the filepath to it.
	 * 
	 * @param absoluteFilePath
	 *            Absolute path of the filename to open.
	 */
	public void open(final String absoluteFilePath) {
		int tabIndex = getTabIndexByFilepath(absoluteFilePath);
		// Make a new file because it was not currently found in the list of
		// open files
		if (tabIndex == -1) {

			JBricxTabItem newItem = new JBricxTabItem(this, absoluteFilePath);
			newItem.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_NXC);
			newItem.setCodeFoldingEnabled(true);
			newItem.setAntiAliasingEnabled(true);
			RTextScrollPane scroller = new RTextScrollPane(newItem);
			scroller.setFoldIndicatorEnabled(true);
			String fileName = newItem.getFileName();
			if (fileName != null) {

				this.add(fileName, scroller);
				this.setTabComponentAt(this.getTabCount() - 1,
						new ButtonTabComponent(this));
				this.setSelectedComponent(scroller);

			} else { // File doesn't exist, throw an error.
				JOptionPane.showMessageDialog(null,
						"The file you have specified does not exits!",
						"File Not Found!", JOptionPane.WARNING_MESSAGE);
			}
		} else {
			this.setSelectedIndex(tabIndex);
		}
	}

	/**
	 * Closes file. If the file is dirty, asks if it should be saved first. If
	 * true, user has chosen to close the file, or the file was not dirty. If
	 * false, user wants to cancel the close. Have to do some black magic to get
	 * the Tab item out of the scrollpane.
	 * 
	 * @param index
	 *            of the file to close.
	 */
	public boolean closeFile(int n) {
		// This first if statement is logic
		// for deciding when to close a file if there is only one left.
		if ((!getSelection().isNewFile()
				|| getSelection().getFileAbsolutePath().endsWith(".bak.nxc") || getSelection()
				.isDirty())
				|| closingTime || getComponentCount() - 1 > 1) {

			JBricxTabItem tabItem = (JBricxTabItem) ((JScrollPane) getComponentAt(n))
					.getViewport().getView();
			String fileName = tabItem.getFileAbsolutePath();
			// User needs to be prompted to save file before closing
			if (tabItem.isDirty()
					|| (tabItem.isNewFile() && fileName.endsWith(".bak.nxc"))) {

				Object[] options = { "Save", "Don't save", "Cancel" };
				int overwrite = JOptionPane
						.showOptionDialog(
								this,
								"Changes to \""
										+ tabItem.getFileName()
										+ "\" have not been saved. Do you wish to save your changes?",
								"Unsaved Changes",
								JOptionPane.YES_NO_CANCEL_OPTION,
								JOptionPane.QUESTION_MESSAGE, null, options,
								options[0]);
				// User wishes to save the file before closing.
				if (overwrite == JOptionPane.YES_OPTION) {
					boolean saveSuccess;

					if (tabItem.getFileAbsolutePath() != null
							&& tabItem.getFileAbsolutePath().endsWith(
									".bak.nxc")) {

						String fpathname = tabItem.getFileAbsolutePath();
						saveSuccess = ActionControlClass.saveFile(tabItem,
								true, manager);
						if (!tabItem.getFileAbsolutePath().endsWith(".bak.nxc")) {
							// File was successfully saved, cleanup the
							// temporary file
							File f = new File(fpathname);
							f.delete();
						}
					} else {
						saveSuccess = ActionControlClass.saveFile(tabItem,
								false, manager);
					}

					if (saveSuccess) {
						if (closingTime) {
							listOfFiles.add(tabItem.getFileAbsolutePath());
						} else {
							keepAFileOpen();
						}
						return true;
					} else {
						return false;
					}

					// User said they do not wish to save (close without saving)
				} else if (overwrite == JOptionPane.NO_OPTION) {
					// User chose not to save .bak.nxc file. Delete it.
					if (tabItem.getFileAbsolutePath().endsWith(".bak.nxc")) {
						String fpathname = tabItem.getFileAbsolutePath();
						File f = new File(fpathname);
						f.delete();
					} else {
						if (closingTime && !tabItem.isNewFile()) {
							listOfFiles.add(tabItem.getFileAbsolutePath());
						}
					}
					keepAFileOpen();
					return true;
					// user chose to cancel or hit x. Do nothing
				} else {
					return false;
				}
				// File was already saved, do not need to prompt
			} else {
				if (closingTime) {
					if (!tabItem.isNewFile()) {
						listOfFiles.add(tabItem.getFileAbsolutePath());
					}
				}
				keepAFileOpen();
				return true;
			}
		} else {
			return false;
		}
	}

	/**
	 * Used to keep one file tab open if all else are closed.
	 */
	private void keepAFileOpen() {
		if (getComponentCount() == 2) {
			openNewFile();
		}

	}

	/**
	 * Opens a new file with a default name, and blank text. Changes focus to
	 * that file. Not sure atm why this returns true instead of being void but
	 * I'm leaving it for now.
	 * 
	 * @return true when done
	 */
	public boolean openNewFile() {
		newFileCount++;

		JBricxTabItem newTabItem = new JBricxTabItem(this, newFileCount);
		newTabItem.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_NXC);
		newTabItem.setCodeFoldingEnabled(true);
		newTabItem.setAntiAliasingEnabled(true);
		// newTabItem.setFont(new Font(null, Font.BOLD,50));

		RTextScrollPane scroller = new RTextScrollPane(newTabItem);
		scroller.setFoldIndicatorEnabled(true);

		this.add(newTabItem.getFileName(), scroller);
		this.setTabComponentAt(this.getTabCount() - 1, new ButtonTabComponent(
				this));
		this.setSelectedComponent(scroller);
		return true;
	}

	/**
	 * Called to see if anything needs to be saved before closing. If it does,
	 * saves files.
	 * 
	 * @return true if everything is cleaned up, false if they weren't done.
	 */
	public boolean checkOverwrite() {
		closingTime = true;
		boolean proceed = true;

		int paneCount = this.getTabCount();

		for (int i = 0; i < paneCount; i++) {

			if (closeFile(i)) {
				// file save succeeded or they chose not to save the file, or
				// file doesnt need to be saved.
			} else {
				proceed = false;
				closingTime = false;
				listOfFiles.clear();
				break;
			}

		}
		return proceed;
	}

	/**
	 * Uses the getSelection(index) method to get the currently focused tab item
	 * 
	 * @return JBricxTabItem
	 */
	public JBricxTabItem getSelection() {
		return this.getSelection(getSelectedIndex());
	}

	/**
	 * Sets the currently selected tab to the index specified
	 * 
	 * @param selectedIndex
	 *            index of tab you you want selected
	 */
	public void setSelection(int selectedIndex) {
		this.setSelectedIndex(selectedIndex);
	}

	/**
	 * Pulls out the selected JBricxTabItem for whatever purpose.
	 * 
	 * @param index
	 *            of the requested tab item
	 * @return the tab item requested
	 */
	public JBricxTabItem getSelection(int index) {
		return (JBricxTabItem) (((JScrollPane) getComponentAt(index))
				.getViewport().getView());
	}

	/**
	 * Gets the file name of the currently selected tab.
	 * 
	 * @return Name of the file
	 */
	public String getCurrentFilename() {
		return getSelection().getFileName();
	}

	public void print() {
		try {
			PrinterJob job = PrinterJob.getPrinterJob();
			job.setPrintable(getSelection());
			job.printDialog();
			job.print();
		} catch (PrinterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Refreshes tab items text and color etc.
	 */
	public void refreshTabItems() {
		int paneCount = this.getTabCount();

		for (int i = 0; i < paneCount; i++) {
			// Color for Lines
			RTextScrollPane scroller = (RTextScrollPane) getComponentAt(i);
			Gutter theGutter = scroller.getGutter();
			theGutter.setBackground(new Color(PreferenceStore.getPrefs()
					.getInt(PreferenceStore.ColorFor.LINENUMBERBG.toString(),
							PreferenceStore.LINENUMBERBG_DEFAULT)));
			theGutter.setLineNumberColor(new Color(PreferenceStore.getPrefs()
					.getInt(PreferenceStore.ColorFor.LINENUMBERFG.toString(),
							PreferenceStore.LINENUMBERFG_DEFAULT)));
			theGutter.setLineNumberFont(Font.decode(PreferenceStore.getPrefs()
					.get(PreferenceStore.FONT, PreferenceStore.FONT_DEFAULT)));

			// Fonts
			JBricxTabItem tab = (JBricxTabItem) (scroller.getViewport()
					.getView());
			Font font = Font.decode(PreferenceStore.getPrefs().get(
					PreferenceStore.FONT, PreferenceStore.FONT_DEFAULT));
			if (font != null) {
				SyntaxScheme ss = tab.getSyntaxScheme();
				ss = (SyntaxScheme) ss.clone();
				for (int j = 0; j < ss.styles.length; j++) {
					if (ss.styles[i] != null) {
						ss.styles[i].font = font;
					}
				}
				tab.setSyntaxScheme(ss);
				tab.setFont(font);
			}
			// Colors for main code
			tab.setSyntaxScheme(tab.getDefaultSyntaxScheme());
		}
	}

	/**
	 * Refreshes the tabs title names
	 */
	public void refreshTabTitles() {
		int paneCount = this.getTabCount();
		for (int i = 0; i < paneCount; i++) {
			RTextScrollPane scroller = (RTextScrollPane) getComponentAt(i);
			JBricxTabItem tab = (JBricxTabItem) (scroller.getViewport()
					.getView());
			if (tab.getFileName() != null) {
				this.setTitleAt(i, tab.getFileName());
				this.setTabComponentAt(i, new ButtonTabComponent(this));
			}
		}
	}

	/**
	 * Returns the index of the tab item based on the file path given.
	 * 
	 * @param filePath
	 *            absolute file path of the desired tab item
	 * @return the index of the tab. If it does not exist will return -1
	 */
	public int getTabIndexByFilepath(String filePath) {
		int index = -1;
		int count = getComponentCount();
		for (int i = 0; i < count - 1; i++) {
			try {
				if (getSelection(i).getFileAbsolutePath().equals(filePath)) {
					index = i;
					break;
				}
			} catch (NullPointerException ne) {
				// in case a new tab is opened no filename is open so.. let the
				// loop conitnue
			}
		}
		return index;
	}

	/**
	 * Gets the list of files currently open in order to open them next time.
	 * 
	 * @return StringBuilder of Recent Files
	 */
	public StringBuilder getFileList() {
		StringBuilder recentFiles = new StringBuilder();
		for (String eachName : listOfFiles) {
			recentFiles.append(eachName);
			recentFiles.append(";");
		}
		return recentFiles;
	}

}
