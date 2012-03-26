package com.jbricx.filters;

import java.io.File;

public class FolderFilter extends ViewerFilter {

	public boolean select(Viewer viewer, Object parentElement, Object element) {
		File file = (File) element;
		boolean isNXCFile = file.getName().endsWith(".nxc");
		boolean isDirectory = file.isDirectory();
		boolean isHiddenFile = file.getName().startsWith(".");
		return  (isNXCFile ||isDirectory) && !isHiddenFile ;
	}
}
