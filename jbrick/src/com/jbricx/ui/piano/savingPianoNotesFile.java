package com.jbricx.ui.piano;

import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

public class savingPianoNotesFile {
	
	public void receivingNotes(ArrayList<PianoNote> noteList){
		
		System.out.println("noteList.size():" + noteList.size());
		String starting = "task main{";
		String closing = "}";
				try{
					FileOutputStream fileOut = new FileOutputStream("C://prueba.nxc");
					PrintStream p;
					p = new PrintStream(fileOut);
					p.println (starting);
					
						for (int x = 0; x < noteList.size(); x++){
							PianoNote pianoNote = (PianoNote)noteList.get(x);
							p.println ("  PlayTone("+pianoNote.getTone()+","+pianoNote.getNoteTime()+");");
							p.println ("  Wait("+pianoNote.getWaitTime()+");");
						}
						
					p.println(closing);
					p.close();
					
					}catch (Exception e)
					{
                        System.err.println ("Error writing to file");
					}
		}

}
