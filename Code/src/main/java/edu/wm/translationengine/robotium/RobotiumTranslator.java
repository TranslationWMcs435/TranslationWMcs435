package edu.wm.translationengine.robotium;

import java.io.IOException;
import java.util.ArrayList;

import edu.wm.translationengine.classes.TestCase;
import edu.wm.translationengine.trans.GenericTranslator;

/**
 * Translator for Robotium. Writes test cases to a .java file.
 *
 */
public class RobotiumTranslator extends GenericTranslator {

	private String mainActivityName;
	private String packageName;
	
	public RobotiumTranslator() throws IOException {
		super();
	}
	
	@Override
	public void steps_iterator(TestCase testCase) throws IOException { 
		
		mainActivityName = testCase.getMainActivity();
		packageName = testCase.getPackageName();
		
		RobotiumFileModifier fileModifier = new RobotiumFileModifier(packageName, mainActivityName, testCase.getSteps());
		fileModifier.setupFileImports();
		fileModifier.setupTestMethodHeader();
		fileModifier.closeTestMethod();
	}
	
	public void writeToFile() throws IOException {
		
		writeToFile(toWrite);
	}

	public void writeToFile(ArrayList<String> al) throws IOException {
		
		for(int i = 0; i < toWrite.size(); i++){
			bw.write(toWrite.get(i));
		}
	}

}