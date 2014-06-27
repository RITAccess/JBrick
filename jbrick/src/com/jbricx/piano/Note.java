package com.jbricx.piano;

public enum Note {
	A,
	B,
	C,
	D,
	E,
	F,
	G,
	ASHARP,
	CSHARP,
	DSHARP,
	FSHARP,
	GSHARP,
	REST,
	;
	
	public static String getNote(Note note, String length, int octave) {
		return String.format("%s %s%d note \n", length, note.toString(), octave);
	}
	
}
