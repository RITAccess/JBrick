/*
 * 04/25/2007
 *
 * RTextAreaUI.java - UI used by instances of RTextArea.
 * 
 * This library is distributed under a modified BSD license.  See the included
 * RSyntaxTextArea.License.txt file for details.
 */
package org.fife.ui.rtextarea;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;

import javax.swing.*;
import javax.swing.text.*;
import javax.swing.border.Border;
import javax.swing.plaf.*;
import javax.swing.plaf.basic.*;

import com.jbricx.swing.ui.preferences.PreferenceStore;
import com.jbricx.swing.ui.tabs.AudioBreak;

/**
 * The UI used by instances of <code>RTextArea</code>.  This UI takes into
 * account all of the "extras" involved in an <code>RTextArea</code>, including
 * having a special caret (for insert and overwrite), background images,
 * highlighting the current line, etc.
 *
 * @author Robert Futrell
 * @version 0.5
 */
public class RTextAreaUI extends BasicTextAreaUI implements ViewFactory {

	private static final String SHARED_ACTION_MAP_NAME	= "RTextAreaUI.actionMap";
	private static final String SHARED_INPUT_MAP_NAME	= "RTextAreaUI.inputMap";

	protected RTextArea textArea;				// The text area for which we are the UI.

	private static final EditorKit defaultKit = new RTextAreaEditorKit();
	private static final TransferHandler defaultTransferHandler =
										new RTATextTransferHandler();

	private static final String RTEXTAREA_KEYMAP_NAME	= "RTextAreaKeymap";
	
	private ArrayList<AudioBreak> audioBreakList = new ArrayList<AudioBreak>();


	/**
	 * Creates a UI for an RTextArea.
	 *
	 * @param textArea A text area.
	 * @return The UI.
	 */
	public static ComponentUI createUI(JComponent textArea) {
		return new RTextAreaUI(textArea);
	}


	/**
	 * Constructor.
	 *
	 * @param textArea An instance of <code>RTextArea</code>.
	 * @throws IllegalArgumentException If <code>textArea</code> is not an
	 *         instance of <code>RTextArea</code>.
	 */
	public RTextAreaUI(JComponent textArea) {
		if (!(textArea instanceof RTextArea)) {
			throw new IllegalArgumentException("RTextAreaUI is for " +
							 		"instances of RTextArea only!");
		}
		this.textArea = (RTextArea)textArea;
	}


	/**
	 * The Nimbus LAF (and any Synth laf might have similar issues) doesn't set
	 * many UIManager properties that BasicLAF UI's look for.  This causes
	 * problems for custom Basic-based UI's such as RTextAreaUI.  This method
	 * attempts to detect if Nimbus has been installed, and if so, sets proper
	 * values for some editor properties.
	 *
	 * @param editor The text area.
	 */
	private void correctNimbusDefaultProblems(JTextComponent editor) {

		// Don't check UIManager.getLookAndFeel().getName() for "Nimbus",
		// as other Synth-based LaFs might have not set these properties,
		// in which case we'll need to use our fallback values.

		// Check for null, but not for UIResource, for these properties,
		// because if Nimbus was installed these values would all be given
		// null values.  Another laf might have successfully installed
		// UIResource values, which we don't want to override.

		Color c = editor.getCaretColor();
		if (c==null) {
			editor.setCaretColor(RTextArea.getDefaultCaretColor());
		}

		c = editor.getSelectionColor();
		if (c==null) {
			c = UIManager.getColor("nimbusSelectionBackground");
			if (c==null) { // Not Nimbus, but still need a value - fallback
				c = UIManager.getColor("textHighlight");
				if (c==null) {
					c = new ColorUIResource(Color.BLUE);
				}
			}
			editor.setSelectionColor(c);
		}

		c = editor.getSelectedTextColor();
		if (c==null) {
			c = UIManager.getColor("nimbusSelectedText");
			if (c==null) { // Not Nimbus, but still need a value - fallback
				c = UIManager.getColor("textHighlightText");
				if (c==null) {
					c = new ColorUIResource(Color.WHITE);
				}
			}
			editor.setSelectedTextColor(c);
		}

		c = editor.getDisabledTextColor();
		if (c==null) {
			c = UIManager.getColor("nimbusDisabledText");
			if (c==null) { // Not Nimbus, but still need a value - fallback
				c = UIManager.getColor("textInactiveText");
				if (c==null) {
					c = new ColorUIResource(Color.DARK_GRAY);
				}
			}
			editor.setDisabledTextColor(c);
		}

		Border border = editor.getBorder();
		if (border==null) {
			editor.setBorder(new BasicBorders.MarginBorder());
		}

		Insets margin = editor.getMargin();
		if (margin==null) {
			editor.setMargin(new InsetsUIResource(2, 2, 2, 2));
		}
	}


