package edu.wm.translationengine.trans;

import java.io.IOException;

/**
 * This is the FileModifier interface, it is the centralized hub for formatting the output 
 * test file. It sets up the immediately necessary imports and can also set necessary text at the beginning of
 * every test. It then closes the TestMethod.
 * 
 * Essentially, if it's a string you'll always need, here's where you put it.
 * 
 * @modified_by Mark Hutchens
 *
 */
public interface FileModifierInterface {
	
	//Place all your import statements into toWrite. Call this first.
	//e.g. [YourTranslator].toWrite.add("import ...;\n");
	public void setupFileImports() throws IOException;
	
	//Place all your test/function initialization into toWrite. Call this function inside your loops.
	public void setupTestMethodHeader(String packageName, String mainActivity);
	
	//Whatever you need to end all your tests. Probably just a couple close brackets. Call this last.
	//e.g. [YourTranslator].toWrite.add("\t }\n }");
	public void closeTestMethod();

}
