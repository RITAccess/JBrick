package com.jbricx.ui.findbrick;
/*
 * @author Michael Goldstein
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.jbricx.communications.NXT.ConnectionType;

public class FindBrickFileIO {

  private static final String file = "JBConn.properties";

  /**
   * constructor
   */
  //FindBrickFileIO() {
  //}
  /**
   *
   * @return ConnectionType enum value
   */
  public static ConnectionType getCT() {
    ConnectionType ct = null;
    try {
      BufferedReader in = new BufferedReader(new FileReader(file));
      String str;
      try {
        str = in.readLine();
        if (str.equals("BT")) {
          ct = ConnectionType.BLUETOOTH;
        } else {
          ct = ConnectionType.USB;
        }
      } catch (NullPointerException ne) {
        ct = ConnectionType.USB;
      }
      in.close();
    } catch (IOException e) {
      System.err.println("FindBrickFileIO.java@44: Could not connect to " + ct.getName());
    }

    if (ct == null) {
      ct = ConnectionType.USB;
    }

    return ct;
  }

  public void saveCT(ConnectionType ct) {
    if (ct == ConnectionType.USB) {
      saveCT("USB");
    } else {
      saveCT("BT");
    }
  }

  public static void saveCT(String str) {
    try {
      BufferedWriter out = new BufferedWriter(
              new FileWriter("JBConn.properties"));
      if (str.equals("USB")) {
        out.write("USB");
        System.out.println("Saved 'USB' to File");
      } else {
        out.write("BT");
        System.out.println("Saved 'BT' to File");
      }
      out.close();
    } catch (IOException e) {
      System.err.println("FindBrickFileIO.java@43: There was error while saving"
              + " the connection :" + str);
    }
  }
}
