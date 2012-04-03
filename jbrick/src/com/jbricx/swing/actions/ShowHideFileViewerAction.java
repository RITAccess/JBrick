package com.jbricx.swing.actions;

import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;
import javax.swing.JSplitPane;

import com.jbricx.swing.ui.JBricxManager;

public class ShowHideFileViewerAction extends JBricxAbstractAction{
	
	boolean isOpen = true;
	
	

	public ShowHideFileViewerAction(final JBricxManager manager){
		super("", new ImageIcon(""), manager);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		JSplitPane leftRightPane = getManager().getSplitPane();
		
		if(isOpen){
			leftRightPane.setDividerSize(0);
			leftRightPane.setDividerLocation(leftRightPane.getLocation().x);
			isOpen = false;
		}
		else{
			leftRightPane.setDividerSize(5);
			leftRightPane.setDividerLocation(leftRightPane.getLocation().x+50);
			isOpen = true;
		}
		
		
		
	}
}
