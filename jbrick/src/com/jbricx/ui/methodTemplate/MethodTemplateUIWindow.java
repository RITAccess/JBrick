package com.jbricx.ui.methodTemplate;

import java.awt.Composite;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import com.jbricx.ui.tabs.TabFolder;

/**
 * @author Priya Sankaran
 * @author byktol
 */
public class MethodTemplateUIWindow extends ApplicationWindow {

  private static MethodTemplateUIWindow instance = null;
  private static TabFolder ftabFolder = null;

  public static MethodTemplateUIWindow getInstance(final TabFolder tabFolder) {
    if (instance == null) {
      ftabFolder = tabFolder;
      instance = new MethodTemplateUIWindow();
    }
    return instance;
  }

  public MethodTemplateUIWindow() {
    super(Display.getCurrent().getActiveShell());
  }

  @Override
  protected Control createContents(Composite parent) {
    getShell().setText("Method Template");

    parent.setSize(300, 410);

    String key;

    File f = new File("src/com/jbricx/ui/methodTemplate/Programs.txt");

    BufferedReader input = null;
    try {
      input = new BufferedReader(new FileReader(f));
      final Tree tree = new Tree(getShell(), SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
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
            ftabFolder.insertText(sub.getText());
          }
        }
      });

      TreeItem item = null;
      Font initialFont = tree.getFont();
      FontData[] fontData = initialFont.getFontData();
      for (int i = 0; i < fontData.length; i++) {
        fontData[i].setHeight(14);
      }

      Font newFont = new Font(getShell().getDisplay(), fontData);
      tree.setFont(newFont);

      while ((key = input.readLine()) != null) {

        if (2 <= key.length() && key.substring(0, 2).compareTo("- ") == 0
                || key.substring(0, 2).compareTo("| ") == 0) {
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
    } catch (Exception e) {
      e.printStackTrace();
    }

    return parent;
  }

}
