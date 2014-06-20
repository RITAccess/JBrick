package com.jbricx.swing.actions;

import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;
import javax.swing.JSplitPane;

import com.jbricx.swing.ui.JBricxManager;

@SuppressWarnings("serial")
public class ShowHideFileViewerAction extends JBricxAbstractAction{
	
	boolean isOpen = false;
	
	public boolean isOpen()
	{
		return isOpen;
	}
	

	public ShowHideFileViewerAction(final JBricxManager manager){
		super("", new ImageIcon(ShowHideFileViewerAction.class.getResource("/images/right.png")), manager);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		JSplitPane leftRightPane = getManager().getSplitPane();
		
		if(isOpen){
			leftRightPane.setDividerSize(0);
			leftRightPane.remove(getManager().getFilePane());
			isOpen = false;
		}
		else{
			leftRightPane.setDividerSize(10);
			leftRightPane.add(getManager().getFilePane(), 0);
			leftRightPane.setDividerLocation(leftRightPane.getLocation().x+250);
			isOpen = true;
		}
		
		
		
	}
}
