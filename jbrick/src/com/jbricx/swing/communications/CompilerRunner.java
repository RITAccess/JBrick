/**
 * 
 */
package com.jbricx.swing.communications;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.prefs.Preferences;

import javax.swing.JOptionPane;

import com.jbricx.swing.communications.ExitStatus;
import com.jbricx.swing.ui.preferences.PreferenceStore;

/**
 * Runs the compiler tool in its own process. The compiler tool is external to
 * this application and is dependent on the operating system. The purpose is for
 * the "upper layers" to not know anything about the compiler and its
 * interaction with the application.
 * 
 * @author byktol
 * @see ExitStatus
 * @see CompilerError
 */
public class CompilerRunner {

	/**
	 * The preference store on where to find the compiler. I haven't found a way
	 * to eliminate this dependency and make the
	 */
	private Preferences preferences;

	public CompilerRunner(){
		this.preferences = PreferenceStore.getPrefs();
	}
	
	public ExitStatus download(final String filename, final String port) {
		return run(preferences.get(PreferenceStore.NBCTOOL, PreferenceStore.NBCTOOL_DEFAULT), port,
				"-d", filename);
	}

	public ExitStatus compile(final String filename) {
		if(preferences.get(PreferenceStore.NBCTOOL, PreferenceStore.NBCTOOL_DEFAULT).equals("")){
			return run(getClass().getResource("nbc.exe").getPath(),
					filename);
		}
		return run(preferences.get(PreferenceStore.NBCTOOL, PreferenceStore.NBCTOOL_DEFAULT),
				filename);
	}

	private ExitStatus run(final String... command) {
		int length = command.length;
		final List<CompilerError> list = new ArrayList<CompilerError>();
		Process proc;
		ProcessBuilder pb = new ProcessBuilder(command);

		try {
			proc = pb.start();
			final BufferedReader bufferedreader = new BufferedReader(
					new InputStreamReader(new BufferedInputStream(
							proc.getErrorStream())));
			

			/*
			 * Listen up, boyo, every (most?) error message by NBC is composed
			 * of 4 lines. Therefore, we'll force our way to read 4 consecutive
			 * lines. Hopefully, we're not mistaken on this.
			 * 
			 * Oh, one more thing: Sometimes the output contains an additional
			 * error message that prints right after all the error messages,
			 * e.g.: 3 errors during compilation
			 */
			String line = bufferedreader.readLine();
			while (!(line == null || line.contains("during compilation"))) {
				CompilerError ce = new CompilerError();
				ce.setMessageLine(line);
				ce.setFileLine(bufferedreader.readLine());
				ce.setLine(bufferedreader.readLine());

				// Last line of error message is never used.
				line = bufferedreader.readLine();
				// Somehow, sometimes it comes empty so we read it again to be
				// sure
				if (line.isEmpty()) {
					line = bufferedreader.readLine();
				}

				list.add(ce);
				line = bufferedreader.readLine();
			}

			bufferedreader.close();
			return new ExitStatus(list.isEmpty()? ExitStatus.OK : ExitStatus.ERROR, list);

		}catch(IOException e){
			JOptionPane.showMessageDialog(null, "The compiler could not be found.");
			return new ExitStatus(ExitStatus.ERROR, list);
		}catch (final Exception e) {
			e.printStackTrace();
			return new ExitStatus(ExitStatus.ERROR, list);
		}	
	}

}
