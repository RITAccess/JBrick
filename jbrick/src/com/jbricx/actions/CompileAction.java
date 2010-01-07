package com.jbricx.actions;

import java.io.IOException;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

import com.jbricx.communications.BrickCreator;
import com.jbricx.communications.ExitStatus;
import com.jbricx.pjo.ActionControlClass;
import com.jbricx.pjo.JBrickEditor;

/**
 * This class shows an About box
 */
public class CompileAction extends Action {
  /**
   * AboutAction constructor
   */
  public CompileAction() {
    super("&Compile@Ctrl+Shift-b", ImageDescriptor.createFromFile(CompileAction.class, 
    		"/images/compile.png"));
    setToolTipText("Compile");
  }

  /**
   * Shows an about box
   */
  public void run(){
	  
	if (JBrickEditor.getMainWindow().getCurrentTabItem().getDocument().getFileName() == null){ /* Save before compiling */
		MessageBox box = new MessageBox(JBrickEditor.getApp().getMainWindow().getShell(), SWT.OK);
		box.setText("Compile");
		box.setMessage("Before compiling, you need to save the code to file");
		int ret = box.open();
		if (ret == SWT.OK){
			ActionControlClass.saveFile(JBrickEditor.getMainWindow().getCurrentTabItem()) ;
		}		
	}
	
	if (JBrickEditor.getMainWindow().getCurrentTabItem().getDocument().isDirty()){
		try {
			JBrickEditor.getMainWindow().getCurrentTabItem().getDocument().save();
			JBrickEditor.getMainWindow().setStatus("Saving File . . .");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
    
    ExitStatus e = BrickCreator.createBrick().compile(JBrickEditor.getMainWindow().getCurrentTabItem().getDocument().getFileName());
    if (e.isOk()){
    	MessageDialog.openInformation(JBrickEditor.getApp().getMainWindow().getShell(),"Compile", "Compile was a success!");
    }
    else{
    	String msg = e.getMesage();
    	System.out.println(msg);
    	int startpt = msg.indexOf("line ")+5;
    	String lineNum = "Line: "+msg.substring(startpt, msg.indexOf("#",startpt));
    	System.out.print(lineNum);
    	
    	startpt = msg.indexOf("Error: ");
    	String error = msg.substring(startpt, msg.indexOf("File ",startpt));
    	System.out.println(error);
    	
    	
    	Table tbl = JBrickEditor.getMainWindow().table;
    	TableItem line = new TableItem(tbl, SWT.NONE);
    	line.setText(lineNum + " " +error);
    	
    	
//    	MessageDialog.openInformation(JBrickEditor.getApp().getMainWindow().getShell(),"Compile", );
    }
    	        
  }
}
