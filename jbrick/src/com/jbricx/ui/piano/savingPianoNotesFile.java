package com.jbricx.ui.piano;

import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

public class savingPianoNotesFile {
	
	public void receivingNotes(ArrayList<PianoNote> noteList){
		
		System.out.println("noteList.size():" + noteList.size());
		String starting = "task main{";
		String closing = "}";
		
		for (int x = 0; x < noteList.size(); x++) {
				PianoNote pianoNote = (PianoNote)noteList.get(x);
				PrintStream p;
				
				try{
					FileOutputStream fileOut = new FileOutputStream("D://prueba.nxc");
					p = new PrintStream(fileOut);
					
					p.println ("PlayTone("+pianoNote.getTone()+","+pianoNote.getNoteTime()+");");
					p.println ("Wait("+pianoNote.getTone()+");");
					p.close();
					//System.out.println("piano noteTime " + pianoNote.getNoteTime());
					//System.out.println("piano note " + pianoNote.getTone());
					//System.out.println("piano waittime " + pianoNote.getWaitTime());
					}catch (Exception e)
					{
                        System.err.println ("Error writing to file");
					}
		}
	
	}

}
