package edu.wm.translationengine.robotium;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.wm.translationengine.classes.StepTestCase;
import edu.wm.translationengine.classes.TestCase;
import edu.wm.translationengine.trans.GenericTranslator;

/**
 * Translator for Robotium. Writes test cases to a .java file.
 *
 */
public class RobotiumTranslator extends GenericTranslator {

	public static String mainActivityName;
	public static String packageName;
	private RobotiumFileModifier fileModifier;
	private RobotiumChecker robotiumChecker;
	private RobotiumFunctions functions;
	
	private List<String> extraBundle;
	
	public RobotiumTranslator() throws IOException {
		super();
	}
	
	public void steps_iterator(TestCase testCase) throws IOException { 
		
		extraBundle = new ArrayList<String>();
		
		robotiumChecker = new RobotiumChecker();
		robotiumChecker.runCheck(testCase);
		
		mainActivityName = testCase.getMainActivity();
		packageName = testCase.getPackageName();
		
		fileModifier = new RobotiumFileModifier(packageName, mainActivityName, testCase.getSteps());
		fileModifier.setupFileImports();
		fileModifier.setupTestMethodHeader();
		
		List<StepTestCase> stepTestCases = testCase.getSteps();
		functions = new RobotiumFunctions(extraBundle);
		
		for(int i = 0; i < stepTestCases.size(); i++){
			
			StepTestCase cur = stepTestCases.get(i);
			
			//if(isInArray(cur, robotiumChecker.getCasesWithErrors())) {
			//	RobotiumTranslator.toWrite.add("\n\t\t// Skipping case because it failed the checker...");
			//	continue;
			//}
			
			try {
				functions.writeStep(cur);
			} catch (Exception e) {
				e.printStackTrace();
			}	
		}
		RobotiumTranslator.toWrite.add("\t}\n\n");
		
		fileModifier.closeTestMethod();
		fileModifier.closeClass(extraBundle);
	}
	
	public void addExtras(String bundle) {
		extraBundle.add(bundle);
	}
	
	public String getPackageName() {
		return packageName;
	}
}