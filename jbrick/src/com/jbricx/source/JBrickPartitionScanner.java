package com.jbricx.source;

import org.eclipse.jface.text.rules.EndOfLineRule;
import org.eclipse.jface.text.rules.IPredicateRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.MultiLineRule;
import org.eclipse.jface.text.rules.RuleBasedPartitionScanner;
import org.eclipse.jface.text.rules.Token;

/**
 * This class scans a document and partitions it
 */
public class JBrickPartitionScanner extends RuleBasedPartitionScanner {
  // Create a partition for comments, and leave the rest for code
  public static final String COMMENT = "comment";
  public static final String[] TYPES = { COMMENT};

  /**
   * JBrickPartitionScanner constructor
   */
  public JBrickPartitionScanner() {
    super();

    // Create the token for comment partitions
    IToken comment = new Token(COMMENT);

    // Set the rule--anything from # to the end of the line is a comment
    IPredicateRule[] rulesList = new IPredicateRule[3];
    rulesList[0] = new EndOfLineRule("//", comment)   ;
	// Javadoc rule: starts with /**, ends with */, has no escape character,
    // and breaks on EOF
    rulesList[1] =new MultiLineRule("/**", "*/", comment);


    rulesList[2] =new MultiLineRule("/*", "*/", comment);


    
    setPredicateRules(rulesList);
  }
}
