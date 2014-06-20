package com.jbricx.communication;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.jbricx.swing.ui.preferences.PreferenceStore;
import com.jbricx.swing.ui.preferences.PreferenceStore.Preference;

public class NXTAccess {
	public static void compile(String filepath){
		NXTAccess.runNBC(filepath, false);
	}
	
	public static int downloadToBrick(String filepath){
		return NXTAccess.runNBC(filepath, true);
	}
	
	private static int runNBC(String filepath, boolean download){
		String nbc = PreferenceStore.getString(Preference.NBCTOOL);
		int success = 0;
		ProcessBuilder pb = new ProcessBuilder(
				nbc,
				download ? "-d" : "",
				filepath
		);
		try {
			Process process = pb.start();
			BufferedReader buffReader = new BufferedReader(
					new InputStreamReader(new BufferedInputStream(
							process.getInputStream())));
			String line = "";
			do {
				line = buffReader.readLine();
				System.out.println(line);
			} while (line != null);
			buffReader.close();
			process.destroy();
			success = 1; // TODO: check if was success or fail... make enum?
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return success;
	}
}
