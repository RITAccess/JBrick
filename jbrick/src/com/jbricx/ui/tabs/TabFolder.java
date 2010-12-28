/**
 * 
 */
package com.jbricx.ui.tabs;

import org.eclipse.swt.custom.CTabFolder2Adapter;
import org.eclipse.swt.custom.CTabItem;


/**
 * @author byktol
 */
public interface TabFolder {

    boolean open(final String filename);

    void closeFile(String filename);

    boolean openNewFile();

    boolean checkOverwrite();

    JBrickTabItem getSelection();

    void addCTabFolder2Listener(CTabFolder2Adapter cTabFolder2Adapter);

    void setMaximized(boolean b);

    void setMinimized(boolean b);

    void setSelection(int selectedIndex);

    CTabItem[] getItems();

    int getSelectionIndex();

    void saveFile(String filePath);

    void insertText(String text);

    int contains(String fileName);
}
