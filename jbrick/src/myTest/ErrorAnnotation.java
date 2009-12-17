package myTest;

import org.eclipse.core.resources.IMarker;
import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.source.Annotation;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

// one error annotation
public class ErrorAnnotation extends Annotation {
	// error identifiers, images and colors
	public static String ERROR_TYPE = "error.type";
	public static Image ERROR_IMAGE;
	private IMarker marker;
	private String text;
	private int line;
	private Position position;

	public ErrorAnnotation(IMarker marker) {
		this.marker = marker;
	}

	public ErrorAnnotation(int line, String text) {
		super(ERROR_TYPE, true, null);
		this.marker = null;
		this.line = line;
		this.text = text;
		
		ERROR_IMAGE = new Image(Display.getDefault(),
		"src/images/error_ovr.gif");
	}

	public IMarker getMarker() {
		return marker;
	}

	public int getLine() {
		return line;
	}

	public String getText() {
		return text;
	}

	public Image getImage() {
		return ERROR_IMAGE;
	}

	public int getLayer() {
		return 3;
	}

	public String getType() {
		return ERROR_TYPE;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}
}
