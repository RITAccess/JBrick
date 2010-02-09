package com.jbricx.ui.methodTemplate;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.swt.widgets.TreeItem;

public class MethodTemplateComposite extends Composite {

	public MethodTemplateComposite(Composite parent, int style) {
		super(parent, style);
	}

	public static void main(String[] args) throws IOException {
		showGUI();
	}

	/**
	 * Creates the main window's contents
	 * 
	 * @param shell
	 *            the main window
	 * @throws IOException
	 */
	public static void showGUI() throws IOException {

		Display display = new Display();
		final Shell shell = new Shell(display);
		shell.setLayout(new FillLayout());

		String key;

		File f = new File(
				"C://Priya_personal//java//JBrick//jbrick//src//com//jbricx//ui//methodTemplate//Program.properties");

		FileInputStream in = new FileInputStream(f);
		Properties properties = new Properties();
		properties.load(in);

		String[] itemHead = new String[] { "Programs", "Debugging",
				"If statements", "Loops etc...", "Outputs", "Timing",
				"Sensors", "Sensor types", "Sensor modes", "Sensor misc",
				"Digital Sensors", "Multitasking", "Sounds", "Drawing",
				"Buttons", "Misc stuff", "Strings", "File IO", "Messaging",
				"Low-level System Calls", "HiTechnic API functions",
				"Mindsensor API functions" };

		Tree tree = new Tree(shell, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		tree.setHeaderVisible(true);
		TreeColumn methodTemplate = new TreeColumn(tree, SWT.LEFT);
		methodTemplate.setText("Method Templates");
		methodTemplate.setWidth(100);
		for (int i = 0; i < 1; i++) {

			TreeItem item = new TreeItem(tree, SWT.NONE);
			item.setText(new String[] { itemHead[0] });

			Enumeration<?> e = properties.propertyNames();
			while (e.hasMoreElements()) {
				TreeItem subItem = new TreeItem(item, SWT.NONE);
				key = (String) e.nextElement();
				String keyActual = key;
				key = key.substring(2);
				subItem.setText(new String[] { key });
				System.out.println(key + " " + properties.getProperty(keyActual));
			}
		}

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
