package com.jbricx.swing.actions;

import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;
import javax.swing.JSplitPane;

import com.jbricx.swing.ui.JBricxManager;

@SuppressWarnings("serial")
public class MaxViewerAction extends JBricxAbstractAction{

	/**
	 * maximize the viewer
	 * @param manager
	 */
	public MaxViewerAction(final JBricxManager manager) {
		super("", new ImageIcon(MaxViewerAction.class.getResource("/icons/maxFileView.png")), manager);
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		JSplitPane leftRightPane = getManager().getSplitPane();
		
		leftRightPane.setDividerSize(0);
		leftRightPane.setDividerLocation(leftRightPane.getLocation().x+1750);
		
	}

}
