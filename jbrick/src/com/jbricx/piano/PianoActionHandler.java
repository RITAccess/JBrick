package com.jbricx.piano;

public interface PianoActionHandler {
	
	/**
	 *  A PianoKeyboard button was hit.
	 *  The Implemented method will be called when when a button is hit
	 *  
	 *  Is required for a pianoKeyboard
	 * @param action
	 * @return
	 */
	public void pianoActionHit(String noteInformation); 
}
