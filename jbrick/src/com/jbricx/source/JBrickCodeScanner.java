package com.jbricx.source;

import java.util.*;

import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.rules.*;
import org.eclipse.swt.SWT;

import com.jbricx.pjo.JBrickEditor;
import com.jbricx.preferences.JBrickObservable;


/**
 * This class scans through a code partition and colors it.
 */
public class JBrickCodeScanner extends RuleBasedScanner implements JBrickObservable {
	/**
	 * JBrickCodeScanner constructor
	 */
	public JBrickCodeScanner() {
		initialization() ;
	}
	
	private void initialization(){
		// Get the color manager
		ColorManager cm = JBrickEditor.getInstance().getColorManager();

		// Create the tokens for keywords, strings, and other (everything else)
		IToken keyword = new Token(
							new TextAttribute(
									cm.getColor(ColorManager.KEYWORD), 
									cm.getColor(ColorManager.BACKGROUND), SWT.BOLD));
		
		IToken specialKeyword = new Token(
				new TextAttribute(
						cm.getColor(ColorManager.KEYWORD), 
						cm.getColor(ColorManager.BACKGROUND), SWT.BOLD|SWT.ITALIC));
		
		IToken other = new Token(new TextAttribute(cm.getColor(ColorManager.DEFAULT)));
		IToken string = new Token(new TextAttribute(cm.getColor(ColorManager.STRING)));
		
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
		for (int i = 0; i<SyntaxKeyWords.getKeyWords().size(); i++){
			wordRule.addWord(SyntaxKeyWords.getKeyWords().get(i), keyword);
		}
		
		for (int i = 0; i<SyntaxConstants.getKeyWords().size(); i++){
			wordRule.addWord(SyntaxConstants.getKeyWords().get(i), specialKeyword);
		}
		rules.add(wordRule);
		
		

		IRule[] result = new IRule[rules.size()];
		rules.toArray(result);
		setRules(result);
	}

	@Override
	public void update() {
		// Update the syntax highliting after preference update
		initialization() ;
	}
}
