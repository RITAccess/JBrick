package com.jbricx.source;


/**
 * This class detects constant words in a NXC file
 */

public class JBrickConstantWordDetector implements IWordDetector {
	/**
	   * Gets whether the specified character is the start of a word
	   * 
	   * @return boolean
	   */
	  public boolean isWordStart(char c) {
	    for (int i = 0; i < SyntaxConstants.getKeyWords().size(); i++){
	    	if (c == ((String) SyntaxConstants.getKeyWords().get(i)).charAt(0)){
	    		System.out.println("single constant true");
	    		return true;
	    	}
	    }
	      
	    return false;
	  }

	  /**
	   * Gets whether the specified character is part of a word
	   * 
	   * @return boolean
	   */
	  public boolean isWordPart(char c) {
		  for (int i = 0; i < SyntaxConstants.getKeyWords().size(); i++){
			  if (((String) SyntaxConstants.getKeyWords().get(i)).indexOf(c) != -1){
				  System.out.println("constant true");
				  return true;
			  }
		  }
	    return false;
	  }
}
