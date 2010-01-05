package com.jbricx.help;


import java.io.File;
import java.io.FilenameFilter;
import java.net.MalformedURLException;
import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTError;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

public class HelpBrowser{
 
	private Display display = new Display();
	private final Shell shell = new Shell(display);
	private Browser browser;
	private ArrayList<String> urls = new ArrayList<String>();
	private String[] titles;
	private int index;
	
	
	public HelpBrowser(){
		
		buildUrls();
	}

	@SuppressWarnings("deprecation")
	private void buildUrls(){
		urls.add("www.google.com");
		DirectoryDialog dialog = new DirectoryDialog(shell);
	      String folder = "C:\\Users\\spencer\\workspace\\jbrick\\help\\html";//dialog.open();
	      System.out.println(folder);
	      if (folder == null) return;
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
	         } catch (MalformedURLException ex) {}
	      }
		
	}
	
	public void show(){
		
		shell.setText("Jbricx Help Browser");
		shell.setLayout(new GridLayout());
		
		Composite compTools = new Composite(shell,SWT.NONE);
		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		compTools.setLayoutData(data);
		compTools.setLayout(new GridLayout(2,false));
		
		
		Composite compWin = new Composite(shell,SWT.NONE);
		data = new GridData(GridData.FILL_BOTH);
		compWin.setLayoutData(data);
		compWin.setLayout(new FillLayout());
		final SashForm form = new SashForm(compWin, SWT.HORIZONTAL);
		form.setLayout(new FillLayout());

		
		final List list = new List(form,SWT.SIMPLE);
		for (String s: urls){
			list.add(s);
		}
		
		

		
		try{
			browser = new Browser(form, SWT.NONE);
		}
		catch (SWTError e) {
			MessageBox msg = new MessageBox(shell, SWT.ICON_ERROR | SWT.OK);
			msg.setMessage("Help Browser cannot be initialized.");
			msg.setText("Exit");
			msg.open();
		}
		
		
		list.addListener(SWT.Selection, new Listener(){
			@Override
			public void handleEvent(Event arg0) {
				int index = list.getSelectionIndex();
				browser.setUrl(urls.get(index));
				
			}
		});
		
		
		shell.open();
		while (!shell.isDisposed ()) {
			if (!display.readAndDispatch ()) display.sleep ();
		}
		display.dispose();
	}
	
	public static void main(String[] args) {
		HelpBrowser hb = new HelpBrowser();
		hb.show();
		
		
		
	}
}
