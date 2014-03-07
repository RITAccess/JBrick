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
	
}
