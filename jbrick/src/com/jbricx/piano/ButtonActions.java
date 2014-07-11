package com.jbricx.piano;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

import com.jbricx.swing.ui.browser.Browser;
import com.jbricx.tools.AudioPlayer;

/**
 * Class for creating the buttons in the piano frame and giving them functionality
 * 
 * @author Melissa Y
 *
 */
public class ButtonActions {
	
	GridBagConstraints gbCon = new GridBagConstraints();
	
	private JButton clear;
	private JButton copy;
	private JButton play;
	private JButton save;
	private JButton help;
	private JButton back;
	
	private JPanel buttonPanel;
	private JPanel checkPanel;
	private Border buttonBorder;
	private JCheckBox javaBox;
	private JCheckBox nxtBox;
	
	protected boolean nxtOutput = false;
	protected boolean javaOutput = true;
	
	NotesView checkInput = new NotesView();
	
	/**
	 * Constructor of the buttons used
	 * @param textViewPanel - where all the note information is ready to be grabbed 
	 * 
	 */
	public ButtonActions(final NotesView textViewPanel, final PianoWindow window) {
		
		this.buttonPanel = new JPanel(new GridBagLayout());
		this.buttonBorder = new EtchedBorder();
		
		// THE PLAY BUTTON
		this.play = new JButton("Play");
		this.play.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				final Note[] notes = Note.getNotesFromText(textViewPanel.getStringNotes());
				if (notes == null){
					window.accessibleStatus.readLabel("No Notes To Play", window, ButtonActions.this.play);
					return;
				}
				int[] lengths = new int[notes.length];
				String[] noteStrs = new String[notes.length];
				int count = 0;
				for (Note n: notes){
					lengths[count] = n.length;
					noteStrs[count++] = n.note;
				}
				if (ButtonActions.this.javaOutput){
					AudioPlayer.play(lengths, noteStrs);
				}
				if (ButtonActions.this.nxtOutput){
					for (final Note n : notes){
						AudioPlayer.playNXT(n.length, n.note);
						try {
							Thread.sleep(n.length);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
			
		});
		
		// THE COPY BUTTON
		this.copy = new JButton("Copy");
		this.copy.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				Note[] notes = Note.getNotesFromText(textViewPanel.getStringNotes());
				if (notes == null){
					window.accessibleStatus.readLabel("No Notes To Copy", window, ButtonActions.this.copy);
					return;
				}
				String copyStr = Note.getNXC(notes);
				StringSelection selection = new StringSelection(copyStr);
				Toolkit.getDefaultToolkit().getSystemClipboard().setContents(selection, selection);
			}
			
		});
		
		// THE SAVE BUTTON
		this.save = new JButton("Save");
		this.save.addActionListener(new ActionListener(){

			@Override
            public void actionPerformed(ActionEvent arg0) {
				Note[] notes = Note.getNotesFromText(textViewPanel.getStringNotes());
				if (notes == null){
					window.accessibleStatus.readLabel("No Notes To Save", window, ButtonActions.this.save);
					return;
				}
				String startStr = "task main()\n{\n";
				String saveStr = Note.getNXC(notes);
                JFileChooser saveFile = new JFileChooser();
                if (saveFile.showSaveDialog(null) == JFileChooser.APPROVE_OPTION){
                	PrintWriter out = null;
                	try {
						out = new PrintWriter(saveFile.getSelectedFile().getAbsolutePath());
						out.println(startStr + saveStr + "}");
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} finally {
						out.close();
					}
                	
                };
            }
			
		});
		
		this.help = new JButton("Help");
		this.help.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				new Browser(window);
			}
			
		});
		
		
		
		// THE CLEAR BUTTON
		this.clear = new JButton("Clear");
		this.clear.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				textViewPanel.clearText();
				
			}
			
		});
		
		// THE BACK BUTTON
		this.back = new JButton("Undo");
		this.back.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				Note[] notes = Note.getNotesFromText(textViewPanel.getStringNotes());
				if (notes == null){
					window.accessibleStatus.readLabel("No Notes to delete", window, ButtonActions.this.back);
					return;
				}
				if((textViewPanel.getText().contains("\n"))) {
					String newNotes = Note.deleteLastNote(textViewPanel.getText());
					textViewPanel.setText(newNotes);
				}
				else{
					textViewPanel.clearText();
				}
				
			}
			
		});
		
		// THE CHECK BOXES
		javaBox = new JCheckBox("Java Output");
		javaBox.setSelected(true);
		javaBox.addItemListener(new ItemListener(){

			@Override
			public void itemStateChanged(ItemEvent arg0) {
				ButtonActions.this.javaOutput = (arg0.getStateChange() == ItemEvent.SELECTED ? true : false);
				ButtonActions.this.play.setEnabled(nxtOutput | javaOutput);
			}

			
		});
		nxtBox = new JCheckBox("NXT Output");
		nxtBox.addItemListener(new ItemListener(){

			@Override
			public void itemStateChanged(ItemEvent arg0) {
				ButtonActions.this.nxtOutput = (arg0.getStateChange() == ItemEvent.SELECTED ? true : false);
				ButtonActions.this.play.setEnabled(nxtOutput | javaOutput);
			}

			
		});
	}
	
	/**
	 * Set up for the buttons orientations
	 * 
	 * Add a button to the panel 
	 * 
	 * Exception: The rest button
	 * 
	 * @param ctrlButton
	 * @param xplace
	 * @param yplace
	 */
	private void bPanel(JButton ctrlButton,int xPlace,int yPlace) {
		
		gbCon.anchor = GridBagConstraints.CENTER;
		gbCon.insets = new Insets(10,10,10,10);
		gbCon.ipadx = 50;
		gbCon.ipady = 20;
		gbCon.gridx = xPlace;
		gbCon.gridy = yPlace;
		buttonPanel.add(ctrlButton,gbCon);

	}

	/**
	 * Give buttons functionality 
	 * 
	 * Set button information 
	 * 
	 */
	public void buttonAction() {
		
		play.setToolTipText("Plays back notes and rests added");
		copy.setToolTipText("Copies NXC code that will play");
		save.setToolTipText("Saves NXC code that will play");
		help.setToolTipText("Opens help browser to piano composer");
		clear.setToolTipText("Clears all keys");
		back.setToolTipText("Deletes last note typed");
	}
	
	public JPanel soundCheck() {
		checkPanel = new JPanel(new GridLayout(1,2));
		checkPanel.add(javaBox);
		checkPanel.add(nxtBox);
		checkPanel.setBorder(BorderFactory.createTitledBorder(buttonBorder,"Sound Location Output"));
		return checkPanel;
	}
	
	/**
	 * Add all of the buttons to the panel (except the rest)
	 * 
	 * @return the editing button panel
	 */
	public JPanel setUpBPanel() {
		
		bPanel(play,0,0);
		bPanel(copy,0,1);
		bPanel(save,1,0);
		bPanel(help,1,1);
		bPanel(back,0,2);
		bPanel(clear,1,2);
		buttonAction();
		buttonPanel.setBorder(BorderFactory.createTitledBorder(buttonBorder, "Edit Controls"));
		return buttonPanel;
		
	}
	
}

