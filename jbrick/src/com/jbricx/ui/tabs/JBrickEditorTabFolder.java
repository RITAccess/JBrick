/**
 * 
 */
package com.jbricx.ui.tabs;

import java.awt.Color;
import java.awt.Composite;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import annotation.AnnotationMarkerAccess;
import annotation.ColorCache;

import com.jbricx.pjo.FileExtensionConstants;
import com.jbricx.source.ColorManager;
import com.jbricx.source.CommentScanner;
import com.jbricx.source.JBrickCodeScanner;
import com.jbricx.source.JBrickEditorSourceViewerConfiguration;
import com.jbricx.ui.JBrickManager;

/**
 * @author byktol
 */
public class JBrickEditorTabFolder extends CTabFolder implements TabFolder {

  public LineNumberChangeRulerColumn lnrc;
  private final List<String> filenamesList;
  private int newFileCount;
  private JBrickManager manager;
  private JBrickEditorSourceViewerConfiguration sourceViewerConfiguration;
  // The color manager
  private ColorManager colorManager;

  public JBrickEditorTabFolder(final Composite parent,
      final JBrickManager manager, final IPreferenceStore ps, final int style) {
    super(parent, style);
    this.manager = manager;
    filenamesList = new ArrayList<String>();
    newFileCount = 0;

    colorManager = new ColorManager(ps);
    JBrickCodeScanner codeScanner = new JBrickCodeScanner(colorManager);
    CommentScanner commentScanner = new CommentScanner(colorManager);

    // TODO: these three lines. The scanners should observe the
    // colorManager.
    manager.registerObserver(colorManager);
    manager.registerObserver(commentScanner);
    manager.registerObserver(codeScanner);

    sourceViewerConfiguration = new JBrickEditorSourceViewerConfiguration(
        codeScanner, commentScanner);

    setMinimizeVisible(true);
    setMaximizeVisible(true);

    addCTabFolder2Listener(new CTabFolder2Adapter() {

      @Override
      public void close(CTabFolderEvent event) {
        JBrickTabItem tabItem = (JBrickTabItem) event.item;

        if (askCloseWithoutSaving(tabItem)) {
          manager.setStatus("Closed");

          try {
            filenamesList.remove(tabItem.getFilename());
          } catch (NullPointerException ne) {
            // the file has not been saved yet so ignore
          }
        } else {
          event.doit = false;
        }
        filenamesList.remove(tabItem.getFilename());
      }
    });

    /* Construction time */
    long start = System.currentTimeMillis();
    // Composite rightPanel = new Composite(sashForm, SWT.NONE);

    /*
     * GridLayout gridLayout = new GridLayout(); gridLayout.numColumns = 1;
     */

    // ******** top part of the right panel **********************
    // Create the viewer
    CompositeRuler ruler = new CompositeRuler(10);

    /*
     * LineNumberRulerColumn lnrc = new LineNumberRulerColumn();
     * lnrc.setForeground(new Color(parent.getShell().getDisplay(), new RGB(
     * 255, 0, 0)));
     */
    lnrc = new LineNumberChangeRulerColumn(new ColorCache());
    lnrc.setForeground(new Color(parent.getShell().getDisplay(), new RGB(255,
        0, 0)));
    // lnrc.getLineOfLastMouseButtonActivity();

    // lnrc.getControl().getAccessible().textSelectionChanged()
    ruler.addDecorator(0, lnrc);

    // tabFolder.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,
    // false));
    setSimple(false);
    setUnselectedImageVisible(false);
    setUnselectedCloseVisible(false);
    Color titleForeColor = parent.getShell().getDisplay()
        .getSystemColor(SWT.COLOR_TITLE_FOREGROUND);

    Color titleBackColor1 = parent.getShell().getDisplay()
        .getSystemColor(SWT.COLOR_TITLE_BACKGROUND);

    // Color titleBackColor1 =
    // parent.getShell().getDisplay().getSystemColor(
    // SWT.COLOR_TITLE_FOREGROUND);

    Color titleBackColor2 = parent.getShell().getDisplay()
        .getSystemColor(SWT.COLOR_TITLE_BACKGROUND_GRADIENT);
    setSelectionForeground(titleForeColor);
    setSelectionBackground(new Color[] { titleBackColor1, titleBackColor2 },
        new int[] { 100 }, true);

    // TODO: change tabs names and content, not byktol's
    // tab1
    // JBrickTabItem tabItem = new JBrickTabItem(tabFolder, SWT.CLOSE,
    // null);
    // tabFolder.setSelection(tabItem);

    ArrayList<String> recentfiles = getRecentFiles(ps);

    if (recentfiles.size() > 0) {
      for (String file : recentfiles) {
        if (new File(file).exists()) {
          open(file);
        }
      }
    } else {
      this.openNewFile();
    }

    // rulers
    AnnotationModel fAnnotationModel = new AnnotationModel();
    IAnnotationAccess fAnnotationAccess = new AnnotationMarkerAccess();

    ColorCache cc = new ColorCache();
    CompositeRuler fCompositeRuler = new CompositeRuler();
    OverviewRuler fOverviewRuler = new OverviewRuler(fAnnotationAccess, 12, cc);
    AnnotationRulerColumn annotationRuler = new AnnotationRulerColumn(
        fAnnotationModel, 16, fAnnotationAccess);
    fCompositeRuler.setModel(fAnnotationModel);
    fOverviewRuler.setModel(fAnnotationModel);

    // annotation ruler is decorating our composite ruler
    fCompositeRuler.addDecorator(0, annotationRuler);
    // ///////////////////////////////////////////////////////////////

    long end = System.currentTimeMillis();
    start = end - start;
    System.out.println("it took : " + start);
  }

