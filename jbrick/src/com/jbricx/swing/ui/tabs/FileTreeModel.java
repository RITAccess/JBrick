package com.jbricx.swing.ui.tabs;

import javax.swing.event.TreeModelListener;
import javax.swing.tree.DefaultTreeSelectionModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

/**
 * 
 * @author Stack Overflow, http://stackoverflow.com/questions/10151341/how-to-create-a-file-directory-and-contained-files-from-a-file-array
 *
 */
public class FileTreeModel  extends DefaultTreeSelectionModel implements TreeModel{
// We specify the root directory when we create the model.

protected FileNode root;
public FileTreeModel(FileNode root) { this.root = root; 
//setSelectionMode(TreeSelectionModel.DISCONTIGUOUS_TREE_SELECTION); 
}

// The model knows how to return the root object of the tree
public Object getRoot() { return root; }

// Tell JTree whether an object in the tree is a leaf
public boolean isLeaf(Object node) {  return ((FileNode)node).isFile(); }

// Tell JTree how many children a node has
public int getChildCount(Object parent) {
String[] children = ((FileNode)parent).list();
if (children == null) return 0;
return children.length;
}

// Fetch any numbered child of a node for the JTree.
// Our model returns FileNode objects for all nodes in the tree.  The
// JTree displays these by calling the FileNode.toString() method.
public Object getChild(Object parent, int index) {
String[] children = ((FileNode)parent).list();
if ((children == null) || (index >= children.length)) return null;
return new FileNode((FileNode) parent, children[index]);
}

// Figure out a child's position in its parent node.
public int getIndexOfChild(Object parent, Object child) {
String[] children = ((FileNode)parent).list();
if (children == null) return -1;
String childname = ((FileNode)child).getName();
for(int i = 0; i < children.length; i++) {
if (childname.equals(children[i])) return i;
}
return -1;
}

// This method is invoked by the JTree only for editable trees.  
// This TreeModel does not allow editing, so we do not implement 
// this method.  The JTree editable property is false by default.
public void valueForPathChanged(TreePath path, Object newvalue) {}

// Since this is not an editable tree model, we never fire any events,
// so we don't actually have to keep track of interested listeners*/
public void addTreeModelListener(TreeModelListener l) {}
public void removeTreeModelListener(TreeModelListener l) {}

}