	/**
	 * Creates the view for an element.  Returns a WrappedPlainView or
	 * PlainView.
	 *
	 * @param elem The element.
	 * @return The view.
	 */
	public View create(Element elem) {
		if (textArea.getLineWrap())
			return new WrappedPlainView(elem, textArea.getWrapStyleWord());
		else
			return new PlainView(elem);
	}


	/**
	 * Returns the default caret for an <code>RTextArea</code>.  This caret is
	 * capable of displaying itself differently for insert/overwrite modes.
	 *
	 * @return The caret.
	 */
	protected Caret createCaret() {
		Caret caret = new ConfigurableCaret();
		caret.setBlinkRate(500);
		return caret;
	}


	/**
	 * Creates the keymap for this text area.  This takes the super class's
	 * keymap, but sets the default keystroke to be RTextAreaEditorKit's
	 * DefaultKeyTypedAction.  This must be done to override the default
	 * keymap's default key-typed action.
	 *
	 * @return The keymap.
	 */
	protected Keymap createKeymap() {

		// Load the keymap we'll be using (it's saved by
		// JTextComponent.addKeymap).
		Keymap map = JTextComponent.getKeymap(RTEXTAREA_KEYMAP_NAME);
		if (map==null) {
			Keymap parent = JTextComponent.getKeymap(JTextComponent.DEFAULT_KEYMAP);
			map = JTextComponent.addKeymap(RTEXTAREA_KEYMAP_NAME, parent);
			map.setDefaultAction(new RTextAreaEditorKit.DefaultKeyTypedAction());
		}

		return map;

	}

	/**
	 * Creates a default action map.  This action map contains actions for all
	 * basic text area work - cut, copy, paste, select, caret motion, etc.<p>
	 *
	 * This isn't named <code>createActionMap()</code> because there is a
	 * package-private member by that name in <code>BasicTextAreaUI</code>,
	 * and some compilers will give warnings that we are not overriding that
	 * method since it is package-private.
	 *
	 * @return The action map.
	 */
	protected ActionMap createRTextAreaActionMap() {

		// Get the actions of the text area (which in turn gets them from its
		// DefaultEditorKit).
		ActionMap map = new ActionMapUIResource();
		Action[] actions = textArea.getActions();
		int n = actions.length;
		for (int i = 0; i < n; i++) {
			Action a = actions[i];
			map.put(a.getValue(Action.NAME), a);
		}

		// Not sure if we need these; not sure they are ever called
		// (check their NAMEs).
		map.put(TransferHandler.getCutAction().getValue(Action.NAME),
									TransferHandler.getCutAction());
		map.put(TransferHandler.getCopyAction().getValue(Action.NAME),
									TransferHandler.getCopyAction());
		map.put(TransferHandler.getPasteAction().getValue(Action.NAME),
									TransferHandler.getPasteAction());

		return map;

	}


	/**
	 * Returns the name to use to cache/fetch the shared action map.  This
	 * should be overridden by subclasses if the subclass has its own custom
	 * editor kit to install, so its actions get picked up.
	 *
	 * @return The name of the cached action map.
	 */
	protected String getActionMapName() {
		return SHARED_ACTION_MAP_NAME;
	}


