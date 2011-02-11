package com.jbricx.ui;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.eclipse.jface.action.CoolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.StatusLineManager;
import org.eclipse.jface.action.ToolBarManager;
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
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

import com.jbricx.communications.NXT;
import com.jbricx.communications.NXTManager;
import com.jbricx.pjo.FileExtensionConstants;
import com.jbricx.preferences.JBrickObserver;
import com.jbricx.preferences.TextPreferencePage;
import com.jbricx.ui.findbrick.FindBrickFileIO;
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
public class MainWindow extends ApplicationWindow implements
    IPropertyChangeListener, JBrickManager {

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
  public MainWindow(final PreferenceStore preferences) {
    super(null);
    menuAndToolbarManagerDelegate = new MenuAndToolBarManagerDelegate(this);
    addMenuBar();
    addCoolBar(SWT.NONE);
    addStatusLine();
    prefs = preferences;
    prefs.addPropertyChangeListener(this);

    if (NXT.isFantomDriverLoaded()) {
      NXTManager.getInstance().connect(FindBrickFileIO.getCT());
    } else {
      // TODO: make the notification accessible!
      System.out.println("MainWindow.MainWindow(): Fantom driver missing!");
    }
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
   *          the shell
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
   *          the main window
   * @return Control
   */
  @Override
  protected Control createContents(final Composite parent) {
    this.treeRootFile = new File(getWorkspacePath());
    setStatus("Successfully Lauched!");

    // Someday we'll know what these three lines are for.
    final Label l = new Label(parent, SWT.NONE);
    l.setToolTipText("helllllll oooooo Jaws:");
    l.setVisible(false);

    /*
     * Divide the main window in three sections: Explorer, Editor and Status.
     * The first SashForm contains the Explorer and the second SashForm. The
     * second SashForm contains the Editor and the Status.
     */

    // Create the first SashForm to hold the Explorer and second SashForm.
    final SashForm sashForm1 = new SashForm(parent, SWT.HORIZONTAL);
    sashForm1.setLayout(new FillLayout());

    // Create the tree viewer to display the file tree.
    final CTabFolder explorerTabFolder = new CTabFolder(sashForm1, SWT.LEFT);
    explorer = new FileExplorerTabItem(explorerTabFolder, SWT.FILL,
        getWorkspacePath());

    explorer.addTreeListener(SWT.DefaultSelection, new Listener() {

      public void handleEvent(Event e) {
        IStructuredSelection selection = (IStructuredSelection) explorer
            .getSelection();
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
    // TODO: Resolve code tangling. This is kept just to avoid breaking
    // something, and I don't like it.
    final CTabFolder statusTabFolder = new CTabFolder(sashForm2, SWT.PUSH);
    statusTabFolder.setMaximizeVisible(true);

    statusTabItem = new StatusTabItem(statusTabFolder, SWT.FILL, this) {

      @Override
      protected IDocument getDocument() {
        return tabFolder.getSelection().getDocument();
      }

      @Override
      protected void setSelectedRange(int offset, int lineLength) {
        tabFolder.getSelection().getViewer()
            .setSelectedRange(offset, lineLength);
      }

      @Override
      protected StatusLineManager getStatusLineManager() {
        return MainWindow.this.getStatusLineManager();
      }
    };

    // Add listeners the Editor Panel. The order matters, be careful.
    final CTabFolder2Adapter toolbarizerAdapter = new ToolBarizeEditTabFolderAdapter(
        (CTabFolder) tabFolder, getCoolBarManager(), sashForm2);
    new MainTabFolderAdapter((CTabFolder) tabFolder, sashForm2);

    // Add the listener for the Status Panel
    statusTabFolder.addCTabFolder2Listener(new CTabFolder2Adapter() {

      @Override
      public void restore(CTabFolderEvent event) {
        toolbarizerAdapter.restore(event);
      }
    });
    new MainTabFolderAdapter((CTabFolder) statusTabFolder, sashForm2);

    // Set the different weights for both panels. This affects their size.
    // TODO : find a way to eliminate this. Automatic is always better!
    sashForm1.setWeights(new int[] { 20, 80 });
    sashForm2.setWeights(new int[] { 80, 20 });

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

  /**
   * Updates the view with the preferences
   */
  @Override
  public void propertyChange(PropertyChangeEvent event) {
    /*
     * if (FileExtensionConstants.FONT.equals(event.getProperty()))
     * this.getCurrentTabItem().setFont((FontData[]) event.getNewValue());
     * 
     * /* if (FileExtensionConstants.WRAP.equals(event.getProperty()))
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
   * @param style
   *          the style for the toolbar
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

        StringBuilder recentfiles = new StringBuilder();
        for (CTabItem t : tabFolder.getItems()) {
          JBrickTabItem i = (JBrickTabItem) t;

          recentfiles.append(i.getDocument().getFileName());
          recentfiles.append(';');
        }
        PreferenceManager mgr = new PreferenceManager();
        mgr.addToRoot(new PreferenceNode("text", "Text", null,
            TextPreferencePage.class.getName()));
        PreferenceStore ps = getPreferences();
        ps.putValue(FileExtensionConstants.RECENTFILES, recentfiles.toString());

        try {
          ps.save();
        } catch (IOException e) {
          System.out.println("Error Saving Preferences: " + e.getMessage());
        }
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

  @Override
  public String getWorkspacePath() {
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

  @Override
  public TabFolder getTabFolder() {
    return tabFolder;
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
    tabFolder.refreshTabItems();
  }

  public PreferenceStore getPreferences() {
    return prefs;
  }

  @Override
  public void updatePreferences() {
    notifyViewers();
  }
}
