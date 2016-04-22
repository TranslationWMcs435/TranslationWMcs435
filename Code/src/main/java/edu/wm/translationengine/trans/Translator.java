package edu.wm.translationengine.trans;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import edu.wm.translationengine.classes.TestCase;

/*
 * This is the parent class of the translators for each environment.
 */
public interface Translator {

	// The main function of the iterator. Loops through the TestCase and saves text to toWrite
	public void steps_iterator(TestCase tc)  throws IOException;
	
	// Just writes what is held in toWrite. No need to override.
	public void writeToFile() throws IOException;
	
	// Closes the file.
	public void closeFile() throws IOException;
	
	//Change the file to which we are exporting.
	public void setFile(String f) throws IOException;
	

}
