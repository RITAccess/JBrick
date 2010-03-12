package com.jbricx.help;

import java.io.File;
import java.io.FilenameFilter;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Vector;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.browser.OpenWindowListener;
import org.eclipse.swt.browser.ProgressEvent;
import org.eclipse.swt.browser.ProgressListener;
import org.eclipse.swt.browser.StatusTextEvent;
import org.eclipse.swt.browser.StatusTextListener;
import org.eclipse.swt.browser.TitleEvent;
import org.eclipse.swt.browser.TitleListener;
import org.eclipse.swt.browser.WindowEvent;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;

public class HelpBrowser {

	private Display display = Display.getCurrent();// new Display();
	private final Shell shell = new Shell(display);
	private Browser browser;
	private ArrayList<String> urls = new ArrayList<String>();
	private String[] titles;
	private int index;
	private boolean flag = false;

	Label labelStatus;
	Text textLocation;

	static Vector nodes = new Vector();

	public HelpBrowser() {

		buildUrls();
	}

	ArrayList urlList = new ArrayList();

	@SuppressWarnings("deprecation")
	private void buildUrls() {
		if (!flag) {
			urls.add("www.google.com");
			urlList.add("www.google.com");
			DirectoryDialog dialog = new DirectoryDialog(shell);
			// String folder =
			// "C:\\Users\\spencer\\workspace\\jbrick\\help\\html";//dialog.open();
			String folder = "help\\html";// dialog.open();
			// System.out.println(folder);
			if (folder == null)
				return;
			File file = new File(folder);
			File[] files = file.listFiles(new FilenameFilter() {
				public boolean accept(File dir, String name) {
					return name.endsWith(".html");
				}
			});

			titles = new String[files.length];
			index = 0;
			for (int i = 0; i < files.length; i++) {
				try {
					String url = files[i].toURL().toString();
					urls.add(url);
					urlList.add(url);
				} catch (MalformedURLException ex) {
				}
			}
			flag = true;
		}
	}

	public void show() {

		shell.setText("Jbricx Help Browser");
		shell.setLayout(new GridLayout());

		ToolBar toolBar = new ToolBar(shell, SWT.FLAT | SWT.RIGHT);
		final ToolBarManager manager = new ToolBarManager(toolBar);

		Composite compTools = new Composite(shell, SWT.NONE);
		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		compTools.setLayoutData(data);
		compTools.setLayout(new GridLayout(2, false));

		Composite compWin = new Composite(shell, SWT.NONE);
		data = new GridData(GridData.FILL_BOTH);

		compWin.setLayoutData(data);
		compWin.setLayout(new FillLayout());
		final SashForm form = new SashForm(compWin, SWT.HORIZONTAL);

		form.setLayout(new FillLayout());

		// final List list = new List(form,SWT.SIMPLE);

		// list.setSize(300, SWT.FILL);
		// for (String s: urls){
		// list.add(s);
		// }

		final CustomTree tree = new CustomTree(form, this);

		browser = new Browser(form, SWT.NONE);

		// list.addListener(SWT.Selection, new Listener(){
		// @Override
		// public void handleEvent(Event arg0) {
		// int index = list.getSelectionIndex();
		// browser.setUrl(urls.get(index));
		//				
		// }
		// });

		Composite compositeStatus = new Composite(shell, SWT.NULL);
		compositeStatus.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		compositeStatus.setLayout(new GridLayout(2, false));

		labelStatus = new Label(compositeStatus, SWT.NULL);
		labelStatus.setText("Ready");
		labelStatus.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		final ProgressBar progressBar = new ProgressBar(compositeStatus,
				SWT.SMOOTH);

		// Adds tool bar items using actions.
		final Action actionBackward = new Action("&Back", ImageDescriptor
				.createFromFile(HelpBrowser.class, "/images/go-previous.png")) {
			public void run() {
				browser.back();
			}
		};
		actionBackward.setEnabled(true); // action is disabled at start up.

		final Action actionForward = new Action("&Forward", ImageDescriptor
				.createFromFile(HelpBrowser.class, "/images/go-next.png")) {
			public void run() {
				browser.forward();
			}
		};
		actionForward.setEnabled(true); // action is disabled at start up.

		Action actionStop = new Action("&Stop", ImageDescriptor.createFromFile(
				HelpBrowser.class, "/images/process-stop.png")) {
			public void run() {
				browser.stop();
			}
		};

		Action actionRefresh = new Action("&Refresh", ImageDescriptor
				.createFromFile(HelpBrowser.class, "/images/view-refresh.png")) {
			public void run() {
				browser.refresh();
			}
		};

		Action actionHome = new Action("&Home", ImageDescriptor.createFromFile(
				HelpBrowser.class, "/images/go-home.png")) {
			public void run() {
				browser.setUrl(tree.getHome());
			}
		};

		manager.add(actionBackward);
		manager.add(actionForward);
		manager.add(actionStop);
		manager.add(actionRefresh);
		manager.add(actionHome);
		manager.update(true);
		toolBar.pack();

		browser.addProgressListener(new ProgressListener() {
			public void changed(ProgressEvent event) {
				progressBar.setMaximum(event.total);
				progressBar.setSelection(event.current);
			}

			public void completed(ProgressEvent event) {
				progressBar.setSelection(0);
			}
		});

		browser.addStatusTextListener(new StatusTextListener() {
			public void changed(StatusTextEvent event) {
				labelStatus.setText(event.text);
			}
		});

		browser.addTitleListener(new TitleListener() {
			public void changed(TitleEvent event) {
				shell.setText(event.title + " - powered by SWT");
			}
		});

		browser.addOpenWindowListener(new OpenWindowListener() {

			@Override
			public void open(WindowEvent we) {
				we.browser = browser;
			}
		});

		form.setWeights(new int[] { 15, 85 });
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();

		}
		System.out.println("before clear url");
		tree.clearNodes();

	}

	public void setUrl(String url) {
		System.out.println(url);
		browser.setUrl(url);

	}

}
