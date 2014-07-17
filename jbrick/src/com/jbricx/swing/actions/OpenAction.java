package com.jbricx.swing.actions;

import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

import com.jbricx.swing.ui.JBricxManager;
import com.jbricx.swing.ui.preferences.PreferenceStore;

/**
 * This action class responds to requests open a file
 */
@SuppressWarnings("serial") 
public class OpenAction extends JBricxAbstractAction {

  /**
   * OpenAction constructor
   */
  public OpenAction(final JBricxManager manager) {
	    super("", new ImageIcon(OpenAction.class.getResource("/icons/openDocument.png")), manager);
	  }

	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		class MyCustomFilter extends FileFilter {
	        @Override
	        public boolean accept(File file) {
	            // Allow only directories, or files with ".nxc" extension
	        	String extension = getExtension(file);
	        	boolean validFile = extension.equalsIgnoreCase(PreferenceStore.FILTER_EXTENSION);
	        	
	        	return file.isDirectory() || validFile;
	        }
	        @Override
	        public String getDescription() {
	            return PreferenceStore.FILTER_NAME;
	        }
	    }
		MyCustomFilter filter = new MyCustomFilter();
		
		final JFileChooser fileOpener = new JFileChooser();
		fileOpener.setFileFilter(filter);
		fileOpener.setCurrentDirectory(new File(PreferenceStore.getString(PreferenceStore.Preference.WORKSPACE)));
		
		//fileOpener.setFileFilter(new FileNameExtensionFilter("Accepted",PreferenceStore.FILTER_EXTENSIONS));
		int returnVal = fileOpener.showOpenDialog(getManager().getShell());
		if(returnVal == JFileChooser.APPROVE_OPTION){
			File selectedFile = fileOpener.getSelectedFile();
			if(selectedFile.exists()){
				getManager().getTabFolder().open(selectedFile.getAbsolutePath());
//			    JBricxTabItem tab =(JBricxTabItem)((RTextScrollPane)getManager().getTabFolder().getSelectedComponent()).getViewport().getView();
//			    ((RTextAreaUI) tab.getUI()).setAudioBreaks(BreakpointsStore.getBreakLines(tab.getFileName()));
//			    tab.clearUndo();
			}
		}
		
	}
	
	/**
	 * Gets the extension of a file
	 * @param file
	 * @return Returns extension or and empty string if it has none.
	 */
	public String getExtension(File file){
		String name = file.getName();
		if(name.lastIndexOf(".") > 0){
			return "." + name.substring(name.lastIndexOf(".")+1);
		}
		else{
			return "";
		}
	}
}