	/**
	 * Fetches the EditorKit for the UI.
	 *
	 * @param tc the text component for which this UI is installed
	 * @return the editor capabilities
	 * @see TextUI#getEditorKit
	 */
	public EditorKit getEditorKit(JTextComponent tc) {
		return defaultKit;
	}


	/**
	 * Returns the text area for which we are the UI.
	 *
	 * @return The text area.
	 */
	public RTextArea getRTextArea() {
		return textArea;
	}


	/**
	 * Returns an action map to use by a text area.<p>
	 *
	 * This method is not named <code>getActionMap()</code> because there is
	 * a package-private method in <code>BasicTextAreaUI</code> with that name.
	 * Thus, creating a new method with that name causes certain compilers to
	 * issue warnings that you are not actually overriding the original method
	 * (since it is package-private).
	 *
	 * @return The action map.
	 * @see #createRTextAreaActionMap()
	 */
	private ActionMap getRTextAreaActionMap() {

		// Get the UIManager-cached action map; if this is the first
		// RTextArea created, create the action map and cache it.
		ActionMap map = (ActionMap)UIManager.get(getActionMapName());
		if (map==null) {
			map = createRTextAreaActionMap();
			UIManager.put(getActionMapName(), map);
		}

		ActionMap componentMap = new ActionMapUIResource();
		componentMap.put("requestFocus", new FocusAction());

		if (map != null)
			componentMap.setParent(map);
		return componentMap;

	}


	/**
	 * Get the InputMap to use for the UI.<p>
	 *  
	 * This method is not named <code>getInputMap()</code> because there is
	 * a package-private method in <code>BasicTextAreaUI</code> with that name.
	 * Thus, creating a new method with that name causes certain compilers to
	 * issue warnings that you are not actually overriding the original method
	 * (since it is package-private).
	 */
	protected InputMap getRTextAreaInputMap() {
		InputMap map = new InputMapUIResource();
		InputMap shared = (InputMap)UIManager.get(SHARED_INPUT_MAP_NAME);
		if (shared==null) {
			shared = new RTADefaultInputMap();
			UIManager.put(SHARED_INPUT_MAP_NAME, shared);
		}
		//KeyStroke[] keys = shared.allKeys();
		//for (int i=0; i<keys.length; i++)
		//	System.err.println(keys[i] + " -> " + shared.get(keys[i]));
		map.setParent(shared);
		return map;
	}


	/**
	 * Gets the allocation to give the root View.  Due
	 * to an unfortunate set of historical events this 
	 * method is inappropriately named.  The Rectangle
	 * returned has nothing to do with visibility.  
	 * The component must have a non-zero positive size for 
	 * this translation to be computed.
	 *
	 * @return the bounding box for the root view
	 */
	protected Rectangle getVisibleEditorRect() {
		Rectangle alloc = textArea.getBounds();
		if ((alloc.width > 0) && (alloc.height > 0)) {
			alloc.x = alloc.y = 0;
			Insets insets = textArea.getInsets();
			alloc.x += insets.left;
			alloc.y += insets.top;
			alloc.width -= insets.left + insets.right;
			alloc.height -= insets.top + insets.bottom;
			return alloc;
		}
		return null;
	}


	protected void installDefaults() {

		super.installDefaults();

		JTextComponent editor = getComponent();
		editor.setFont(RTextAreaBase.getDefaultFont());

		// Nimbus (and possibly other Synth lafs) doesn't play by BasicLaf
		// rules and doesn't set properties needed by custom BasicTextAreaUI's.
		correctNimbusDefaultProblems(editor);

		editor.setTransferHandler(defaultTransferHandler);

	}


