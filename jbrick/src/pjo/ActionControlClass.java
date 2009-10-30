package pjo;

import java.io.IOException;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;

import ui.JBrickTabItem;
import ui.SafeSaveDialog;

public class ActionControlClass {

	public static void saveFile(JBrickTabItem TabItem) {
		String fileName = TabItem.getDocument().getFileName();
		if (fileName == null) {
			SafeSaveDialog dlg = new SafeSaveDialog(TabItem.getParent().getShell());
			dlg.setFilterNames(FileExtensionConstants.FILTER_NAMES);
			dlg.setFilterExtensions(FileExtensionConstants.FILTER_EXTENSIONS);
			fileName = dlg.open();
		}
		if (fileName != null)
			try {
				TabItem.getDocument().setFileName(fileName);
				TabItem.getDocument().save();
				TabItem.setText(fileName);
			} catch (IOException e) {
				showError(TabItem.getParent().getShell(), "Can't save file " + fileName + "; " + e.getMessage());
			}
	}
	public static void showError(Shell shell, String msg) {
		MessageDialog.openError(shell, "Error", msg);
	}
	

}
