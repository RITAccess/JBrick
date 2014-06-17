package com.jbricx.swing.actions;

import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

import org.fife.ui.rtextarea.RTextScrollPane;

import com.jbricx.swing.ui.JBricxManager;
import com.jbricx.swing.ui.preferences.PreferenceStore;
import com.jbricx.swing.ui.tabs.JBricxTabItem;

/**
 * This action class responds to requests open a file
 */
@SuppressWarnings("serial") 
public class OpenAction extends JBricxAbstractAction {

  /**
   * OpenAction constructor
   */
  public OpenAction(final JBricxManager manager) {
	    super("", new ImageIcon(OpenAction.class.getResource("/images/document-open.png")), manager);
	  }

	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		class MyCustomFilter extends FileFilter {
	        @Override
	        public boolean accept(File file) {
	            // Allow only directories, or files with ".nxc" extension
	        	return file.isDirectory() || file.getAbsolutePath().endsWith(PreferenceStore.FILTER_EXTENSION);
	        }
	        @Override
	        public String getDescription() {
	            return PreferenceStore.FILTER_NAME;
	        }
	    }
		MyCustomFilter filter = new MyCustomFilter();
		
		final JFileChooser fileOpener = new JFileChooser();
		fileOpener.setFileFilter(filter);
		
		//fileOpener.setFileFilter(new FileNameExtensionFilter("Accepted",PreferenceStore.FILTER_EXTENSIONS));
		int returnVal = fileOpener.showOpenDialog(getManager().getShell());
		if(returnVal == JFileChooser.APPROVE_OPTION){
			File selectedFile = fileOpener.getSelectedFile();
			if(selectedFile.exists()){
				getManager().getTabFolder().open(selectedFile.getAbsolutePath());
			    JBricxTabItem tab =(JBricxTabItem)((RTextScrollPane)getManager().getTabFolder().getSelectedComponent()).getViewport().getView();
			    tab.clearUndo();
			}
		}
		
	}
	}
