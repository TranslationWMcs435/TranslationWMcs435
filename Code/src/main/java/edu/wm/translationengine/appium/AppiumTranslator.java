package edu.wm.translationengine.appium;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.wm.translationengine.classes.*;
import edu.wm.translationengine.main.*;
import edu.wm.translationengine.trans.*;

public class AppiumTranslator extends GenericTranslator{
	private AppiumFunctions af;
	public static String appName;
	public static String packageName; 
	public static String mainActivity;
	public AppiumTranslator() throws IOException {
		super();
		fm = new AppiumFileModifier();
		af = new AppiumFunctions();
	}
	
//	public void writeToFile() throws IOException {
//		// TODO copy shit
//		ArrayList<String> al = toWrite;
//		for(int i = 0; i < al.size(); i++){
//			bw.write(al.get(i));
//		}
//	}
	
	
	/* write to the file generating a test case suitable for use with the appium server*/
	public void steps_iterator(TestCase testCase) throws IOException{
		appName = testCase.getAppName();
		packageName = testCase.getPackageName();
		mainActivity = testCase.getMainActivity().substring(packageName.length() + 1);
		List <StepTestCase> stepTestCases = testCase.getSteps();

		//loop through the parsed input to generate the good stuff
		fm.setupFileImports();
		fm.setupTestMethodHeader(packageName, mainActivity);
		for(int i=0; i < stepTestCases.size(); i++){
			StepTestCase cur = stepTestCases.get(i);
			af.appium_switcher(cur.getAction(), cur);
		}
		fm.closeTestMethod();

	}
	
}
