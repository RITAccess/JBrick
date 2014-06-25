package com.jbricx.swing.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.jbricx.swing.ui.preferences.PreferenceStore;
import com.jbricx.swing.ui.preferences.PreferenceStore.Preference;

public class FirstTimeSettingsWindow  extends JDialog implements ActionListener {
	private JPanel mainArea;
	
	private JPanel otherOptionArea;
	private JPanel textOptionArea;
	private JPanel buttonOptionArea;

	private JPanel textOptionsArea;
	private JPanel otherOptionCenterArea;
	
	private GroupLayout textAreaGroupLayout;
	private GroupLayout otherOptionsGroupLayout;
	
	//All of the panels and buttons - need to be global so the group layout can be configured.
	
	//Directory
	private JTextField directoryTextArea;
	private JButton workspaceChangeButton;
	private JLabel workspaceLabel;
	
	private JLabel toolLocationLabel;
	private JTextField toolLocationTextArea;
	private JButton toolLocationBrowseButton;
	
	private JButton cancelButton;
	private JButton okButton;

	private JBricxManager manager;

	/**
	 * Gets the preferences to use throughout the class. Also sets the size of the window. Calls the make components class that creates the rest of the objects.
	 * 
	 * @param manager Main window - Passed in to keep the dialog modal.
	 */
	public FirstTimeSettingsWindow(JBricxManager manager){
		super(manager.getShell(),"Startup",true);
		this.manager = manager;
		
		setLayout(new BorderLayout());
		mainArea = new JPanel();
		mainArea.setLayout(new BoxLayout(mainArea, BoxLayout.Y_AXIS));
		mainArea.setBorder(BorderFactory.createEmptyBorder(10,0,0,0));
		add(mainArea,BorderLayout.CENTER);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setSize((screenSize.width-screenSize.width/2),(screenSize.height-(screenSize.height/5)));
		makeComponents();
		this.pack();
	}
	
	/**
	 * Makes the top layout of components. Main Area is a Box layout comprised of 2 Border layouts (for text options, and other options)
	 * Inside each border layout is a Group Layout for all of the option items.
	 * 
	 * After layouts are made, all of the makeX() functions are called for all the items.
	 * 
	 * Finally @see buildLayout() is called, which puts all the objects instantiated in the make functions, into the layout. 
	 */
	private void makeComponents(){
		textOptionArea = new JPanel(new BorderLayout());
		otherOptionArea = new JPanel(new BorderLayout());
		buttonOptionArea = new JPanel(new BorderLayout());
		
		JLabel titleLabel = new JLabel("<html>&nbsp &nbsp<u>   First Time Startup Options</u></html>");
		otherOptionArea.add(titleLabel,BorderLayout.NORTH);
		
		//otherOptionArea.add(new JSeparator(SwingConstants.HORIZONTAL),BorderLayout.SOUTH);
		
		textOptionsArea = new JPanel();
		textAreaGroupLayout = new GroupLayout(textOptionsArea);
		textOptionsArea.setLayout(textAreaGroupLayout);
		textAreaGroupLayout.setAutoCreateGaps(true);
		textAreaGroupLayout.setAutoCreateContainerGaps(true);
		
		otherOptionCenterArea = new JPanel();
		otherOptionsGroupLayout = new GroupLayout(otherOptionCenterArea);
		otherOptionCenterArea.setLayout(otherOptionsGroupLayout);
		otherOptionsGroupLayout.setAutoCreateGaps(true);
		otherOptionsGroupLayout.setAutoCreateContainerGaps(true);
		
		//textOptionsArea//
		textOptionArea.add(textOptionsArea,BorderLayout.CENTER);	
		otherOptionArea.add(otherOptionCenterArea,BorderLayout.CENTER);

		
		//Three main areas inside the box layout
		mainArea.add(textOptionArea);
		mainArea.add(otherOptionArea);
		mainArea.add(buttonOptionArea);
		
		makeDirectoryBoxes();
		makeToolDirectory();
		makeBottomButtons();
		
		buildLayout();
		
	}

	/**
	 * This builds the lower layouts using a grouplayout. 
	 */
	private void buildLayout() {		
		//Horizontal group for other area
		GroupLayout.SequentialGroup hGroup2 = otherOptionsGroupLayout.createSequentialGroup();
		hGroup2.addGroup(otherOptionsGroupLayout.createParallelGroup()
				.addComponent(toolLocationLabel)
				.addComponent(workspaceLabel)
		);
		hGroup2.addGroup(otherOptionsGroupLayout.createParallelGroup()
				.addComponent(toolLocationTextArea)
				.addComponent(directoryTextArea)
		);
		hGroup2.addGroup(otherOptionsGroupLayout.createParallelGroup()
				.addComponent(toolLocationBrowseButton)
				.addComponent(workspaceChangeButton)
		);
		otherOptionsGroupLayout.setHorizontalGroup(hGroup2);
		
		//Vertical group for the other area
		GroupLayout.SequentialGroup vGroup2 = otherOptionsGroupLayout.createSequentialGroup();
		vGroup2.addGroup(otherOptionsGroupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(toolLocationLabel)
				.addComponent(toolLocationTextArea)
				.addComponent(toolLocationBrowseButton)
		);
		vGroup2.addGroup(otherOptionsGroupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
			.addComponent(workspaceLabel)
			.addComponent(directoryTextArea)
			.addComponent(workspaceChangeButton)
		);
		otherOptionsGroupLayout.setVerticalGroup(vGroup2);
		
	}

