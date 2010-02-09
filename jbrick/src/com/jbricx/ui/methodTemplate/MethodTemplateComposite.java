package com.jbricx.ui.methodTemplate;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.swt.widgets.TreeItem;

import com.jbricx.pjo.JBrickEditor;

public class MethodTemplateComposite extends org.eclipse.swt.widgets.Composite {

	public MethodTemplateComposite(Composite parent, int style) {
		super(parent, style);
	}

	public static void main(String[] args) throws IOException {
		showGUI();
	}

	/**
	 * Overriding checkSubclass allows this class to extend
	 * org.eclipse.swt.widgets.Composite
	 */
	protected void checkSubclass() {
	}

	/**
	 * Creates the main window's contents
	 * 
	 * @param shell
	 *            the main window
	 * @throws IOException
	 */
	static Display display = Display.getDefault();

	public static void showGUI() throws IOException {

		final Shell shell = new Shell(display);
		shell.setLayout(new FillLayout());

		String key;

		File f = new File("src/com/jbricx/ui/methodTemplate/Programs.txt");

		BufferedReader input = new BufferedReader(new FileReader(f));

		String[] itemHead = new String[] { "Programs", "Debugging",
				"If statements", "Loops etc...", "Outputs", "Timing",
				"Sensors", "Sensor types", "Sensor modes", "Sensor misc",
				"Digital Sensors", "Multitasking", "Sounds", "Drawing",
				"Buttons", "Misc stuff", "Strings", "File IO", "Messaging",
				"Low-level System Calls", "HiTechnic API functions",
				"Mindsensor API functions" };

		final Tree tree = new Tree(shell, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		tree.setHeaderVisible(true);
		TreeColumn methodTemplate = new TreeColumn(tree, SWT.LEFT);
		methodTemplate.setText("Method Templates");
		methodTemplate.setWidth(100);
		
		tree.addListener(SWT.MouseDown, new Listener() {
			@Override
			public void handleEvent(Event event) {
				// TODO Auto-generated method stub
				System.out.println("Mouse: " );
				Point point = new Point(event.x, event.y);
				TreeItem sub = tree.getItem(point);
				if (sub != null) {
					//System.out.println("Mouse down: " + sub.getText());
					JBrickEditor.getMainWindow().getCurrentTabItem().insertString(sub.getText());
				}
			}
		});

		TreeItem item = null ;
		while ((key = input.readLine()) != null) {
			System.out.println( key );
			if ( key.substring(0,2).compareTo("- ") == 0){ /* Next Tree */
				item = new TreeItem(tree, SWT.NONE);
				item.setText(new String[] { key });
			}
			else{ /* Sub Tree */
				if (item != null){
					TreeItem subItem = new TreeItem(item, SWT.NONE);
					subItem.setText(key);
				}
			}
			
		}

/*		for (int i = 0; i < 1; i++) {

			TreeItem item = new TreeItem(tree, SWT.NONE);
			item.setText(new String[] { itemHead[0] });

			while ((key = input.readLine()) != null) {
				TreeItem subItem = new TreeItem(item, SWT.NONE);
				subItem.setText(0, key);
			}
		}
*/		input.close();
		shell.pack();
		shell.open();

		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		display.dispose();
	}

}
