package com.jbricx.swing.actions;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;

import org.fife.ui.rtextarea.RTextScrollPane;

import com.jbricx.communication.NXTAccess;
import com.jbricx.swing.ui.CompilerNotFoundWindow;
import com.jbricx.swing.ui.JBricxManager;
import com.jbricx.swing.ui.tabs.JBricxTabItem;
import com.jbricx.swing.ui.preferences.PreferenceStore;

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
		HashMap<String, ArrayList<String>> value =
				NXTAccess.compile(
						this.getManager().getTabFolder().getSelection().getFileFullPath()
						);
		
		if(value.containsKey("No NBC Tool Compiler")){
		    JBricxTabItem tab =(JBricxTabItem)((RTextScrollPane)getManager().getTabFolder().getSelectedComponent()).getViewport().getView();
			Object[] options = { "Yes", "No" };
			int response = JOptionPane
					.showOptionDialog(
							tab,
							"The compiler was not found. Would you like to specify one now?",
							"Compiler Not Found",
							JOptionPane.YES_NO_OPTION,
							JOptionPane.QUESTION_MESSAGE, null, options,
							options[0]);
			if(response == JOptionPane.YES_OPTION){
				CompilerNotFoundWindow window = new CompilerNotFoundWindow(jBManager);
				window.setVisible(true);
				return run();
			}
		}
		
		return value;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		compile("saved", "compile", "save", "Save");
	}
	
	/**
	 * Preforms checks to make sure the file was saved before it compiles
	 * @param actionText
	 * @param resultText
	 * @param buttonText
	 * @return
	 */
	public Boolean compile(String actionText, String resultText, String finalActionText, String buttonText)
	{
	    JBricxTabItem tab =(JBricxTabItem)((RTextScrollPane)getManager().getTabFolder().getSelectedComponent()).getViewport().getView();
	    Boolean saved = true;
	    //If the file hasnt been saved then prompt the user to save.
	    if(tab.isDirty())
	    {
	    	if(PreferenceStore.getBool(PreferenceStore.Preference.AUTOCOMPILE) || saveDialog(tab, actionText, resultText, finalActionText, buttonText))
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
	public Boolean saveDialog(JBricxTabItem tab, String actionText, String resultText, String finalActionText, String buttonText)
	{ 	
		JCheckBox checkbox = new JCheckBox("Always compile and save");
		checkbox.getAccessibleContext().setAccessibleName("Auto Compile Box. Press Space to Toggle");
		Object[] options = { buttonText, "Cancel", checkbox };
		int overwrite = JOptionPane
				.showOptionDialog(
						tab,
						"File must be " + actionText + " before it can " + resultText + "."
								+ " \nWould you like to " + finalActionText + " \""
								+ tab.getFileName()
								+ "\"?",
						"Unsaved Changes",
						JOptionPane.OK_CANCEL_OPTION,
						JOptionPane.QUESTION_MESSAGE, null, options,
						options[0]);
		
		// User wishes to save the file before closing.
		if (overwrite == JOptionPane.OK_OPTION) {
			//Checks if the user wants to always preform this acton
			if(checkbox.isSelected()){
				PreferenceStore.set(PreferenceStore.Preference.AUTOCOMPILE, true);
			}
			
			return true;
		}
		return false;
	}
}
