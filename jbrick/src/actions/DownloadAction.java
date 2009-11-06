package actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;

import communcation.Communication;
import communcation.ExitStatus;
import communcation.JCompiler;

import pjo.JBrickEditor;

public class DownloadAction extends Action {
	  /**
	   * AboutAction constructor
	   */
	  public DownloadAction() {
	    super("&Download@Ctrl+D", ImageDescriptor.createFromFile(AboutAction.class,
	        "/images/media-playback-start.png"));
	    setToolTipText("Download");
	  }

	  /**
	   * Shows an about box
	   */
	  public void run() {
		Communication c = new Communication();
		System.out.println(JBrickEditor.getMainWindow().getCurrentTabItem().getDocument().getFileName());
		ExitStatus exitstatus = c.download(JBrickEditor.getMainWindow().getCurrentTabItem().getDocument().getFileName());
		
		if (exitstatus == ExitStatus.Ok){
			MessageDialog.openInformation(JBrickEditor.getApp().getMainWindow().getShell(),
			        "Compile", "Compiling was a success!!");
		}
		else if (exitstatus==ExitStatus.Error){
			MessageDialog.openInformation(JBrickEditor.getApp().getMainWindow().getShell(),
			        "Compile", "Compiling failed: \n"+c.getErrorMessage());
		}
	  }
	}
