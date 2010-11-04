package com.jbricx.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;

import com.jbricx.pjo.FileExtensionConstants;
import com.jbricx.pjo.JBrickEditor;

/**
 * This action class responds to requests open a file
 */
public class OpenAction extends Action {

	/**
	 * OpenAction constructor
	 */
	public OpenAction() {
		super("&Open...@Ctrl+O", ImageDescriptor.createFromFile(OpenAction.class,
				"/images/document-open.png"));
		setToolTipText("Open");
	}

	/**
	 * Opens an existing file
	 */
	public void run() {
		// Use the file dialog
		FileDialog dlg = new FileDialog(JBrickEditor.getInstance().getMainWindow().getShell(), SWT.OPEN);
		dlg.setFilterNames(FileExtensionConstants.FILTER_NAMES);
		dlg.setFilterExtensions(FileExtensionConstants.FILTER_EXTENSIONS);
		String fileName = dlg.open();
		
		if (fileName != null) {
			JBrickEditor.getInstance().getMainWindow().openFile(fileName);
		}
	}
}
