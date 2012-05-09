package com.jbricx.swing.ui.tabs;

import java.io.File;

@SuppressWarnings("serial")
public class FileNode extends File{

	public FileNode(String pathname) {
		super(pathname);
		// TODO Auto-generated constructor stub
	}
	
	public FileNode(File parent, String child) {
		super(parent, child);
		// TODO Auto-generated constructor stub
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
