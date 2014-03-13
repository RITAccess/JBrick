/*
 * TestUtils
 * Set of functions from javaworld.com to help with the automation of swing functions
 */
 
package jbrick.library;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;

import com.jbricx.swing.ui.MainWindow;

public class TestUtils {
	
	public static Component getChildNamed(Component parent, String text, Component...ignoreComponents){
		if (parent instanceof AbstractButton){
			if (((AbstractButton) parent).getText().equals(text)){
				boolean ignore = false;
				for (Component ignoreCom: ignoreComponents){
					if (ignoreCom != null)
						if (ignoreCom.equals(parent)){
							ignore = true;
						}
				}
				if (!ignore){
					return parent;
				}
			}
		}
		if (parent instanceof Container){
			Component[] children = (parent instanceof JMenu) ?
					((JMenu) parent).getMenuComponents():
					((Container) parent).getComponents();
			for (Component child: children){
				Component childReturn = getChildNamed(child, text, ignoreComponents);
				if (childReturn != null)
					return childReturn;
			}
		}
		return null;
	}
	
	/**
	 * getButton - returns a button in the parent, that has the text (or is named) name
	 * @param parent - component that the search starts from
	 * @param name - the name of the button
	 * @return - returns AbstractButton, unless the button cannot be found (which then it returns null)
	 */
	public static AbstractButton getButton(Component parent, String name, Component...ignore){
		AbstractButton item = (AbstractButton) getChildNamed(parent, name, ignore);
		return item;
	}
}
