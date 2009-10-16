package actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.widgets.MessageBox;

import pjo.JBrickEditor;

import communcation.*;

/**
 * This class shows an About box
 */
public class CompileAction extends Action {
  /**
   * AboutAction constructor
   */
  public CompileAction() {
    super("&About@Ctrl+Shift-b", ImageDescriptor.createFromFile(CompileAction.class, "/images/compile.png"));
    setToolTipText("Compile");
  }

  /**
   * Shows an about box
   */
  public void run() {
	  
    JCompiler c = new JCompiler();
    System.out.println(JBrickEditor.getApp().getDocument().getFileName());
    int exitstatus = c.compile(JBrickEditor.getApp().getDocument().getFileName());
    
    if (exitstatus == JCompiler.EXITSTATUS_OK){
    	MessageDialog.openInformation(JBrickEditor.getApp().getMainWindow().getShell(),
    	        "Compile", "Compiling was a success!!");
    }
    else if (exitstatus==JCompiler.EXITSTATUS_ERROR){
    	MessageDialog.openInformation(JBrickEditor.getApp().getMainWindow().getShell(),
    	        "Compile", "Compiling failed: \n"+c.getErrorMessage());
    }
  }
}
