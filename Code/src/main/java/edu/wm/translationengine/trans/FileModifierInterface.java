package edu.wm.translationengine.trans;

import java.io.IOException;

public interface FileModifierInterface {
	
	public void setupFileImports() throws IOException;
	
	public void setupTestMethodHeader(String packageName, String mainActivity);
	
	public void closeTestMethod();
}
