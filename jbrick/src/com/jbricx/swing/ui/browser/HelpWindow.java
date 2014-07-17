package com.jbricx.swing.ui.browser;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.URL;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkEvent.EventType;
import javax.swing.event.HyperlinkListener;

@SuppressWarnings("serial")
public class HelpWindow extends JFrame{
	
	// This is the helpWindow that prevents multiple help windows
	static HelpWindow helpWindow = null;
	// Main panel that contians the content
	JScrollPane jDisplayPane = null;
	
	/* The HelpOptions Enum contains all the information for each of the buttons 
	 * to be laid out and have correct association with the pages available.
	 * 
	 * When making a new page add the page name to the enum.
	 */
	enum HelpOptions {
		HOME,
		BASICS,
		TUTORIAL,
		JOYSTICK,
		PIANO,
		LEGAL,
		SHORTCUTS,
		;
		
		JButton button;
		String name;
		
		HelpOptions(){
			name = toString().charAt(0) + toString().substring(1).toLowerCase();
			button = new JButton(name);
			button.setActionCommand(toString());
			button.setAlignmentX(Component.CENTER_ALIGNMENT);
			//button.setForeground(Color.black);
			button.setFont(Font.decode("Consolas=plain-24"));
			button.setMargin(new Insets(0,0,0,0));
			button.getAccessibleContext().setAccessibleName(
					String.format("%s page. Click to go to the %s page.", toString(), toString())
				);
		}
	}
	
	/**
	 * creation of the HelpWindow
	 * Note : this contructor is private - as to allow only one instance 
	 * of the HelpWindow at a time. If you want to open the help window 
	 * call the static method openHelpWindow.
	 */
	private HelpWindow(){
		super("Help");
		// Layout : BorderLayout
		this.setLayout(new BorderLayout());
		// Adding a left panel
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(14, 1));
		for (final HelpOptions b : HelpOptions.values()){
			b.button.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent arg0) {
					helpWindow.changePageContent(b.toString());
				}
				
			});
			buttonPanel.add(b.button);
		}
		this.add(buttonPanel, BorderLayout.WEST);
		this.setSize(800, 600);
		this.setDefaultCloseOperation(HIDE_ON_CLOSE);
		this.setVisible(true);
		this.repaint();
	}
	
	/**
	 * htmlContent - generates the editor that is used 
	 * to display html pages. This will generate a new 
	 * JEditorPane on the string provided.
	 * @param page
	 * @return
	 */
	private static JEditorPane htmlContent(String page) {
		JEditorPane editor = null;
		try {
			editor = new JEditorPane(new File("help/html/" + page + ".html").toURI().toURL());
		} catch (Exception e) {
			e.printStackTrace();
		}
		editor.setEditable(false);
		editor.addHyperlinkListener(new HyperlinkListener(){

			@Override
			public void hyperlinkUpdate(HyperlinkEvent arg0) {
				if (arg0.getEventType() == EventType.ACTIVATED){
					try {
						Desktop.getDesktop().browse(new URL(arg0.getDescription()).toURI());
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			
		});
		return editor;
	}
	
	/**
	 * changePageContent - changes the jDisplayPane
	 * to have the new page.
	 * @param page
	 */
	private void changePageContent(String page){
		if (jDisplayPane != null){
			this.remove(jDisplayPane);
		}
		jDisplayPane = new JScrollPane(htmlContent(page));
		this.add(jDisplayPane, BorderLayout.CENTER);
		this.setVisible(true);
		this.repaint();
	}
	
	/**
	 * openHelpWindow - Opens up the Help Window.
	 * Call this method to open the window (do not 
	 * use the constructor).
	 * @param page - the page to generate (home page = "Home")
	 */
	public static void openHelpWindow(String page){
		if (helpWindow == null){
			helpWindow = new HelpWindow();
		}
		helpWindow.changePageContent(page);
	}
}
