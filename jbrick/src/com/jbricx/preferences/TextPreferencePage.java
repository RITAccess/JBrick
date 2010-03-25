package com.jbricx.preferences;

import java.awt.Color;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.ColorFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.DirectoryFieldEditor;
import org.eclipse.jface.preference.FontFieldEditor;
import org.eclipse.jface.preference.IPreferenceStore;
import com.jbricx.pjo.FileExtensionConstants;

/**
 * This preference page shows preferences for the text
 */
public class TextPreferencePage extends FieldEditorPreferencePage {

	private FontFieldEditor fontFieldEditor;
	private BooleanFieldEditor wrapFieldEditor;
	private DirectoryFieldEditor workspaceFieldEditor;
	private BooleanFieldEditor boolrecentfiles;
	private ColorFieldEditor bgCF ;
	private ColorFieldEditor fgCF ;

	/**
	 * TextPreferencePage constructor
	 */
	public TextPreferencePage() {
		super(GRID);
	}
	
	/**
	 * Called when user clicks Restore Defaults
	 */
	protected void performDefaults() {
		// Get the preference store
		IPreferenceStore store = getPreferenceStore();

		// Reset the fields to the defaults
		store.setDefault("bgColor", Color.white.getRGB());
		store.setDefault("fgColor", Color.black.getRGB());
	}
	
	/**
	 * Creates the field editors
	 */
	protected void createFieldEditors() {
		// Add the field for the font
		fontFieldEditor = new FontFieldEditor(FileExtensionConstants.FONT, "Font:", "Font",
				getFieldEditorParent());
		addField(fontFieldEditor);

		// Add the field for word wrap
		wrapFieldEditor = new BooleanFieldEditor(FileExtensionConstants.WRAP, "Word Wrap",
				getFieldEditorParent());
		addField(wrapFieldEditor);
		
		// Workspace Directory
		workspaceFieldEditor = new DirectoryFieldEditor(FileExtensionConstants.WRKSPC , "Workspace Directory",
				getFieldEditorParent());
		addField(workspaceFieldEditor);
		
		
		// Add the field for word wrap
		boolrecentfiles = new BooleanFieldEditor(FileExtensionConstants.BOOLRECENTFILES, "Load Recently Opened Files",
				getFieldEditorParent());
		addField(boolrecentfiles);
		
		// Add a background color field
		bgCF = new ColorFieldEditor("bgColor", "Background Color:",
	        getFieldEditorParent());
	    addField(bgCF);
	    
	 // Add a foreground color field
	    fgCF = new ColorFieldEditor("fgColor", "Foreground Color:",
	        getFieldEditorParent());
	    addField(fgCF);
	}
}
