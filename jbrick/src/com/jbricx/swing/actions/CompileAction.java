package com.jbricx.swing.actions;

import java.awt.event.ActionEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;

import org.fife.ui.rtextarea.RTextScrollPane;

import com.jbricx.communication.NXTAccess;
import com.jbricx.pjo.ActionControlClass;
import com.jbricx.swing.ui.JBricxManager;
import com.jbricx.swing.ui.MainWindow;
import com.jbricx.swing.ui.tabs.JBricxTabItem;
import com.jbricx.swing.ui.tabs.preference.DirectoryPane;
import com.jbricx.swing.ui.tabs.preference.JBricxCustomPreferenceDialog;
import com.jbricx.swing.ui.preferences.PreferenceStore;
import com.jbricx.swing.ui.preferences.PreferenceStore.Preference;

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
				"/icons/compile.png")), manager);
		jBManager = manager;
	}
	
	/**
	 * run - runs the compile action and returns the hashmap
	 * @return
	 */
	public HashMap<String, ArrayList<String>> run(){

		// runs the nbc compiler
		HashMap<String, ArrayList<String>> value =
				NXTAccess.compile(
						this.getManager().getTabFolder().getSelection().getFileFullPath()
						);
		return value;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		compile("saved", "compile", "save", "Save", false);
	}
	
	/**
	 * Preforms checks to make sure the file was saved before it compiles
	 * @param actionText
	 * @param resultText
	 * @param buttonText
	 * @return
	 */
	public Boolean compile(String actionText, String resultText, String finalActionText, String buttonText, boolean download)
	{
	    JBricxTabItem tab =(JBricxTabItem)((RTextScrollPane)getManager().getTabFolder().getSelectedComponent()).getViewport().getView();
	    
	    Boolean saved = !tab.isDirty();
	    //If the file hasn't been saved then prompt the user to save.
	    if(!saved){
	    	if(PreferenceStore.getBool(PreferenceStore.Preference.AUTOSAVEONCOMPILE) || saveDialog(tab, actionText, resultText, finalActionText, buttonText)){
	    		ActionControlClass.saveFile(getManager().getTabFolder().getSelection(), false, getManager());
	    		saved = true;
	    	}
	    }
	    
		// Checks whether NBC compiler exists. If it doesn't, ask user if they want to specify one.
		Boolean exists = new File(PreferenceStore.getString(Preference.NBCTOOL)).exists();
		if(!exists){
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
				JBricxCustomPreferenceDialog.openPreference(getManager().getShell(), "Compiler Not Found",
						new DirectoryPane(Preference.NBCTOOL, getManager(), false).createPanel()
						);
				return compile(actionText, resultText, finalActionText, buttonText, download);
			}
		}
	    
		// if we are trying to compile, run the compile action
		// if we are trying to download, don't run the compile action
	    if(saved && !download && !tab.isNewFile()){
	    	jBManager.getStatusPane().pushMessage(this.run(), false);
	    }
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
				PreferenceStore.set(PreferenceStore.Preference.AUTOSAVEONCOMPILE, true);
			}
			MainWindow.lostFocusTime = 0;
			return true;
		}
		MainWindow.lostFocusTime = 0;
		return false;
	}
}