package com.jbricx.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.PlainDocument;

/**
 * This class adds persistence to the Document class
 * 
 * Handles FileIO for the document as well.
 */
public class PersistentDocument extends PlainDocument implements
		DocumentListener {

	private String fileName;
	private boolean dirty;

	/**
	 * Constructor
	 */
	public PersistentDocument(String fileName) {
		this.fileName = fileName;
		addDocumentListener(this);
	}

	public PersistentDocument() {
		addDocumentListener(this);
	}

	/**
	 * Gets whether this document is dirty
	 * 
	 * @return boolean
	 */
	public boolean isDirty() {
		return dirty;
	}

	/**
	 * Gets the file name
	 * 
	 * @return String
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * Sets the file name
	 * 
	 * @param fileName
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * Saves the file
	 * 
	 * @throws IOException
	 *             if any problems
	 */
	public void save() throws IOException {
		if (fileName == null) {
			throw new IllegalStateException("Can't save file with null name");
		}
		ObjectOutputStream oos = null;
		try {
			FileOutputStream fos = new FileOutputStream(fileName);
			oos = new ObjectOutputStream(fos);
			oos.writeObject(this);

		} finally {
			oos.flush();
			oos.close();
		}
	}

	/**
	 * Opens the file
	 * 
	 * @throws IOException
	 *             if any problems
	 */
	public void open() throws IOException {
		if (fileName == null) {
			throw new IllegalStateException("Can't open file with null name");
		}
		String strLine;
		FileInputStream in;
		try {
			in = new FileInputStream(new File(fileName));
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			try {
				while ((strLine = br.readLine()) != null) {
					try {
						this.insertString(this.getLength(), strLine + "\n",
								null);
					} catch (BadLocationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Clears the file's contents
	 */
	public void clear() {
		this.clear();
		fileName = "";
		setDirty(false);
	}

	private void setDirty(boolean isDirty) {
		dirty = isDirty;
	}

	@Override
	public void changedUpdate(DocumentEvent arg0) {
		// System.out.println("Changed Update " + arg0.getDocument());
		setDirty(true);
	}

	@Override
	public void insertUpdate(DocumentEvent arg0) {
		// System.out.println("insert update " + arg0.getDocument());
		setDirty(true);

	}

	@Override
	public void removeUpdate(DocumentEvent arg0) {
		// System.out.println("Remove update " + arg0);
		setDirty(true);

	}

	/**
	 * Returns true if the document has something in it.
	 * 
	 * @return
	 */
	public boolean isEmpty() {
		return getLength() > 0;
	}
}
