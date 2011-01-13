package com.jbricx.ui.tabs;

/**
 * Notes : In order to read the status message in the status bar use insert + PgDn
 */
import java.io.File;
import java.io.IOException;

import javax.swing.JOptionPane;

import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferenceConverter;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IDocumentPartitioner;
import org.eclipse.jface.text.IUndoManager;
import org.eclipse.jface.text.TextViewerUndoManager;
import org.eclipse.jface.text.rules.FastPartitioner;
import org.eclipse.jface.text.source.AnnotationBarHoverManager;
import org.eclipse.jface.text.source.AnnotationModel;
import org.eclipse.jface.text.source.AnnotationPainter;
import org.eclipse.jface.text.source.AnnotationRulerColumn;
import org.eclipse.jface.text.source.CompositeRuler;
import org.eclipse.jface.text.source.IAnnotationAccess;
import org.eclipse.jface.text.source.ISharedTextColors;
import org.eclipse.jface.text.source.LineNumberRulerColumn;
import org.eclipse.jface.text.source.OverviewRuler;
import org.eclipse.jface.text.source.SourceViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;

import annotation.AnnotationConfiguration;
import annotation.AnnotationHover;
import annotation.AnnotationMarkerAccess;

import com.jbricx.model.PersistentDocument;
import com.jbricx.pjo.FileExtensionConstants;
import com.jbricx.pjo.JBrickEditor;
import com.jbricx.preferences.JBrickObserver;
import com.jbricx.source.ColorManager.ColorFor;
import com.jbricx.source.JBrickEditorSourceViewerConfiguration;
import com.jbricx.source.JBrickPartitionScanner;
import com.jbricx.ui.JBrickStatusUpdater;

public class JBrickTabItem extends CTabItem implements JBrickObserver {

  private CompositeRuler ruler;
  private LineNumberRulerColumn lnrc;
  // The partition scanner
  private JBrickPartitionScanner scanner;
  private JBrickEditorTabFolder jbrickeditortabfolder;
  // Right Click Menu
  private MenuManager menuManager;
  // The undo manager
  private IUndoManager undoManager;
  // The viewer
  private SourceViewer viewer;
  // The current document
  private PersistentDocument document;
  // error identifiers, images and colors
  public static String ERROR_TYPE = "error.type";
  public static Image ERROR_IMAGE;
  public static final RGB ERROR_RGB = new RGB(255, 0, 0);
  // amarillo
  public static final RGB ERROR_RGB2 = new RGB(255, 255, 0);
  // annotation model
  public AnnotationModel fAnnotationModel = new AnnotationModel();
  File file;

