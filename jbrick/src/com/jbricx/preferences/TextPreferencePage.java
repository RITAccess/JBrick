package com.jbricx.preferences;

import java.awt.Color;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.ColorFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.DirectoryFieldEditor;
import org.eclipse.jface.preference.FontFieldEditor;
import org.eclipse.jface.preference.IPreferenceStore;
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
    // Get the preference store
    IPreferenceStore store = getPreferenceStore();

    // Reset the fields to the defaults
    store.setDefault(ColorFor.BACKGROUND.property(), Color.white.getRGB());
    store.setDefault(ColorFor.FOREGROUND.property(), Color.black.getRGB());
    store.setDefault(ColorFor.OPERATOR.property(), Color.orange.getRGB());
    store.setDefault(ColorFor.COMMENT.property(), Color.gray.getRGB());
    store.setDefault(ColorFor.STRING.property(), Color.green.getRGB());
    store.setDefault(ColorFor.KEYWORD.property(), Color.blue.getRGB());
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

    // Add the field for word wrap
    addField(new BooleanFieldEditor(FileExtensionConstants.BOOLRECENTFILES, "Load Recently Opened Files",
        getFieldEditorParent()));

    // Add the field for word wrap
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
        "Editor BG (?):",
        "Editor FG (?):"
    };
    for (int i = 0; i < ColorFor.values().length; i++) {
      addField(new ColorFieldEditor(ColorFor.values()[i].property(), labels[i], getFieldEditorParent()));
    }
  }
}
