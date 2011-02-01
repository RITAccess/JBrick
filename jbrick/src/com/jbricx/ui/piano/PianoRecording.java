package com.jbricx.ui.piano;

import java.util.ArrayList;
import com.jbricx.ui.piano.PianoNote;

public class PianoRecording {
	private ArrayList<PianoNote> notes;

	PianoRecording(){
		notes = new ArrayList<PianoNote>();
	}
	public void AddKey(Integer tone, Integer duration){
		notes.add(new PianoNote(tone, duration));
	}
	public void AddKey(PianoNote key){
		notes.add(key);
	}
	public void ClearKeys() {
		notes = new ArrayList<PianoNote>();
	}
	public ArrayList<PianoNote> getNotes(){
		return notes;
	}
	
}
