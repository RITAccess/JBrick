package source;

import org.eclipse.jface.text.rules.IWordDetector;

/**
 * This class detects words in a NXC file
 */
public class JBrickWordDetector implements IWordDetector {
  /**
   * Gets whether the specified character is the start of a word
   * 
   * @return boolean
   */
  public boolean isWordStart(char c) {
    for (int i = 0, n = SyntaxKeyWords.KEYWORDS.length; i < n; i++)
      if (c == ((String) SyntaxKeyWords.KEYWORDS[i]).charAt(0)) return true;
    return false;
  }

  /**
   * Gets whether the specified character is part of a word
   * 
   * @return boolean
   */
  public boolean isWordPart(char c) {
    for (int i = 0, n = SyntaxKeyWords.KEYWORDS.length; i < n; i++)
      if (((String) SyntaxKeyWords.KEYWORDS[i]).indexOf(c) != -1) return true;
    return false;
  }
}
