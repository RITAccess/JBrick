package jbrick.tests;

public class ContainerFS {

	/**
	 * TC101 - Default Panes on Startup
	 */
	public static boolean TC101() {
		
		// 1. User opens the JBrick application
		// => The Code Frame has one file open in the code frame (center) and a status pane (bottom)
		// (No file has been opened before hand, this is the equivalent to opening the application for the first time.)
		
		//TODO open the application
		//TODO check the frame positions
		
		return false;
	}
	
	/**
	 * TC102 - Reset Main Window View
	 */
	public static boolean TC102() {
		
		// 1. User opens the JBrick application 
		// => The Code Frame has one file open in the code frame (center) and a status pane (bottom)
		// (No file has been opened before hand, this is the equivalent to opening the application for the first time.)
		
		//TODO open the application
		//TODO check the frame position 
		
		// 2. User rearranges frame sizes
		// => The size of the code frame and status frame are different than their defaults
		// (These may be changed with the 'maximize *' options in the view menu)
		
		//TODO hit the "maximize" button in the view menu options
		//TODO check that the frames changed position
		
		// 3. User selects "Reset View" in view menu
		// => The size of the frames return to their default size.
		
		//TODO select the "reset-view" option in the view menu
		//TODO check the size and position of frames
		
		return false;
	}
	
	/**
	 * TC103 - Open the Expander View
	 */
	public static boolean TC103() {
		
		// 1. User opens the JBrick application 
		// => The Code Frame has one file open in the code frame (center) and a status pane (bottom)
		// (No file has been opened before hand, this is the equivalent to opening the application for the first time.)
		
		//TODO open the application
		//TODO check the frame positions
		
		// 2. User selects the "Show/Hide File Viewer"
		// => The file viewer is opened on the left of the code and status frame. The viewer shows the files and folders in the workspace directory set in the preferences.
		
		//TODO select the file viewer menu option
		//TODO check that the frame is opened and has a width value
		
		return false;
	}
	
}
