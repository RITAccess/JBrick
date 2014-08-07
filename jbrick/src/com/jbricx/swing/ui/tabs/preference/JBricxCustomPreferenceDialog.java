package com.jbricx.swing.ui.tabs.preference;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

import com.jbricx.swing.ui.JBricxDialog;

@SuppressWarnings("serial")
public class JBricxCustomPreferenceDialog extends JBricxDialog implements ActionListener {

	static JBricxCustomPreferenceDialog customPrefsDialog = null;
	JButton cancelButton, okButton;
	
	private JBricxCustomPreferenceDialog(JFrame shell, String title,
			JPanel...preferencePanels) {
		super(shell, title, true);
		this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));
		((JPanel)this.getContentPane()).setBorder(new EmptyBorder(7,7,7,7));
		JPanel buttonPanel = new JPanel();
		cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(this);
		cancelButton.getAccessibleContext().setAccessibleName("Cancel button. Cancel any changes made and exit");
		
		okButton = new JButton("OK");
		okButton.addActionListener(this);
		okButton.getAccessibleContext().setAccessibleName("Ok Button. Press to accept any changes");
		buttonPanel.add(cancelButton);
		buttonPanel.add(okButton);
		
		for (JPanel pp : preferencePanels) {
			pp.setBorder(
					new CompoundBorder(
							BorderFactory.createTitledBorder(pp.getAccessibleContext().getAccessibleName()),
							new EmptyBorder(3, 3, 3, 3)
							)
					);
			this.add(pp);
		}
		this.add(buttonPanel);

		this.pack();
		this.setVisible(true);
	}
	
	/**
	 * provides a method to access and open a preference window
	 * Because this is a modal dialog (it disables the parent process)
	 * it does not need to check if other instances of this object are open,
	 * beacuse the parent cannot generate any more instances of this particular object 
	 * @param shell - requires a JFrame
	 * @param title - title for dialog
	 * @param preferencePanels - panels to be added to the frame.
	 */
	public static void openPreference(JFrame shell, String title,
			JPanel...preferencePanels) {
			new JBricxCustomPreferenceDialog(shell, title, preferencePanels);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == cancelButton) {
			this.dispose();
		}
		if (event.getSource() == okButton) {
			PreferencePanel.applyValues();
			this.dispose();
		}
	}
}
