package com.jbricx.source;

import java.util.ArrayList;

import org.eclipse.jface.preference.PreferenceStore;
import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.rules.EndOfLineRule;
import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.IWhitespaceDetector;
import org.eclipse.jface.text.rules.MultiLineRule;
import org.eclipse.jface.text.rules.RuleBasedScanner;
import org.eclipse.jface.text.rules.SingleLineRule;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.jface.text.rules.WhitespaceRule;
import org.eclipse.jface.text.rules.WordRule;
import org.eclipse.swt.SWT;

import com.jbricx.preferences.JBrickObservable;

/**
 * This class scans through a code partition and colors it.
 */
public class JBrickCodeScanner extends RuleBasedScanner implements JBrickObservable {

  private ColorManager cm;
	/**
	 * JBrickCodeScanner constructor
	 */
	public JBrickCodeScanner(final ColorManager cm) {
	  this.cm = cm;
		initialization() ;
	}
	
	private void initialization(){

		// Create the tokens for keywords, strings, and other (everything else)
		IToken keyword = new Token(
							new TextAttribute(
									cm.getColor(ColorManager.KEYWORD), 
									cm.getColor(ColorManager.BACKGROUND), SWT.BOLD));
		
		IToken specialKeyword = new Token(
							new TextAttribute(
									cm.getColor(ColorManager.KEYWORD), 
									cm.getColor(ColorManager.BACKGROUND), SWT.BOLD|SWT.ITALIC));
		
		
		IToken numericOperator = new Token(
							new TextAttribute(
									cm.getColor(ColorManager.OPERATOR), 
									cm.getColor(ColorManager.BACKGROUND), SWT.BOLD));
		
		
		IToken other = new Token(new TextAttribute(cm.getColor(ColorManager.DEFAULT)));
		IToken string = new Token(new TextAttribute(cm.getColor(ColorManager.STRING)));
		//comments
		IToken javadoc = new Token(new TextAttribute(cm.getColor(ColorManager.COMMENT)));
	    IToken multilineComment = new Token(new TextAttribute(cm.getColor(ColorManager.COMMENT)));
	    IToken singleLineComment = new Token(new TextAttribute(cm.getColor(ColorManager.COMMENT)));
		// Use "other" for default
		setDefaultReturnToken(other);

		// Create the rules
		ArrayList<IRule>  rules = new ArrayList<IRule>();

		// Add rules for strings --> starts with " and ends with " and escapes \\
		rules.add(new SingleLineRule("\"", "\"", string, '\\'));
		rules.add(new SingleLineRule("'", "'", string, '\\'));
		
		// Javadoc rule: starts with /**, ends with */, has no escape character,
	    // and breaks on EOF
	    rules.add(new MultiLineRule("/**", "*/", javadoc, (char) 0, true));

	    // Multi-line comment rule: starts with /*, ends with */, has no escape
	    // character, and breaks on EOF
	    rules.add(new MultiLineRule("/*", "*/", multilineComment));

	    rules.add(new EndOfLineRule("//", singleLineComment));
	    
		// Add rule for whitespace
		rules.add(new WhitespaceRule(new IWhitespaceDetector() {
			public boolean isWhitespace(char c) {
				return Character.isWhitespace(c);
			}
		}));

		// Add rule for keywords, and add the words to the rule
		WordRule wordRule = new WordRule(new JBrickWordDetector(), other);
		
//		System.out.println("SyntaxKeyWords.getKeyWords().size():" + SyntaxKeyWords.getKeyWords().size());
		for (int i = 0; i<SyntaxKeyWords.getKeyWords().size(); i++){
			wordRule.addWord(SyntaxKeyWords.getKeyWords().get(i), keyword);
		}
		
//		System.out.println("SyntaxConstants.getKeyWords().size():" + SyntaxConstants.getKeyWords().size());
		for (int i = 0; i<SyntaxConstants.getKeyWords().size(); i++){
			wordRule.addWord(SyntaxConstants.getKeyWords().get(i), specialKeyword);
		}
		
//		System.out.println("SyntaxOperators.getKeyWords().size():" + SyntaxOperators.getKeyWords().size());
		for (int i = 0; i<SyntaxOperators.getKeyWords().size(); i++){
			wordRule.addWord(SyntaxOperators.getKeyWords().get(i), numericOperator);
		}//it has to exist in the syntaxOperators.getKeyWords
		
		
		rules.add(wordRule);
//		System.out.println("rules.size" + rules.size());
		
		IRule[] result = new IRule[rules.size()];
		rules.toArray(result);
		setRules(result);
	}

	@Override
	public void update(PreferenceStore ps) {
		// Update the syntax highlighting after preference update
		initialization() ;
	}
}
