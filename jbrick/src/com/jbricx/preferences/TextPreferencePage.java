package com.jbricx.preferences;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.FontFieldEditor;

import com.jbricx.pjo.FileExtensionConstants;


/**
 * This preference page shows preferences for the text
 */
public class TextPreferencePage extends FieldEditorPreferencePage {

	private FontFieldEditor fontFieldEditor;
	private BooleanFieldEditor wrapFieldEditor;

	/**
	 * TextPreferencePage constructor
	 */
	public TextPreferencePage() {
		super(GRID);
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
	}
}
