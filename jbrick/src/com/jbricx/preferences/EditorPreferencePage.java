package com.jbricx.preferences;


import org.eclipse.jface.preference.ColorFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;


/**
 * This preference page shows preferences for the text
 */
public class EditorPreferencePage extends FieldEditorPreferencePage {

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

