package com.jbricx.source;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.RuleBasedScanner;
import org.eclipse.jface.text.rules.Token;

import com.jbricx.preferences.JBrickObserver;

/**
 * This class scans comment partitions
 */
public class CommentScanner extends RuleBasedScanner implements JBrickObserver {
  private ColorManager cm;

  /**
   * CommentScanner constructor
   */
  public CommentScanner(final ColorManager cm) {
    this.cm = cm;
    update(null);
  }

  @Override
  public void update(final IPreferenceStore store) {
    // Create the tokens
    IToken other = new Token(new TextAttribute(cm.getColor(ColorManager.ColorFor.COMMENT)));
    
    // Use "other" for default
    setDefaultReturnToken(other);
    
    // This scanner has an easy job--we need no rules. Anything in a comment
    // partition should be scanned as a comment
    
  }
}
