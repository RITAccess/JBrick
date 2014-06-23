package com.jbricx.swing.actions;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.ImageIcon;

import com.jbricx.communication.NXTAccess;
import com.jbricx.swing.ui.JBricxManager;

/**
 * Compiles the current file.
 */
@SuppressWarnings("serial") 
public class CompileAction extends JBricxAbstractAction {

	/**
	 * Constructor
	 */
	public CompileAction(final JBricxManager manager) {
		super("", new ImageIcon(CompileAction.class.getResource(
				"/images/compile.png")), manager);
	}
	
	public HashMap<String, ArrayList<String>> run(){
		return NXTAccess.compile(
				this.getManager().getTabFolder().getSelection().getFileFullPath()
		);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		this.getManager().getStatusPane().pushMessage(this.run());
	}

}
