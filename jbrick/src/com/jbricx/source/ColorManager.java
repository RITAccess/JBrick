package com.jbricx.source;

import java.util.*;

import org.eclipse.jface.preference.PreferenceConverter;
import org.eclipse.jface.preference.PreferenceStore;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.widgets.Display;

import com.jbricx.pjo.JBrickEditor;
import com.jbricx.preferences.JBrickObservable;

/**
 * This class manages the colors. It uses a lazy initialization approach, only
 * creating the actual color if it's requested.
 */
public class ColorManager implements JBrickObservable{
  public static final RGB BACKGROUND = new RGB(255, 255, 255);
  //public static final RGB COMMENT = new RGB(0, 128, 0);
  
  //color para los comentarios
  public static final RGB COMMENT = new RGB(0, 128, 0);
  //public static final RGB COMMENT = new RGB(0, 128, 0);
  //public static final RGB KEYWORD = new RGB(0, 128, 128);


  //palabras claves, int, void
  //public static final RGB KEYWORD = new RGB(0, 128, 128);
  public static final RGB KEYWORD = new RGB(0, 0, 255);
  //public static final RGB KEYWORD = new RGB(0, 255, 0);
  public static final RGB NUMBER = new RGB(255, 0, 255);
  //public static final RGB STRING = new RGB(255, 0, 0);
  public static final RGB STRING = new RGB(255, 255, 0);
  // public static final RGB DEFAULT = new RGB(0, 0, 0);
  //public static RGB DEFAULT = new RGB(129, 129, 0);
  public static RGB DEFAULT = new RGB(255, 255, 0);
  
  public static final RGB OPERATOR = new RGB(255, 140, 0);
  


  // Map to store created colors, with the corresponding RGB as key
  private Map<RGB, Color> colors = new HashMap<RGB, Color>();

  /**
   * Gets a color
   * 
   * @param rgb the corresponding rgb
   * @return Color
   */
  public Color getColor(RGB rgb) {
    // Get the color from the map
    Color color = (Color) colors.get(rgb);
    if (color == null) {
      // Color hasn't been created yet; create and put in map
      color = new Color(Display.getCurrent(), rgb);
      colors.put(rgb, color);
    }
    return color;
  }

  /**
   * Set default color of the string
   */
  private void setDefaultColor(RGB rgb) {
	  // Set the default color
	  DEFAULT = rgb ;
	  return ;
  }
  /**
   * Dispose any created colors
   */
  public void dispose() {
    for (Iterator<Color> itr = colors.values().iterator(); itr.hasNext();)
      ( itr.next()).dispose();
  }

  @Override
  public void update() {
	  // Update from preference
	  PreferenceStore store =  JBrickEditor.getInstance().getPreferences();
	  RGB fgRBG = PreferenceConverter.getColor(store, "fgColor");
	  setDefaultColor(fgRBG) ;	
  }
}
