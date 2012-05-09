//package com.jbricx.swing.actions;
//
//import com.jbricx.ui.JBrickManager;
//import com.jbricx.ui.methodTemplate.MethodTemplateUIWindow;
//
///**
// * This class shows the Method Template dialog box
// * 
// * @author Priya Sankaran
// */
//public class MethodTemplateAction extends AbstractAction {
//	/**
//	 * AboutAction constructor
//	 */
//	public MethodTemplateAction(final JBrickManager manager) {
//		super("&MethodTemplate@Ctrl+M", manager);
//		setToolTipText("Method Template");
//	}
//
//	/**
//	 * Shows an about box
//	 */
//	public void run() {
//		MethodTemplateUIWindow methodTemplate =
//		  MethodTemplateUIWindow.getInstance(getManager().getTabFolder());
//		methodTemplate.setBlockOnOpen(true);
//		methodTemplate.open();
//	}
//}
