package edu.wm.translationengine.trans;

import java.io.IOException;
import java.util.ArrayList;

public interface FileModifierInterface {
	
	public void setupFileImports() throws IOException;
	
	public void setupTestMethodHeader();
	
	public void closeTestMethod();
	
	public void writeToFile(ArrayList<String> al) throws IOException;
	
	public void writeToFile() throws IOException;
	
	
	
}
