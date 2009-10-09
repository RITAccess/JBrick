package communcation;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Compiler {
	public static final int EXITSTATUS_OK = 0;
	public static final int EXITSTATUS_ERROR = 1;
	
	public String errMsg;
	
	public int compile(String filename) {
		errMsg="";
		Process p = null;
		
		if(! new File(filename).exists() ){
			errMsg = "File does not exist.";
			return EXITSTATUS_ERROR;
		}
		
		try{
			if(  System.getProperty("os.name").contains("indow") ){
				String where = "usb";
				
				List<String> command = new ArrayList<String>();
				command.add("C:\\Program Files\\BricxCC\\nbc.exe");
				//command.add("-help");
				command.add("-S");//+where);
				command.add("usb");
				command.add("-d");
				command.add("C:\\Users\\spencer\\sample.nxc");
					

				ProcessBuilder pb = new ProcessBuilder("\"C:/Program Files/BricxCC/nbc.exe\"", "-S", "usb", "-d", "\"C:/Users/spencer/sample.nxc\"");
				
				String sample = "\"C:/Program Files/BricxCC/nbc.exe\" -S usb -d c:/Users/Spencer/sample.nxc";
//				System.out.println(sample);
//				pb = new ProcessBuilder("\"C:/Program Files/BricxCC/nbc.exe\" -S usb -d c:/Users/Spencer/sample.nxc");
				pb = new ProcessBuilder(command);
				p = pb.start();
				
				InputStream is = p.getInputStream();
				InputStream es = p.getErrorStream();
				
				
				BufferedInputStream ebuf = new BufferedInputStream(es);
				InputStreamReader einread = new InputStreamReader(ebuf);
				BufferedReader ebufferedreader = new BufferedReader(einread);
				
				BufferedInputStream buf = new BufferedInputStream(es);
				InputStreamReader inread = new InputStreamReader(buf);
				BufferedReader bufferedreader = new BufferedReader(inread);
				

				String error;
				String nonerror;

				while((error=ebufferedreader.readLine()) != null){
					
					if (error.contains("# Error")){
						String line2=ebufferedreader.readLine();
						errMsg+= line2.substring(line2.indexOf("line"));
						errMsg+= " "+error.substring(error.indexOf("Error:")) + "\n";
					}
				}
				
				while((nonerror=bufferedreader.readLine()) != null){
					System.out.println(nonerror);
				}
				
				
				try{
					
					if (p.waitFor() == 1){
//						System.err.println("exit value = "+p.exitValue());
						return EXITSTATUS_ERROR;
					}
					else{
//						System.out.println("no errors");
						return EXITSTATUS_OK;
					}
					
				}
				catch (InterruptedException e){
					System.err.println(e);
				}
				finally{
					
					bufferedreader.close();
					inread.close();
					buf.close();
					is.close();
				}
				
			}
		}
		catch(Exception e){
			e.printStackTrace();
			return 1;
		}

		return 0;
	}

	public String getErrorMessage(){
		return this.errMsg;
	}
	
	public void debug() {
	
	}
	
	public static void main(String args[]){
		Compiler c = new Compiler();
		int check = c.compile("C:\\Users\\spencer\\sample2.nxc");
		if (check==EXITSTATUS_ERROR){
			System.out.println("ERROR\n");
			System.out.println(c.getErrorMessage());
		}
		else{
			System.out.println("NO ERRORS");
		}
	}

}