  public int contains(final String filename) {
    return filenamesList.indexOf(filename);
  }

  @Override
  public boolean open(String filepath) {
    int tabIndex = getTabIndexByFilepath(filepath);

    if (tabIndex == -1) {
      File file = new File(filepath);
      if (file.exists()) {
        // this file does not exist in the editor so create one
        JBrickTabItem newTabItem = new JBrickTabItem(this, SWT.CLOSE, new File(
            filepath), manager, sourceViewerConfiguration);
        newTabItem.update(manager.getPreferences());

        filenamesList.add(filepath);
        this.setSelection(newTabItem);
      } else {
        JOptionPane.showMessageDialog(null,
            "The file you have specified does not exits!", "File Not Found!",
            JOptionPane.WARNING_MESSAGE);
      }
    } else {
      this.setSelection(this.getItem(tabIndex));
    }
    return true;
  }

  @Override
  public boolean openNewFile() {
    // counter for the number of times a new file is opened
    newFileCount++;
    String fileName = "New File " + newFileCount;
    // load recent files if they exist on preference (JBrickEditor.properties)
    JBrickTabItem newTabItem = new JBrickTabItem(this, SWT.CLOSE, null,
        manager, sourceViewerConfiguration);
    newTabItem.update(manager.getPreferences());
    newTabItem.setText(fileName);
    setSelection(newTabItem);

    manager.registerObserver(newTabItem);
    return true;
  }

  @Override
  public JBrickTabItem getSelection() {
    return (JBrickTabItem) super.getSelection();
  }

  @Override
  public JBrickTabItem getItem(int index) {
    return (JBrickTabItem) super.getItem(index);
  }

  /**
   * Checks the current file for unsaved changes. If it has unsaved changes,
   * confirms that user wants to overwrite
   * 
   * @return boolean
   */
  public boolean checkOverwrite() {
    boolean proceed = true;
    JBrickTabItem tabItem = null;
    for (CTabItem tab : getItems()) {
      tabItem = (JBrickTabItem) tab;

      if (tabItem.getDocument().isDirty()) {
        this.setSelection(tabItem);

        proceed = askCloseWithoutSaving(tabItem);
        if (proceed == false) {
          break;
        }
      }
    }
    tabItem = null;

    return proceed;
  }

  /**
   * Performs check on a tab item's on close event if its document has any
   * unsaved changes
   * 
   * @param tabItem
   *          the JBrickTabItem which is clicked for close
   * @return true if user confirms to proceed without closing else false
   */
  private boolean askCloseWithoutSaving(JBrickTabItem tabItem) {
    boolean proceed = true;
    String fileName = tabItem.getDocument().getFileName();
    if (tabItem.getDocument().isDirty() || fileName != null
        && fileName.endsWith(".bak.nxc")) {
      proceed = MessageDialog.openQuestion(null, "Close without saving?",
          "Changes to \"" + tabItem.getText()
              + "\" have not been saved. Do you still want to proceed?");
      removeTempFile(tabItem, proceed);
    }
    return proceed;
  }

