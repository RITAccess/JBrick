package jbrick.tests;

public class TypingFS {

	/**
	 * TC301 - Line Number Generation
	 */
	public static boolean TC301() {
		
		// 1. User opens JBrick
		// => JBrick opens with the default configuration, "New File 1" is visible in the code frame, there is a single line number on the left marking the beginning of the file.
		
		//TODO open the application
		//TODO check that the file is "New File 1" and that there is only one line at the left marking the beginning of the file
		
		// 2. User types text in the first line in the Code Frame
		// => No new line numbers are generated
		
		//TODO write text in the code frame
		//TODO check that there is only one line in the file still
		
		// 3. The user types in a mix of characters and newlines.
		// => Characters on the same line do not generate new lines, newline characters cause the cursor to make a new line
		
		//TODO write a block of text in the code frame that extends one line
		//TODO check that characters on the same line do NOT make new lines; newline characters do make new lines appear 
		
		// 4. User deletes lines in the frame
		// => The Line numbers are appropriately updated, there are no code lines for trailing empty (no characters or newlines) lines
		
		//TODO delete characters in the code frame
		//TODO check that there are no trailing empty line-numbers
		
		return false;
	}
	
	/**
	 * TC302 - Code Completion Test
	 */
	public static boolean TC302() {
		
		// 1. User opens JBrick
		// => JBrick opens with the default configuration, "New File 1" is visible in the code frame, there is a single line number on the left marking the beginning of the file.
		
		//TODO open the application
		//TODO check that the file is "New File 1" and that there is only one line at the left marking the beginning of the file
		
		// 2. User types the first part (minimum 3) of a word and pushes ctrl + space
		// => A small dialogue opens near the word, listing all possible words.
		
		//TODO type in the first part of a code-completable word
		//TODO check that the code-complete window appears.
		
		// 3. User types a character
		// => The dialogue of words is filtered based on updated word.
		
		//TODO type a character in the code frame on the code-completable word
		//TODO check that the list changes to show words valid for the word being typed
		
		// 4. User deletes a character
		// => The dialogue of words is filtered based on updated word.
		
		//TODO delete the character added in step 3
		//TODO check that the list reverts back to what was seen in step 2
		
		// 5. User navigates up and down selection of words
		// => The dialogue shows selected word based on input.
		
		//TODO press the up and down key
		//TODO check the highlighted word
		
		// 6. User selects a word with enter/ return
		// => The word is finished based on selection.
		
		//TODO press the enter key
		//TODO check that the selected word is now in the code-frame
		
		return false;
	}
}