  /**
   * 
   * @param parent
   * @param style
   * @param file
   */
  public JBrickTabItem(CTabFolder parent, int style, File file, final JBrickStatusUpdater statusUpdater, final JBrickEditorSourceViewerConfiguration configuration) {
    super(parent, style);
    setFile(file);
    setUpDocument(file);

    ruler = new CompositeRuler(10);
    lnrc = new LineNumberRulerColumn();
    ruler.addDecorator(0, lnrc);

    // annotation ruler to view annotation

    ERROR_IMAGE = new Image(Display.getDefault(), "src/images/error_ovr.gif");

    IAnnotationAccess fAnnotationAccess = new AnnotationMarkerAccess();

    ColorCache cc = new ColorCache();

    // rulers
    CompositeRuler fCompositeRuler = new CompositeRuler();
    OverviewRuler fOverviewRuler = new OverviewRuler(fAnnotationAccess, 12, cc);
    AnnotationRulerColumn annotationRuler = new AnnotationRulerColumn(fAnnotationModel, 16, fAnnotationAccess);

    fCompositeRuler.setModel(fAnnotationModel);
    fOverviewRuler.setModel(fAnnotationModel);

    // annotation ruler is decorating the composite ruler
    fCompositeRuler.addDecorator(0, annotationRuler);

    // add what types are show on the different rulers
    annotationRuler.addAnnotationType(ERROR_TYPE);
    fOverviewRuler.addAnnotationType(ERROR_TYPE);
    fOverviewRuler.addHeaderAnnotationType(ERROR_TYPE);

    // set what layer this type is on
    fOverviewRuler.setAnnotationTypeLayer(ERROR_TYPE, 3);

    // set what color is used on the overview ruler for the type
    fOverviewRuler.setAnnotationTypeColor(ERROR_TYPE, new Color(Display.getDefault(), ERROR_RGB));

    // source viewer
    viewer = new SourceViewer(parent, ruler, fOverviewRuler, true, SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL);

    // Configure it and set the document
    undoManager = new TextViewerUndoManager(100);
    undoManager.connect(viewer);

    fAnnotationModel.connect(document);
    viewer.setDocument(document, fAnnotationModel);

    setControl(viewer.getControl());
    viewer.configure(configuration);

    // hover manager that shows text when we hover
    AnnotationBarHoverManager fAnnotationHoverManager = new AnnotationBarHoverManager(fCompositeRuler, viewer,
        new AnnotationHover(), new AnnotationConfiguration());
    fAnnotationHoverManager.install(annotationRuler.getControl());

    // to paint the annotations
    AnnotationPainter ap = new AnnotationPainter(viewer, fAnnotationAccess);
    ap.addAnnotationType(ERROR_TYPE);
    // ap.addAnnotationType(ERROR_RGB2);
    // ap.setAnnotationTypeColor(ERROR_RGB2, new Color(Display.getDefault(),ERROR_RGB2));
    ap.setAnnotationTypeColor(ERROR_TYPE, new Color(Display.getDefault(), ERROR_RGB));

    // this will draw the squigglies under the text
    viewer.addPainter(ap);

    viewer.getTextWidget().addKeyListener(new KeyListener() {

      @Override      
      public void keyPressed(KeyEvent e) {
    	  int code = e.keyCode;
    	  if(code == 13){
    		  
    		   getUndoManager().undo();
    		   getUndoManager().redo();
    		   
    		   int ln = getCursorLocation();
    		   int totalNumberOfLines = viewer.getDocument().getNumberOfLines();
    		   
    		   	if(ln == totalNumberOfLines){
    			   	StyledText styledText = viewer.getTextWidget();
    			   	int charcount = viewer.getTextWidget().getCharCount();
    			   	styledText.setCaretOffset(charcount + 10);
    			}
    		}
    	}

      @Override
      public void keyReleased(KeyEvent arg0) {
        // System.out.println("keyReleased");
        statusUpdater.setStatus("Line: " + getCursorLocation());

      }
    });
    // added
    // inserted this as a fix to show the line numbers on moving the mouse
    // read line numbers using insert+PgDn
    viewer.getTextWidget().addMouseListener(new MouseListener() {

      @Override
      public void mouseDoubleClick(MouseEvent arg0) {
      }

      @Override
      public void mouseDown(MouseEvent arg0) {
        // System.out.println("MouseDown");
        statusUpdater.setStatus("Line: " + getCursorLocation());
      }

      @Override
      public void mouseUp(MouseEvent arg0) {
        // System.out.println("MouseUp");
        statusUpdater.setStatus("Line: " + getCursorLocation());
      }
    });

    // viewer;

    // part of the observer pattern to update all registered object after
    // changing preference
    // page

    // menuManager = createRightClickMenuManager(viewer.getTextWidget());
    // Menu menu = menuManager.createContextMenu(viewer.getTextWidget());
    // viewer.getTextWidget().setMenu(menu);

    // viewer.GAP_SIZE = 1 ;

    // Menu manager initialize
    // menuManager = createRightClickMenuManager(this.viewer.getTextWidget());
    //TODO: this looks important
//    update(null);
  }

  /**
   * Returns the offset of the given source viewer's document that corresponds to the given widget offset or
   * <code>-1</code> if there is no such offset.
   * 
   * @param viewer
   *          the source viewer
   * @param widgetOffset
   *          the widget offset
   * @return the corresponding offset in the source viewer's document or <code>-1</code>
   * @since 2.1
   */
  public int getCursorLocation() {
    // System.out.println("getLocation.......");
    int line = -1;
    if (viewer != null && document != null) {
      StyledText styledText = viewer.getTextWidget();
      // int caret=
      // viewer.widgetOffset2ModelOffset(styledText.getCaretOffset());
      int caret = styledText.getCaretOffset();
      // styledText.getCaretLine();
      IDocument document = viewer.getDocument();
      try {
        line = document.getLineOfOffset(caret) + 1;
      } catch (BadLocationException x) {
      }
    }

    return line;
  }

  public void insertString(String inputString) {
    if (viewer != null) {
      String translatedString = inputString;
      translatedString = translatedString.replace("\\=", "\r\n");
      translatedString = translatedString.replace("\\>", "\r\n\t");
      translatedString = translatedString.replace("\\<", "\r\n");
      viewer.getTextWidget().insert(translatedString);
    }
}

