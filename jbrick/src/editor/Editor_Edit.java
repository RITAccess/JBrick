package editor;

import code.BorderLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;

public class Editor_Edit {
	
	public void displayEditor(Shell OwnerShell)  {
		// create the window
		final Shell thisshell = new Shell(OwnerShell);

		thisshell.setLayout(new BorderLayout());
		// set the application title
		// TODO: Get OpenFile Name 

		Text editorTxt = new Text(thisshell, SWT.WRAP 
									| SWT.MULTI 
									| SWT.BORDER
									| SWT.H_SCROLL 
									| SWT.V_SCROLL);
									editorTxt.setLayoutData(new BorderLayout.BorderData(BorderLayout.CENTER));
		
		thisshell.setText("FileName");
		thisshell.open();

		// fileMenu.dispose();
		// editMenu.dispose();
		//		display.dispose();
		//MessageBox box = new MessageBox(shell, SWT.OK | SWT.CANCEL);
		//box.setText("Title");
		//box.setMessage("Message");
		//int ret = box.open();
		//Text editorTxt = new Text(shell, SWT.WRAP 
		//	| SWT.MULTI 
		//							| SWT.BORDER
		//							| SWT.H_SCROLL 
										//| SWT.V_SCROLL);
										//editorTxt.setLayoutData(new BorderLayout.BorderData(BorderLayout.CENTER));
		//shell.redraw();
	}
	
	public void undo() {
	
	}

	public void redo() {
	
	}

	public void cut() {
	
	}

	public void copy() {
	
	}

	public void paste() {
	
	}

	public void selectAll() {
	
	}

	public void find() {
	
	}

	public void replace() {
	
	}

	public void Goto() {
	
	}

}
