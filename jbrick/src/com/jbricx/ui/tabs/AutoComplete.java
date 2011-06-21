package com.jbricx.ui.tabs;

import java.util.SortedSet;
import org.eclipse.jface.text.source.SourceViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;

public class AutoComplete {
	private static AutoCompleteWindow window;
	private static SortedSet keywords;
	private static SourceViewer viewer;
	private static String selectedWord;

	public AutoComplete(){
		
	}
	
	public static void loadKeywords(String path) {

	}

	public static void attachAutoComplete(SourceViewer viewer) {
		viewer.getTextWidget().addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) {
				if((char)e.keyCode == ' ' && (e.stateMask & SWT.CTRL) != 0){
					//Ctrl-Space
					displayAutoComplete();
				}
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
			}

		});
	}
	private static void displayAutoComplete(){
		
	}
	public static void selectClosest(String typedWord) {
		
	}
}
