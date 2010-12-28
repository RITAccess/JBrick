package com.jbricx.ui.methodTemplate;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.swt.widgets.TreeItem;

import com.jbricx.ui.tabs.TabFolder;

/**
 * @author Priya Sankaran
 * @author Yuji Fujiki
 * @author byktol
 */
public class MethodTemplateComposite {

  private static TabFolder tabFolder;
  private static MethodTemplateComposite instance = new MethodTemplateComposite();
  private static Display display = Display.getDefault();

  private MethodTemplateComposite() { }

  public static MethodTemplateComposite getInstance(final TabFolder tabFolder) {
    MethodTemplateComposite.tabFolder = tabFolder;

    return instance;
  }

  /**
   * Overriding checkSubclass allows this class to extend org.eclipse.swt.widgets.Composite
   */
  protected void checkSubclass() {
  }

  /**
   * Creates the main window's contents
   * 
   * @param shell
   *          the main window
   * @throws IOException
   */
  public void showGUI() throws IOException {

    final Shell shell = new Shell(display);
    shell.setLayout(new FillLayout());
    shell.setText("Method Template");
    String key;

    File f = new File("src/com/jbricx/ui/methodTemplate/Programs.txt");

    BufferedReader input = new BufferedReader(new FileReader(f));
    final Tree tree = new Tree(shell, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
    tree.setHeaderVisible(true);
    TreeColumn methodTemplate = new TreeColumn(tree, SWT.LEFT);
    methodTemplate.setWidth(220);

    tree.addListener(SWT.DefaultSelection, new Listener() {
      /* DefaultSelection = Select by MouseClick or Enter key */
      @Override
      public void handleEvent(Event event) {
        TreeItem sub = (TreeItem) event.item;
        if (sub != null && sub.getItemCount() == 0) {
          /* This is the lowest level of the tree */
          tabFolder.insertText(sub.getText());
        }
      }
    });

    TreeItem item = null;
    Font initialFont = tree.getFont();
    FontData[] fontData = initialFont.getFontData();
    for (int i = 0; i < fontData.length; i++) {
      fontData[i].setHeight(14);
    }
    Font newFont = new Font(display, fontData);
    tree.setFont(newFont);

    while ((key = input.readLine()) != null) {

      if (2 <= key.length() && key.substring(0, 2).compareTo("- ") == 0 || key.substring(0, 2).compareTo("| ") == 0) {
        /* Next Tree */

        item = new TreeItem(tree, SWT.NONE);
        item.setText(new String[] { key });
      } else { /* Sub Tree */
        if (item != null) {
          TreeItem subItem = new TreeItem(item, SWT.NONE);
          subItem.setText(key);

        }
      }
    }

    input.close();
    shell.pack();
    shell.open();

    while (!shell.isDisposed()) {
      if (!display.readAndDispatch()) {
        display.sleep();
      }
    }
    shell.close();

  }

}
