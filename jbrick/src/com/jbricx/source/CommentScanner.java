package com.jbricx.source;

import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.RuleBasedScanner;
import org.eclipse.jface.text.rules.Token;

/**
 * This class scans comment partitions
 */
public class CommentScanner extends RuleBasedScanner {
  /**
   * CommentScanner constructor
   */
  public CommentScanner(final ColorManager cm) {

    // Create the tokens
    IToken other = new Token(new TextAttribute(cm
        .getColor(ColorManager.ColorFor.COMMENT)));

    // Use "other" for default
    setDefaultReturnToken(other);

    // This scanner has an easy job--we need no rules. Anything in a comment
    // partition should be scanned as a comment
  }
}
