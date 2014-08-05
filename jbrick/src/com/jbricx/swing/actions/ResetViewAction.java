package com.jbricx.swing.actions;

import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;
import javax.swing.JSplitPane;

import com.jbricx.swing.ui.JBricxManager;

@SuppressWarnings("serial")
public class ResetViewAction extends JBricxAbstractAction{

	/**
	 * resets the view for jbricks
	 * @param manager
	 */
	public ResetViewAction(JBricxManager manager) {
		super("", new ImageIcon(ResetViewAction.class.getResource("/icons/resetView.png")), manager);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub 
		JSplitPane leftRightPane = getManager().getSplitPane();
		JSplitPane upDownPane = (JSplitPane) leftRightPane.getRightComponent();
		
		leftRightPane.resetToPreferredSizes();
		leftRightPane.setDividerSize(5);
		leftRightPane.setDividerLocation(250);
		upDownPane.resetToPreferredSizes();
		upDownPane.setDividerSize(5);
		
	}

}
