package com.jbricx.swing.ui.tabs;

public class AudioBreak {

	private final int MINTONE = 0;
	private final int MAXTONE = 2000;
	
	private int lineNumber;
	private int tone;
	private int screenPosition;
	private int lineHeight;
	
	/**
	 * Constructor for audio breaks. Breaks contain all information needed to display in preform correctly.
	 * @param nLineNumber The line number where the break will be.
	 * @param nLineHeight The current height of lines. This is determined by font size.
	 */
	public AudioBreak(int nLineNumber, int nLineHeight){
		lineHeight = nLineHeight;
		lineNumber = nLineNumber;
		recalculateScreenPos();
		tone = 750;
	}
	
	/**
	 * Only use this constructor when the line height will be set soon after creation
	 * @param nLineNumber
	 */
	public AudioBreak(int nLineNumber){
		this(nLineNumber, 12);
	}
	
	//Setters
	/**
	 * Set the line number.
	 * @param nLineNum
	 */
	public void setLineNum(int nLineNum){
		lineNumber = nLineNum;
		recalculateScreenPos();
	}
	/**
	 * set the current line height.
	 * @param nLineHeight
	 */
	public void setLineHeight(int nLineHeight){
		lineHeight = nLineHeight;
		recalculateScreenPos();
	}
	/**
	 * Sets the audio breaks current position within the text area.
	 * @param nScreenPos
	 */
	public void setScreenPosition(int nScreenPos){
		screenPosition = nScreenPos;
		recalculateLineNumber();
	}
	/**
	 * Sets the tone to be played when the NXC code is run.
	 * @param nTone
	 */
	public void setTone(int nTone){
		tone = nTone;
		clampTone(MINTONE, MAXTONE);
	}
	
	//Getters
	/**
	 * Get the line number.
	 * @return int Line number
	 */
	public int getLineNumber(){
		return lineNumber;
	}
	/**
	 * Get the current line height.
	 * @return int Line height
	 */
	public int getLineHeight(){
		return lineHeight;
	}
	/**
	 * Get the Audio Breaks current position in the text area
	 * @return
	 */
	public int getScreenPosition(){
		return screenPosition;
	}
	/**
	 * Get the tone to be played when the NXC code is run
	 * @return
	 */
	public int getTone(){
		return tone;
	}
	/**
	 * Raises the pitch of the tone
	 */
	public void raiseTone(){
		tone += 250;
		clampTone(MINTONE, MAXTONE);
	}
	/**
	 * Lowers the pitch of the tone.
	 */
	public void lowerTone(){
		tone -=250;
		clampTone(MINTONE, MAXTONE);
	}
	/**
	 * Recalculates the screen position variable based on the line number and line height.
	 */
	private void recalculateScreenPos(){
		screenPosition = lineNumber * lineHeight;
	}
	/**
	 * Recalculates the line number based on screen position and height.
	 */
	private void recalculateLineNumber(){
		lineNumber = screenPosition/lineHeight;
	}
	/**
	 * Clamps the tone between two variables
	 * @param min
	 * @param max
	 */
	private void clampTone(int min, int max){
		if(tone > max){
			tone = max;
		}
		if(tone < min){
			tone = min;
		}
	}
}
