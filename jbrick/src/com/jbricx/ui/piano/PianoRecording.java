package com.jbricx.ui.piano;

import java.util.ArrayList;

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
	public String getRecordingStr(){
	  String recordStr = "";
	  for(PianoNote note : notes){
		  if(note.getTone() != 0)
			  recordStr += "PlayTone(" + note.getTone().toString() + ", " + note.getNoteTime().toString() + ");\n";
		  recordStr += "Wait(" + note.getWaitTime().toString() + ");\n";
	  }
	  return recordStr;
	}
}
