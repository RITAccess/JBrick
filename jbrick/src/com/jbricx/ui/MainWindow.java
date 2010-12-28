package com.jbricx.ui;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.eclipse.jface.action.CoolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.StatusLineManager;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferenceManager;
import org.eclipse.jface.preference.PreferenceNode;
import org.eclipse.jface.preference.PreferenceStore;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabFolder2Adapter;
import org.eclipse.swt.custom.CTabFolderEvent;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

import com.jbricx.pjo.FileExtensionConstants;
import com.jbricx.preferences.JBrickObserver;
import com.jbricx.preferences.TextPreferencePage;
import com.jbricx.ui.tabs.FileExplorerTabItem;
import com.jbricx.ui.tabs.JBrickEditorTabFolder;
import com.jbricx.ui.tabs.JBrickTabItem;
import com.jbricx.ui.tabs.MainTabFolderAdapter;
import com.jbricx.ui.tabs.StatusTabItem;
import com.jbricx.ui.tabs.TabFolder;
import com.jbricx.ui.tabs.ToolBarizeEditTabFolderAdapter;

/**
 * This class provides the main window of JBrickEditor
 */
public class MainWindow extends ApplicationWindow implements IPropertyChangeListener, JBrickManager {

	// The font
	private Font font;
	private MenuAndToolBarManagerDelegate menuAndToolbarManagerDelegate;
	private File treeRootFile;
	private TabFolder tabFolder;
	private StatusTabItem statusTabItem;
  private FileExplorerTabItem explorer;
  public static ArrayList<JBrickObserver> observerList = new ArrayList<JBrickObserver>();
  // The stored preferences
  private PreferenceStore prefs;

	/**
	 * MainWindow constructor
	 */
	public MainWindow() {
		super(null);
		menuAndToolbarManagerDelegate = new MenuAndToolBarManagerDelegate(this);
		addMenuBar();
		addCoolBar(SWT.NONE);
		addStatusLine();

		prefs = new PreferenceStore("JBrickEditor.properties");
    try {
      prefs.load();
    } catch (IOException e) {
      // Ignore
    }
    prefs.addPropertyChangeListener(this);
	}

  /**
	 * Runs the application
	 */
	public void run() {
		setBlockOnOpen(true);
		open();
		Display.getCurrent().dispose();
	}

	/**
	 * Configures the shell
	 *
	 * @param shell
	 *            the shell
	 */
	@Override
	protected void configureShell(Shell shell) {
		super.configureShell(shell);
		shell.setText("JBrick Editor");
	}

  public void registerObserver(final JBrickObserver observer) {
    observerList.add(observer);
  }

  public void removeObserver(final JBrickObserver observer) {
    if (observerList.contains(observer)) {
      observerList.remove(observer);
    }
  }

