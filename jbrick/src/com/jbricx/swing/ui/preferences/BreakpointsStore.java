package com.jbricx.swing.ui.preferences;

import java.util.prefs.Preferences;


public class BreakpointsStore {

	private static Preferences breakpointPrefs;
	/**
	 * Constructor for Breakpoints Store.
	 */
	public BreakpointsStore() {
		final String NAME = "breakpointsJBricks";
		breakpointPrefs = Preferences.userRoot().node(NAME);
	}
	
	public static void removeBreakpoints(String filename){
		breakpointPrefs.remove(filename);
	}

	public static void putBreakpoints(String filename, int[] lines) {
		if (lines.length == 0){
			removeBreakpoints(filename);
		} else {
			breakpointPrefs.put(filename, intArrayToStr(lines));
		}
	}
	
	public static Preferences getBreakpointPrefs(){
		return breakpointPrefs;
	}

	public static int[] getBreakLines(String fileName) {
		int[] lineNumbers = {};
		String lines = breakpointPrefs.get(fileName, "");
		if (lines != "") { 
			lineNumbers = strToIntArray(lines);
		}
		return lineNumbers;
	}
	
	private static String intArrayToStr(int[] intArray){
		StringBuilder str = new StringBuilder(); 
		for (int i = 0; i < intArray.length - 1; i++){
			str.append(intArray[i] + ", ");
		}
		str.append(intArray[intArray.length - 1]);
		return str.toString();
	}
	
	private static int[] strToIntArray(String str){
		int[] intArray = new int[str.split(", ").length];
		int count = 0;
		for (String s : str.split(", ")){
			intArray[count] = Integer.parseInt(s);
			count++;
		}
		return intArray;
	}
}
