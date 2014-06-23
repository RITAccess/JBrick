package com.jbricx.communication;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import com.jbricx.swing.ui.preferences.PreferenceStore;
import com.jbricx.swing.ui.preferences.PreferenceStore.Preference;

public class NXTAccess {
	public static String[] compile(String filepath){
		return NXTAccess.runNBC(filepath, false);
	}
	
	public static String[] downloadToBrick(String filepath){
		return NXTAccess.runNBC(filepath, true);
	}
	
	private static String[] runNBC(String filepath, boolean download){
		String nbc = PreferenceStore.getString(Preference.NBCTOOL);
		ProcessBuilder pb = new ProcessBuilder(
				nbc + (download ? " -d" : ""),
				filepath
		);
		pb.redirectErrorStream(true);
		String[] errorsList = null;
		try {
			Process process = pb.start();
			BufferedReader buffReader = new BufferedReader(
					new InputStreamReader(new BufferedInputStream(
							process.getInputStream())));
			String line;
			/*
			 * read lines of nbc compiler, 
			 * if there is an error, find out which line it is and print error
			 */
			ArrayList<String> errors = new ArrayList<String>();
			do {
				line = buffReader.readLine();
				System.out.println(line);
				if (line != null && line.contains("Error: ")){
					String errorLineNum = line; // contains the error information
					do {
						line = buffReader.readLine(); // Looking for Line number in error printout
						System.out.println(line);
					} while (!line.contains("ine "));
					errors.add(String.format(
							"Error line %s: %s",
							line.substring(line.indexOf("line ") + 5),
							errorLineNum.substring(errorLineNum.indexOf("Error: ") + 7) 
					));
				}
			} while (line != null);
			buffReader.close();
			process.destroy();
			errorsList = errors.toArray(new String[errors.size()]);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return errorsList;
	}
}
