package com.jbricx.swing.ui.tabs;

import java.io.File;

@SuppressWarnings("serial")
public class FileNode extends File{

	/**
	 * Provides information and filepath for the file viewer
	 * @param pathname
	 */
	public FileNode(String pathname) {
		super(pathname);
	}
	
	public FileNode(File parent, String child) {
		super(parent, child);
	}

	@Override
	public String toString(){
		if( System.getProperty("os.name").startsWith("Windows")){
			String[] list = getPath().split("\\\\");
			return list[list.length-1];
		}
		else{
			String[] list = getPath().split("/");
			return list[list.length-1];
		}
	}

}
