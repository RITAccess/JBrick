/**
 * 
 */
package com.jbricx.swing.communications;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
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
 * We check if there is nothing set in the preferences, which indicates a
 * default running location for the Jar file.
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

	public CompilerRunner() {
		this.preferences = PreferenceStore.getPrefs();
	}

	public ExitStatus download(final String filename, final String port) {
		String nbcPath = preferences.get(PreferenceStore.NBCTOOL,
				PreferenceStore.NBCTOOL_DEFAULT);
		if (nbcPath.equals("")) {
			nbcPath = getCompilerPath();
		}
		return run(nbcPath, port, "-d", filename);
	}

	public ExitStatus compile(final String filename) {
		String nbcPath = preferences.get(PreferenceStore.NBCTOOL,
				PreferenceStore.NBCTOOL_DEFAULT);
		if (nbcPath.equals("")) {
			nbcPath = getCompilerPath();
		}
		return run(nbcPath, filename);
	}

	private ExitStatus run(final String... command) {
		final List<CompilerError> list = new ArrayList<CompilerError>();
		Process proc;
		ProcessBuilder pb = new ProcessBuilder(command);
		
		// Merge the error stream with the input stream so that both can be flushed
		// so that the buffer doesn't fill up and cause the process to halt.
		pb.redirectErrorStream(true);
		for(String eachCommand : command){
			System.out.println(eachCommand);
		}
		try {
			proc = pb.start();
			final BufferedReader bufferedreader = new BufferedReader(
					new InputStreamReader(new BufferedInputStream(
							proc.getInputStream())));

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
				if(line.contains("Error: ")){
					CompilerError ce = new CompilerError();
					ce.setMessageLine(line);
					ce.setFileLine(bufferedreader.readLine());
					ce.setLine(bufferedreader.readLine());
					
					// Last line of error message is never used.
					line = bufferedreader.readLine();
					// Somehow, sometimes it comes empty so we read it again to be
					// sure
					if (line != null && line.isEmpty()) {
						line = bufferedreader.readLine();
					}
					list.add(ce);
				}

				line = bufferedreader.readLine();
			}

			bufferedreader.close();
			proc.destroy();
			return new ExitStatus(list.isEmpty() ? ExitStatus.OK
					: ExitStatus.ERROR, list);

		} catch (IOException e) {
			JOptionPane.showMessageDialog(null,
					"The compiler could not be found.");
			return new ExitStatus(ExitStatus.ERROR, list);
		} catch (final Exception e) {
			e.printStackTrace();
			return new ExitStatus(ExitStatus.ERROR, list);
		}
	}

	/**
	 * Runs to find the location of the temp file for the jar files usage.
	 * 
	 * @return
	 */
	private String getCompilerPath() {
		InputStream src = null;
		FileOutputStream out = null;
		File exeTempFile = null;
		try {
			src = getClass().getResource("/nbc.exe").openStream();

			exeTempFile = File.createTempFile("nbc", ".exe");
			out = new FileOutputStream(exeTempFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NullPointerException e)
		{
			// cannot find nbc tool by default using eclipse, need to point to it
			// in the preferences window
			System.out.println("You forgot to set NBC tool location in the preferences window.");
		}
		byte[] temp = new byte[32768];
		int rc;
		try {
			while ((rc = src.read(temp)) > 0) {
				out.write(temp, 0, rc);
			}
			src.close();
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		exeTempFile.deleteOnExit();
		return exeTempFile.getAbsolutePath();
	}

}
