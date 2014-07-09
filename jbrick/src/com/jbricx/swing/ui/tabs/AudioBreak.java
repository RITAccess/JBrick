package com.jbricx.swing.ui.tabs;

import java.awt.Color;

import com.jbricx.swing.ui.preferences.PreferenceStore;

public class AudioBreak {

	private final int MINKEY = 37; 	//key 40 on piano (C4 Middle C)
	private final int MAXKEY = 87; 	//key 50 on piano (A#4/B(flat)4)
	private final int STARTKEY = 61;	//key 45 on piano (F4)
	private final int INCREMENT = 4;
	
	private int lineNumber;
	private int key;
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
		key = STARTKEY;
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
	public void setKey(int nKey){
		key = clamp(nKey, MINKEY, MAXKEY);
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
		int keyAsTone = (int) (Math.pow(2.0, (((double)key)-49.0)/12)*440);
		System.out.println(keyAsTone);
		return keyAsTone;
	}
	
	public int getKey(){
		return key;
	}
	/**
	 * Raises the pitch of the tone
	 */
	public void raiseKey(){
		key = clamp(key+INCREMENT, MINKEY, MAXKEY);
	}
	/**
	 * Lowers the pitch of the tone.
	 */
	public void lowerKey(){
		key = clamp(key-INCREMENT, MINKEY, MAXKEY);
	}
	
	public Color calculateColor(Color bGColor)
	{
		int hRed = clamp((int) ((PreferenceStore.getColor(PreferenceStore.Preference.LINENUMBERFG).getRed() + bGColor.getRed()*1)/2), 0, 255);
		int hGreen = clamp((int) ((PreferenceStore.getColor(PreferenceStore.Preference.LINENUMBERFG).getGreen() + bGColor.getGreen()*1)/2), 0, 255);
		int hBlue = clamp((int) ((PreferenceStore.getColor(PreferenceStore.Preference.LINENUMBERFG).getBlue() + bGColor.getBlue()*1)/2),0 ,255);
		
		float[] hsbValues = new float[3];
		Color.RGBtoHSB(hRed, hGreen, hBlue, hsbValues);
		if(hsbValues[0] == 0 && hsbValues[1] == 0){
			hsbValues[1] += 100;
		}
		int range = MAXKEY - MINKEY + 1;
		int index = (key-STARTKEY);
		if(index < 0){
			index += range;
		}
		float increment = 1.0f/((float)range);
		
		for(int i = 0; i < index; i ++){
			hsbValues[0] = (hsbValues[0]+increment)%1;
		}
		
		Color highlight = new Color(Color.HSBtoRGB(hsbValues[0], hsbValues[1], hsbValues[2]));
		
		return highlight;
	}

	
	/**
	 * Clamps a int between a min and max value
	 * @param val Int to be clamped
	 * @param min Minimum possible value
	 * @param max Maximum possivle value
	 * @return
	 */
	private int clamp(int val, int min, int max){
		if(val > max){
			val = max;
		}
		else if(val < min){
			val = min;
		}
		return val;
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
}