class Note{
	String note;
	int length;
	NotesView textViewPanel = new NotesView();
	
	Note(String note, int length){
		this.note = note;
		this.length = length;
	}
	
	Note(String txt){
		String[] len = txt.split(" ")[1].split("/");
		this.note = txt.split(" ")[0];
		this.length = (2000 / Integer.parseInt(len[1])) * Integer.parseInt(len[0]);
	}
	
	public String getNXC(){
		return String.format("PlayTone(%d, %d); Wait(%d);\n", (int)AudioPlayer.getFreq(note), length, length);
	}
	
	public static String getNXC(Note[] notes){
		String str = "";
		for (Note n: notes){
			str = str + n.getNXC();
		}
		return str;
	}
	
	/**
	 * Grabs the notes from the text and outputs a Note array
	 * returns null if no text
	 * (the Note array contains the String of the Note as well as it's duration in ms)
	 * @param text
	 * @return
	 */
	public static Note[] getNotesFromText(String[] text){
		Note[] notes = new Note[text.length];
		if (text[0].length() == 0){
			return null;
		}
		int count = 0;
		for (String txt: text){
			notes[count++] = new Note(txt);
		}
		return notes;
	}
	
	/**
	 * Deletes the last note written
	 * 
	 * @param text
	 * @return 
	 */
	public static String deleteLastNote(String text){
		if (text == " ") {
			return text;
		}
		String result = text.substring(0, text.lastIndexOf('\n'));
		
		return result;
	}
	
}