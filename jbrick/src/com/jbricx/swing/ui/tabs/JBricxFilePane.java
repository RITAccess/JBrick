package com.jbricx.swing.ui.tabs;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.prefs.Preferences;

import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.ScrollPaneConstants;
import com.jbricx.swing.ui.JBricxManager;
import com.jbricx.swing.ui.preferences.PreferenceStore;

public class JBricxFilePane extends JScrollPane implements MouseListener{
	
	Preferences prefs;
	FileNode root;
	JBricxManager manager;
	JTree tree;
	
	public JBricxFilePane(JBricxManager manager){
		super( ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		tree = new JTree();
		this.manager = manager;
		root = new FileNode(PreferenceStore.getPrefs().get(PreferenceStore.WRKSPC, PreferenceStore.WRKSPC_DEFAULT));
		FileTreeModel model = new FileTreeModel(root);
		tree.setModel(model);
		tree.addMouseListener(this);
		this.setViewportView(tree);
	}
	
	public String stripString(String name){
		String[] stringList = name.split("\\.");
		String newString = stringList[stringList.length-1];
		return newString;
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		if( arg0.getClickCount() >= 2){
			File ftoOpen = (File) tree.getLastSelectedPathComponent();
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
