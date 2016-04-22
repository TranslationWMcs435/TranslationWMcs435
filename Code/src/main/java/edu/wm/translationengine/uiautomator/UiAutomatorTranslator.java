package edu.wm.translationengine.uiautomator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.wm.translationengine.classes.StepTestCase;
import edu.wm.translationengine.classes.TestCase;
import edu.wm.translationengine.espresso.EspressoFileModifier;
import edu.wm.translationengine.espresso.EspressoFunctions;
import edu.wm.translationengine.trans.GenericTranslator;

public class UiAutomatorTranslator extends GenericTranslator{
	private UiAutomatorFunctions uf;
	public static String appName;
	public static String packageName; 
	public static String mainActivity; 
	
	public UiAutomatorTranslator() throws IOException {
		super();
		fm = new UiAutomatorFileModifier();
		uf = new UiAutomatorFunctions();	}

	
	/**
	 * Iterates through all the StepTestCase objects in the stepTestCases List.
	 * @param testCase The current TestCase object being looked at
	 * @throws IOException 
	 */
	public void steps_iterator(TestCase testCase) throws IOException{
		appName = testCase.getAppName();
		if(testCase.getAppName() == null){
			System.out.println("App Name is null\n");
		}
		packageName = testCase.getPackageName();
		if(testCase.getPackageName() == null){
			System.out.println("Package Name is null\n");
		}	
		mainActivity = testCase.getMainActivity().substring(packageName.length() + 1);
		if(testCase.getMainActivity() == null){
			System.out.println("Main Activity is null\n");
		}
		System.out.println(fout);
		
		List<StepTestCase> stepTestCases = testCase.getSteps();
		fm.setupFileImports();
		fm.setupTestMethodHeader(packageName, mainActivity);
		
		for(int i = 0; i < stepTestCases.size(); i++){
			StepTestCase cur = stepTestCases.get(i);
			try {
				uf.uiautomator_switcher(cur.getAction(), cur);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		fm.closeTestMethod();
	}


}
