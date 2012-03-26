package annotation;

import java.awt.Canvas;
import java.awt.Rectangle;

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
		ImageUtilities.drawImage(((ErrorAnnotation) annotation).getImage().createImage(), gc,
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
