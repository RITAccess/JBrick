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
		compile("saved", "compile", "Save");
	}
	
	/**
	 * Preforms checks to make sure the file was saved before it compiles
	 * @param actionText
	 * @param resultText
	 * @param buttonText
	 * @return
	 */
	public Boolean compile(String actionText, String resultText, String buttonText)
	{
	    JBricxTabItem tab =(JBricxTabItem)((RTextScrollPane)getManager().getTabFolder().getSelectedComponent()).getViewport().getView();
	    Boolean saved = true;
	    //If the file hasnt been saved then prompt the user to save.
	    if(tab.isDirty())
	    {
	    	if(saveDialog(tab, actionText, resultText, buttonText))
	    	{
		    	SaveAction action = new SaveAction(jBManager);
		    	saved = action.saveFile();
	    	}
	    	else
	    		saved = false;
	    }
	    if(saved)
	    	this.getManager().getStatusPane().pushMessage(this.run());
	    
	    return saved;
	}
	
	/**
	 * Creates dialog box for the save prompt
	 * @param tab
	 * @param actionText
	 * @param resultText
	 * @param buttonText
	 * @return
	 */
	public Boolean saveDialog(JBricxTabItem tab, String actionText, String resultText, String buttonText)
	{ 
		Object[] options = { buttonText, "Cancel" };
		int overwrite = JOptionPane
				.showOptionDialog(
						tab,
						"File must be " + actionText + " before it can " + resultText + "."
								+ " \nWould you like to " + actionText + " \""
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
