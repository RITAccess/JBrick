package treeProviders;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProviderChangedEvent;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.graphics.Image;

/**
 * This class provides the content for the tree in FileTree
 */

public class FileTreeContentProvider implements ITreeContentProvider {
  String path;
	public FileTreeContentProvider(String path){
		this.path = path;
	}
	
	/**
   * Gets the children of the specified object
   * 
   * @param arg0
   *            the parent object
   * @return Object[]
   */
  public Object[] getChildren(Object arg0) {
    // Return the files and subdirectories in this directory
    return ((File) arg0).listFiles();
  }

  /**
   * Gets the parent of the specified object
   * 
   * @param arg0
   *            the object
   * @return Object
   */
  public Object getParent(Object arg0) {
    // Return this file's parent file
    return ((File) arg0).getParentFile();
  }

  /**
   * Returns whether the passed object has children
   * 
   * @param arg0
   *            the parent object
   * @return boolean
   */
  public boolean hasChildren(Object arg0) {
    // Get the children
    Object[] obj = getChildren(arg0);

    // Return whether the parent has children
    return obj == null ? false : obj.length > 0;
  }

  /**
   * Gets the root element(s) of the tree
   * 
   * @param arg0
   *            the input data
   * @return Object[]
   */
  public Object[] getElements(Object arg0) {
    // These are the root elements of the tree
    // We don't care what arg0 is, because we just want all
    // the root nodes in the file system
	  File file = new File(path);
    //return File.listRoots();
	  
	  return file.listFiles();
    
  }

  /**
   * Disposes any created resources
   */
  public void dispose() {
    // Nothing to dispose
  }

  /**
   * Called when the input changes
   * 
   * @param arg0
   *            the viewer
   * @param arg1
   *            the old input
   * @param arg2
   *            the new input
   */
  public void inputChanged(Viewer arg0, Object arg1, Object arg2) {
    // Nothing to change
  }
}
