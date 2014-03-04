/*
 * TestUtils
 * Set of functions from javaworld.com to help with the automation of swing functions
 */
 
package jbrick.library;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;

public class TestUtils {

	static int counter;

	public static Component getChildNamed(Component parent, String name) {

		if (parent instanceof AbstractButton) {
			parent.setName(((AbstractButton) parent).getText());
		}
		
		if (name.equals(parent.getName())) { return parent; }

		if (parent instanceof Container) {
			Component[] children = (parent instanceof JMenu) ?
					((JMenu)parent).getMenuComponents() :
					((Container)parent).getComponents();

			for (int i = 0; i < children.length; i++) {
				Component child = getChildNamed(children[i], name);
				if (child != null) { return child; }
			}
		}
		
		return null;
	}	
	
	public static Component getChildNamed(Component parent, String name, Component...ignoreComponents){
		boolean ignore = false;
		for (int i = 0; i < ignoreComponents.length; i++){
			ignore = ignoreComponents[i].equals(parent) || ignore; 
		}
		
		if (!ignore){
			if (parent instanceof AbstractButton) {
				parent.setName(((AbstractButton) parent).getText());
			}
			
			if (name.equals(parent.getName())) { return parent; }
		}
	
		if (parent instanceof Container) {
			Component[] children = (parent instanceof JMenu) ?
					((JMenu)parent).getMenuComponents() :
					((Container)parent).getComponents();

			for (int i = 0; i < children.length; i++) {
				Component child = getChildNamed(children[i], name, ignoreComponents);
				if (child != null) { return child; }
			}
		}
		
		return null;
	}
	
	/**
	 * AbstractButton - returns a button in the parent, that has the text (or is named) name
	 * @param parent - component that the search starts from
	 * @param name - the name of the button
	 * @return - returns the button, unless the button cannot be found (which then it returns null)
	 */
	public static AbstractButton getButton(Component parent, String name){
		AbstractButton item = (AbstractButton) TestUtils.getChildNamed(parent, "Save");
		
		if (item != null)
			return item;
		return null;
	}
}

