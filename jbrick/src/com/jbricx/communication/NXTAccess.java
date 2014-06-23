package com.jbricx.communication;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import com.jbricx.swing.ui.preferences.PreferenceStore;
import com.jbricx.swing.ui.preferences.PreferenceStore.Preference;

public class NXTAccess {
	public static HashMap<String, ArrayList<String>> compile(String filepath){
		return NXTAccess.runNBC(filepath, false);
	}
	
	public static HashMap<String, ArrayList<String>> downloadToBrick(String filepath){
		return NXTAccess.runNBC(filepath, true);
	}
	
	private static HashMap<String, ArrayList<String>> runNBC(String filepath, boolean download){
		String nbc = PreferenceStore.getString(Preference.NBCTOOL);
		ProcessBuilder pb = new ProcessBuilder(
				nbc + (download ? " -d" : ""),
				filepath
		);
		HashMap<String, ArrayList<String>> errorMap = null;
		try {
			Process process = pb.start();
			BufferedReader buffReader = new BufferedReader(
					new InputStreamReader(new BufferedInputStream(
							process.getErrorStream())));
			String line;
			/*
			 * read lines of nbc compiler, 
			 * if there is an error, find out which line it is and print error
			 * 
			 * store errors in a hash with <FilePath, Error> 
			 */
			errorMap = new HashMap<String, ArrayList<String>>();
			do {
				line = buffReader.readLine();
				if (line != null && line.contains("Error: ")){
					String errorLineNum = line; // contains the error information
					do {
						line = buffReader.readLine(); // Looking for Line number / File name in error printout
					} while (!line.contains("line "));
					String file = line.subSequence(6, line.indexOf(" ; ")).toString();
					ArrayList<String> errorList = errorMap.get(file);
					if (errorList == null){
						errorList = new ArrayList<String>();
						errorMap.put(file, errorList);
					}
					errorMap.get(file).add(
							String.format( "Error line %s: %s",
							line.substring(line.indexOf("line ") + 5),
							errorLineNum.substring(errorLineNum.indexOf("Error: ") + 7) 
					));
				}
			} while (line != null);
			buffReader.close();
			process.destroy();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return errorMap;
	}
}
