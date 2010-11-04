/**
 * 
 */
package com.jbricx.ui;


/**
 * @author byktol
 */
public interface TabFolder {

  boolean contains(final String filename);
  boolean open(final String filename);
  void save(String filename);
  boolean openNewFile();
  boolean checkOverwrite();
}
