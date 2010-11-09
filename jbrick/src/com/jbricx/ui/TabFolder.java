/**
 * 
 */
package com.jbricx.ui;

/**
 * @author byktol
 */
public interface TabFolder {

    boolean open(final String filename);

    boolean openNewFile();

    boolean checkOverwrite();
}
