package actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;

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
	
	  }
	}
