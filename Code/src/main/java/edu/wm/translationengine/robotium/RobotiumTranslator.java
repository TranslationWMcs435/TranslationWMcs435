package edu.wm.translationengine.robotium;

import java.io.IOException;
import java.util.List;

import edu.wm.translationengine.classes.StepTestCase;
import edu.wm.translationengine.classes.TestCase;
import edu.wm.translationengine.trans.GenericTranslator;

/**
 * Translator for Robotium. Writes test cases to a .java file.
 *
 */
public class RobotiumTranslator extends GenericTranslator {

	private String mainActivityName;
	private String packageName;
	private RobotiumFileModifier fileModifier;
	
	public RobotiumTranslator() throws IOException {
		super();
	}
	
	public void steps_iterator(TestCase testCase) throws IOException { 
		
		mainActivityName = testCase.getMainActivity();
		packageName = testCase.getPackageName();
		
		fileModifier = new RobotiumFileModifier(packageName, mainActivityName, testCase.getSteps());
		fileModifier.setupFileImports();
		fileModifier.setupTestMethodHeader();
		
		List<StepTestCase> stepTestCases = testCase.getSteps();
		RobotiumFunctions functions = new RobotiumFunctions();
		
		for(int i = 0; i < stepTestCases.size(); i++){
			
			StepTestCase cur = stepTestCases.get(i);
			
			try {
				functions.writeStep(cur);
			} catch (Exception e) {
				e.printStackTrace();
			}	

		}
		RobotiumTranslator.toWrite.add("\t}\n\n");
		
		fileModifier.closeTestMethod();
	}

}