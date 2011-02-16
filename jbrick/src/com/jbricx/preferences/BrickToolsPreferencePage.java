/**
 * 
 */
package com.jbricx.preferences;

import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.FileFieldEditor;
import org.eclipse.swt.widgets.Composite;

import com.jbricx.pjo.FileExtensionConstants;

/**
 * Preference Page containing the fields for the tools used by JBrick.
 * 
 * @author byktol
 */
public class BrickToolsPreferencePage extends FieldEditorPreferencePage {

  @Override
  protected void createFieldEditors() {
    final Composite fieldEditorParent = getFieldEditorParent();

    final FileFieldEditor nbcToolPath = new FileFieldEditor(
            FileExtensionConstants.NBCTOOL, "NBC Tool", fieldEditorParent);
    final FileFieldEditor nextToolPath = new FileFieldEditor(
            FileExtensionConstants.NEXTTOOL, "NeXT Tool", fieldEditorParent);
    final FileFieldEditor brickToolPath = new FileFieldEditor(
            FileExtensionConstants.BRICKTOOL, "Brick Tool", fieldEditorParent);

    /* Add the fields */
    addField(nextToolPath);
    addField(brickToolPath);
    addField(nbcToolPath);
  }

}
