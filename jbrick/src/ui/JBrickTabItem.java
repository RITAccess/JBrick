package ui;

import model.PersistentDocument;

import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.text.DefaultUndoManager;
import org.eclipse.jface.text.IDocumentPartitioner;
import org.eclipse.jface.text.IUndoManager;
import org.eclipse.jface.text.rules.DefaultPartitioner;
import org.eclipse.jface.text.source.CompositeRuler;
import org.eclipse.jface.text.source.LineNumberRulerColumn;
import org.eclipse.jface.text.source.SourceViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabFolderAdapter;
import org.eclipse.swt.custom.CTabFolderEvent;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;

import pjo.JBrickEditor;
import source.JBrickEditorSourceViewerConfiguration;
import source.JBrickPartitionScanner;

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

	
	/**
 * 
 * @param parent
 * @param style
 * @param fileName
 */
	public JBrickTabItem(CTabFolder parent, int style, String fileName) {
		super(parent, style);
		if (fileName == null){
			setText("New File") ;			
		}
		else{
			setText(fileName) ;			
		}
		
		undoManager = new DefaultUndoManager(100);
		undoManager.connect(viewer);

		// TODO Auto-generated constructor stub
		// Create the viewer
		// Set up the document
		
		System.out.println("1.1");

		setUpDocument();
		System.out.println("1.2");

		ruler = new CompositeRuler(10);
		lnrc = new LineNumberRulerColumn();
		lnrc.setForeground(new Color(parent.getShell().getDisplay(), new RGB(255, 0, 0)));
		ruler.addDecorator(0, lnrc);

		System.out.println("1.3");
		viewer = new SourceViewer(parent, ruler, SWT.V_SCROLL | SWT.H_SCROLL);
		// Configure it and set the document

		setControl(viewer.getControl());
		viewer.configure(new JBrickEditorSourceViewerConfiguration());
		viewer.setDocument(document);

		
		System.out.println("1.4");

		menuManager = createRightClickMenuManager(viewer.getTextWidget());
		Menu menu = menuManager.createContextMenu(viewer.getTextWidget());
		viewer.getTextWidget().setMenu(menu);

		System.out.println("1.5");

		// viewer.GAP_SIZE = 1 ;

		// Menu manager initialize
		menuManager = createRightClickMenuManager(this.viewer.getTextWidget());

//		viewer.getTextWidget().setFocus();

		// TODO Add close event for tab close
/*		this.addCTabFolderListener(new CTabFolderAdapter(){
            public void itemClosed(CTabFolderEvent event) {
                    System.out.println("itemClosed() is invoked.");
                    System.out.println("event: " + event);
                    if (event.item.equals(uncloseableItem)){
                            event.doit = false;
                    }
            }*/
//    });

		System.out.println("1.6");

	
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
	
	protected void setUpDocument() {
		// Create the document
		document = new PersistentDocument();

		// Create the partition scanner
		scanner = new JBrickPartitionScanner();

		// Create the partitioner
		IDocumentPartitioner partitioner = new DefaultPartitioner(scanner,
				JBrickPartitionScanner.TYPES);

		// Connect the partitioner and document
		document.setDocumentPartitioner(JBrickEditor.JBRICK_PARTITIONING, partitioner);
		partitioner.connect(document);
	}
	
	protected MenuManager createRightClickMenuManager(Composite parent) {
		MenuManager rightMenuBar = new MenuManager();
		rightMenuBar.add(new actions.UndoAction());
		rightMenuBar.add(new actions.RedoAction());
		rightMenuBar.add(new Separator());
		rightMenuBar.add(new actions.CutAction());
		rightMenuBar.add(new actions.CopyAction());
		rightMenuBar.add(new actions.PasteAction());
		Menu menu = rightMenuBar.createContextMenu(parent);
		// Right Click Attach
		parent.setMenu(menu);
		return rightMenuBar;
	}
}
