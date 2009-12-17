package myTest;

import org.eclipse.jface.text.source.Annotation;
import org.eclipse.jface.text.source.IAnnotationAccess;
import org.eclipse.jface.text.source.IAnnotationAccessExtension;
import org.eclipse.jface.text.source.ImageUtilities;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Canvas;

//santa's little helper
public class AnnotationMarkerAccess implements IAnnotationAccess,
		IAnnotationAccessExtension {
	public Object getType(Annotation annotation) {
		return annotation.getType();
	}

	public boolean isMultiLine(Annotation annotation) {
		return true;
	}

	public boolean isTemporary(Annotation annotation) {
		return !annotation.isPersistent();
	}

	public String getTypeLabel(Annotation annotation) {
		if (annotation instanceof ErrorAnnotation)
			return "Errors";

		return null;
	}

	public int getLayer(Annotation annotation) {
		if (annotation instanceof ErrorAnnotation)
			return ((ErrorAnnotation) annotation).getLayer();

		return 0;
	}

	public void paint(Annotation annotation, GC gc, Canvas canvas,
			Rectangle bounds) {
		ImageUtilities.drawImage(((ErrorAnnotation) annotation).getImage(), gc,
				canvas, bounds, SWT.CENTER, SWT.TOP);
	}

	public boolean isPaintable(Annotation annotation) {
		if (annotation instanceof ErrorAnnotation)
			return ((ErrorAnnotation) annotation).getImage() != null;

		return false;
	}

	public boolean isSubtype(Object annotationType, Object potentialSupertype) {
		if (annotationType.equals(potentialSupertype))
			return true;

		return false;

	}

	public Object[] getSupertypes(Object annotationType) {
		return new Object[0];
	}
}
