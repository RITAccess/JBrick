package com.jbricx.swing.ui.tabs;

import java.io.File;
import java.util.prefs.Preferences;

import javax.swing.JTree;
import com.jbricx.swing.ui.preferences.PreferenceStore;

public class JBricxFilePane extends JTree {
	
	Preferences prefs;
	File root;
	
	public JBricxFilePane(){
		root = new File(PreferenceStore.getPrefs().get(PreferenceStore.WRKSPC, PreferenceStore.WRKSPC_DEFAULT));
		FileTreeModel model = new FileTreeModel(root);
		this.setModel(model);
	}

}
