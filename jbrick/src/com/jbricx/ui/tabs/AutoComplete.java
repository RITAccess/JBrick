package com.jbricx.ui.tabs;

import java.util.SortedSet;

import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.source.SourceViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.graphics.DeviceData;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class AutoComplete {
	private static Display display;
	private static Shell shell;
	private static AutoCompleteWindow window;
	private static SortedSet keywords;
	private static SourceViewer viewer;
	private static String selectedWord;

	static {
		display = Display.getDefault();
		shell = new Shell(display);
		shell.setSize(245, 219);
		window = new AutoCompleteWindow(shell, SWT.NULL);
		loadKeywords("");
		window.addKeyListener(new KeyListener() {
			
			@Override
			public void keyReleased(KeyEvent arg0) {
				
			}
			
			@Override
			public void keyPressed(KeyEvent arg0) {
				if(shell.getVisible() == true && arg0.keyCode == SWT.ESC){
					shell.setVisible(false);
			}
		}});
		window.getKeywordList().addKeyListener(new KeyListener() {
			
			@Override
			public void keyReleased(KeyEvent arg0) {
				if(shell.getVisible() == true && arg0.keyCode == SWT.ESC){
					shell.setVisible(false);
				}
			}
			
			@Override
			public void keyPressed(KeyEvent arg0) {	
			}
		});
	}
	
	public static void loadKeywords(String path) {
		window.getKeywordList().add("task");
		window.getKeywordList().add("main");
		window.getKeywordList().add("if");
		window.getKeywordList().add("while");
		window.getKeywordList().add("ONFWD");
	}

	public static void attachAutoComplete(SourceViewer sourceViewer) {
		viewer = sourceViewer;
		viewer.getTextWidget().addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) {
				if((char)e.keyCode == ' ' && (e.stateMask & SWT.CTRL) != 0){
					//Ctrl-Space
					displayAutoComplete();
				}
				else if(shell.getVisible() == true && e.keyCode == SWT.ESC){
					shell.setVisible(false);
				}
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
			}

		});
	}
	private static void displayAutoComplete(){
		
		shell.setLocation(viewer.getTextWidget().getCaret().getLocation());
		shell.setVisible(true);
		shell.moveAbove(null);
		window.getKeywordList().forceFocus();

		selectClosest(selectedWord);
	}
	public static String parseCompleteWord(){
		String typedWord = "";
		
		int i = viewer.getTextWidget().getCaretOffset();
		int test = 5;
		if(test == 6);
		return typedWord;
	}
	public static void selectClosest(String typedWord) {
		
	}
}
