package com.jbricx.swing.ui.findbrick;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.LayoutManager;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.jbricx.swing.ui.JBricxManager;

/**
 * @author Priya Sankaran
 */
public class FindBrickUIWindow extends JDialog implements ActionListener {
	
	private JBricxManager manager;

	public FindBrickUIWindow(JBricxManager manager) {
		super(manager.getShell(), "Find Brick", true);
		this.manager = manager;
		
		setLayout(new BorderLayout());
		JPanel info = new JPanel();
		info.setLayout(new FlowLayout());
		info.setBorder(BorderFactory.createTitledBorder("Information"));
		info.add(new JLabel("To connect to the brick, select the communication method "
          + "and click Connect.  You can save your preference by clicking "
          + "the Save button so you do not need to come back to this screen "
          + "in the future."));
		add(info,BorderLayout.CENTER);
		this.setSize(200, 400);
		this.setVisible(true);
		
	}


@Override
public void actionPerformed(ActionEvent arg0) {
	// TODO Auto-generated method stub
	
}

}
