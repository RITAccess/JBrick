package com.jbricx.ui;

import java.io.File;
import java.io.IOException;

import javax.swing.JOptionPane;


import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.text.DefaultUndoManager;
import org.eclipse.jface.text.IDocumentPartitioner;
import org.eclipse.jface.text.IUndoManager;
import org.eclipse.jface.text.TextViewerUndoManager;
import org.eclipse.jface.text.rules.DefaultPartitioner;
import org.eclipse.jface.text.rules.FastPartitioner;
import org.eclipse.jface.text.source.*;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabFolder2Adapter;
import org.eclipse.swt.custom.CTabFolderEvent;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;

import com.jbricx.model.PersistentDocument;
import com.jbricx.pjo.JBrickEditor;
import com.jbricx.source.JBrickEditorSourceViewerConfiguration;
import com.jbricx.source.JBrickPartitionScanner;


public class JBrickTabItem extends CTabItem {
	
	private CompositeRuler ruler;
	private LineNumberRulerColumn lnrc;
	// The partition scanner
	private JBrickPartitionScanner scanner;
	// Right Click Menu
	private MenuManager menuManager;
	// The undo manager
	private IUndoManager undoManager;
	// The viewer
	private SourceViewer viewer;
	// The current document
	private PersistentDocument document;
	
	File file;

	
	/**
 * 
 * @param parent
 * @param style
 * @param fileName
 */
	//public JBrickTabItem(CTabFolder parent, int style, String fileName) {
	public JBrickTabItem(CTabFolder parent, int style, File file) {
		super(parent, style);
		/*if (fileName == null){
			setText("New File") ;	
			
		}
		else{
			setText(fileName) ;			
		}*/
		this.file = file;
		
		undoManager = new TextViewerUndoManager(100);
		undoManager.connect(viewer);

		// TODO Auto-generated constructor stub
		// Create the viewer
		// Set up the document
		
		setUpDocument(file);
		
		ruler = new CompositeRuler(10);
		lnrc = new LineNumberRulerColumn();
		lnrc.setForeground(new Color(parent.getShell().getDisplay(), new RGB(255, 0, 0)));
		ruler.addDecorator(0, lnrc);

		//annotation ruler to view annotation
		IOverviewRuler overviewRuler = new OverviewRuler(null, 12, new ColorCache());
		
		viewer = new SourceViewer(parent, ruler,overviewRuler, true , SWT.V_SCROLL | SWT.H_SCROLL);
		// Configure it and set the document

		setControl(viewer.getControl());
		viewer.configure(new JBrickEditorSourceViewerConfiguration());
		viewer.setDocument(document);
		


		menuManager = createRightClickMenuManager(viewer.getTextWidget());
		Menu menu = menuManager.createContextMenu(viewer.getTextWidget());
		viewer.getTextWidget().setMenu(menu);

		// viewer.GAP_SIZE = 1 ;

		// Menu manager initialize
		menuManager = createRightClickMenuManager(this.viewer.getTextWidget());

	
	}
	
	public void setFont(FontData[] fontData) {
		// Create the font
		Font temp = new Font(this.getDisplay(), fontData);

		// If creation succeeded, dispose the old font
/*		if (font != null)
			font.dispose();
*/
		// Use the new font
		this.getViewer().getTextWidget().setFont(temp);
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
		try{
		// Create the document
		document = new PersistentDocument();
		if(file != null){
			setText(file.getName());
			document.setFileName(file.getPath());
			document.open();
		}else{
			setText("New File") ;
		}

		// Create the partition scanner
		scanner = new JBrickPartitionScanner();

		// Create the partitioner
		IDocumentPartitioner partitioner; 
		partitioner = new FastPartitioner(scanner, JBrickPartitionScanner.TYPES);

		// Connect the partitioner and document
		document.setDocumentPartitioner(JBrickEditor.JBRICK_PARTITIONING, partitioner);
		partitioner.connect(document);
		}
		catch(IOException e){
			JOptionPane.showMessageDialog(null, e.getMessage(), ":)", 1);
		}
	}
	
	protected MenuManager createRightClickMenuManager(Composite parent) {
		MenuManager rightMenuBar = new MenuManager();
		rightMenuBar.add(new com.jbricx.actions.UndoAction());
		rightMenuBar.add(new com.jbricx.actions.RedoAction());
		rightMenuBar.add(new Separator());
		rightMenuBar.add(new com.jbricx.actions.CutAction());
		rightMenuBar.add(new com.jbricx.actions.CopyAction());
		rightMenuBar.add(new com.jbricx.actions.PasteAction());
		Menu menu = rightMenuBar.createContextMenu(parent);
		// Right Click Attach
		parent.setMenu(menu);
		return rightMenuBar;
	}
	class ColorCache implements ISharedTextColors {
		public Color getColor(RGB rgb) {
			return new Color(Display.getDefault(), rgb);
		}

		public void dispose() {
		}
	}
}
