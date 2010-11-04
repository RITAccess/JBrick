package com.jbricx.pjo;

import java.io.IOException;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;

import com.jbricx.ui.JBrickTabItem;
import com.jbricx.ui.SafeSaveDialog;
import java.io.File;

public class ActionControlClass {

	public static void saveFile(JBrickTabItem tabItem, boolean isSaveAs) {
		String fileLocation = tabItem.getDocument().getFileName();

		if (isSaveAs || fileLocation == null) {
			SafeSaveDialog dlg = new SafeSaveDialog(tabItem.getParent().getShell());
			fileLocation = dlg.open();
		}

		try {
			String filename = new File(fileLocation).getName(); // just the name of the file

			tabItem.getDocument().setFileName(fileLocation);
			tabItem.getDocument().save();
			tabItem.setText(filename);// to show the filename in the tab
			
			JBrickEditor.getInstance().getMainWindow().saveFile(filename);
			System.out.println("saving ...");
			JBrickEditor.getInstance().getMainWindow().refresh_2();
			System.out.println("refresh the folder");
			
		} catch (IOException e) {
			showError(tabItem.getParent().getShell(), "Can't save file " + fileLocation + "; " + e.getMessage());
		} catch (NullPointerException ne) {
			// user has opted to cancel the save dialog
		}
	}

	public static void showError(Shell shell, String msg) {
		MessageDialog.openError(shell, "Error", msg);
	}
}