	/**
	 * Creates the main window's contents
	 *
	 * @param parent
	 *            the main window
	 * @return Control
	 */
	@Override
	protected Control createContents(final Composite parent) {

		String workspacePath = getWorkspacePath();
		if (workspacePath == null) {
			workspacePath = setWorkspacePath(parent);
		}
		this.treeRootFile = new File(workspacePath);
		setStatus("Successfully Lauched!");

		// Someday we'll know what these three lines are for.
		final Label l = new Label(parent, SWT.NONE);
		l.setToolTipText("helllllll oooooo Jaws:");
		l.setVisible(false);

		/* Divide the main window in three sections: Explorer, Editor and Status.
		 * The first SashForm contains the Explorer and the second SashForm.
		 * The second SashForm contains the Editor and the Status.
		 */

		// Create the first SashForm to hold the Explorer and second SashForm.
		final SashForm sashForm1 = new SashForm(parent, SWT.HORIZONTAL);
		sashForm1.setLayout(new FillLayout());

		// Create the tree viewer to display the file tree.
		final CTabFolder explorerTabFolder = new CTabFolder(sashForm1, SWT.LEFT);
		explorer = new FileExplorerTabItem(explorerTabFolder, SWT.FILL, workspacePath);

		explorer.addTreeListener(SWT.DefaultSelection, new Listener() {

			public void handleEvent(Event e) {
				IStructuredSelection selection = (IStructuredSelection) explorer.getSelection();
				File file = (File) selection.getFirstElement();

				if (!file.isDirectory()) {                    
					getTabFolder().open(file.getAbsolutePath());
				}
			}
		});

		// Create the second SashForm to hold the editor and status.
		final SashForm sashForm2 = new SashForm(sashForm1, SWT.VERTICAL);
		sashForm1.setLayout(new FillLayout());

		// Create the panel for the editor (JBrickEditorTabFolder)
		tabFolder = new JBrickEditorTabFolder(sashForm2, this, prefs, SWT.PUSH);

		// Create the status panel.
		// TODO: Resolve code tangling. This is kept just to avoid breaking something, and I don't like it.
		final CTabFolder statusTabFolder = new CTabFolder(sashForm2, SWT.PUSH);
		statusTabFolder.setMaximizeVisible(true);

		statusTabItem = new StatusTabItem(statusTabFolder, SWT.FILL, this) {
			@Override
			protected IDocument getDocument() {
				return tabFolder.getSelection().getDocument();
			};

			@Override
			protected void setSelectedRange(int offset, int lineLength) {
				tabFolder.getSelection().getViewer().setSelectedRange(offset, lineLength);
			}

			@Override
			protected StatusLineManager getStatusLineManager() {
				return MainWindow.this.getStatusLineManager();
			}
		};

		// Add listeners the Editor Panel. The order matters, be careful.
    final CTabFolder2Adapter toolbarizerAdapter =
      new ToolBarizeEditTabFolderAdapter((CTabFolder) tabFolder, getCoolBarManager(), sashForm2);
		new MainTabFolderAdapter((CTabFolder) tabFolder, sashForm2);

		// Add the listener for the Status Panel
		statusTabFolder.addCTabFolder2Listener(new CTabFolder2Adapter() {
		  @Override
		  public void restore(CTabFolderEvent event) {
		    toolbarizerAdapter.restore(event);
		  }
		});
		new MainTabFolderAdapter((CTabFolder) statusTabFolder, sashForm2);
		

		// Set the different weights for both panels. This affect their size.
		// TODO : find a way to eliminate this. Automatic is always better!
		sashForm1.setWeights(new int[]{20, 80});
		sashForm2.setWeights(new int[]{80, 20});

		getMenuBarManager().updateAll(true);
		l.setFocus();

		notifyViewers();
		return sashForm1; // return the created Composite.
	}

	public void TabItemClosed(CTabFolderEvent event) {
		if (event.item.equals(this)) {
			event.doit = false;
		}
	}

	protected void loadPreferences(JBrickTabItem tabItem) {
		IPreferenceStore ps = getPreferences();

		String fontProp = ps.getString(FileExtensionConstants.FONT);
		if (fontProp.length() > 0) {
			FontData[] fd = new FontData[1];
			fd[0] = new FontData(fontProp);
			// setFont(fd, tabItem);
			this.getCurrentTabItem().setFont(fd);
		}
	}

	/**
	 * Updates the view with the preferences
	 */
	@Override
	public void propertyChange(PropertyChangeEvent event) {
		/*if (FileExtensionConstants.FONT.equals(event.getProperty()))
		this.getCurrentTabItem().setFont((FontData[]) event.getNewValue());

		/*
		 * if (FileExtensionConstants.WRAP.equals(event.getProperty()))
		 * setWrap(((Boolean) event.getNewValue()).booleanValue()); else if
		 * (FileExtensionConstants.FONT.equals(event.getProperty()))
		 * this.getCurrentTabItem().setFont((FontData[]) event.getNewValue());
		 */
	}

	/**
	 * Creates the menu manager
	 *
	 * @return MenuManager
	 */
	@Override
	protected MenuManager createMenuManager() {
		return menuAndToolbarManagerDelegate.createMenuManager();
	}

	@Override
	protected ToolBarManager createToolBarManager(final int style) {
		return menuAndToolbarManagerDelegate.createToolBarManager(style);
	}

	/**
	 * Creates the toolbar
	 *
	 * @param style the style for the toolbar
	 * @return ToolBarManager
	 */
	@Override
	protected CoolBarManager createCoolBarManager(int style) {
	  CoolBarManager coolBarManager = new CoolBarManager(style);
	  coolBarManager.add(this.createToolBarManager(SWT.FLAT));

    return coolBarManager;
	}

	/**
	 * Closes the main window
	 */
	@Override
	public boolean close() {
		boolean close = false;
		if (getTabFolder().checkOverwrite()) {
			close = super.close();
			if (close) {
				if (font != null) {
					font.dispose();
				}

				String recentfiles = "";
				for (CTabItem t : tabFolder.getItems()) {
					JBrickTabItem i = (JBrickTabItem) t;

					recentfiles += i.getDocument().getFileName() + ";";
				}
				PreferenceManager mgr = new PreferenceManager();
				mgr.addToRoot(new PreferenceNode("text", "Text", null,
						TextPreferencePage.class.getName()));
				PreferenceStore ps = getPreferences();
				ps.putValue(FileExtensionConstants.RECENTFILES, recentfiles);

				try {
					ps.save();
				} catch (IOException e) {
					System.out.println("Error Saving Preferences: "
							+ e.getMessage());
				}
				//System.out.println(recentfiles);
			}
		}
		return close;
	}

