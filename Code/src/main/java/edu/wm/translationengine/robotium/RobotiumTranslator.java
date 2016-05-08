package edu.wm.translationengine.robotium;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.wm.translationengine.classes.StepTestCase;
import edu.wm.translationengine.classes.TestCase;
import edu.wm.translationengine.trans.GenericTranslator;

/**
 * Translator for Robotium. Writes test cases to a .java file.
 */
public class RobotiumTranslator extends GenericTranslator {

	/** The main activity name. */
	public static String mainActivityName;
	
	/** The package name. */
	public static String packageName;
	
	/** The file modifier, which specifies how the test file is formatted. */
	private RobotiumFileModifier fileModifier;
	
	/** The robotium checker, which checks whether the input JSON file has correct formatting as well
	 * as enough info to translate. */
	private RobotiumChecker robotiumChecker;
	
	/** The functions class, which provides a set of methods to specify different action commands. */
	private RobotiumFunctions functions;
	
	/** The extra bundle, which contains helper methods or other extra methods to append to the test file. */
	private List<String> extraBundle;
	
	/**
	 * Instantiates a new robotium translator.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public RobotiumTranslator() throws IOException {
		super();
	}
	
	/**
	 * Loops through the test case, checks the input file,
	 * writes code needed to generate the command, and then also
	 * writes to an output file.
	 * 
	 * @testCase an input test case, containing a series of UI commands
	 */
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
	
	/**
	 * Adds the extra methods to append at the end of the test file.
	 *
	 * @param bundle a string representation of an extra method
	 */
	public void addExtras(String bundle) {
		extraBundle.add(bundle);
	}
	
	/**
	 * Gets the package name.
	 *
	 * @return the package name
	 */
	public String getPackageName() {
		return packageName;
	}
}