package aspectPackage;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.jbricx.fileOperation.FileIO;
import com.jbricx.pjo.JBrickEditor;

public aspect LogAspect {
/*	

	// detect all run
	pointcut allrun() : execution(* *.run(..)) && !within(LogAspect);

	before() : allrun() {
	    Date date1 = new Date();  //(1)
	    SimpleDateFormat sdf1 = new SimpleDateFormat("[yyyy/MM/dd HH:mm:ss]");
	    System.out.println(sdf1.format( date1)) ;  
	    if (JBrickEditor.getMainWindow() != null ){
		    String workSpace = JBrickEditor.getMainWindow().getWorkspacePath() ; 
		    if (workSpace != null){
			    FileIO.append(workSpace + "\\JBrickLog.txt", sdf1.format( date1) + thisJoinPoint + "\r\n") ;
		    }
	    	
	    }
	}
*/
	
}
