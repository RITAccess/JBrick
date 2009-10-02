package guiPackage;

/*
 * ToolBar example snippet: Place a drop down menu in a tool bar
 *
 * For a list of all SWT example snippets see
 * http://dev.eclipse.org/viewcvs/index.cgi/%7Echeckout%7E/platform-swt-home/dev.html#snippets
 */
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

import code.BorderLayout;

public class MainGUIClass {

	public static void main(String[] args) {
		MainGUIClass mainClass = new MainGUIClass();
		mainClass.displayMainMenu();

	}

	/**
	 * a method that creates and display the main window of the application
	 */
	public void displayMainMenu() {
		// create the window
		final Display display = new Display();
		final Shell shell = new Shell(display);

		shell.setLayout(new BorderLayout());
		// set the application title
		shell.setText("JBrix");

		// Create the menuBar
		Menu menu = new Menu(shell, SWT.BAR);

		// Create all the items in the bar menu
		MenuItem fileItem = new MenuItem(menu, SWT.CASCADE);
		fileItem.setText("File");
		MenuItem editItem = new MenuItem(menu, SWT.CASCADE);
		editItem.setText("Edit");
		MenuItem formatItem = new MenuItem(menu, SWT.CASCADE);
		formatItem.setText("TODO");
		MenuItem viewItem = new MenuItem(menu, SWT.CASCADE);
		viewItem.setText("TODO");
		MenuItem helpItem = new MenuItem(menu, SWT.CASCADE);
		helpItem.setText("Help");
		// ======== FILE =============================
		Menu fileMenu = new Menu(menu);
		fileItem.setMenu(fileMenu);

		// Create all the items in the File dropdown menu
		MenuItem newItem = new MenuItem(fileMenu, SWT.NONE);
		newItem.setText("New");
		newItem.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				// TODO: Add implementation here
			}
		});

		MenuItem openItem = new MenuItem(fileMenu, SWT.NONE);
		openItem.setText("Open...");
		openItem.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				// TODO: Add implementation here
			}
		});

		MenuItem saveItem = new MenuItem(fileMenu, SWT.NONE);
		saveItem.setText("Save");
		saveItem.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				// TODO: Add implementation here
			}
		});

		MenuItem saveAsItem = new MenuItem(fileMenu, SWT.NONE);
		saveAsItem.setText("Save As...");
		saveAsItem.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				// TODO: Add implementation here
			}
		});

		// create a separator
		new MenuItem(fileMenu, SWT.SEPARATOR);

		MenuItem printItem = new MenuItem(fileMenu, SWT.NONE);
		printItem.setText("Print...");
		printItem.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				// TODO: Add implementation here
			}
		});

		// create a separator
		new MenuItem(fileMenu, SWT.SEPARATOR);

		MenuItem exitItem = new MenuItem(fileMenu, SWT.NONE);
		exitItem.setText("Exit");
		exitItem.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				// TODO: Add implementation here to execute the code if this
				// item is clicked
			}
		});
		// +++++++ End FILE +++++++++++++++++++++++
		// ======== EDIT =============================
		Menu editMenu = new Menu(menu);
		editItem.setMenu(editMenu);

		// Create all the items in the Edit dropdown menu
		MenuItem undoItem = new MenuItem(editMenu, SWT.NONE);
		undoItem.setText("Undo");
		undoItem.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				// TODO: Add implementation here
			}
		});

		MenuItem redoItem = new MenuItem(editMenu, SWT.NONE);
		redoItem.setText("Redo");
		redoItem.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				// TODO: Add implementation here
			}
		});

		MenuItem copyItem = new MenuItem(editMenu, SWT.PUSH);
		copyItem.setText("Copy");
		copyItem.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				// TODO: Add implementation here
			}
		});

		MenuItem cutItem = new MenuItem(editMenu, SWT.PUSH);
		cutItem.setText("Cut");
		cutItem.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				// TODO: Add implementation here
			}
		});

		MenuItem pasteItem = new MenuItem(editMenu, SWT.PUSH);
		pasteItem.setText("Paste");
		pasteItem.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				// TODO: Add implementation here
			}
		});

		MenuItem deleteItem = new MenuItem(editMenu, SWT.PUSH);
		deleteItem.setText("Delete");
		deleteItem.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				// TODO: Add implementation here
			}
		});

		MenuItem findItem = new MenuItem(editMenu, SWT.PUSH);
		findItem.setText("Find ...");
		findItem.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				// TODO: Add implementation here
			}
		});
		
		// create tree for the left navigator
		TabFolder tabFolder = new TabFolder(shell, SWT.BORDER);
		//TODO: change tabs names and content
		//tab1
		TabItem tabItem = new TabItem(tabFolder, SWT.NULL);
		tabItem.setText("Tab #1");

		Text text = new Text(tabFolder, SWT.NULL);
		text.setText("This is tab 1 content");
		tabItem.setControl(text);
		//tab2
		TabItem tabItem2 = new TabItem(tabFolder, SWT.NULL);
		tabItem2.setText("Tab #2");

		Text text2 = new Text(tabFolder, SWT.NULL);
		text2.setText("This is tab 2 content");
		tabItem2.setControl(text2);
		//set tabs layout to be in the left side
		tabFolder.setLayoutData(new BorderLayout.BorderData(BorderLayout.WEST));

		//create the main editor text area
		Text editorTxt = new Text(shell, SWT.WRAP 
										| SWT.MULTI 
										| SWT.BORDER
										| SWT.H_SCROLL 
										| SWT.V_SCROLL);
		editorTxt.setLayoutData(new BorderLayout.BorderData(BorderLayout.CENTER));

		
		// Set the bar menu as the menu in the shell
		shell.setMenuBar(menu);

		shell.setSize(600, 300);

		// toolBar.pack();
		// shell.pack();
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		// fileMenu.dispose();
		// editMenu.dispose();
		display.dispose();

	}
}