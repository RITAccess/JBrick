package com.jbricx.actions;

import org.eclipse.jface.action.Action;

import com.jbricx.ui.methodTemplate.MethodTemplateUIWindow;

/**
 * This class shows the Method Template dialog box
 * 
 * @author Priya Sankaran
 */
public class MethodTemplateAction extends Action {
	/**
	 * AboutAction constructor
	 */
	public MethodTemplateAction() {
		super("&MethodTemplate@Ctrl+M");
		setToolTipText("Method Template");
	}

	/**
	 * Shows an about box
	 */
	public void run() {
		MethodTemplateUIWindow methodTemplate = new MethodTemplateUIWindow();
		methodTemplate.setBlockOnOpen(true);
		methodTemplate.open();

	}
}
