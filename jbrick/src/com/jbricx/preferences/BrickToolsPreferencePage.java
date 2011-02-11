/**
 * 
 */
package com.jbricx.preferences;

import org.eclipse.jface.preference.FileFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.widgets.Composite;

import com.jbricx.pjo.FileExtensionConstants;

/**
 * @author byktol
 */
public class BrickToolsPreferencePage extends FieldEditorPreferencePage {

  public BrickToolsPreferencePage() {
  }

  /**
   * @param style
   */
  public BrickToolsPreferencePage(int style) {
    super(style);
    // TODO Auto-generated constructor stub
  }

  /**
   * @param title
   * @param style
   */
  public BrickToolsPreferencePage(String title, int style) {
    super(title, style);
  }

  /**
   * @param title
   * @param image
   * @param style
   */
  public BrickToolsPreferencePage(String title, ImageDescriptor image, int style) {
    super(title, image, style);
  }

  @Override
  protected void createFieldEditors() {
    Composite fieldEditorParent = getFieldEditorParent();
    final FileFieldEditor nextToolPath = new FileFieldEditor(
            FileExtensionConstants.NEXTTOOL, "NeXT Tool", fieldEditorParent);
    final FileFieldEditor brickToolPath = new FileFieldEditor(
            FileExtensionConstants.BRICKTOOL, "Brick Tool", fieldEditorParent);
    final FileFieldEditor nbcToolPath = new FileFieldEditor(
            FileExtensionConstants.NBCTOOL, "NBC Tool", fieldEditorParent);

    /* Disable this field editors */
    nextToolPath.setEnabled(false, fieldEditorParent);
    brickToolPath.setEnabled(false, fieldEditorParent);
    nbcToolPath.setEnabled(false, fieldEditorParent);

    /* Add the fields */
    addField(nextToolPath);
    addField(brickToolPath);
    addField(nbcToolPath);
  }

}
