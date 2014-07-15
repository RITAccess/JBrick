package com.jbricx.swing.ui.preferences;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.concurrent.Callable;
import java.util.prefs.Preferences;

import com.jbricx.swing.ui.MainWindow;

public class MiscProperties {
	private static String store = "propertiesJbricks"; 
	private static Preferences propertiesMisc = Preferences.userRoot().node(store);
	private static MainWindow window;
	private static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	
	public MiscProperties(MainWindow jbricks){
		MiscProperties.window = jbricks;
	}
	
	public static enum misc {
		X(new Callable<Integer>(){
			public Integer call() throws Exception { return window.getX(); }
		}, screenSize.width-screenSize.width/10),
		Y(new Callable<Integer>(){
			public Integer call() throws Exception { return window.getY(); }
		}, screenSize.height-(screenSize.height/10) ),
		WIDTH(new Callable<Integer>(){
			public Integer call() throws Exception { return window.getWidth(); }
		}, 0),
		HEIGHT(new Callable<Integer>(){
			public Integer call() throws Exception { return window.getHeight(); }
		}, 0),
		;

		Callable<Integer> propertyUpdateFunction;
		int defaultValue;
		
		misc(Callable<Integer> func, int defaultValue){
			this.propertyUpdateFunction = func;
			this.defaultValue = defaultValue;
		}
		
		public int getCurrent() {
			try {
				return propertyUpdateFunction.call();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return 0;
		}
	}
	
	public static int getInt(misc property){
		return propertiesMisc.getInt(
				property.toString(),
				property.defaultValue
			);
	}
	
	public static void setInt(misc property){
		propertiesMisc.putInt(
				property.toString(), 
				property.getCurrent()
			);
	}
	
}
