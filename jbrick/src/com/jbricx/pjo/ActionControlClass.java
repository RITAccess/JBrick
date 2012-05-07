package com.jbricx.pjo;

import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.jbricx.swing.ui.JBricxManager;
import com.jbricx.swing.ui.SafeSaveDialog;
import com.jbricx.swing.ui.tabs.JBricxTabItem;


/**
 * 
 * @author Daniel Larsen 
 * 
 * Deals with errors from file saving and presenting them to the user.
 * 
 * Depending on whether save or save as will prompt for a file dialog box (SafeSaveDialog, which handles overwriting conditions)
 * 
 * 
 *
 */
public class ActionControlClass {

	/**
	 * 
	 * @param tabItem Tab Item to save
	 * @param isSaveAs Whether the user requested Save(false) or Save As(true)
	 * @param manager Main Manager for reference.
	 */
    public static boolean saveFile(JBricxTabItem tabItem, boolean isSaveAs, final JBricxManager manager) {
        
    	String fileLocation = tabItem.getFileAbsolutePath();

        boolean isNewFile = false;


        if (isSaveAs || tabItem.isNewFile()) {
            SafeSaveDialog dlg = new SafeSaveDialog(manager);
            fileLocation = dlg.open();
            isNewFile = true;
        }
        try {
            /*
             * trigger save only if changes have been made to editor or SaveAs
             * is perfomed or is a newly opened file
             */
            if (isSaveAs || tabItem.isDirty() || isNewFile) {
                if (isSaveAs) { // remove the currently selected file from the list
//                    try {
//                        int currFileIndex = manager.getTabFolder().getSelectedIndex();
//                        manager.getTabFolder().closeFile(currFileIndex);
//                    } catch (NullPointerException ne) {
//                    }
                }
                //Save as save
                if(isSaveAs|| isNewFile){
                	tabItem.saveAs(fileLocation);
                //Save save
                }else{
                	tabItem.save();
                }
                tabItem.setNewFile(false);
                manager.refreshExplorerContent();
                return true;
            } 
        } catch (NullPointerException ne) {
        	return false;
            // user has opted to cancel the save dialog
        } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
    }

    public static void showError(JFrame shell, String msg) {
    	JOptionPane.showMessageDialog(shell,
    			"Error" ,
    			msg,
    		    JOptionPane.WARNING_MESSAGE);
    }
}