	/**
	 * {@inheritDoc}
	 */
	protected void installKeyboardActions() {

		// NOTE: Don't call super.installKeyboardActions(), as that causes
		// JTextAreas to stop responding to certain keystrokes if an RTextArea
		// is the first-instantiated text area.  This is because of the code
		// path installKeyboardActions() -> getActionMap() -> createActionMap().
		// In BasicTextUI#createActionMap(), "editor.getActions()" is called,
		// and the current editor's returned Actions are used to create the
		// ActionMap, which is then cached and used in all future J/RTextAreas.
		// Unfortunately, RTextArea actions don't worn in JTextAreas.
		//super.installKeyboardActions();

		RTextArea textArea = getRTextArea();

		// backward compatibility support... keymaps for the UI
		// are now installed in the more friendly input map.
		textArea.setKeymap(createKeymap()); 

		// Since BasicTextUI.getInputMap() is package-private, instead use
		// our own version here.
		InputMap map = getRTextAreaInputMap();
		SwingUtilities.replaceUIInputMap(textArea,JComponent.WHEN_FOCUSED,map);

		// Same thing here with action map.
		ActionMap am = getRTextAreaActionMap();
		if (am!=null) {
		    SwingUtilities.replaceUIActionMap(textArea, am);
		}
	}

	/**
	 * Installs this UI to the given text component.
	 */
	public void installUI(JComponent c) {
		if (!(c instanceof RTextArea)) {
			throw new Error("RTextAreaUI needs an instance of RTextArea!");
		}
		super.installUI(c);
	}


	protected void paintBackground(Graphics g) {

		// Only fill in the background if an image isn't being used.
		Color bg = textArea.getBackground();
		if (bg!=null) {
			g.setColor(bg);
			//g.fillRect(0, 0, textArea.getWidth(), textArea.getHeight());
			Rectangle r = g.getClipBounds();
			g.fillRect(r.x,r.y, r.width,r.height);
		}

		Rectangle visibleRect = textArea.getVisibleRect();

		paintLineHighlights(g);
		paintCurrentLineHighlight(g, visibleRect);
		paintMarginLine(g, visibleRect);
	}


	/**
	 * Paints the highlighted current line, if it is enabled.
	 *
	 * @param g The graphics context with which to paint.
	 * @param visibleRect The visible rectangle of the text area.
	 */
	protected void paintCurrentLineHighlight(Graphics g, Rectangle visibleRect) {
		if (textArea.getHighlightCurrentLine()) {
			
			Caret caret = textArea.getCaret();
			
			if (caret.getDot()==caret.getMark()) {

				Color highlight = textArea.getCurrentLineHighlightColor();
				// NOTE:  We use the getLineHeight() method below instead
				// of currentCaretRect.height because of a bug where
				// currentCaretRect.height is incorrect when an
				// RSyntaxTextArea is first displayed (it is initialized 
				// with the text area's font.getHeight() (via RTextArea),
				// but isn't changed to account for the syntax styles
				// before it is displayed).
				//int height = textArea.currentCaretRect.height);
				int height = textArea.getLineHeight();
				g.setColor(highlight);
				g.fillRect(visibleRect.x,textArea.currentCaretY,
						visibleRect.width, height);
			} // End of if (caret.getDot()==caret.getMark()).
		} // End of if (textArea.isCurrentLineHighlightEnabled()...
		
		drawHighlights(g, visibleRect);
		
	}
	
	/**
	 * Draws all highlights on line needed to be highlighted
	 * @param g The graphics context with which to paint.
	 * @param visibleRect The visible rectangle of the text area.
	 */
	private void drawHighlights(Graphics g, Rectangle visibleRect){
		Color currBg = PreferenceStore.getColor(PreferenceStore.Preference.BACKGROUND);
		int height = textArea.getLineHeight();
		
		for (AudioBreak line : audioBreakList){
			Color highlight = line.calculateColor(currBg); 
			g.setColor(highlight);
			Graphics2D g2d = (Graphics2D)g;
			Color bg = textArea.getBackground();
			
			Color blend = new Color(bg.getRed(), bg.getGreen(), bg.getBlue(), 0);
			GradientPaint paint = new GradientPaint(
				visibleRect.x,0, highlight,
				visibleRect.x+visibleRect.width,0,
						bg==null ? Color.WHITE : blend);
			g2d.setPaint(paint);
			g2d.fillRect(visibleRect.x,line.getScreenPosition(),
							visibleRect.width, height);
		}
	}
	
