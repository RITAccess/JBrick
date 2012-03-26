package annotation;

import java.text.Annotation;

import javax.swing.text.Position;

// one error annotation
public class ErrorAnnotation extends Annotation {
	// error identifiers, images and colors
	public static final String ERROR_TYPE = "error.type";
	public static ImageDescriptor ERROR_IMAGE;
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

		ERROR_IMAGE = ImageDescriptor.createFromFile(ErrorAnnotation.class, "/images/help-browser.png");
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

	public ImageDescriptor getImage() {
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
