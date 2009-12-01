package com.jbricx.communications;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.eclipse.core.commands.Command;

public class ProcessRunner {

	private String errMsg;
	private String okMsg;
	
	public ExitStatus run(List<String> command,String okMsg, String errMsg){
		this.okMsg = okMsg;
		this.errMsg = this.errMsg+="\n"+errMsg;
		
		try{
			Process p;
			ProcessBuilder pb = new ProcessBuilder(command);
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
			String errout="";
	
			while((error=ebufferedreader.readLine()) != null){
				
				if (error.contains("# Error")){
					String line2=ebufferedreader.readLine();
					errout+= line2.substring(line2.indexOf("line"));
					errout+= " "+error.substring(error.indexOf("Error:")) + "\n";
				}
			}
			
			while((nonerror=bufferedreader.readLine()) != null){
				System.out.println(nonerror);
			}
			
			
			try{
				
				if (p.waitFor() == 1){
	//				System.err.println("exit value = "+p.exitValue());
					this.errMsg += errout;
					System.out.println("lksdjfld");
					return ExitStatus.Error;
					
				}
				else{
	//				System.out.println("no errors");
					return ExitStatus.Ok;
					
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
		catch (Exception e){
			e.printStackTrace();
			return ExitStatus.Error;
		}
		
		return ExitStatus.Ok;
	}
	
	public String getErrorMessage(){
		return errMsg;
	}
	
	public String getMessage(){
		return okMsg;
	}
}
