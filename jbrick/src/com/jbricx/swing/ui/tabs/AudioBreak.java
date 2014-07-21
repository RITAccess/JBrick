package com.jbricx.swing.ui.tabs;

import java.awt.Color;
import com.jbricx.swing.ui.preferences.PreferenceStore;

public class AudioBreak {

	//Keys based on http://en.wikipedia.org/wiki/Piano_key_frequencies
	private final int MINKEY = 37; //Lower limit of NXT brick
	private final int MAXKEY = 87; //Upper limit of NXT brick
	private final int STARTKEY = 61;
	private final int INCREMENT = 4;
	
	private int lineNumber;
	private int key;
	
	/**
	 * Constructor for audio breaks. Breaks contain all information needed to display in preform correctly.
	 * @param nLineNumber The line number where the break will be.
	 * @param nLineHeight The current height of lines. This is determined by font size.
	 */
	public AudioBreak(int nLineNumber){
		lineNumber = nLineNumber;
		key = STARTKEY;
	}
	
	//Setters
	/**
	 * Set the line number.
	 * @param nLineNum
	 */
	public void setLineNum(int nLineNum){
		lineNumber = nLineNum;
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
	 * Get the tone to be played when the NXC code is run
	 * @return
	 */
	public int getTone(){
		int keyAsTone = (int) (Math.pow(2.0, (((double)key)-49.0)/12)*440);
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
	
}
