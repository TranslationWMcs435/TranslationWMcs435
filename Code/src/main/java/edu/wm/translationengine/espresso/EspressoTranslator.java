package edu.wm.translationengine.espresso;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.wm.translationengine.classes.*;
import edu.wm.translationengine.main.*;
import edu.wm.translationengine.trans.GenericTranslator;
import edu.wm.translationengine.uiautomator.UiAutomatorFileModifier;

/**
 * This is the EspressoTranslator class, it takes TestCase objects and creates Espresso
 * commands that correspond to those TestCase objects. It goes through all the stepTestCases
 * in the TestCase to generate all the commands that correspond to each stepTestCase. A
 * stepTestCase describes an action in JSON formatting.
 * 
 * @author Nathan Chen
 *
 */
public class EspressoTranslator extends GenericTranslator {
	private EspressoFunctions ef;
	public static String appName;
	public static String packageName; 
	public static String mainActivity; 
	
	/**
	 * Constructor for the EspressoTranslator class. Initializes the EspressoFileModifier
	 * and EspressoFunctions for use.
	 * 
	 * @throws IOException 
	 */
	public EspressoTranslator() throws IOException{
		super();
		fm = new EspressoFileModifier();
		ef = new EspressoFunctions();
	}
	
	/**
	 * Iterates through all the StepTestCase objects in the stepTestCases List and 
	 * calls the espresso_switcher(args) method in EspressoFunctions in order to
	 * get the Strings which correspond to the actions listed in the stepTestCases.
	 *  
	 * @param testCase The current TestCase object being looked at
	 * @throws IOException 
	 */
	public void steps_iterator(TestCase testCase) throws IOException{

		appName = testCase.getAppName();
		packageName = testCase.getPackageName();
		mainActivity = testCase.getMainActivity().substring(packageName.length() + 1);

		System.out.println(fout);
		
		List<StepTestCase> stepTestCases = testCase.getSteps();
		fm.setupFileImports();
		fm.setupTestMethodHeader(packageName, mainActivity);
		for(int i = 0; i < stepTestCases.size(); i++){
			StepTestCase cur = stepTestCases.get(i);
			ef.espresso_switcher(cur.getAction(), cur);
		}
		
		fm.closeTestMethod();
	}
}
