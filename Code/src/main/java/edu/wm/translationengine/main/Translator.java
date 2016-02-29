package edu.wm.translationengine.main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import edu.wm.translationengine.classes.TestCase;

/*
 * This is the parent class of the translators for each environment.
 */
public interface Translator {

	void steps_iterator(TestCase tc)  throws IOException;

	void writeToFile(ArrayList<String> al) throws IOException;
	
	public void writeToFile() throws IOException;
	
	void setupFile() throws IOException;
	
	public void closeFile() throws IOException;
	
	public void setFile(File f) throws IOException;
	
	
	

}
