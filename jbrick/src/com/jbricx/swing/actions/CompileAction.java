package com.jbricx.swing.actions;

import java.awt.Component;
import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.jbricx.swing.communications.NXTManager;
import com.jbricx.swing.communications.ExitStatus;
import com.jbricx.swing.ui.JBricxManager;

/**
 * Compiles the current file.
 */
@SuppressWarnings("serial") 
public class CompileAction extends AbstractCompilerAction {

	/**
	 * Constructor
	 */
	public CompileAction(final JBricxManager manager) {
		super("", new ImageIcon(CompileAction.class.getResource(
				"/images/compile.png")), manager);
	}

	@Override
	public void onSuccess() {
		JOptionPane.showMessageDialog(null, "Compile was a success!");
	}

	@Override
	public void onFailure(ExitStatus run) {
		JOptionPane pane=new JOptionPane("Errors Found during compilation.",JOptionPane.INFORMATION_MESSAGE);
        JDialog dialog=pane.createDialog(null,"Compile");
        Component[] comps=pane.getComponents();
        for(int i=0;i<comps.length;i++){
            if(comps[i] instanceof JPanel){
            	if(i == 0){
            		Component[] children=((JPanel)comps[i]).getComponents();
            		String errorMessages = run.getCompilerErrors().toString();
            		String[] messageArray = errorMessages.split("]");
            		messageArray = messageArray[0].split("\\[");
            		((JLabel)children[1]).getAccessibleContext().setAccessibleName(messageArray[1]);
            	}
            }
        }        
        dialog.setModal(true);
        dialog.setVisible(true);;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		this.run();
	}

	@Override
	protected ExitStatus doRun(String filename) {
		return NXTManager.getInstance().compile(filename);	
	}
}
