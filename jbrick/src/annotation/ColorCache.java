package annotation;

import java.awt.Color;

public class ColorCache implements ISharedTextColors {
	
	public Color getColor(RGB rgb) {
		System.out.println("color class");
		return new Color(Display.getDefault(), rgb);
	}

	public void dispose() {
	}
	
}