	public File getTreeRootFile() {
		return treeRootFile;
	}

	public void setTreeRootFile(File treeRootFile) {
		this.treeRootFile = treeRootFile;
	}

	// Going to modify this to request preferences
	public String setWorkspacePath(Composite parent) {
		String workspace = null;
		String path;
		PreferenceStore ps = getPreferences();
		do {
			DirectoryDialog dialog = new DirectoryDialog(parent.getShell());
			dialog.setText("Workspace Selection");
			path = dialog.open();
		} while (path == null);
		ps.putValue(FileExtensionConstants.WRKSPC, path);
		try {
			ps.save();
		} catch (IOException e) {
			System.out.println("Error Saving Preferences: " + e.getMessage());
		}
		workspace = path;
		return workspace;
	}

	// Going to modify this to request preferences
	public String getWorkspacePath() {
		// Get the preference store
		PreferenceManager mgr = new PreferenceManager();
		mgr.addToRoot(new PreferenceNode("text", "Text", null,
				TextPreferencePage.class.getName()));
		PreferenceStore ps = getPreferences();
		String workspace = ps.getString(FileExtensionConstants.WRKSPC);

		// Check if directory exists
		File file = new File(workspace);
		boolean exists = file.exists();
		if (workspace.equals("") || !exists) {
			return null;
		} else {
			return workspace;
		}
	}

  @Override
  public String getWorkPath() {
    return prefs.getString(FileExtensionConstants.WRKSPC);
  }

	// Going to modify this to request preferences
	public boolean isAutoCompile() {
		// Get the preference store
		PreferenceManager mgr = new PreferenceManager();
		mgr.addToRoot(new PreferenceNode("text", "Text", null,
				TextPreferencePage.class.getName()));
		PreferenceStore ps = getPreferences();
		Boolean autoCompile = ps.getBoolean(FileExtensionConstants.AUTOCOMPILE);
		return autoCompile;
	}

	public TabFolder getTabFolder() {
		return tabFolder;
	}

	public JBrickTabItem getCurrentTabItem() {
		return getTabFolder().getSelection();
	}

	protected int getCurrentTabIndex() {
		return getTabFolder().getSelectionIndex();
	}

	// TODO : check and delete. What's the purpose of this?
	public void refreshCurrentTabItem() {
		int selectedIndex = getCurrentTabIndex();
		CTabItem tabItems[] = tabFolder.getItems();

		int valor = tabItems.length;
		String valor2 = String.valueOf(valor);
		System.out.println("refresh refreshCurrentTabItem = " + valor2);

		for (CTabItem tbItem : tabItems) {
			System.out.println(":tabItems:");
			if (tbItem != null) {
				System.out.println("tbItem != null");
				JBrickTabItem tabItem = (JBrickTabItem) tbItem;
				String currentString = tabItem.getViewer().getTextWidget().getText();
				String currentSaveString = tabItem.getDocument().getFileName();
				removeObserver(tabItem);
				tabItem.dispose();
				System.out.println("tbItem.dispose...");
				if (currentSaveString != null) {
					getTabFolder().open(currentSaveString);
					System.out.println("openFile");

				} else {
				  getTabFolder().openNewFile();
					System.out.println("openNewFile");
				}
				tabItem = getCurrentTabItem();
				tabItem.getViewer().getTextWidget().setText(currentString);

			}
		}
		if (0 <= selectedIndex){
			tabFolder.setSelection(selectedIndex);
		}
	}

	public void refreshExplorerContent() {
		explorer.refreshView();
	}

	public void setTabFolder(JBrickEditorTabFolder tabFolder) {
		this.tabFolder = tabFolder;
	}

	public Composite getTable() {
		return statusTabItem.getTable();
	}

  public void notifyViewers() {
    for (JBrickObserver observer : observerList) {
      observer.update(prefs);
    }
    refreshCurrentTabItem();
  }
  
  public PreferenceStore getPreferences() {
    return prefs;
  }
  
  @Override
  public void setPreferences(PreferenceStore store) {
    this.prefs = store;
    notifyViewers();
  }
}
