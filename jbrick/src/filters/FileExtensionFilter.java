package filters;

import java.io.File;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

public class FileExtensionFilter extends ViewerFilter {

	public boolean select(Viewer viewer, Object parentElement, Object element) {
		return ((File) element).getName().endsWith(".nxc");
	}

}