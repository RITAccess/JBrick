package com.jbricx.swing.actions;

import java.awt.event.ActionEvent;

import javafx.application.Platform;

import javax.swing.ImageIcon;
import javax.swing.SwingWorker;

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
		
	    SwingWorker<?, ?> work = new SwingWorker<Object, Object>(){

			@Override
			protected Object doInBackground() throws Exception {
				voice = voiceManager.getVoice("kevin16");
				voice.allocate();
			    JBricxTabItem tab = getManager().getTabFolder().getSelection();
			    String lineInfo = "Line number " + (((RTextAreaUI) tab.getUI()).getCurrentLine()+1);
			    
			    if( ((RTextAreaUI) tab.getUI()).breakExists() != null){
			    	lineInfo += ", with audio break";
			    }
			    
			    lineInfo += ".";
			    
				
				voice.speak(lineInfo);
				voice.deallocate();
				return null;
			};
			
	    };
	    
	    work.execute();
	}

}