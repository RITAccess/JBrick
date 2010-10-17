package annotation;

import org.eclipse.jface.text.source.ISharedTextColors;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;

public class ColorCache implements ISharedTextColors {
	
	public Color getColor(RGB rgb) {
		System.out.println("color class");
		return new Color(Display.getDefault(), rgb);
	}

	public void dispose() {
	}
	
}