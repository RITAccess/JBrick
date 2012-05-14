package com.jbricx.swing.ui.findbrick;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
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
		setLayout(new BorderLayout());
		JPanel info = new JPanel();
		
		info.setLayout(new FlowLayout());
		info.setBorder(BorderFactory.createTitledBorder("Information:"));
		info.add(new JLabel("<html>To connect to the brick, select the communication method "
          + "<br>and click Connect. You can save your preference by clicking "
          + "<br>the Save button so you do not need to come back to this screen "
          + "<br>in the future.</html>"));
		add(info, BorderLayout.NORTH);
		
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
		add(status, BorderLayout.CENTER);
		
		// create  the buttons
		JPanel buttons = new JPanel();
		buttons.setLayout(new FlowLayout());
		
		JButton connect = new JButton("Connect");
		connect.setSize(100, 100);
		connect.getAccessibleContext().setAccessibleName("connect to brick via USB");
		connect.addActionListener(this);
		
		JButton ok = new JButton("Ok");
		ok.setSize(100, 100);
		ok.getAccessibleContext().setAccessibleName("Ok");
		ok.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				dispose();
			}
			
		});
		
		buttons.add(connect);
		buttons.add(ok);
		
		add(buttons, BorderLayout.SOUTH);
		
//		this.getAccessibleContext().setAccessibleName("Information: To connect to the brick, select the communication method "
//          + "and click Connect. You can save your preference by clicking "
//          + "the Save button so you do not need to come back to this screen "
//          + "in the future."
//          + "Connection satus:"
//          + connected.getText());
		this.pack();
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
	        	this.getAccessibleContext().setAccessibleName(connected.getText());
	        	this.repaint();

	        	Toolkit.getDefaultToolkit().beep();
	        } 
	        else {
	        	connected.setText("Connected using "
						+ NXTManager.getInstance());
	        	this.getAccessibleContext().setAccessibleName(connected.getText());
	        	this.repaint();
	        }
	        nxtManager.notifyAllObservers(isConnected);
	     //}
	}
}
