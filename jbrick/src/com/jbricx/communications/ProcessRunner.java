package com.jbricx.communications;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class ProcessRunner {

	
	public ExitStatus run(List<String> command){
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
			
			InputStreamReader tempReader = new InputStreamReader(
	                new BufferedInputStream(p.getInputStream()));
	            BufferedReader reader = new BufferedReader(tempReader);
	            
			String out="";
			String nonerror="";
			String errout="";
			
			while((out=ebufferedreader.readLine()) != null){
				
				errout+=out;
//				if (out.contains("# Error")){
//					String line2=ebufferedreader.readLine();
//					errout+= line2.substring(line2.indexOf("line"));
//					errout+= " "+out.substring(out.indexOf("Error:")) + "\n";
//				}
			}
			
			while((out=bufferedreader.readLine()) != null){
				nonerror += out;
				System.out.println("\tNonError");
				
			}
			
			
			try{
				
				if (p.waitFor() == 1){
//					System.err.println("exit value = "+p.exitValue());
				
					return new ExitStatus(ExitStatus.ERROR,errout);
					
				}
				else{
//					System.out.println("no errors");
//					System.out.println(nonerror);
					return new ExitStatus(ExitStatus.OK,nonerror);
					
				}
				//System.out.println("1");
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
			//return new ExitStatus(ExitStatus.ERROR,"Epic Process Failure!!");
			return new ExitStatus(ExitStatus.ERROR,e.getMessage());
			
		}
		
		return new ExitStatus(ExitStatus.ERROR,"If you got this error something terrible happened. Good luck finding out what happend. Let the force be with you.");
	}
	
	
}
