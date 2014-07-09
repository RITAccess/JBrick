package com.jbricx.swing.ui.preferences;

import java.util.prefs.Preferences;

import com.jbricx.swing.ui.tabs.AudioBreak;


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
	/**
	 * Put an array of break points in a preferences file for a specific NXC file.
	 * @param filename Name of the NXC file.
	 * @param breaks Array of breaks.
	 */
	public static void putBreakpoints(String filename, AudioBreak[] breaks) {
		removeBreakpoints(filename);
		if (breaks.length > 0){
			breakpointPrefs.put(filename, breakPointsToStr(breaks));
		}
	}
	
	public static Preferences getBreakpointPrefs(){
		return breakpointPrefs;
	}
	/**
	 * Get the audio breaks from a file.
	 * @param fileName Name of NXC file.
	 * @return Array of audio breaks.
	 */
	public static AudioBreak[] getBreakLines(String fileName) {
		AudioBreak[] audioBreaks = {};
		String lines = breakpointPrefs.get(fileName, "");
		if (lines.matches("([0-9]*: [0-9]*(, )?)+")) { 
			audioBreaks = strToAudioBreakArray(lines);
		} else {
			removeBreakpoints(fileName);
		}
		return audioBreaks;
	}
	
	/**
	 * Converts an array of audio breaks into a string for storage within java preferences.
	 * @param breakArray Array to be stored
	 * @return String for use in preferences
	 */
	private static String breakPointsToStr(AudioBreak[] breakArray){
		StringBuilder str = new StringBuilder(); 
		for (int i = 0; i < breakArray.length - 1; i++){
			str.append(breakArray[i].getLineNumber() + ": " + breakArray[i].getKey() + ", ");
		}
		
		str.append(breakArray[breakArray.length - 1].getLineNumber() + ": " + breakArray[breakArray.length - 1].getKey());
		
		return str.toString();
	}
	
	/**
	 * Converts a string to an array of audio breaks. These breaks do not have their line height set. 
	 * To properly display them this must be set at a later point
	 * @param str String to convert.
	 * @return AudioBreak[]
	 */
	private static AudioBreak[] strToAudioBreakArray(String str){
		AudioBreak[] breakArray = new AudioBreak[str.split(", ").length];
		int count = 0;
		for (String s : str.split(", ")){
			int lineNum = Integer.parseInt(s.split(":")[0].trim());
			int key = Integer.parseInt(s.split(":")[1].trim());
			breakArray[count] = new AudioBreak(lineNum);
			breakArray[count].setKey(key);
			count++;
		}
		return breakArray;
	}
}
