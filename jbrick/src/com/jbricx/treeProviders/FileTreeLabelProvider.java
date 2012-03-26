package com.jbricx.treeProviders;

import java.awt.Image;
import java.io.File;
import java.util.ArrayList;

/**
 * This class provides the labels for the file tree
 */

public class FileTreeLabelProvider implements ILabelProvider {
  // The listeners
  private ArrayList<ILabelProviderListener> listeners;

  // Images for tree nodes
  private Image fileIcon;

  private Image dirIcon;
  
  ImageRegistry imageRegistry;

  // Label provider state: preserve case of file names/directories
  boolean preserveCase;
  
  

  /**
   * Constructs a FileTreeLabelProvider
   */
  public FileTreeLabelProvider() {
    // Create the list to hold the listeners
    listeners = new ArrayList<ILabelProviderListener>();
    
   

    // Create the images
    fileIcon = ImageDescriptor.createFromFile(getClass(), "/images/accessories-text-editor.png").createImage();
    dirIcon = ImageDescriptor.createFromFile(getClass(), "/images/folder_package.gif").createImage();
  }
  
  
    

  /**
   * Sets the preserve case attribute
   * 
   * @param preserveCase
   *            the preserve case attribute
   */
  public void setPreserveCase(boolean preserveCase) {
    this.preserveCase = preserveCase;

    // Since this attribute affects how the labels are computed,
    // notify all the listeners of the change.
    LabelProviderChangedEvent event = new LabelProviderChangedEvent(this);
    for (int i = 0, n = listeners.size(); i < n; i++) {
      ILabelProviderListener ilpl = (ILabelProviderListener) listeners
          .get(i);
      ilpl.labelProviderChanged(event);
    }
  }

  /**
   * Gets the image to display for a node in the tree
   * 
   * @param arg0
   *            the node
   * @return Image
   */
 /* public Image getImage(Object arg0) {
    // If the node represents a directory, return the directory image.
    // Otherwise, return the file image.
   // return ((File) arg0).isDirectory() ? dir : file;
  }*/
  public Image getImage(Object element) {
      return getIcon((File)element);
    }

  /**
   * Gets the text to display for a node in the tree
   * 
   * @param arg0
   *            the node
   * @return String
   */
  public String getText(Object arg0) {
    // Get the name of the file
    String text = ((File) arg0).getName();

    // If name is blank, get the path
    if (text.length() == 0) {
      text = ((File) arg0).getPath();
    }

    // Check the case settings before returning the text
    //return preserveCase ? text : text.toUpperCase();
    return  text;
  }

  /**
   * Adds a listener to this label provider
   * 
   * @param arg0
   *            the listener
   */
  public void addListener(ILabelProviderListener arg0) {
    listeners.add(arg0);
  }

  /**
   * Called when this LabelProvider is being disposed
   */
  public void dispose() {
    // Dispose the images
    if (dirIcon != null)
      dirIcon.dispose();
    if (fileIcon != null)
      fileIcon.dispose();
  }

  /**
   * Returns whether changes to the specified property on the specified
   * element would affect the label for the element
   * 
   * @param arg0
   *            the element
   * @param arg1
   *            the property
   * @return boolean
   */
  public boolean isLabelProperty(Object arg0, String arg1) {
    return false;
  }

  /**
   * Removes the listener
   * 
   * @param arg0
   *            the listener to remove
   */
  public void removeListener(ILabelProviderListener arg0) {
    listeners.remove(arg0);
  }
  
  private Image getIcon(File file) {
	    if (file.isDirectory())
	      return dirIcon;

	    int lastDotPos = file.getName().indexOf('.');
	    if (lastDotPos == -1)
	      return fileIcon;

	    Image image = getIcon(file.getName().substring(lastDotPos + 1));
	    return image == null ? fileIcon : image;
	  }
  
  private Image getIcon(String extension) {
	    if (imageRegistry == null)
	      imageRegistry = new ImageRegistry();
	    Image image = imageRegistry.get(extension);
	    if (image != null)
	      return image;

	    Program program = Program.findProgram(extension);
	    ImageData imageData = (program == null ? null : program.getImageData());
	    if (imageData != null) {
	      image = new Image(null, imageData);
	      imageRegistry.put(extension, image);
	    } else {
	      image = fileIcon;
	    }

	    return image;
	  }
}


