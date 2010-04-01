package com.jbricx.preferences;


import org.eclipse.jface.preference.ColorFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.FontFieldEditor;

import com.jbricx.pjo.FileExtensionConstants;


/**
 * This preference page shows preferences for the text
 */
public class EditorPreferencePage extends FieldEditorPreferencePage {

	private FontFieldEditor fontFieldEditor;
	private ColorFieldEditor bgCF ;
	private ColorFieldEditor fgCF ;

	/**
	 * TextPreferencePage constructor
	 */
	public EditorPreferencePage() {
		super(GRID);
	}

	/**
	 * Creates the field editors
	 */
	protected void createFieldEditors() {

		// Add the field for the font
		fontFieldEditor = new FontFieldEditor(FileExtensionConstants.FONT, "Font:", "editorFont",
				getFieldEditorParent());
		addField(fontFieldEditor);

		// Add a background color field
		bgCF = new ColorFieldEditor("editorBGColor", "Background Color:",
	        getFieldEditorParent());
	    addField(bgCF);
	    
	 // Add a foreground color field
	    fgCF = new ColorFieldEditor("editorFGColor", "Foreground Color:",
	        getFieldEditorParent());
	    addField(fgCF);
	}
}

