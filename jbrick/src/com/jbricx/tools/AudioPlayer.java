package com.jbricx.tools;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

public class AudioPlayer {

	public static final int SAMPLE_RATE = 16 * 1024; // ~16KHz
    public static final int SECONDS = 2;
    
    /**
     * plays a set of given notes (all with the same length)
     * example of notes are as follows:
     * A0, B3, C8, F#4, Fb4, REST
     * Any note A-G, Sharp (#) or Flat (b), and octave (0-8) 
     * will be recognized, as will REST. 
     * 
     * @param lengths - the lengths in ms of the note
     * @param notes - the notes in Scientific piano notation (examples above)
     */
    public static void play(int[] lengths, String[] notes){
    	AudioFormat af = new AudioFormat(SAMPLE_RATE, 8, 1, true, true);
    	
		try {
			SourceDataLine line = AudioSystem.getSourceDataLine(af);
	    	line.open(af, SAMPLE_RATE);
	    	line.start();
	    	for (int i = 0; i < notes.length; i++){
	    		play(line, notes[i], lengths[i]);
	    	}
	    	line.drain();
	    	line.close();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
    }
    
    public static void play(int length, String...notes){
    	int[] lengths = new int[notes.length];
    	for (int i = 0; i < lengths.length; i++){
    		lengths[i] = length;
    	}
    	play(lengths, notes);
    }
    
    public static void play(String...notes){
    	play(500, notes);
    }
    
    /**
     * Takes in a String, and returns the byte array to create a sound
     * 
     * @param note - any piano note (ex. C#2, E4, Gb6)
     * @return byte array to create a sound
     */
    private static byte[] getByteNote(String note){
        byte[] sin = new byte[SECONDS * SAMPLE_RATE]; // the REST byte array
        
    	/*
    	 * this should take a note and turn it into a number
    	 * example: A0 => 1, F#1 => 10, C3 => 16, D#4 => 43, C8 => 88
    	 */
    	if (!note.matches("^([A-G](b|#)?[0-8]|REST)$")){
    		System.err.println("Invalid note, using REST instead");
    		return sin;
    	}
    	
    	int n; // number of note
    	switch (note.charAt(0)) {
	    	case 'A' : n = 1; break;
	    	case 'B' : n = 3; break;
	    	case 'C' : n = 4; break;
	    	case 'D' : n = 6; break;
	    	case 'E' : n = 8; break;
	    	case 'F' : n = 9; break;
	    	case 'G' : n = 11; break;
	    	default : return sin; // is a rest note
    	}
    	
    	if (note.charAt(1) == '#'){
    		n++; // if Sharp (#) then up the value by one
    		n = n + Character.getNumericValue(note.charAt(2)) * 12; // setting the octave (must check 3rd char)
    	} else if (note.charAt(1) == 'b'){
    		n--; // if Flat (b) then down the value by one
    		n = n + Character.getNumericValue(note.charAt(2)) * 12; // setting the octave (must check 3rd char)
    	} else {
    		n = n + Character.getNumericValue(note.charAt(1)) * 12; // setting the octave (must check 2nd char)
    	}

    	// find the frequency
    	double freq = Math.pow(2d, (double)(n-49)/12d) * 440;

    	// create the byte array
    	for (int i = 0; i < sin.length; i++) {
            double period = (double)SAMPLE_RATE / freq;
            double angle = 2.0 * Math.PI * i / period;
            sin[i] = (byte)(Math.sin(angle) * 127f);
        }
    	return sin;
    }

    /**
     * Uses the SourceDataLine provided to make sound
     * 
     * @param line
     * @param note
     * @param ms
     */
    private static void play(SourceDataLine line, String note, int ms){
    	ms = Math.min(ms, SECONDS * 1000);
        int length = SAMPLE_RATE * ms / 1000;
        line.write(getByteNote(note), 0, length);
    }
    

    public static void main(String[] args) throws LineUnavailableException {
    	AudioPlayer.play(750, "C4", "C3", "C2");
    	int[] lens = {250, 500, 750}; String[] notes = {"A4", "B4", "C4"};
    	AudioPlayer.play(lens, notes);
        AudioPlayer.play("D#4", "REST", "C4");
    }
}
