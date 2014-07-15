package com.jbricx.communication;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import com.jbricx.swing.ui.preferences.PreferenceStore;
import com.jbricx.swing.ui.preferences.PreferenceStore.Preference;

/**
 * NXTAccess provides all the necessary functions to get the programs to download on a NXT brick
 * and to compile.
 * 
 * @author Ethan Jurman (ehj2229)
 */
public class NXTAccess {
	/**
	 * Compiles the filepath program, returns any errors that might exist. 
	 * @param filepath - the filepath of the nxc program
	 * @return a HashMap containing filepaths, and errors for individual files
	 */
	public static HashMap<String, ArrayList<String>> compile(String filepath){
		return NXTAccess.runNBC(filepath, false, false, false);
	}
	
	/**
	 * Downloads the program onto the brick. Does all the commands of the compiler as well.
	 * @param filepath
	 * @return a HashMap containing filepaths, and errors for individual files
	 */
	public static HashMap<String, ArrayList<String>> downloadToBrick(String filepath, boolean debug, boolean run){
		return NXTAccess.runNBC(filepath, true, debug, run);
	}
	
	/**
	 * Builds and runs a process (requires the NBC compiler) that downloads / compiles the application
	 * onto the NXT brick.
	 * 
	 * @param filepath - filepath of the program
	 * @param download - whether or not the program needs to be downloaded to the nxt brick
	 * @return a HashMap containing filepaths, and errors for individual files
	 */
	private static HashMap<String, ArrayList<String>> runNBC(String filepath, boolean download, boolean debug, boolean run){		
		//If it is in debugging mode then use the debug version of the file
		if(debug){
			String[] path = filepath.split("/");
			String newPath = "";
			for(int i = 0; i < path.length-1; i++){
				newPath += path[i] + "/";
			}
			newPath += "debug/";
			newPath += "debug." + path[path.length-1];
			filepath = newPath;
		}
		
		String nbc = PreferenceStore.getString(Preference.NBCTOOL);
		File nbcFile = new File(nbc);
		
		if (!nbcFile.exists()){
			HashMap<String, ArrayList<String>> noNBC = new HashMap<String, ArrayList<String>>();
			noNBC.put("No NBC Tool Compiler", new ArrayList<String>());
			return noNBC; // requires nbc program
		}
		
		if(download && !USBConnection.isConnected()){
			HashMap<String, ArrayList<String>> noNXT = new HashMap<String, ArrayList<String>>();
			noNXT.put("No NXT Brick connected. Please plug in and turn on your NXT Brick through a USB Cable.", new ArrayList<String>());
			return noNXT; // requires nbc program
		}
		
		ProcessBuilder pb;
		if (download){
			if(run){
				pb = new ProcessBuilder(nbc, "-r", filepath);
			}
			else{
				pb = new ProcessBuilder(nbc, "-d", filepath);	
			}
		} else {
			pb = new ProcessBuilder(nbc, filepath);
		}
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
					String file = line.subSequence(6, line.indexOf(" ; ")-1).toString();
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