	/**
	 * Makes the boxes associated with changing the workspace
	 */
	private void makeDirectoryBoxes() {
		workspaceLabel = new JLabel("Workspace directory");
		
		directoryTextArea = new JTextField();
		directoryTextArea.setText(PreferenceStore.getString(Preference.WORKSPACE));
		directoryTextArea.getAccessibleContext().setAccessibleName("Workspace Directory text area");
		
		workspaceChangeButton = new JButton("Browse...");
		workspaceChangeButton.setActionCommand("browseButton");
		workspaceChangeButton.getAccessibleContext().setAccessibleName("Change Workspace Button. Press Enter to browse.");
		workspaceChangeButton.addActionListener(this);
	}
	
	/**
	 * Makes items for the location of the NBC Tool
	 */
	private void makeToolDirectory(){
		toolLocationLabel = new JLabel("NBC Tool Location");
		
		toolLocationTextArea = new JTextField();
		toolLocationTextArea.setText(PreferenceStore.getString(Preference.NBCTOOL));
		toolLocationTextArea.getAccessibleContext().setAccessibleName("NBC Tool Location field");
		
		toolLocationBrowseButton = new JButton("Browse...");
		
		toolLocationBrowseButton.setActionCommand("toolLocationbutton");
		toolLocationBrowseButton.addActionListener(this);
		toolLocationBrowseButton.getAccessibleContext().setAccessibleName("Change NBC Tool Location Button. Press Enter to change");
	}
	
	/**
	 * Makes the bottom "Reset to Default", "Cancel", "Apply" and "OK"
	 */
	private void makeBottomButtons() {
		JPanel bottomPane = new JPanel();
		bottomPane.setLayout(new BoxLayout(bottomPane, BoxLayout.LINE_AXIS));
		
		cancelButton = new JButton("Cancel");
		cancelButton.setActionCommand("cancel");
		cancelButton.addActionListener(this);
		cancelButton.getAccessibleContext().setAccessibleName("Cancel button. Cancel any changes made and exit");
		
		okButton = new JButton("OK");
		okButton.setActionCommand("OK");
		okButton.addActionListener(this);
		okButton.getAccessibleContext().setAccessibleName("Ok Button. Press to accept any changes");
		
		bottomPane.add(Box.createRigidArea(new Dimension(10,0)));
		bottomPane.add(Box.createHorizontalGlue());
		bottomPane.add(cancelButton);
		bottomPane.add(Box.createHorizontalGlue());
		bottomPane.add(okButton);
		bottomPane.add(Box.createRigidArea(new Dimension(10,0)));
		bottomPane.setBorder(BorderFactory.createEmptyBorder(0,0,5,0));
			
		buttonOptionArea.add(bottomPane,BorderLayout.SOUTH);
		
	}
	
	/**
	 * Goes through all the values and sets what is there to the new preference value.
	 */
	private void saveValues(){
		PreferenceStore.set(Preference.NBCTOOL, toolLocationTextArea.getText());
		PreferenceStore.set(Preference.WORKSPACE, directoryTextArea.getText());
	}
	
	/**
	 * Action Listener method for all of the buttons.
	 */
	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getActionCommand().equals("browseButton")){
			final JFileChooser fc = new JFileChooser();
			fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			int returnVal = fc.showOpenDialog(this);
			//They picked a file
			if(returnVal == JFileChooser.APPROVE_OPTION){
				File selectedDir = fc.getSelectedFile();
				directoryTextArea.setText(selectedDir.getAbsolutePath());	
			}
			
		// the browse button was chosen for tool location. open file dialog to change directory.	
		}else if(arg0.getActionCommand().equals("toolLocationbutton")){
			final JFileChooser fc = new JFileChooser();
			fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
			int returnVal = fc.showOpenDialog(this);
			//They picked a file
			if(returnVal == JFileChooser.APPROVE_OPTION){
				File selectedDir = fc.getSelectedFile();
				toolLocationTextArea.setText(selectedDir.getAbsolutePath());	
			}
		}else if(arg0.getActionCommand().equals("cancel")){
			int n = JOptionPane.showConfirmDialog(
				    this,
				    "Are you sure you wish to cancel? You wont be able to compile or run without the NBC tool.",
				    "First Time Setup",
				    JOptionPane.YES_NO_OPTION);
			if(n == JOptionPane.YES_OPTION){
				//manager.updatePreferences(); //Should automatically update.
				this.dispose();
			}
		// User wishes to leave. Saves changes and leaves	
		}else if(arg0.getActionCommand().equals("OK")){
			saveValues();
			manager.updatePreferences(); //Should automatically Update
			this.dispose();
		}
	}	
}
