package com.jbricx.ui.piano;

import java.awt.Point;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.event.InputEvent;

public class PianoCompositeTest extends TestCase {
	protected Display display = Display.getDefault();
	protected Shell shell = null;
	Robot robot = null;
	
	public void testPianoRecording() throws Exception {
		ApplicationThread appThread = new ApplicationThread();
		appThread.start();
		robot = new Robot();
		Thread.sleep(1000);
		assertTrue(appThread.isAlive());
		
		robot.mouseMove(590, 190);
		Thread.sleep(1000);	
		assertTrue(appThread.isAlive());
		robot.mousePress(InputEvent.BUTTON1_MASK);
		Thread.sleep(500);
		robot.mouseRelease(InputEvent.BUTTON1_MASK);
		robot.mouseMove(900, 190);
		Thread.sleep(1000);
		assertTrue(appThread.isAlive());
		robot.mousePress(InputEvent.BUTTON1_MASK);
		Thread.sleep(500);
		robot.mouseRelease(InputEvent.BUTTON1_MASK);
		robot.mouseMove(545, 330);
		Thread.sleep(1000);
		assertTrue(appThread.isAlive());
		robot.mousePress(InputEvent.BUTTON1_MASK);
		Thread.sleep(500);
		robot.mouseRelease(InputEvent.BUTTON1_MASK);
		assertTrue(appThread.isAlive());
		Thread.sleep(1000);
		assertTrue(appThread.isAlive());
		robot.mousePress(InputEvent.BUTTON1_MASK);
		Thread.sleep(500);
		robot.mouseRelease(InputEvent.BUTTON1_MASK);
		robot.mouseMove(680, 190);
		Thread.sleep(1000);
		assertTrue(appThread.isAlive());
		robot.mousePress(InputEvent.BUTTON1_MASK);
		Thread.sleep(500);
		robot.mouseRelease(InputEvent.BUTTON1_MASK);
		robot.mouseMove(775, 190);
		Thread.sleep(1000);
		assertTrue(appThread.isAlive());
		robot.mousePress(InputEvent.BUTTON1_MASK);
		Thread.sleep(500);
		robot.mouseRelease(InputEvent.BUTTON1_MASK);
		robot.mouseMove(1040, 325);
		Thread.sleep(1000);
		assertTrue(appThread.isAlive());
		robot.mousePress(InputEvent.BUTTON1_MASK);
		Thread.sleep(500);
		robot.mouseRelease(InputEvent.BUTTON1_MASK);
		assertTrue(appThread.isAlive());
		Thread.sleep(1000);
		robot.mousePress(InputEvent.BUTTON1_MASK);
		robot.mouseRelease(InputEvent.BUTTON1_MASK);
		assertTrue(appThread.isAlive());
		robot.mouseMove(1070, 12);
		Thread.sleep(500);
		robot.mousePress(InputEvent.BUTTON1_MASK);
		Thread.sleep(500);
		robot.mouseRelease(InputEvent.BUTTON1_MASK);	

		String clipContents = (String)Toolkit.getDefaultToolkit()
        .getSystemClipboard().getData(DataFlavor.stringFlavor);
		assertEquals(clipContents, "PlayTone(349, 250);\r\nWait(125);\r\nPlayTone(698, 250);\r\nWait(125);\r\nPlayTone(440, 1000);\r\nWait(500);\r\nPlayTone(523, 1000);\r\nWait(500);\r\n");
	
		appThread.join();
	}
	
	class ApplicationThread extends Thread {
		PianoController controller = null;
		PianoComposite piano = null;

		public void run() {

			display = new Display();
			shell = new Shell(display, SWT.SHELL_TRIM);
			shell.setBounds(400,0, 700, 520);
			//ApplicationWindow win = new ApplicationWindow(shell);			
			//win.setBlockOnOpen(true);
			//win.open();
			shell.open();
			try {
				controller = new PianoController(shell, "C", true);
			} catch (NoteNotFoundException e) {
				e.printStackTrace();
			}
			piano = new PianoComposite(shell, 0, controller);
			piano.setVisible(true);
			while (!shell.isDisposed()) {
				if (!display.readAndDispatch())
					display.sleep();
			}
		}
	};
	
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