	/**
	 * Toggles the highlighting on the current line for audio breaks
	 * also checks if the line is valid - if not the line will not be toggled
	 */
	public void toggleAudioBreak(){
		
		// toggle off if highlightedLines contains line ... else toggle on
		AudioBreak tempBreak;
		if((tempBreak = containsLine(audioBreakList, textArea.currentCaretY)) != null){
			audioBreakList.remove(tempBreak);
		} else {
			audioBreakList.add(new AudioBreak(textArea.currentCaretY/textArea.getLineHeight(), textArea.getLineHeight()));
		}
	}
	
	/**
	 * A method to check if a audio break with a certain screen position exists. If it does then it will return the break
	 * @param list
	 * @param screenPosition
	 * @return
	 */
	private AudioBreak containsLine(ArrayList<AudioBreak> list, int screenPosition){
		for(AudioBreak audioBreak : list){
			if(audioBreak.getScreenPosition() == screenPosition){
				return audioBreak;
			}
		}
		return null;
	}
	
	/**
	 * Raises or lowers the tone of the audio break at the current carret position.
	 * @param raise True to raise. False to lower.
	 */
	public void RaiseLowerTone(boolean raise){
		AudioBreak tempBreak;
		if((tempBreak = containsLine(audioBreakList, textArea.currentCaretY)) != null){
			if(raise){
				tempBreak.raiseKey();
				}
			else{
				tempBreak.lowerKey();
				}
		}
	}
	
	/**
	 * Sets the audio breaks array list equal to an array.
	 * @param breaks Array to use
	 */
	public void setAudioBreaks(AudioBreak[] breaks){
		audioBreakList.clear();
		for(AudioBreak audioBreak : breaks){
			audioBreak.setLineHeight(this.textArea.getLineHeight());
			audioBreakList.add(audioBreak);
		}
		checkBreaks();
	}
	
	private void checkBreaks(){
		for(int i = 0; i < audioBreakList.size(); i ++){
			if(audioBreakList.get(i).getLineNumber() < 0 || audioBreakList.get(i).getLineNumber() > textArea.getLineCount()){
				audioBreakList.remove(i);
				i--;
			}
		}
	}
	
	/**
	 * Gets an array of the lines with audio breaks
	 * @return Array of lines
	 */
	public AudioBreak[] getBreakPoints(){
		AudioBreak[] pointArray = new AudioBreak[audioBreakList.size()];
		for(int i = 0; i < pointArray.length; i++){
			pointArray[i] = audioBreakList.get(i);
		}
		return pointArray;
	}

	/**
	 * Paints any line highlights.
	 *
	 * @param g The graphics context.
	 */
	protected void paintLineHighlights(Graphics g) {
		LineHighlightManager lhm = textArea.getLineHighlightManager();
		if (lhm!=null) {
			lhm.paintLineHighlights(g);
		}
	}
	
	/**
	 * Adjusts the audio breaks when lines are changed in the document
	 * @param offset number of lines added or removed
	 */
	public void updateAudioBreaks(int offset, int numSelecLines){
		
		int selecStart = 0;
		int selecEnd = 0;
		//Atempt to find the lines that are currently highlighted
		try {
			if(textArea.getSelectionStart() <= textArea.getDocument().getLength()){
				selecStart = textArea.getLineOfOffset(textArea.getSelectionStart());
				selecEnd = selecStart + numSelecLines;
			}
		} catch (BadLocationException e1) {
			e1.printStackTrace();
		}
		
		for(int i = 0; i < audioBreakList.size(); i++) {
			//If the break is inside a line that was highlighted then remove it
			if(audioBreakList.get(i).getLineNumber() > selecStart && audioBreakList.get(i).getLineNumber() < selecEnd){
				audioBreakList.remove(i);
				i--;
			}
			//If the break was on a line where a new line was added then remove it
			else if(textArea.currentCaretY == audioBreakList.get(i).getScreenPosition()) {
				audioBreakList.remove(i);
				i--;
			}
			else if(textArea.currentCaretY < audioBreakList.get(i).getScreenPosition()) {
				audioBreakList.get(i).setLineNum(audioBreakList.get(i).getLineNumber() + offset);
			}
		}
	}

