package com.jbricx.swing.ui.findbrick;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.jbricx.swing.communications.NXTConnectionManager;
import com.jbricx.swing.communications.NXTManager;
import com.jbricx.swing.ui.JBricxManager;
import com.jbricx.swing.communications.enums.ConnectionType;

/**
 * @author Jim Grosso
 */
public class FindBrickUIWindow extends JDialog implements ActionListener {
	
	private JBricxManager manager;
	JLabel connected;

	public FindBrickUIWindow(JBricxManager manager) {
		super(manager.getShell(), "Find Brick", true);
		this.manager = manager;
		
		// create the information panel
		setLayout(new GridLayout(3,1));
		JPanel info = new JPanel();
		
		info.setLayout(new FlowLayout());
		info.setBorder(BorderFactory.createTitledBorder("Information"));
		info.add(new JLabel("To connect to the brick, select the communication method "
          + "and click Connect. You can save your preference by clicking "
          + "the Save button so you do not need to come back to this screen "
          + "in the future."));
		add(info);
		
		// create the status panel
		JPanel status = new JPanel();
		status.setLayout(new FlowLayout());
		status.setBorder(BorderFactory.createTitledBorder("Connection satus:"));
		
		connected = new JLabel();
		
		if (NXTManager.getInstance().isConnected()) {
			connected.setText("Connected using "
					+ NXTManager.getInstance());
			}
		else {
			connected.setText("Not Connected..!");
			}
		
		status.add(connected);
		add(status);
		
		// create  the buttons
		JPanel buttons = new JPanel();
		buttons.setLayout(new GridLayout(1,2));
		
		JButton connect = new JButton("Connect");
		connect.addActionListener(this);
		
		JButton quit = new JButton("Quit");
		quit.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				dispose();
			}
			
		});
		
		buttons.add(connect);
		buttons.add(quit);
		
		add(buttons);
		
		this.pack();
		//this.setSize(200, 400);
		this.setVisible(true);
		
	}


	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
		//if(arg0.getActionCommand().equals("connect")){
			NXTConnectionManager nxtManager = NXTManager.getInstance();
			ConnectionType ct = ConnectionType.USB;
			FindBrickFileIO.saveCT(ct);
	        nxtManager.connect(ct);
	        boolean isConnected = nxtManager.isConnected();
	         
	        if (!isConnected) {
	        	String status = "Connection attempted using " + ct + " but failed!";
	        	connected.setText(status);

	        	Toolkit.getDefaultToolkit().beep();
	        } 
	        else {
	        	this.dispose();
	        }
	        nxtManager.notifyAllObservers(isConnected);
	     //}
	}
}
