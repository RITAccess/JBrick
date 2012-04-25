package com.jbricx.swing.ui.tabs;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.prefs.Preferences;

import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.TreePath;

import com.jbricx.swing.ui.JBricxManager;
import com.jbricx.swing.ui.preferences.PreferenceStore;

public class JBricxFilePane extends JTree implements MouseListener{
	
	Preferences prefs;
	File root;
	JBricxManager manager;
	
	public JBricxFilePane(JBricxManager manager){
		this.manager = manager;
		root = new File(PreferenceStore.getPrefs().get(PreferenceStore.WRKSPC, PreferenceStore.WRKSPC_DEFAULT));
		FileTreeModel model = new FileTreeModel(root);
		this.setModel(model);
		
		this.add(new JScrollPane(
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS));
		
		this.addMouseListener(this);
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		if( arg0.getClickCount() >= 2){
			File ftoOpen = (File) this.getLastSelectedPathComponent();
			manager.openTab(ftoOpen.getAbsolutePath());
			
		}
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
