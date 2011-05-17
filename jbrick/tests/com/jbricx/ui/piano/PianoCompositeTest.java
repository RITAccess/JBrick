package com.jbricx.ui.piano;

import junit.framework.TestCase;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import javax.swing.SwingUtilities;
import org.junit.After;
import org.junit.Before;
import java.awt.Robot;
import java.awt.event.InputEvent;

public class PianoCompositeTest extends TestCase {
	protected Display display = Display.getDefault();
	protected Shell shell = null;
	PianoController controller = null;
	PianoComposite piano = null;
	Robot robot = null;

	protected void setUp() throws Exception {
		super.setUp();
		newShell();
		robot = new Robot();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
		display.syncExec(new Runnable() {
			public void run() {
				disposeShell();
			}
		});
	}

	protected void newShell() {
		disposeShell();
		shell = new Shell(display, SWT.SHELL_TRIM);
		shell.setLayout(new FillLayout());
		shell.open();
	}

	private void disposeShell() {
		if (shell != null) {
			shell.dispose();
			shell = null;
		}
	}
	
	public void testPianoOpen() throws Exception {
		controller = new PianoController(shell, "C", true);
		piano = new PianoComposite(shell, 0, controller);
		Control[] controls = piano.getChildren();
		for(Control c : controls)
		{
			if(c.getClass() == Class.forName("org.eclipse.swt.widgets.Button"))
			{
				System.out.println("I'm a button");
				Button btn = (Button)c;
				System.out.println(btn.getText());
				Point screenPoint = btn.getLocation();
				robot.mouseMove(screenPoint.x, screenPoint.y);
				robot.mousePress(InputEvent.BUTTON1_MASK);
	            robot.mouseRelease(InputEvent.BUTTON1_MASK);
			}	
		}
		
		Listener[] listeners = piano.getListeners(1);
	}
	
	public Point pointToScreen(Control c){
		Point pos = c.getLocation();
		if(c.getParent() != null){
			Point parentPos = pointToScreen(c.getParent());
			return new Point(parentPos.x + pos.x, parentPos.y + pos.y);
		}
		else
			return pos;
	}
}
