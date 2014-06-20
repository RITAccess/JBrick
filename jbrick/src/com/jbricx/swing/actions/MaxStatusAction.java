package com.jbricx.swing.actions;

import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;
import javax.swing.JSplitPane;

import com.jbricx.swing.ui.JBricxManager;

@SuppressWarnings(value = { "serial" }) 
public class MaxStatusAction extends JBricxAbstractAction{

	public MaxStatusAction(final JBricxManager manager) {
		super("", new ImageIcon(MaxStatusAction.class.getResource("/images/go-top.png")), manager);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		JSplitPane leftRightPane = getManager().getSplitPane();
		JSplitPane upDownPane = (JSplitPane) leftRightPane.getRightComponent();
		
		leftRightPane.setDividerSize(0);
		leftRightPane.setDividerLocation(leftRightPane.getLocation().x);
		
		upDownPane.setDividerSize(0);
		upDownPane.setDividerLocation(upDownPane.getLocation().y);
	}

}
