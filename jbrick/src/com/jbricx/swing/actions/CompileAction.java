package com.jbricx.swing.actions;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import org.fife.ui.rtextarea.RTextScrollPane;

import com.jbricx.communication.NXTAccess;
import com.jbricx.swing.ui.JBricxManager;
import com.jbricx.swing.ui.tabs.JBricxTabItem;

/**
 * Compiles the current file.
 */
@SuppressWarnings("serial") 
public class CompileAction extends JBricxAbstractAction {
	
	JBricxManager jBManager;
	
	/**
	 * Constructor
	 */
	public CompileAction(final JBricxManager manager) {
		super("", new ImageIcon(CompileAction.class.getResource(
				"/images/compile.png")), manager);
		jBManager = manager;
	}
	
	public HashMap<String, ArrayList<String>> run(){
		return NXTAccess.compile(
				this.getManager().getTabFolder().getSelection().getFileFullPath()
		);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
	    JBricxTabItem tab =(JBricxTabItem)((RTextScrollPane)getManager().getTabFolder().getSelectedComponent()).getViewport().getView();
	    Boolean saved = true;
	    //If the file hasnt been saved then prompt the user to save.
	    if(tab.isDirty())
	    {
	    	if(saveDialog(tab))
	    	{
		    	SaveAction action = new SaveAction(jBManager);
		    	saved = action.saveFile();
	    	}
	    	else
	    		saved = false;
	    }
	    if(saved)
	    	this.getManager().getStatusPane().pushMessage(this.run());
	}
	
	/**
	 * Dialog box that asks the user if they want to save.
	 * @param tab
	 * @return Did they save?
	 */
	public Boolean saveDialog(JBricxTabItem tab)
	{ 
		Object[] options = { "Save", "Cancel" };
		int overwrite = JOptionPane
				.showOptionDialog(
						tab,
						"File must be saved before it can compile."
								+ " \nWould you like to save  \""
								+ tab.getFileName()
								+ "\"?",
						"Unsaved Changes",
						JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE, null, options,
						options[0]);
		// User wishes to save the file before closing.
		if (overwrite == JOptionPane.YES_OPTION) {
			return true;
		}
		return false;
	}
}
