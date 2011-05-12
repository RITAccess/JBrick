package com.jbricx.help;

import java.io.File;
import java.util.Vector;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

public class CustomTree {

  final Tree tree;
  private TreeViewer treeViewer;
  private MyLabelProvider labelProvider;
  static Vector<Node> nodes = new Vector<Node>();
  @SuppressWarnings("unused")
  private HelpBrowser browser;
  private String home;

  public CustomTree(SashForm form, final HelpBrowser browser) {
    tree = new Tree(form, SWT.BORDER);
    this.browser = browser;
    treeViewer = new TreeViewer(tree);
    treeViewer.setContentProvider(new MyTreeContentProvider());
    labelProvider = new MyLabelProvider();
    treeViewer.setLabelProvider(labelProvider);

    this.setInput(genNodes());

    Node homenode = nodes.firstElement();
    // bug fix for multiple window pane open
    this.clearNodes();
    File file = new File(homenode.getUrl());

    home = file.getAbsolutePath();

    treeViewer.addSelectionChangedListener(new ISelectionChangedListener() {
      public void selectionChanged(SelectionChangedEvent event) {
        if (event.getSelection().isEmpty()) {
          System.out.println("Nothing");
          return;
        }
        if (event.getSelection() instanceof IStructuredSelection) {
          IStructuredSelection selection = (IStructuredSelection) event
              .getSelection();
          Node item = (Node) selection.getFirstElement();

          File file = new File(item.getUrl());
          System.out.println(file.getAbsolutePath());
          browser.setUrl(file.getAbsolutePath());
        }
      }
    });
  }

  public String getHome() {
    return this.home;
  }

  @SuppressWarnings(value = { "unchecked" })
  public Vector<Node> genNodes() {
    // Node category = new Node("Getting Started", null);
    // nodes.add(category);
    // new Node("b1", category);

    Node category = new Node("Overview", null, "help\\html\\overview.html");
    nodes.add(category);

    category = new Node("Getting Started", null,
        "help\\html\\gettingstarted.html");
    nodes.add(category);
    new Node("Basic Tutorial", category, "help\\html\\basictutorial.html");
    nodes.add(new Node("Piano Composer", null,
        "help\\html\\Piano Composer.html"));
    nodes.add(new Node("Joystick Composer", null,
        "help\\html\\Joistick Composer.html"));
    // category = new Node("Concepts", null, "help\\html\\concepts.html");
    // nodes.add(category);

    // category = new Node("Tasks", null, "help\\html\\tasks.html");
    // nodes.add(category);

    category = new Node("Reference", null, "help\\html\\reference.html");
    nodes.add(category);

    // category = new Node("Tips and Tricks", null,
    // "help\\html\\tipsandtricks.html");
    // nodes.add(category);

    category = new Node("Keyboard Shortcuts", null,
        "help\\html\\keyboardshortcuts.html");
    nodes.add(category);

    category = new Node("Legal", null, "help\\html\\legal.html");
    nodes.add(category);

    return nodes;
  }

  public void setInput(Vector<Node> nodes) {
    treeViewer.setInput(nodes);
  }

  public void traditional() {
    for (int i = 0; nodes != null && i < nodes.size(); i++) {
      Node node = nodes.elementAt(i);
      addNode(null, node);
    }
  }

  public void clearNodes() {
    nodes.removeAllElements();
  }

  private void addNode(TreeItem parentItem, Node node) {
    TreeItem item = null;
    if (parentItem == null)
      item = new TreeItem(tree, SWT.NONE);
    else
      item = new TreeItem(parentItem, SWT.NONE);

    item.setText(node.getName());

    Vector<Node> subs = node.getSubCategories();
    for (int i = 0; subs != null && i < subs.size(); i++)
      addNode(item, subs.elementAt(i));
  }

}

class MyLabelProvider implements ILabelProvider {
  public String getText(Object element) {
    return ((Node) element).getName();
  }

  public Image getImage(Object arg0) {
    return null;
  }

  public void addListener(ILabelProviderListener arg0) {
    System.out.println("Clicked");
  }

  public void dispose() {
  }

  public boolean isLabelProperty(Object arg0, String arg1) {
    return false;
  }

  public void removeListener(ILabelProviderListener arg0) {
  }
}

class MyTreeContentProvider implements ITreeContentProvider {
  public Object[] getChildren(Object parentElement) {
    Vector<Node> subcats = ((Node) parentElement).getSubCategories();
    return subcats == null ? new Object[0] : subcats.toArray();
  }

  public Object getParent(Object element) {
    return ((Node) element).getParent();
  }

  public boolean hasChildren(Object element) {
    return ((Node) element).getSubCategories() != null;
  }

  public Object[] getElements(Object inputElement) {
    if (inputElement != null && inputElement instanceof Vector) {
      return ((Vector) inputElement).toArray();
    }
    return new Object[0];
  }

  public void dispose() {
  }

  public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
  }
}

class Node {

  private String name;
  private Vector<Node> subCategories;
  private Node parent;
  private String url;

  public Node(String name, Node parent) {
    this.name = name;
    this.parent = parent;
    if (parent != null)
      parent.addSubCategory(this);
  }

  public Node(String name, Node parent, String url) {
    this.name = name;
    this.parent = parent;
    this.url = url;
    if (parent != null)
      parent.addSubCategory(this);
  }

  public String getUrl() {
    return this.url;
  }

  public Vector<Node> getSubCategories() {
    return subCategories;
  }

  private void addSubCategory(Node subcategory) {
    if (subCategories == null)
      subCategories = new Vector<Node>();
    if (!subCategories.contains(subcategory))
      subCategories.add(subcategory);
  }

  public String getName() {
    return name;
  }

  public Node getParent() {
    return parent;
  }
}