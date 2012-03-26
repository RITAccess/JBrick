package com.jbricx.filters;

import java.io.File;

public class FileExtensionFilter extends ViewerFilter {

	public boolean select(Viewer viewer, Object parentElement, Object element) {
		return ((File) element).getName().endsWith(".nxc");
	}

}