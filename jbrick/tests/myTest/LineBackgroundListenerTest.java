package myTest;
//Send questions, comments, bug reports, etc. to the authors:

//Rob Warner (rwarner@interspatial.com)
//Robert Harris (rbrt_harris@yahoo.com)


import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.LineBackgroundEvent;
import org.eclipse.swt.custom.LineBackgroundListener;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * This class demonstrates LineBackgroundListeners
 */
public class LineBackgroundListenerTest {
  // The color to use for backgrounds
  Color red;

  /**
   * Runs the application
   */
  public void run() {
    Display display = new Display();
    red = display.getSystemColor(SWT.COLOR_RED);
    Shell shell = new Shell(display);
    createContents(shell);
    shell.open();
    while (!shell.isDisposed()) {
      if (!display.readAndDispatch()) {
        display.sleep();
      }
    }
    display.dispose();
  }

  /**
   * Creates the main window's contents
   * 
   * @param shell the main window
   */
  private void createContents(Shell shell) {
    shell.setLayout(new FillLayout());
    StyledText styledText = new StyledText(shell, SWT.BORDER);

    // Add the line background listener
    styledText.addLineBackgroundListener(new LineBackgroundListener() {
      public void lineGetBackground(LineBackgroundEvent event) {
        if (event.lineText.indexOf("SWT") > -1) {
          event.lineBackground = red;
        }
      }
    });

  }

  /**
   * The application entry point
   * 
   * @param args the command line arguments
   */
  public static void main(String[] args) {
    new LineBackgroundListenerTest().run();
  }
}
  