  public void setFont(FontData[] fontData) {
    // System.out.print("setFont");
    // Create the font
    Font temp = new Font(this.getDisplay(), fontData);

    // If creation succeeded, dispose the old font
    /*
     * if (font != null) font.dispose();
     */
    // Use the new font
    this.getViewer().getTextWidget().setFont(temp);
    this.ruler.setFont(temp);
  }

  public IUndoManager getUndoManager() {
    return undoManager;
  }
  
  public void setWrap(boolean wrap) {
    viewer.getTextWidget().setWordWrap(wrap);
  }

  public void setViewer(SourceViewer viewer) {
    this.viewer = viewer;
  }

  public SourceViewer getViewer() {
    return viewer;
  }

  public void setDocument(PersistentDocument document) {
    this.document = document;
  }

  public PersistentDocument getDocument() {
    return document;
  }

  protected void setUpDocument(File file) {
    // System.out.println("setUpDocument");
    try {
      // Create the document
      document = new PersistentDocument();
      if (file != null) {
        // System.out.println("file is not empty");
        setText(file.getName());
        document.setFileName(file.getPath());
        document.open();
      } else {
        setText("New File");
      }

      // Create the partition scanner
      scanner = new JBrickPartitionScanner();

      // Create the partitioner
      IDocumentPartitioner partitioner;
      partitioner = new FastPartitioner(scanner, JBrickPartitionScanner.TYPES);

      // Connect the partitioner and document
      document.setDocumentPartitioner(JBrickEditor.JBRICK_PARTITIONING, partitioner);
      partitioner.connect(document);
    } catch (IOException e) {
      JOptionPane.showMessageDialog(null, e.getMessage(), "File Not Found!", 1);
    }
  }

  // TODO: uncomment? Somehow.
  // protected MenuManager createRightClickMenuManager(Composite parent) {
  // MenuManager rightMenuBar = new MenuManager();
  // rightMenuBar.add(new com.jbricx.actions.UndoAction());
  // rightMenuBar.add(new com.jbricx.actions.RedoAction());
  // rightMenuBar.add(new Separator());
  // rightMenuBar.add(new com.jbricx.actions.CutAction());
  // rightMenuBar.add(new com.jbricx.actions.CopyAction());
  // rightMenuBar.add(new com.jbricx.actions.PasteAction());
  // rightMenuBar.add(new Separator());
  // rightMenuBar.add(new com.jbricx.actions.SelectAllAction());
  // Menu menu = rightMenuBar.createContextMenu(parent);
  // // Right Click Attach
  // parent.setMenu(menu);
  // return rightMenuBar;
  // }

  class ColorCache implements ISharedTextColors {

    public Color getColor(RGB rgb) {
      return new Color(Display.getDefault(), rgb);
    }

    public void dispose() {
    }
  }

  @Override
  public void update(final IPreferenceStore store) {
    //TODO: even though this works, why is it been read from the preference store?
    RGB bgRBG = PreferenceConverter.getColor(store, ColorFor.BACKGROUND.property());
    RGB fgRBG = PreferenceConverter.getColor(store, ColorFor.FOREGROUND.property());

    String fontProp = store.getString(FileExtensionConstants.FONT);
    if (fontProp.length() > 0) { /* Check if the font is available */
      FontData[] fd = new FontData[1];
      fd[0] = new FontData(fontProp);
      this.setFont(fd);
    }
    if (bgRBG != fgRBG) {/* Check if the colors are available */

      Color bgColor = new Color(getDisplay(), bgRBG);
      lnrc.setForeground(new Color(getDisplay(), PreferenceConverter.getColor(store, ColorFor.LINENUMBERFG.property())));
      lnrc.setBackground(new Color(getDisplay(), PreferenceConverter.getColor(store, ColorFor.LINENUMBERBG.property())));

      // Color fgColor = new
      // Color(JBrickEditor.getMainWindow().getShell().getDisplay(),fgRBG);
      viewer.getTextWidget().setBackground(bgColor);
      // Notes ; fore color will be changed though jbrickcode scanner and
      // colormanager
      // viewer.setTextColor(fgColor);
    }
    // viewer.refresh() ;
  }

  public String getFilename() {
    if (file == null) {
      return null;
    }
    return this.file.getAbsolutePath();
  }

  public void setFile(File file) {
    this.file = file;
  }
}
