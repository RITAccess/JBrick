/*
 * TestUtils
 * Set of functions from javaworld.com to help with the automation of swing functions
 */
 
package jbrick.library;

import java.awt.*;
import java.util.concurrent.Callable;

import com.jbricx.swing.ui.tabs.JBricxFilePane;
import com.jbricx.swing.ui.tabs.JBricxTabItem;

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
	
	/**
	 * getComponent
	 */
	public static Component getComponent(Component parent, Class<?> compClass){
		if (parent.getClass().equals(compClass))
			return parent;
		
		if (parent instanceof Container){
			Component[] children = (parent instanceof JMenu) ?
					((JMenu) parent).getMenuComponents():
					((Container) parent).getComponents();
			for (Component child: children){
				Component childReturn = getComponent(child, compClass);
				if (childReturn != null)
					return childReturn;
			}
		}
		return null;
	}
	
	
	/**
	 * Loop for given number of milliseconds
	 */
	public static void waitUntil(final int milli, Callable<Object> c) {
		
		Thread timer = new Thread() {
			public void run() {
				try {
					Thread.sleep(milli);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		timer.start();
		
		try {
			while ((!(Boolean) c.call()) && (timer.isAlive())) {
			}
			if  ((!(Boolean) c.call()) && (!timer.isAlive())) {
				System.out.println("waitUntil ended from Timer");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	
	// DEBUGGER
	public static void main(String[] args) {
	
		MainWindow mainWindow = new MainWindow();
		mainWindow.run();
		Component comp = TestUtils.getComponent(mainWindow, JBricxTabItem.class);
		if (comp instanceof JBricxFilePane){
			System.out.println(((JBricxFilePane) comp).getName());
		}
		System.out.println(comp);
	}
}
