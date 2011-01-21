package com.jbricx.preferences;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.ColorFieldEditor;
import org.eclipse.jface.preference.DirectoryFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.FontFieldEditor;

import com.jbricx.pjo.FileExtensionConstants;
import com.jbricx.source.ColorManager.ColorFor;

/**
 * This preference page shows preferences for the text
 */
public class TextPreferencePage extends FieldEditorPreferencePage {

  public TextPreferencePage() {
    super(GRID);
  }

  /**
   * Called when user clicks Restore Defaults
   */
  @Override
  protected void performDefaults() {
    //Workaround to preserve the workspace
    getPreferenceStore().setDefault(FileExtensionConstants.WRKSPC,
        getPreferenceStore().getString(FileExtensionConstants.WRKSPC));

    super.performDefaults();
  }

  /**
   * Creates the field editors
   */
  @Override
  protected void createFieldEditors() {
    // Add the field for the font
    addField(new FontFieldEditor(FileExtensionConstants.FONT, "Font:", "Font", getFieldEditorParent()));

    // Add the field for word wrap
    addField(new BooleanFieldEditor(FileExtensionConstants.WRAP, "Word Wrap", getFieldEditorParent()));

    // Workspace Directory
    addField(new DirectoryFieldEditor(FileExtensionConstants.WRKSPC, "Workspace Directory",
        getFieldEditorParent()));

    // Add the field for loading recent files
    addField(new BooleanFieldEditor(FileExtensionConstants.BOOLRECENTFILES, "Load Recently Opened Files",
        getFieldEditorParent()));

    // Add the field for auto-compile
    addField(new BooleanFieldEditor(FileExtensionConstants.AUTOCOMPILE, "Auto Compile",
        getFieldEditorParent()));

    // Color fields
    String[] labels = new String[] {
        "Foreground color:",
        "Background color:",
        "Comment color:",
        "Keyword color:",
        "Operator color:",
        "String color:",
        "Line numbers FG color:",
        "Line numbers BG color:"
    };
    for (int i = 0; i < ColorFor.values().length; i++) {
      addField(new ColorFieldEditor(ColorFor.values()[i].property(), labels[i], getFieldEditorParent()));
    }
  }
}
