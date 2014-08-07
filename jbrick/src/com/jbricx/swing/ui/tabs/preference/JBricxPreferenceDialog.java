package com.jbricx.swing.ui.tabs.preference;

import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

import com.jbricx.swing.ui.JBricxDialog;
import com.jbricx.swing.ui.JBricxManager;
import com.jbricx.swing.ui.preferences.PreferenceStore.Preference;

/**
 * Preference Dialog - dialog to change update preference information
 * @author Ethan Jurman (ehj2229@g.rit.edu)
 *
 */
@SuppressWarnings("serial")
public class JBricxPreferenceDialog extends JBricxDialog {
	private JPanel themePanel, colorPanel, fontPanel, miscPanel, nbcPanel, workspacePanel, buttonPanel;
	private JBricxManager manager;
	public static boolean isDirty;
	static JBricxPreferenceDialog preferenceDialog = null;
	
	/**
	 * constructor
	 * @param manager
	 */
	private JBricxPreferenceDialog(JBricxManager manager){
		super(manager.getShell(),"Preferences",false);
		isDirty = false;
		this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));
		((JPanel)this.getContentPane()).setBorder(new EmptyBorder(7,7,7,7));
		this.setSize(new Dimension(550,660));
		this.manager = manager;
		// Setting up the Theme Panel
		themePanel = new ThemePane(manager).createPanel();
		
		// Setting up the Color Panel
		colorPanel = ColorPane.createPanels();
		
		// Setting up the Font Panel
		fontPanel = new FontPane().createPanel();
		
		// Setting up the Misc Panel
		miscPanel = new CheckboxPane().createPanel();
		
		// Setting up the NBC Panel
		nbcPanel = new DirectoryPane(Preference.NBCTOOL, manager, false).createPanel();
		
		// Setting up the Workspace Panel
		workspacePanel = new DirectoryPane(Preference.WORKSPACE, manager, true).createPanel();
		
		// Setting up the button Panel
		buttonPanel = new ButtonPane(this);
		
		// Loop add / titled borders
		for(JPanel jp : new JPanel[]{themePanel, colorPanel, fontPanel, miscPanel, nbcPanel, workspacePanel}){
			jp.setBorder(
					new CompoundBorder(
							BorderFactory.createTitledBorder(jp.getAccessibleContext().getAccessibleName()),
							new EmptyBorder(3, 3, 3, 3)
							)
					);
			this.add(jp);
		}

		this.add(buttonPanel);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.pack();
	}
	
	/**
	 * apply values of preference window
	 */
	protected static void applyValues(){
		PreferencePanel.applyValues();
		isDirty = false;
	}
	
	/**
	 * reset values of the preference window
	 */
	protected static void resetValues() {
		PreferencePanel.resetValues();
	}
	
	protected JBricxManager getManager(){
		return this.manager;
	}
	
	/**
	 * provides a method to access and open a preference window
	 * @param manager - requires a JBricxManager
	 */
	public static void openPreference(JBricxManager manager) {
		if (preferenceDialog == null) {
			preferenceDialog = new JBricxPreferenceDialog(manager);
		}
		preferenceDialog.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent evt) {
				preferenceDialog = null;
			}
		});
	}

}