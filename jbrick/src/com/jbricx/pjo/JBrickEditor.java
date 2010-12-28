package com.jbricx.pjo;

import com.jbricx.ui.MainWindow;

/**
 * This class demonstrates TextViewer and Document.
 */
public class JBrickEditor {
	// Set up the name of the partitioner

	public static final String JBRICK_PARTITIONING = "jbrick_partitioning";

	/**
	 * Runs the application
	 */
	public void run() {
		System.out.println("JbrickEditor:Run");
		MainWindow mainWindow = new MainWindow();
    mainWindow.run();
	}

	/**
	 * The application entry point
	 * 
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String[] args) {
		//ExitStatus e = BrickCreator.createBrick().getBatteryLevel();
		new JBrickEditor().run();
	}
}
