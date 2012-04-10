/*
 * 11/13/2008
 *
 * FileFileLocation.java - The location of a local file.
 * 
 * This library is distributed under a modified BSD license.  See the included
 * RSyntaxTextArea.License.txt file for details.
 */
package org.fife.ui.rsyntaxtextarea;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.IOException;
import java.io.OutputStream;


/**
 * The location of a local file.
 *
 * @author Robert Futrell
 * @version 1.0
 */
class FileFileLocation extends FileLocation {

	/**
	 * The file.  This may or may not actually exist.
	 */
	private File file;


	/**
	 * Constructor.
	 *
	 * @param file The local file.
	 */
	public FileFileLocation(File file) {
		try {
			// Useful on Windows and OS X.
			this.file = file.getCanonicalFile();
		} catch (IOException ioe) {
			this.file = file;
		}
	}


	/**
	 * Returns the last time this file was modified, or
	 * {@link TextEditorPane#LAST_MODIFIED_UNKNOWN} if this value cannot be
	 * computed (such as for a remote file).
	 *
	 * @return The last time this file was modified.
	 */
	protected long getActualLastModified() {
		return file.lastModified();
	}


	/**
	 * Returns the full path to the file.
	 *
	 * @return The full path to the file.
	 * @see #getFileName()
	 */
	public String getFileFullPath() {
		return file.getAbsolutePath();
	}


	/**
	 * Returns the name of the file.
	 *
	 * @return The name of the file.
	 * @see #getFileFullPath()
	 */
	public String getFileName() {
		return file.getName();
	}


	/**
	 * Opens an input stream for reading from this file.
	 *
	 * @return The input stream.
	 * @throws IOException If the file does not exist, or some other IO error
	 *         occurs.
	 */
	protected InputStream getInputStream() throws IOException {
		return new FileInputStream(file);
	}


	/**
	 * Opens an output stream for writing this file.
	 *
	 * @return An output stream.
	 * @throws IOException If an IO error occurs.
	 */
	protected OutputStream getOutputStream() throws IOException {
		return new FileOutputStream(file);
	}


	/**
	 * Returns whether this file location is a local file.
	 *
	 * @return Whether this is a local file.
	 * @see #isLocalAndExists()
	 */
	public boolean isLocal() {
		return true;
	}


	/**
	 * Returns whether this file location is a local file and already
	 * exists.
	 *
	 * @return Whether this file is local and actually exists.
	 * @see #isLocal()
	 */
	public boolean isLocalAndExists() {
		return file.exists();
	}


}