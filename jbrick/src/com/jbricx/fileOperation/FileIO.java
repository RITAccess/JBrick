package com.jbricx.fileOperation;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;

/**
 * @author pxs1160
 *
 */
public class FileIO {

	/**
	 * Open() method takes in the 
	 * location and the name of the file and 
	 * imports the file content to the editor 
	 */
	public StringBuffer open(String fileNameLocation) {		
		StringBuffer codeFile = new StringBuffer();
		try{
		    // Open the file that is the first 
		    // command line parameter
		    FileInputStream fstream = new FileInputStream(fileNameLocation);
		    // Get the object of DataInputStream
		    DataInputStream in = new DataInputStream(fstream);
		        BufferedReader br = new BufferedReader(new InputStreamReader(in));
		    String strLine;
		    //Read File Line By Line
		    while ((strLine = br.readLine()) != null)   {
		      codeFile.append(strLine);
		      codeFile.append("\n");		      
		    }		   
		    //Close the input stream
		    in.close();
		   
		    }catch (Exception e){//Catch exception if any
		      System.err.println("Error: " + e.getMessage());
		    }
		    return codeFile;
	}

    
	/**
	 * save method just writes the information back into a file which is available
	 * if the file is not available it calls the save as method
	 */
	public void save() {
		
	}

	
	/**
	 * saveaAs method takes in the input information which should be written
	 * creates a file and then saves into it.
	 * If the file already exists it re-creates it and writes into it 
	 */
	public void saveAs() {
	
	}

}
