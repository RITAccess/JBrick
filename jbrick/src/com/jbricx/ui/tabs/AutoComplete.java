package com.jbricx.ui.tabs;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.TreeSet;

import org.eclipse.jface.text.source.SourceViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.w3c.dom.*;
import org.xml.sax.*;

import com.jbricx.pjo.FileExtensionConstants;

import javax.xml.parsers.*;

public class AutoComplete {
	private static Display display;
	private static Shell shell;
	private static AutoCompleteWindow window;
	private static ArrayList<String> keywords;
	private static SourceViewer viewer;
	private static StyledText textWidget;
	private static String selectedWord = "";
	private static String typedStr = "";
	private static int removeIndex;
	private static int offset;

	static {
		display = Display.getDefault();
		shell = new Shell(display, SWT.BORDER | SWT.APPLICATION_MODAL);
		shell.setSize(245, 219);
		window = new AutoCompleteWindow(shell, SWT.NULL);
		loadKeywords();

		window.getKeywordList().addKeyListener(new KeyListener() {
			@Override
			public void keyReleased(KeyEvent arg0) {
			}

			@Override
			public void keyPressed(KeyEvent arg0) {
				if (shell.getVisible() == true && arg0.keyCode == SWT.ESC) {
					shell.setVisible(false);
				} else if (arg0.keyCode == SWT.CR) {
					autocompleteSelected();
				}
				else if(arg0.keyCode == SWT.BS){
					if(!typedStr.equals("")){
						typedStr = typedStr.substring(0, typedStr.length()-1);
						setKeywordFilter();
					}
				}
				else if(Character.isLetterOrDigit((char)arg0.keyCode)){
					typedStr += (char)arg0.keyCode;
					setKeywordFilter();
				}
			}
		});
		window.getKeywordList().addMouseListener(new MouseListener() {

			@Override
			public void mouseDoubleClick(MouseEvent arg0) {
				autocompleteSelected();
			}

			@Override
			public void mouseDown(MouseEvent arg0) {
			}

			@Override
			public void mouseUp(MouseEvent arg0) {
			}

		});
		window.getKeywordList().addSelectionListener(new SelectionListener() {

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				if (window.getKeywordList().getSelectionCount() > 0)
					selectedWord = window.getKeywordList().getSelection()[0];
			}

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				if (window.getKeywordList().getSelectionCount() > 0)
					selectedWord = window.getKeywordList().getSelection()[0];
			}

		});
	}

	public static void loadKeywords() {
		try {
			keywords = new ArrayList<String>();
			InputStream keywordStream = ClassLoader.getSystemResourceAsStream(
			          FileExtensionConstants.KEYWORDS_FILE);
			DocumentBuilder builder = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder();
			Document doc = builder.parse(keywordStream);
			NodeList nodes = doc.getElementsByTagName("word");
			for(int i = 0; i < nodes.getLength(); i++){
				keywords.add(((Element)nodes.item(i)).getTextContent());
			}
			keywordStream.close();
					
			keywordStream = ClassLoader.getSystemResourceAsStream(
			          FileExtensionConstants.CONSTANTS_FILE);
			doc = builder.parse(keywordStream);
			nodes = doc.getElementsByTagName("constant");
			for(int i = 0; i < nodes.getLength(); i++){
				keywords.add(((Element)nodes.item(i)).getTextContent());
			}
			keywordStream.close();
			
			keywordStream = ClassLoader.getSystemResourceAsStream(
			          FileExtensionConstants.AUTOCOMPLETE_FILE);
			doc = builder.parse(keywordStream);
			nodes = doc.getElementsByTagName("word");
			for(int i = 0; i < nodes.getLength(); i++){
				keywords.add(((Element)nodes.item(i)).getTextContent());
			}
			keywordStream.close();
		} catch (Exception e) {
			System.out.println("Error in AutoComplete keyword loading: " + e.getMessage());
		}

		Collections.sort(keywords, String.CASE_INSENSITIVE_ORDER);
		for (String keyword : keywords)
			window.getKeywordList().add(keyword);
	}
	public static void attachAutocomplete(SourceViewer sourceViewer) {
		viewer = sourceViewer;
		textWidget = viewer.getTextWidget();
		textWidget.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) {
				if ((char) e.keyCode == ' ' && (e.stateMask & SWT.CTRL) != 0) {
					// Ctrl-Space
					displayAutocomplete();
				} else if (shell.getVisible() == true && e.keyCode == SWT.ESC) {
					shell.setVisible(false);
				}
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
			}

		});
	}
	private static void displayAutocomplete() {
		Point widgetScreenLoc = pointToScreen(textWidget);
		Point caretLoc = textWidget.getCaret().getLocation();

		shell.setLocation(widgetScreenLoc.x + caretLoc.x, widgetScreenLoc.y
				+ caretLoc.y + 70);
		selectClosest();
		shell.setVisible(true);
		shell.moveAbove(null);
		window.getKeywordList().forceFocus();
	}
	public static void selectClosest() {
		offset = textWidget.getCaretOffset();
		String allText = textWidget.getText();
		removeIndex = offset - 1;
		if (removeIndex == -1)
			removeIndex = 0;
		while (removeIndex > 0) {
			if (allText.charAt(removeIndex) == ' '
					|| allText.charAt(removeIndex) == '\n'
					|| allText.charAt(removeIndex) == '(') {
				removeIndex++;
				break;
			}
			removeIndex--;
		}
		typedStr = "";
		if (removeIndex != -1 && removeIndex < offset)
			typedStr = allText.substring(removeIndex, offset).toLowerCase();
		while (offset < allText.length()) {
			if (allText.charAt(offset) == ' ' || allText.charAt(offset) == '\n'
					|| allText.charAt(offset) == ';'
					|| allText.charAt(offset) == ')') {
				// offset--;
				break;
			}
			offset++;
		}
		setKeywordFilter();
	}
	public static void setKeywordFilter(){
		window.getKeywordList().removeAll();
		int i;
		for (i = 0; i < keywords.size(); i++) {
			if (keywords.get(i).toLowerCase().startsWith(typedStr)) {
				window.getKeywordList().add(keywords.get(i));
			}
		}
		window.getKeywordList().setSelection(0);
		if(window.getKeywordList().getItems().length > 0)
			selectedWord = window.getKeywordList().getItem(0);
	}
	public static void autocompleteSelected() {
		String str = textWidget.getText();
		str = new StringBuffer(str).replace(removeIndex, offset, selectedWord)
				.toString();
		textWidget.setText(str);
		textWidget.setCaretOffset(removeIndex + selectedWord.length());
		shell.setVisible(false);
	}
	public static Point pointToScreen(Control c) {
		Point pos = c.getLocation();
		if (c.getParent() != null) {
			Point parentPos = pointToScreen(c.getParent());
			return new Point(parentPos.x + pos.x, parentPos.y + pos.y);
		} else
			return pos;
	}
		
}
