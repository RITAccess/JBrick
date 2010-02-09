package com.jbricx.actions;

/*
 * @author Priya Sankaran
 */
import org.eclipse.jface.action.Action;

import com.jbricx.ui.methodTemplate.MethodTemplateUIWindow;

/**
 * This class shows an About box
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
