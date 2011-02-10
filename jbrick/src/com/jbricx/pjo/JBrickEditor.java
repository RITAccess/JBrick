package com.jbricx.pjo;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.Properties;

import org.eclipse.jface.preference.PreferenceConverter;
import org.eclipse.jface.preference.PreferenceStore;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Shell;

import com.jbricx.communications.NXTManager;
import com.jbricx.source.ColorManager.ColorFor;
import com.jbricx.ui.MainWindow;

/**
 * This class demonstrates TextViewer and Document.
 */
public class JBrickEditor {
  // Set up the name of the partitioner

  public static final String JBRICK_PARTITIONING = "jbrick_partitioning";

  /**
   * Runs the application
   */
  protected void run() {
    MainWindow mainWindow = new MainWindow(setDefaults());
    mainWindow.run();
    NXTManager.getInstance().stopPolling();
  }

  /**
   * The application entry point
   * 
   * @param args
   *          the command line arguments
   * @throws IOException
   */
  public static void main(String[] args) throws IOException {
    // ExitStatus e = BrickCreator.createBrick().getBatteryLevel();
    new JBrickEditor().run();
  }

  protected static void detectOS() {
    Properties properties = System.getProperties();
    for (Object i : properties.keySet()) {
      System.out.println(i + " --> " + properties.get(i));
    }
    //os.name --> Windows 7
    //os.name --> Mac
    //user.dir --> D:\workspace\jbrick
  }

  protected PreferenceStore setDefaults() {
    final PreferenceStore preferences = new PreferenceStore("JBrickEditor.properties");


    try {
      preferences.load();
    } catch (IOException e) { /* Ignore */ }

    // Set the default colors
    PreferenceConverter.setDefault(preferences, ColorFor.BACKGROUND.property(),
      new RGB(Color.white.getRed(), Color.white.getGreen(), Color.white.getBlue()));

    PreferenceConverter.setDefault(preferences, ColorFor.FOREGROUND.property(),
      new RGB(Color.black.getRed(), Color.black.getGreen(), Color.black.getBlue()));

    PreferenceConverter.setDefault(preferences, ColorFor.OPERATOR.property(),
      new RGB(Color.orange.getRed(), Color.orange.getGreen(), Color.orange.getBlue()));

    PreferenceConverter.setDefault(preferences, ColorFor.COMMENT.property(),
      new RGB(Color.gray.getRed(), Color.gray.getGreen(), Color.gray.getBlue()));

    PreferenceConverter.setDefault(preferences, ColorFor.LINENUMBERFG.property(),
      new RGB(Color.red.getRed(), Color.red.getGreen(), Color.red.getBlue()));

    PreferenceConverter.setDefault(preferences, ColorFor.LINENUMBERBG.property(),
      new RGB(Color.white.getRed(), Color.white.getGreen(), Color.white.getBlue()));

    PreferenceConverter.setDefault(preferences, ColorFor.STRING.property(),
      new RGB(Color.green.getRed(), Color.green.getGreen(), Color.green.getBlue()));

    PreferenceConverter.setDefault(preferences, ColorFor.KEYWORD.property(),
      new RGB(Color.blue.getRed(), Color.blue.getGreen(), Color.blue.getBlue()));

    // Check the workspace
    String workspace = preferences.getString(FileExtensionConstants.WRKSPC);
    // Check if directory exists
    File file = new File(workspace);
    boolean exists = file.exists();
    
    if (workspace.isEmpty() || !exists) {
      do {
        DirectoryDialog dialog = new DirectoryDialog(new Shell());
        dialog.setText("Workspace Selection");
        workspace = dialog.open();
      } while (workspace == null);

      preferences.putValue(FileExtensionConstants.WRKSPC, workspace);

      try {
        preferences.save();
      } catch (IOException e) {
        System.err.println("Error Saving Preferences: " + e.getMessage());
      }
    }
    preferences.setDefault(FileExtensionConstants.WRKSPC, workspace);

    // Set the default paths for the tools
    final Properties properties = System.getProperties();
    final String osName = properties.getProperty("os.name");
    final String dir = properties.getProperty("user.dir");
    final String separator = properties.getProperty("file.separator");

    final String toolPath = String.format("%s%s%s%s", dir, separator, "lib", separator);

    if (osName.contains("Windows")) {
      preferences.setDefault(FileExtensionConstants.BRICKTOOL, toolPath + "BrickTool.exe");
      preferences.setDefault(FileExtensionConstants.NEXTTOOL, toolPath + "NeXTTool.exe");
      preferences.setDefault(FileExtensionConstants.NBCTOOL, toolPath + "nbc.exe");

    } else if (osName.contains("Mac") || osName.contains("Linux")) {
      preferences.setDefault(FileExtensionConstants.BRICKTOOL, toolPath + "bricktool");
      preferences.setDefault(FileExtensionConstants.NEXTTOOL, toolPath + "nexttool");
      preferences.setDefault(FileExtensionConstants.NBCTOOL, toolPath + "nbc");

    }

    return preferences;
  }
}