	/**
	 * Changes the hight of the highlights on all lines. Use when font size has changed.
	 * @param newSize New height of lines
	 */
	public void changeHighlightSize(int newSize) {
		for(int i = 0; i < audioBreakList.size(); i++){
				audioBreakList.get(i).setLineHeight(newSize);
		}
	}

	/**
	 * Draws the "margin line" if enabled.
	 *
	 * @param g The graphics context to paint with.
	 * @param visibleRect The visible rectangle of this text area.
	 */
	protected void paintMarginLine(Graphics g, Rectangle visibleRect) {
		if (textArea.isMarginLineEnabled()) {
			g.setColor(textArea.getMarginLineColor());
			Insets insets = textArea.getInsets();
			int marginLineX = textArea.getMarginLinePixelLocation() +
							(insets==null ? 0 : insets.left);
			g.drawLine(marginLineX,visibleRect.y,
						marginLineX,visibleRect.y+visibleRect.height);
		}
	}


	/**
	 * Returns the y-coordinate of the specified line.<p>
	 *
	 * The default implementation is equivalent to:
	 * <pre>
	 * int startOffs = textArea.getLineStartOffset(line);
	 * return yForLineContaining(startOffs);</code>
	 * </pre>
	 *
	 * Subclasses that can calculate this value more quickly than traditional
	 * {@link #modelToView(JTextComponent, int)} calls should override this
	 * method to do so. This method may be used when the entire bounding box
	 * isn't needed, to speed up rendering.
	 *
	 * @param line The line number.
	 * @return The y-coordinate of the top of the line, or <code>-1</code> if
	 *         this text area doesn't yet have a positive size or the line is
	 *         hidden (i.e. from folding).
	 * @throws BadLocationException If <code>line</code> isn't a valid line
	 *         number for this document.
	 */
	public int yForLine(int line) throws BadLocationException {
		int startOffs = textArea.getLineStartOffset(line);
		return yForLineContaining(startOffs);
	}


	/**
	 * Returns the y-coordinate of the line containing an offset.<p>
	 *
	 * The default implementation is equivalent to:
	 * <pre>
	 * int line = textArea.getLineOfOffset(offs);
	 * int startOffs = textArea.getLineStartOffset(line);
	 * return modelToView(startOffs).y;</code>
	 * </pre>
	 *
	 * Subclasses that can calculate this value more quickly than traditional
	 * {@link #modelToView(JTextComponent, int)} calls should override this
	 * method to do so. This method may be used when the entire bounding box
	 * isn't needed, to speed up rendering.
	 *
	 * @param offs The offset info the document.
	 * @return The y-coordinate of the top of the offset, or <code>-1</code> if
	 *         this text area doesn't yet have a positive size or the line is
	 *         hidden (i.e. from folding).
	 * @throws BadLocationException If <code>offs</code> isn't a valid offset
	 *         into the document.
	 */
	public int yForLineContaining(int offs) throws BadLocationException {
		Rectangle r = modelToView(textArea, offs);
		return r!=null ? r.y : -1;
	}


	/**
	 * Registered in the ActionMap.
	 */
	@SuppressWarnings("serial")
	class FocusAction extends AbstractAction {

		public void actionPerformed(ActionEvent e) {
			textArea.requestFocus();
		}

		public boolean isEnabled() {
			return textArea.isEditable();
		}
	}
}