package myTest;
import java.io.IOException;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferenceDialog;
import org.eclipse.jface.preference.PreferenceManager;
import org.eclipse.jface.preference.PreferenceNode;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.preference.PreferenceStore;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

/**
 * This class demonstrates JFace preferences
 */
public class ShowPrefs {
  /**
   * Runs the application
   */
  public void run() {
    Display display = new Display();

    // Create the preference manager
    PreferenceManager mgr = new PreferenceManager();

    // Create the nodes
    PreferenceNode one = new PreferenceNode("one", "One", ImageDescriptor
        .createFromFile(ShowPrefs.class, "src/images/error_ovr.gif"),
        PrefPageOne.class.getName());
    PreferenceNode two = new PreferenceNode("two", new PrefPageTwo());

    // Add the nodes
    mgr.addToRoot(one);
    mgr.addTo(one.getId(), two);

    // Create the preferences dialog
    PreferenceDialog dlg = new PreferenceDialog(null, mgr);

    // Set the preference store
    PreferenceStore ps = new PreferenceStore("showprefs.properties");
    try {
      ps.load();
    } catch (IOException e) {
      // Ignore
    }
    dlg.setPreferenceStore(ps);

    // Open the dialog
    dlg.open();

    try {
      // Save the preferences
      ps.save();
    } catch (IOException e) {
      e.printStackTrace();
    }
    display.dispose();
  }

  /**
   * The application entry point
   * 
   * @param args
   *            the command line arguments
   */
  public static void main(String[] args) {
    new ShowPrefs().run();
  }
}


