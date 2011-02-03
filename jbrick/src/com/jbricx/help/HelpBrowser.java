package com.jbricx.help;

import java.io.File;
import java.io.FilenameFilter;
import java.net.MalformedURLException;
import java.util.ArrayList;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.window.ApplicationWindow;
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
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Text;

public class HelpBrowser extends ApplicationWindow {

	private Browser browser;
	private ArrayList<String> urls = new ArrayList<String>();
	private int x = 0;
	private boolean flag = false;
	private Label labelStatus;
	private Text textLocation;
	private CustomTree tree;

	

	public HelpBrowser() {
		super(Display.getCurrent().getActiveShell());
		addToolBar(0);
	}

	ArrayList<String> urlList = new ArrayList<String>();

	@SuppressWarnings("deprecation")
	private void buildUrls() {
		
		System.out.println("build Url");
		
		if (!flag) {
			urls.add("www.google.com");
			urlList.add("www.google.com");
			DirectoryDialog dialog = new DirectoryDialog(getShell());
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

	@Override
	protected ToolBarManager createToolBarManager(int style) {
	  final ToolBarManager manager = new ToolBarManager(SWT.FLAT | SWT.RIGHT);
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
        browser.setUrl("");
        x = 1;
        // browser.stop();
      }
    };

    Action actionRefresh = new Action("&Refresh", ImageDescriptor
        .createFromFile(HelpBrowser.class, "/images/view-refresh.png")) {
      public void run() {
        if (x==1){
            browser.back();
        }else{
            browser.refresh();  
           }
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
	  
	  return manager;
	}

	@Override
	protected Control createContents(final Composite parent) {
	  buildUrls();
	  getShell().setText("Jbricx Help Browser");
	  getShell().setLayout(new GridLayout());


		Composite compTools = new Composite(parent, SWT.NONE);
		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		compTools.setLayoutData(data);
		compTools.setLayout(new GridLayout(2, false));

		Composite compWin = new Composite(compTools, SWT.NONE);
		data = new GridData(GridData.FILL_BOTH);

		compWin.setLayoutData(data);
		compWin.setLayout(new FillLayout());
		final SashForm form = new SashForm(compWin, SWT.HORIZONTAL);

		form.setLayout(new FillLayout());

		tree = new CustomTree(form, this);
		browser = new Browser(form, SWT.NONE);

		Composite compositeStatus = new Composite(parent, SWT.NULL);
		compositeStatus.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		compositeStatus.setLayout(new GridLayout(2, false));

		labelStatus = new Label(compositeStatus, SWT.NULL);
		labelStatus.setText("Ready");
		labelStatus.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		final ProgressBar progressBar = new ProgressBar(compositeStatus, SWT.SMOOTH);


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
//        HelpBrowser.this.shell.setText(event.title + " - powered by SWT");
      }
    });

    browser.addOpenWindowListener(new OpenWindowListener() {
      @Override
      public void open(WindowEvent we) {
        we.browser = browser;
      }
    });

    //form.setWeights(new int[] { 15, 85 });
    form.setSize(750, 500);
    tree.clearNodes();
	  return compTools;
	}

	public void setUrl(String url) {
		browser.setUrl(url);
	}

}
