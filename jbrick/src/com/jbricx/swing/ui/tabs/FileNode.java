package com.jbricx.swing.ui.tabs;

import java.io.File;

public class FileNode extends File{

	public FileNode(String pathname) {
		super(pathname);
		// TODO Auto-generated constructor stub
	}
	
	public FileNode(File parent, String child) {
		super(parent, child);
		// TODO Auto-generated constructor stub
	}

	public String toString(){
		String[] list = getPath().split("\\\\");
		return list[list.length-1];
	}

}
