package com.jbricx.ui.findbrick;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.jbricx.communications.enums.ConnectionType;

/**
 * @author Michael Goldstein
 */
public class FindBrickFileIO {

	private static final String file = "JBConn.properties";

	/**
	 * @return ConnectionType enum value
	 */
	public static ConnectionType getCT() {
		ConnectionType ct = null;
		try {
			FileReader f = new FileReader(file);
			BufferedReader in = new BufferedReader(f);
			
			String str;
			try {
				str = in.readLine();

				if (str.equals("BT")) {
					ct = ConnectionType.BTH;
				} else {
					ct = ConnectionType.USB;
				}
			} catch (NullPointerException ne) {
				ct = ConnectionType.USB;
			}
			//f.close();
			//in.close();
		} catch (IOException e) {
			//TODO: while opening file exception is thrown at this point!
			if (ct == null) {
				ct = ConnectionType.USB;
			}
		}
		return ct;
	}

	public static void saveCT(ConnectionType ct) {
		if (ct == ConnectionType.USB) {
			saveCT("USB");
		} else {
			saveCT("BT");
		}
	}

	public static void saveCT(String str) {
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(file));
			if (str.equals("USB")) {
				out.write("USB");
				System.out.println("Saved 'USB' to File");
			} else {
				out.write("BT");
				System.out.println("Saved 'BT' to File");
			}
			out.close();
		} catch (IOException e) {
			System.err
					.println("FindBrickFileIO.java@43: There was error while saving"
							+ " the connection :" + str);
		}
	}
}