  /**
   * Removes a temporary file if a user decided not to change it
   * 
   * @param tab
   *          - the tabItem to be removed
   * @param remove
   *          - whether the file should be removed or not
   */
  private void removeTempFile(JBrickTabItem tab, Boolean remove) {
    String fileName = tab.getDocument().getFileName();
    if (remove && fileName != null && fileName.endsWith(".bak.nxc")) {
      File deleteFile = new File(tab.getDocument().getFileName());
      deleteFile.delete();
      tab.dispose();
    }
  }

  public ArrayList<String> getRecentFiles(final IPreferenceStore ps) {
    // Get the preference store
    Boolean loadrecent = ps.getBoolean(FileExtensionConstants.BOOLRECENTFILES);
    ArrayList<String> recentfiles = new ArrayList<String>();
    if (loadrecent) {
      File dir = new File(manager.getWorkspacePath());
      String[] fileNames = dir.list();

      for (int i = 0; fileNames != null && i < fileNames.length; i++) {
        if (fileNames[i].endsWith(".bak.nxc")) {
          recentfiles.add(manager.getWorkspacePath() + "\\" + fileNames[i]);
        }
      }

      for (String s : ps.getString(FileExtensionConstants.RECENTFILES).split(
          ";")) {
        recentfiles.add(s);
      }
    }
    return recentfiles;
  }

  public void saveFile(String filePath) {
    filenamesList.add(filePath);
  }

  public void closeFile(String filePath) {
    filenamesList.remove(filePath);
  }

  public final void addCTabFolder2Listener(CTabFolder2Adapter adapter) {
    super.addCTabFolder2Listener(adapter);
  }

  public int getTabIndexByFilepath(String filePath) {
    int index = -1;
    int count = getItems().length;
    for (int i = 0; i < count; i++) {
      try {
        if (getItem(i).getFilename().equals(filePath)) {
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

  @Override
  public void insertText(final String text) {
    if (getSelection() != null) {
      getSelection().insertString(text);
    }
  }

  @Override
  public void undo() {
    if (getSelection() != null) {
      getSelection().getUndoManager().undo();
    }
  }

  @Override
  public void redo() {
    if (getSelection() != null) {
      getSelection().getUndoManager().redo();
    }
  }

  /**
   * Cuts the selection to the clipboard
   */
  @Override
  public void cut() {
    if (getSelection() != null) {
      getSelection().getViewer().getTextWidget().cut();
    }
  }

  /**
   * Copies the selection to the clipboard
   */
  @Override
  public void copy() {
    if (getSelection() != null) {
      getSelection().getViewer().getTextWidget().copy();
    }
  }

  /**
   * Pastes the clipboard
   */
  @Override
  public void paste() {
    if (getSelection() != null) {
      getSelection().getViewer().getTextWidget().paste();
    }
  }

  /**
   * Select all of the text in the editor
   */
  @Override
  public void selectAll() {
    getSelection().getViewer().getTextWidget().selectAll();
  }

  @Override
  public SourceViewer getSourceViewer() {
    return getSelection().getViewer();
  }

  @Override
  public String getCurrentFilename() {
    return getSelection().getFilename();
  }

  @Override
  public int getCurrentIndex() {
    return getSelectionIndex();
  }

  /**
   * Refreshes the content of the tabs. Basically, it only copies the content
   * from the current tab and paste it over, letting the associated classes deal
   * with the actual refreshing. This method is convenient for changing the
   * colors of the tab's text.
   */
  @Override
  public void refreshTabItems() {
    final CTabItem tabItems[] = getItems();

    for (CTabItem currentItem : tabItems) {
      if (currentItem != null) {
        JBrickTabItem tabItem = (JBrickTabItem) currentItem;
        tabItem.getViewer().getTextWidget()
            .setText(tabItem.getViewer().getTextWidget().getText());
      }
    }
  }
}
