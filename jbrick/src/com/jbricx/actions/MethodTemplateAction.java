package com.jbricx.actions;

import com.jbricx.ui.JBrickManager;
import com.jbricx.ui.methodTemplate.MethodTemplateComposite;

/**
 * This class shows the Method Template dialog box
 * 
 * @author Priya Sankaran
 */
public class MethodTemplateAction extends AbstractAction {
	/**
	 * AboutAction constructor
	 */
	public MethodTemplateAction(final JBrickManager manager) {
		super("&MethodTemplate@Ctrl+M", manager);
		setToolTipText("Method Template");
	}

	/**
	 * Shows an about box
	 */
	public void run() {
//		MethodTemplateUIWindow methodTemplate = new MethodTemplateUIWindow();
//		methodTemplate.setBlockOnOpen(true);
//		methodTemplate.open();

		MethodTemplateComposite.getInstance(getManager().getTabFolder());
	}
}
