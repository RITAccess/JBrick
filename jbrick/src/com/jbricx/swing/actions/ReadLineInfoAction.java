package com.jbricx.swing.actions;

import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;

import org.fife.ui.rtextarea.RTextAreaUI;

import com.jbricx.swing.ui.JBricxManager;
import com.jbricx.swing.ui.tabs.AudioBreak;
import com.jbricx.swing.ui.tabs.JBricxTabItem;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

@SuppressWarnings("serial")
/**
 * This action reads off information about the line the caret is currently on.
 * @author Roderick Kendrick
 *
 */
public class ReadLineInfoAction extends JBricxAbstractAction{

	private VoiceManager voiceManager;
	private Voice voice;
	
	public ReadLineInfoAction(final JBricxManager manager) {
		  super("", new ImageIcon(ReadLineInfoAction.class.getResource("/icons/save.png")), manager);
			voiceManager = VoiceManager.getInstance();
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		String lineInfo = "";
	    JBricxTabItem tab = getManager().getTabFolder().getSelection();
	    
	    lineInfo = "Line number " + (((RTextAreaUI) tab.getUI()).getCurrentLine()+1);
	    
	    AudioBreak temp = ((RTextAreaUI) tab.getUI()).breakExists();
	    if(temp !=null){
	    	lineInfo += ", with audio break";
	    }
	    
	    lineInfo += ".";
	    
		voice = voiceManager.getVoice("kevin16");
		voice.allocate();
		voice.speak(lineInfo);
		voice.deallocate();
	}

}