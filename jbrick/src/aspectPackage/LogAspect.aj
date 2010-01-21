package aspectPackage;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.jbricx.fileOperation.FileIO;
import com.jbricx.pjo.JBrickEditor;

public aspect LogAspect {
	pointcut saveFile() : call(*  *.saveFile(..));

	before() : saveFile() {
		System.out.println("Aspect : before " + thisJoinPoint);
	}
	
	// detect all run
	pointcut allrun() : execution(* *.run(..)) && !within(LogAspect);

	before() : allrun() {
	    Date date1 = new Date();  //(1)Date�I�u�W�F�N�g�𐶐�
	    SimpleDateFormat sdf1 = new SimpleDateFormat("[yyyy/MM/dd HH:mm:ss]");
	    System.out.println(sdf1.format( date1)) ;  
	    if (JBrickEditor.getMainWindow() != null ){
		    String workSpace = JBrickEditor.getMainWindow().getWorkspacePath() ; 
		    if (workSpace != null){
			    FileIO.append(workSpace + "\\JBrickLog.txt", sdf1.format( date1) + thisJoinPoint + "\r\n") ;
		    }
	    	
	    }
	}
	
	pointcut allMessageBox() : call(* *.openInformation(..)) && !within(LogAspect);

	before() : allMessageBox() {
		System.out.println("Aspect : openInformation " + thisJoinPoint);
	}

	pointcut errorDetection(Exception e) : handler(Exception) && args(e);
	before(Exception e) : errorDetection(e) {
	    System.out.println(" Aspect : exception detected! " + e.getMessage());
	}

}
