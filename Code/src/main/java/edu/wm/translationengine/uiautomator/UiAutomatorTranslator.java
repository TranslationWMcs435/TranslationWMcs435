package edu.wm.translationengine.uiautomator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.wm.translationengine.classes.StepTestCase;
import edu.wm.translationengine.classes.TestCase;
import edu.wm.translationengine.espresso.EspressoFunctions;
import edu.wm.translationengine.trans.GenericTranslator;

public class UiAutomatorTranslator extends GenericTranslator{
	private UiAutomatorFunctions uf;
	public static String appName;
	public static String packageName; 
	public static String mainActivity; 
	
	public UiAutomatorTranslator() throws IOException {
		super();
		uf = new UiAutomatorFunctions();	}

	public void writeToFile() throws IOException {
		ArrayList<String> al = toWrite;
		for(int i = 0; i < al.size(); i++){
			bw.write(al.get(i));
		}
	}
	
	/**
	 * Iterates through all the StepTestCase objects in the stepTestCases List.
	 * @param testCase The current TestCase object being looked at
	 * @throws IOException 
	 */
	public void steps_iterator(TestCase testCase) throws IOException{
		appName = testCase.getAppName();
		packageName = testCase.getPackageName();
		mainActivity = testCase.getMainActivity().substring(packageName.length() + 1);
		System.out.println(fout);
		
		List<StepTestCase> stepTestCases = testCase.getSteps();
		
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
