package com.jbricx.source;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferenceConverter;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;

import com.jbricx.preferences.JBrickObserver;

/**
 * This class manages the colors. It uses a lazy initialization approach, only
 * creating the actual color if it's requested.
 */
public class ColorManager implements JBrickObserver {

  public enum ColorFor {
    FOREGROUND,
    BACKGROUND,
    COMMENT,
    KEYWORD,
    OPERATOR,
    STRING,
    LINENUMBERFG,
    LINENUMBERBG;

    public String property() {
      return this.toString().toLowerCase() + "Color";
    }
  }

  // Map to store created colors
  private Map<ColorFor, Color> colors = new HashMap<ColorFor, Color>();

  public ColorManager(final IPreferenceStore ps) {
    update(ps);
  }

  /**
   * Gets a color
   * 
   * @param color the corresponding rgb
   * @return Color
   */
  public Color getColor(ColorFor color) {
    return colors.get(color);
  }

  /**
   * Dispose any created colors
   */
  @Override
  protected void finalize() throws Throwable {
    for (Color c : colors.values()) {
      c.dispose();
    }

    super.finalize();
  };

  /**
   * Update from preference
   */
  @Override
  public void update(final IPreferenceStore ps) {
    for (ColorFor c : ColorFor.values()) {
      RGB rgb = PreferenceConverter.getColor(ps, c.property());
      colors.put(c, new Color(Display.getCurrent(), rgb));
    }
  }
}
